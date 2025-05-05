package com.mic.dto;

import com.mic.repository.OppRepository;
import org.springframework.data.domain.Page;

import java.util.List;

public class OppResponseApi {

    private String message;
    private OppDto oppDto;

    private Page<OppDto> opps;

    public OppResponseApi() {
    }

    public OppResponseApi(String message, OppDto oppDto) {
        this.message = message;
        this.oppDto = oppDto;
    }

    public OppResponseApi(String message, Page<OppDto> opps){
        this.message = message;
        this.opps = opps;

    }
    public OppResponseApi(String message){
        this.message = message;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OppDto getOppDto() {
        return oppDto;
    }

    public void setOppDto(OppDto oppDto) {
        this.oppDto = oppDto;
    }

    public Page<OppDto> getOpps() {
        return opps;
    }

    public void setOpps(Page<OppDto> opps) {
        this.opps = opps;
    }
}
