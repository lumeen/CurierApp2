package com.social.services;

import com.social.dao.ParcelRepository;
import com.social.entities.Parcel;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParcelService {

    private final ParcelRepository parcelRepository;


    public void updateParcels(List<Parcel> parcels) {
        List<Long> parcelIds = parcels.stream().map(parcel -> parcel.getId()).collect(Collectors.toList());
        List<Parcel> updatedEntities = parcelRepository.findByCarId(parcels.get(0).getCar().getId());
        parcels.forEach(p ->
        {
            p.setPriority(parcels.indexOf(p));
        });
List<Parcel> aa =        updatedEntities.stream().filter(p -> !parcelIds.contains(p.getId())).collect(Collectors.toList());
        updatedEntities.stream().filter(p -> !parcelIds.contains(p.getId())).collect(Collectors.toList()).forEach(r->{r.setPriority(-1);
        r.setCar(null);});

        parcelRepository.save(parcels);

    }

    public List<Parcel> findByCarId(Long id){
        return parcelRepository.findByCarId(id);
    }

}
