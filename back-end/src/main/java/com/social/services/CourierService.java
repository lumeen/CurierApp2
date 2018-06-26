package com.social.services;

import com.social.dao.CourierRepository;
import com.social.entities.Courier;
import com.social.entities.Parcel;
import com.social.model.CourierRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository courierRepository;
    private final ParcelService parcelService;

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

    public Courier addParcelToCourier(Long courierId, Long parcelId) {
        Courier courier = courierRepository.findById(courierId);
        List<Parcel> parcels = parcelService.findById(parcelId);

        if (parcels.size() == 0) {
            throw new RuntimeException("No search results");
        } else {
            Parcel parcel = parcels.get(0);
            if (!parcel.getStatus().equals("WAINTING")) {

                throw new RuntimeException("Bad status");
            } else {

                parcel.setCar(courier.getCar());
                parcel.setPriority(courier.getCar().getParcels().size());
                if (courier.getCar().getParcels().isEmpty()) {
                    parcel.setStatus("IN TRANSPORT");
                } else {
                    parcel.setStatus("IN QUEUE");
                }
                parcelService.save(parcel);

            }

        }
return  courier;
    }


}
