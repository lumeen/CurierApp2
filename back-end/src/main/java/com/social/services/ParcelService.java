package com.social.services;


import com.social.dao.ParcelRepository;
import com.social.entities.Parcel;
import com.social.entities.User;
import com.social.model.CourierWithDistanceToParcel;
import com.social.model.ParcelConfirmCollect;
import com.social.model.ParcelPickUpConfirmation;
import com.social.model.ParcelRequest;
import com.social.model.ParcelVerifyRequest;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ParcelService {


    private final ParcelRepository parcelRepository;
    private final CourierService courierService;
    private final UserService userService;
    private final Haversine haversine;
    private final AndroidPushNotificationsService androidPushNotificationsService;
    private final NotificationProvider notificationProvider;

    public List<Parcel> updateParcels(List<Parcel> parcels) {
        List<Long> parcelIds = parcels.stream().map(parcel -> parcel.getId()).collect(Collectors.toList());
        List<Parcel> updatedEntities = parcelRepository.findByCarId(parcels.get(0).getCar().getId());
        parcels.forEach(r -> r.setStatus("IN QUEUE"));
        parcels.forEach(p ->
        {
            p.setPriority(parcels.indexOf(p));
        });
        updatedEntities.stream().filter(p -> !parcelIds.contains(p.getId())).collect(Collectors.toList()).forEach(r -> {
            r.setPriority(-1);
            r.setStatus("WAINTING");
            r.setCar(null);
        });

        parcels.get(0).setStatus("IN PROGRESS");

        parcelRepository.save(parcels);
        return parcels;

    }

    public List<Parcel> findByCarId(Long id) {
        return parcelRepository.findByCarId(id);
    }

    public List<Parcel> getAllParcels() {
        return parcelRepository.findAll();
    }

    public void createParcel(ParcelRequest parcelRequest) {

        Parcel parcel = Parcel.builder().startLat(parcelRequest.getStartLat()).startLng(parcelRequest.getStartLng())
            .startPhone(parcelRequest.getStartPhone()).endPhone(parcelRequest.getEndPhone())
            .weight(Long.valueOf(parcelRequest.getWeight())).status("unverified").build();
        parcelRepository.save(parcel);
        androidPushNotificationsService.send(notificationProvider
            .createNotification(parcelRequest.getUserName(), "Parcel not verified", "You have new parcel: start",
                new Date() + ": Parcel you want to sand is waiting for verification by reciver"));


    }

    public boolean verify(ParcelVerifyRequest parcelVerifyRequest) throws InterruptedException {
        List<Parcel> a = parcelRepository.findByStartPhoneAndEndPhoneAndStatus(parcelVerifyRequest.getStartPhone(),
            parcelVerifyRequest.getEndPhone(), "unverified");
        Parcel parcel = parcelRepository.findByStartPhoneAndEndPhoneAndStatus(parcelVerifyRequest.getStartPhone(),
            parcelVerifyRequest.getEndPhone(), "unverified").get(0);
        if (parcel != null) {
            parcel.setStatus("verified");
            parcel.setEndLat(parcelVerifyRequest.getEndLat());
            parcel.setEndLng(parcelVerifyRequest.getEndLng());
            User sender = userService.findByPhone(parcelVerifyRequest.getStartPhone());
            User reciver = userService.findByPhone(parcelVerifyRequest.getEndPhone());
            androidPushNotificationsService.send(notificationProvider
                .createNotification(sender.getUsername(), "Parcel not verified", "You have new parcel: start",
                    new Date().toString() +'\n'+ ": Parcel was verified"));
            androidPushNotificationsService.send(notificationProvider
                .createNotification(reciver.getUsername(), "Parcel not verified", "You have new parcel: start",
                    new Date().toString() +'\n'+ "You verified parcel"));
            parcelRepository.save(parcel);
           return findCourierForParcel(parcel);


        }
        return false;

    }


    public List<Parcel> findById(Long id) {
        return parcelRepository.findById(id);
    }


    public void save(Parcel parcel) {

        parcelRepository.save(parcel);
    }

    private boolean findCourierForParcel(Parcel parcel) {

        List<User> activeCouriers = userService.findAll().stream().filter(r -> r.getStatus().equals("READY") && r.getMaxWeight()<=parcel.getWeight())
            .collect(Collectors.toList());
        List<CourierWithDistanceToParcel> courierWithDistanceToParcels = activeCouriers.stream().
            map(c -> new CourierWithDistanceToParcel(c.getId(),
                haversine.distance(c.getLat(), c.getLng(), parcel.getStartLat(), parcel.getStartLng()))).sorted(
            Comparator.comparing(CourierWithDistanceToParcel::getDistance)).collect(Collectors.toList());

        User courier = userService.getUserById(courierWithDistanceToParcels.get(0).getCourierId());
        User sender = userService.findByPhone(parcel.getStartPhone());
        User reciver = userService.findByPhone(parcel.getEndPhone());
        if(activeCouriers.size()==0){
            androidPushNotificationsService.send(notificationProvider
                .createNotification(sender.getUsername(), "Parcel not verified", "You have new parcel: start",
                    new Date().toString() +'\n'+ "Not courier available. Try again"));
            androidPushNotificationsService.send(notificationProvider
                .createNotification(reciver.getUsername(), "Parcel not verified", "You have new parcel: start",
                    new Date().toString() +'\n'+ "Not courier available. Try again"));
            parcel.setStatus("REJECTED");
            parcelRepository.save(parcel);
            return false;
        }

        if (!courierWithDistanceToParcels.isEmpty()) {

            RestTemplate rest = new RestTemplate();
            String start = rest
                .getForObject("https://maps.googleapis.com/maps/api/geocode/json?latlng={lat},{lng}&key={key}",
                    String.class, parcel.getStartLat().toString(), parcel.getStartLng().toString(), UserService.KEY);

            String end = rest
                .getForObject("https://maps.googleapis.com/maps/api/geocode/json?latlng={lat},{lng}&key={key}",
                    String.class, parcel.getEndLat().toString(), parcel.getEndLng().toString(), UserService.KEY);

            JSONObject json = new JSONObject(start);
            JSONArray ja_data = json.getJSONArray("results");
            String startAdress = UserService.stripAccents(ja_data.getJSONObject(0).getString("formatted_address"));
            json = new JSONObject(end);
            JSONArray ja_data2 = json.getJSONArray("results");
            String endAdress = UserService.stripAccents(ja_data2.getJSONObject(0).getString("formatted_address"));
            parcel.setCourierId(courierWithDistanceToParcels.get(0).getCourierId());

            androidPushNotificationsService.send(notificationProvider
                .createNotification(courier.getUsername(), "New parcel", "You have new parcel: start",
                    "You have new parcel to deliver:" + '\n' + "Start: " + (startAdress) + '\n' + "End: " + endAdress + '\n' + "Sender number: "+ sender.getPhone() ));
            androidPushNotificationsService.send(notificationProvider
                .createNotification(sender.getUsername(), "New parcel", "You have new parcel: start",
                    "Courier number is: " + courier.getPhone() ));
            androidPushNotificationsService.send(notificationProvider
                .createNotification(reciver.getUsername(), "New parcel", "You have new parcel: start",
                    "Courier number is: " + courier.getPhone() ));
            courier.setStatus("BUSY");
            parcel.setStatus("READY_TO_PICKED_UP");
            parcelRepository.save(parcel);
            userService.update(courier);

        }
        return true;
    }

    public void confirmPickUp(ParcelPickUpConfirmation parcelPickUpConfirmation) {
        User sender = userService.getUserByUserName(parcelPickUpConfirmation.getSender());

        Parcel parcel = parcelRepository.findByStartPhoneAndStatus(sender.getPhone(), "READY_TO_PICKED_UP").get(0);
        User reciver = userService.findByPhone(parcel.getEndPhone());
        User courier = userService.getUserById(parcel.getCourierId());
        parcel.setStatus("PICKED_UP");
        parcelRepository.save(parcel);

        androidPushNotificationsService.send(notificationProvider
            .createNotification(sender.getUsername(), "Parcel not verified", "You have new parcel: start",
                new Date().toString() +'\n'+  "Parcel was picked up"));
        androidPushNotificationsService.send(notificationProvider
            .createNotification(reciver.getUsername(), "Parcel not verified", "You have new parcel: start",
                new Date().toString() +'\n'+  "Parcel was pickedUp"));
        androidPushNotificationsService.send(notificationProvider
            .createNotification(courier.getUsername(), "Parcel not verified", "You have new parcel: start",
                new Date().toString() +'\n'+  "newParcelYou have picked up a parcel"));


    }

    public void confirmCollect(ParcelConfirmCollect parcelConfirmCollect) {
        User reciver = userService.getUserByUserName(parcelConfirmCollect.getReciver());

        Parcel parcel = parcelRepository.findByEndPhoneAndStatus(reciver.getPhone(), "PICKED_UP").get(0);
        User sender = userService.findByPhone(parcel.getStartPhone());
        User courier = userService.getUserById(parcel.getCourierId());
        sender.setSaldo(sender.getSaldo() - 5);
        courier.setSaldo(courier.getSaldo() + 5);
        userService.update(sender);userService.update(courier);
        androidPushNotificationsService.send(notificationProvider
            .createNotification(sender.getUsername(), "Parcel not verified", "You have new parcel: start",
                new Date().toString() +'\n'+  ": Parcel was delivered"));
        androidPushNotificationsService.send(notificationProvider
            .createNotification(reciver.getUsername(), "Parcel not verified", "You have new parcel: start",
                new Date().toString() +'\n'+  "Parcel was delivered"));
        androidPushNotificationsService.send(notificationProvider
            .createNotification(courier.getUsername(), "Parcel not verified", "You have new parcel: start",
                new Date().toString() +'\n'+  "finishParcel was delivered"));


    }


}
