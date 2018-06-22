package com.social.controller;

import com.social.entities.Parcel;
import com.social.services.ParcelService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    @CrossOrigin
    @RequestMapping(value = "/parcels/update", method = RequestMethod.PUT)
    public void updateParcels(@RequestBody List<Parcel> parcelList){
        parcelService.updateParcels(parcelList);

    }

    @CrossOrigin
    @RequestMapping(value = "/parcels/byCarId", method = RequestMethod.POST)
    public List<Parcel> updateParcels(@RequestBody Long carId){
     return parcelService.findByCarId(carId).stream().sorted(Comparator.comparing(Parcel::getPriority)).collect(
         Collectors.toList());

    }

}
