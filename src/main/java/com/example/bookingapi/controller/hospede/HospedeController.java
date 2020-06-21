package com.example.bookingapi.controller.hospede;

import com.example.bookingapi.controller.util.APIPaths;
import com.example.bookingapi.controller.util.exception.ResourceNotFoundException;
import com.example.bookingapi.model.hospede.HospedeEntity;
import com.example.bookingapi.model.hospede.HospedeSpecs;
import com.example.bookingapi.model.repository.HospedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(APIPaths.HOSPEDE)
public class HospedeController {

    @Autowired
    private HospedeRepository hospedeRepository;

    @PostMapping(value = "/register")
    public HospedeEntity createHospede(@Valid @RequestBody HospedeEntity hospede) {
        return hospedeRepository.save(hospede);
    }

    @PutMapping("/{hospedeId}")
    public HospedeEntity updateHospede(@PathVariable Long hospedeId,
                                       @Valid @RequestBody HospedeEntity hospedeRequest) {
        return hospedeRepository.findById(hospedeId)
                .map(hospede -> {
                    hospede.setName(hospedeRequest.getName());
                    hospede.setDocument(hospedeRequest.getDocument());
                    hospede.setPhone(hospedeRequest.getPhone());
                    return hospedeRepository.save(hospede);
                }).orElseThrow(() -> new ResourceNotFoundException("Hospede com id: " + hospedeId + " não encontrado."));
    }

    @DeleteMapping("/{hospedeId}")
    public ResponseEntity<?> deleteHospede(@PathVariable Long hospedeId) {
        return hospedeRepository.findById(hospedeId)
                .map(hospede -> {
                    hospedeRepository.delete(hospede);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Hospede com id: " + hospedeId + " não encontrado."));
    }

    @GetMapping(value = "/")
    public Page<HospedeEntity> getHospede(Pageable pageable) {
        return hospedeRepository.findAll(pageable);
    }

    @GetMapping(value = "/q/")
    public Page<HospedeEntity> findHospedes(
            @RequestParam(required=false,name="name") String name,
            @RequestParam(required=false,name="document") String document,
            @RequestParam(required=false,name="phone") String phone,
            Pageable pageable) {

        Specification<HospedeEntity> spec = Specification.where(HospedeSpecs.getEmployeesByNameSpec(name)
                .or(HospedeSpecs.getEmployeesByDocumentSpec(document))
                .or(HospedeSpecs.getEmployeesByPhoneSpec(phone)));

        return hospedeRepository.findAll(spec, pageable);
    }



}
