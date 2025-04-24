package com.mic.service;

import com.mic.dto.OppDto;
import com.mic.exception.OpportunityNotFoundException;
import com.mic.model.Opportunity;
import com.mic.repository.OppRepository;
import com.mic.utils.OppMapper;
import com.mic.utils.OppPriority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OppService {

    private final OppRepository oppRepository;
    private final OppMapper oppMapper;

    public OppService(OppRepository oppRepository, OppMapper oppMapper) {
        this.oppRepository = oppRepository;
        this.oppMapper = oppMapper;
    }

    public OppDto saveOpp(OppDto oppDto){

        Opportunity createdOpp = oppMapper.oppDtoToOpp(oppDto);

        Opportunity savedOpp = oppRepository.save(createdOpp);

        return oppMapper.oppToOppDto(savedOpp);
    }

    //retornamos la entidad de la opportunity mediante su public Id
    public Opportunity getOppByPublicId(String publicId){
        return oppRepository.findByPublicId(publicId)
                .orElseThrow( () -> new OpportunityNotFoundException(publicId));

    }

    public List<OppDto> getAllOpps(){
        return oppRepository.findAll().stream()
                .map(oppMapper::oppToOppDto)
                .toList();
    }

    public OppDto updateOpp(String publicId, OppDto oppDto){
        //opportunity coincidente sacada de la bd
        Opportunity opp = getOppByPublicId(publicId);
        //mapeo del dto con los cambios a la opp persistida
        Opportunity oppUpdated = oppMapper.updateOpp(oppDto, opp);

        Opportunity savedOpp = oppRepository.save(oppUpdated);
        //retornamos el dto con los cambios
        return oppMapper.oppToOppDto(savedOpp);
    }

    public void deleteOpp(String publicId){
        Opportunity opp = getOppByPublicId(publicId);
        oppRepository.deleteById(opp.getId());

    }
}
