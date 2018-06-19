package com.social.services;

import com.social.dao.CurierRepository;
import com.social.entities.Curier;
import com.social.model.CurierRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurierService {

    private final CurierRepository curierRepository;

    public void createCurier(CurierRequest curierRequest) {
        Curier curier = Curier.builder().firstName(curierRequest.getFirstName())
            .secondName(curierRequest.getSecondName()).build();
        curierRepository.save(curier);

    }

    public List<Curier> getAllCuriers() {
      return  curierRepository.findAll();

    }

    public List<Curier> findCuriers(CurierRequest curierRequest) {
        return  curierRepository.findByNames(curierRequest.getFirstName(), curierRequest.getSecondName());

    }

    public Curier getCurier(Long id) {
        return  curierRepository.findById(id);

    }


}
