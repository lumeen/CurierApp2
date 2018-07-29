package com.social.controller;

import com.social.entities.Courier;
import com.social.model.AddParcelToCourierRequest;
import com.social.model.CoordsUpdateRequest;
import com.social.model.CourierRequest;
import com.social.services.CourierService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @CrossOrigin
    @RequestMapping(value = "/courier", method = RequestMethod.PUT)
    public void createCourier(@RequestBody CourierRequest courierRequest) {
        courierService.createCourier(courierRequest);
    }


    @CrossOrigin
    @RequestMapping(value = "/courier", method = RequestMethod.GET)
    public ResponseEntity<List<Courier>> getAllCouriers () {
       return ResponseEntity.ok(courierService.getAllCouriers());
    }

    @CrossOrigin
    @RequestMapping(value = "/courier/find", method = RequestMethod.POST)
    public ResponseEntity<List<Courier>> getCouriersByNames (@RequestBody CourierRequest courierRequest) {
        return ResponseEntity.ok(courierService.findCouriers(courierRequest));
    }

    @CrossOrigin
    @RequestMapping(value = "/courier/{id}", method = RequestMethod.GET)
    public ResponseEntity<Courier> getCourier (@PathVariable Long id) {
        return ResponseEntity.ok(courierService.getCourier(id));
    }

    @CrossOrigin
    @RequestMapping(value = "/courier/addParcel", method = RequestMethod.POST)
    public ResponseEntity<Courier> addParcelToCourier (@RequestBody AddParcelToCourierRequest addParcelToCourierRequest) {
        return ResponseEntity.ok(courierService.addParcelToCourier( addParcelToCourierRequest.getParcelId(), addParcelToCourierRequest.getCourierId()));
    }

}
