package com.mic.controller;

import com.mic.dto.OppDto;
import com.mic.dto.OppResponseApi;
import com.mic.model.Opportunity;
import com.mic.service.OppService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/opportunities")
public class OppController {

    private final OppService oppService;

    public OppController(OppService oppService){
        this.oppService = oppService;
    }

    @PostMapping
    public ResponseEntity<OppResponseApi> createOpportunity(@RequestBody @Valid OppDto oppDto){
        OppDto oppCreateDto = oppService.saveOpp(oppDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new OppResponseApi("Oportunidad de Venta Creada", oppCreateDto));
    }

    @GetMapping
    public ResponseEntity<OppResponseApi> getAllOpportunities(){
        List<OppDto> opps = oppService.getAllOpps();

        return ResponseEntity.status(HttpStatus.OK).body(new OppResponseApi("Opportunities founded", opps));
    }

    @PutMapping("/{publicId}")
    public ResponseEntity<OppResponseApi> updateOpportunity( @PathVariable @Valid String publicId, @RequestBody OppDto oppDto){
        OppDto updatedOpp = oppService.updateOpp(publicId, oppDto);
        return ResponseEntity.status(HttpStatus.OK).body(new OppResponseApi("Opportunidad de venta actualizada con éxito"));
    }

    @DeleteMapping("/{publicId}")
    public ResponseEntity<OppResponseApi> deleteOpportunity(@PathVariable String publicId){
        oppService.deleteOpp(publicId);
        return ResponseEntity.ok(new OppResponseApi("Opportunidad eliminada con éxito"));
    }

}
