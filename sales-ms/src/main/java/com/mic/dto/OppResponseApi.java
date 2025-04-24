package com.mic.dto;

import com.mic.repository.OppRepository;

import java.util.List;

public class OppResponseApi {

    private String message;
    private OppDto oppDto;

    private List<OppDto> opps;

    public OppResponseApi() {
    }

    public OppResponseApi(String message, OppDto oppDto) {
        this.message = message;
        this.oppDto = oppDto;
    }

    public OppResponseApi(String message, List<OppDto> opps){
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

    public List<OppDto> getOpps() {
        return opps;
    }

    public void setOpps(List<OppDto> opps) {
        this.opps = opps;
    }
}
