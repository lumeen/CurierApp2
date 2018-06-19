package com.social.controller;

import com.social.entities.Curier;
import com.social.model.CurierRequest;
import com.social.services.CurierService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CurierController {

    private final CurierService curierService;

    @CrossOrigin
    @RequestMapping(value = "/curier", method = RequestMethod.PUT)
    public void CreateCurier(@RequestBody CurierRequest curierRequest) {
        curierService.createCurier(curierRequest);
    }


    @CrossOrigin
    @RequestMapping(value = "/curier", method = RequestMethod.GET)
    public ResponseEntity<List<Curier>> getAllCuriers () {
       return ResponseEntity.ok(curierService.getAllCuriers());
    }

    @CrossOrigin
    @RequestMapping(value = "/curier/find", method = RequestMethod.POST)
    public ResponseEntity<List<Curier>> getCuriersByNames (@RequestBody CurierRequest curierRequest) {
        return ResponseEntity.ok(curierService.findCuriers(curierRequest));
    }

    @CrossOrigin
    @RequestMapping(value = "/curier/{id}", method = RequestMethod.GET)
    public ResponseEntity<Curier> getCurier (@PathVariable Long id) {
        return ResponseEntity.ok(curierService.getCurier(id));
    }

}
