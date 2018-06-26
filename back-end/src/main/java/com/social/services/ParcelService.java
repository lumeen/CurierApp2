package com.social.services;

import com.social.dao.ParcelRepository;
import com.social.entities.Parcel;
import com.social.model.ParcelRequest;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParcelService {

    private final ParcelRepository parcelRepository;


    public List<Parcel> updateParcels(List<Parcel> parcels) {
        List<Long> parcelIds = parcels.stream().map(parcel -> parcel.getId()).collect(Collectors.toList());
        List<Parcel> updatedEntities = parcelRepository.findByCarId(parcels.get(0).getCar().getId());
        parcels.forEach(r-> r.setStatus("IN QUEUE"));
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
        Parcel parcel = Parcel.builder().city(parcelRequest.getCity())
            .postCode(parcelRequest.getPostCode()).streetName(parcelRequest.getStreetName())
            .buildNumber(parcelRequest.getBuildNumber()).status("WAINTING").build();
        parcelRepository.save(parcel);

    }

    public List<Parcel> findById(Long id) {
        return parcelRepository.findById(id);
    }

    public void save(Parcel parcel){

        parcelRepository.save(parcel);
    }

}
