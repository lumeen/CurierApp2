package com.social.services;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.GeocodedWaypoint;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.social.dao.CarRepository;
import com.social.dao.CourierRepository;
import com.social.dao.ParcelRepository;
import com.social.entities.Car;
import com.social.entities.Coordinate;
import com.social.entities.Courier;
import com.social.entities.Parcel;
import com.social.model.CoordsUpdateRequest;
import com.social.model.CourierRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierService {

    private static String KEY = "AIzaSyBMmm_Q-BzFlb5Hx-b74uHOA4f2PY9jxXE";
    private final CourierRepository courierRepository;
    private final CarRepository carRepository;
    private final ParcelRepository parcelRepository;
    private final Haversine haversine;

    public void createCourier(CourierRequest curierRequest) {
        Courier curier = Courier.builder().firstName(curierRequest.getFirstName())
            .secondName(curierRequest.getSecondName()).phoneNumber(curierRequest.getPhoneNumber()).build();
        courierRepository.save(curier);

    }

    public List<Courier> getAllCouriers() {
        return courierRepository.findAll();

    }

    public List<Courier> findCouriers(CourierRequest curierRequest) {
        return courierRepository.findByNames(curierRequest.getFirstName(), curierRequest.getSecondName());

    }

    public Courier getCourier(Long id) {
        return courierRepository.findById(id);

    }

    public Courier addParcelToCourier(Long parcelId, Long courierId) {

        List<Parcel> parcels = parcelRepository.findById(parcelId);
 /*       List<Parcel> allParcels = parcelRepository.findAll();

        Map<Long, Double> parcelWithDistance = new HashMap<>();

        allParcels.forEach(r -> parcelWithDistance.put(r.getId(),
            haversine.distance(r.getLat(), r.getLng(), parcels.get(0).getLat(), parcels.get(0).getLng())));

        List<Double> distances = new ArrayList<Double>(parcelWithDistance.values()).stream().sorted(Double::compareTo)
            .collect(Collectors.toList());
        List<Parcel> parcels2 = parcelRepository.findById(getKey(parcelWithDistance, distances.get(1)));

        Courier courier;
        if (courierId == null) {
            courier = parcels2.get(0).getCar().getCurier();
        } else {
            courier = courierRepository.findById(courierId);

        }
        if (parcels.size() == 0) {
            throw new RuntimeException("No search results");
        }
        Parcel parcel = parcels.get(0);

        parcel.setCar(courier.getCar());
        List<Parcel> courierParcels = courier.getCar().getParcels();
        GeoApiContext context = new GeoApiContext.Builder()
            .apiKey(KEY)
            .build();

        List<LatLng> wayPoints = courierParcels.stream().map(r -> new LatLng(r.getLat(), r.getLng())).collect(
            Collectors.toList());

        wayPoints.add(new LatLng(parcel.getLat(), parcel.getLng()));
        courierParcels.add(parcel);

        DirectionsResult result = DirectionsApi.newRequest(context)
            .mode(TravelMode.DRIVING)
            .origin(new LatLng(52.1383487, 21.0508035))
            .waypoints(wayPoints.toArray(new LatLng[wayPoints.size()]))
            .optimizeWaypoints(true)
            .destination(new LatLng(52.1383487, 21.0508035))
            .awaitIgnoreError();
        int order[] = result.routes[0].waypointOrder;

        IntStream.range(0, order.length)
            .forEach(i -> courierParcels.get(i).setPriority(getArrayIndex(order, i)));

        courierParcels.forEach(courierParcel -> {
            if (courierParcel.getPriority() == 0) {

                courierParcel.setStatus("IN TRANSPORT");
            } else {
                courierParcel.setStatus("IN QUEUE");
            }
        });

        courier.getCar().setParcels(courierParcels);

        courierRepository.save(courier);
*/
        return null;
    }

    public void updateCoords(CoordsUpdateRequest coordsUpdateRequest) {

        Car car = courierRepository.findByEmail(coordsUpdateRequest.getUserName()).getCar();
        car.getCoordinate().setLatitude(Double.valueOf(coordsUpdateRequest.getLatitude()));
        car.getCoordinate().setLongitude(Double.valueOf(coordsUpdateRequest.getLongitude()));
        carRepository.save(car);

    }

    public int getArrayIndex(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }

    private Long getKey(Map<Long, Double> mapref, Double value) {
        Long key = null;
        for (Map.Entry<Long, Double> map : mapref.entrySet()) {
            if (map.getValue() == (value)) {
                key = map.getKey();
                return key;
            }
        }
        return key;
    }


}
