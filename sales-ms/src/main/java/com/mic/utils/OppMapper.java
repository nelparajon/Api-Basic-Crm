package com.mic.utils;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.mic.dto.OppDto;
import com.mic.model.Opportunity;
import com.mic.utils.OppState;
import com.mic.utils.OppPriority;
import org.aspectj.lang.annotation.After;
import org.mapstruct.*;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring")
public interface OppMapper {
    Opportunity oppDtoToOpp(OppDto oppDto);
    OppDto oppToOppDto(Opportunity opp);

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    Opportunity updateOpp(OppDto oppDto, @MappingTarget Opportunity opportunity );

    @AfterMapping
    default void setStateAndPriority(@MappingTarget Opportunity opportunity, OppDto oppDto) {
        //Solo asignar un valor por defecto si no se pasó el valor en el DTO y además no exist en la entity
        if (oppDto.getState() == null && opportunity.getState() == null) {
            opportunity.setState(OppState.NUEVA);
        }
        if (oppDto.getPriority() == null && opportunity.getPriority() == null) {
            opportunity.setPriority(OppPriority.MEDIA);
        }
        if (oppDto.getAmount() == null && opportunity.getAmount() == null) {
            opportunity.setAmount(0.0);
        }
    }
}
