package com.social.controller;

import com.social.dao.UserRepository;
import com.social.entities.Parcel;
import com.social.entities.User;
import com.social.model.ParcelConfirmCollect;
import com.social.model.ParcelPickUpConfirmation;
import com.social.model.ParcelRequest;
import com.social.model.ParcelVerifyRequest;
import com.social.services.ParcelService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;
    private final UserRepository userRepository;

    @CrossOrigin
    @RequestMapping(value = "/parcels/update", method = RequestMethod.PUT)
    public List<Parcel> updateParcels(@RequestBody List<Parcel> parcelList){
     return    parcelService.updateParcels(parcelList);

    }

    @CrossOrigin
    @RequestMapping(value = "/parcels/byCarId", method = RequestMethod.POST)
    public List<Parcel> updateParcels(@RequestBody Long carId){
     return parcelService.findByCarId(carId).stream().sorted(Comparator.comparing(Parcel::getPriority)).collect(
         Collectors.toList());

    }

    @CrossOrigin
    @RequestMapping(value = "/parcels/getAll", method = RequestMethod.GET)
    public List<Parcel> getAll(){
        return parcelService.getAllParcels();

    }

    @CrossOrigin
    @RequestMapping(value = "/parcel", method = RequestMethod.PUT)
    public ResponseEntity<?> createParcel( ParcelRequest parcelRequest){
        User  user = userRepository.findOneByUsername(parcelRequest.getUserName());
        if(user.getSaldo()<5){
            return (ResponseEntity<?>) ResponseEntity.badRequest();

        }
         parcelService.createParcel(parcelRequest);
        return ResponseEntity.ok("");

    }

    @CrossOrigin
    @RequestMapping(value = "/parcel/verify", method = RequestMethod.PUT)
    public boolean verifyParcel( ParcelVerifyRequest parcelRequest) throws InterruptedException {
 return parcelService.verify(parcelRequest);

    }

    @CrossOrigin
    @RequestMapping(value = "/parcel/{id}", method = RequestMethod.GET)
    public List<Parcel> findById(@PathVariable Long id){
     return parcelService.findById(id);

    }


    @CrossOrigin
    @RequestMapping(value = "/parcel/confirmPickUp", method = RequestMethod.PUT)
    public void ConfirmPickUp(ParcelPickUpConfirmation parcelPickUpConfirmation){
        parcelService.confirmPickUp(parcelPickUpConfirmation);

    }


    @CrossOrigin
    @RequestMapping(value = "/parcel/confirmCollect", method = RequestMethod.PUT)
    public void confirmCollect(ParcelConfirmCollect parcelConfirmCollect){
        parcelService.confirmCollect(parcelConfirmCollect);

    }





}
