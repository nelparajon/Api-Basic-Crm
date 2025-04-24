package com.mic.crm.api_crm.utils;

import com.mic.crm.api_crm.dto.ContactDto;
import com.mic.crm.api_crm.model.Contact;
import com.mic.crm.api_crm.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContactMapper {

    @Mapping(source = "customer.id", target = "customerId")
    ContactDto contactToContactDto(Contact contact);

    @Mapping(target = "customer", ignore = true) //evita recursión
    Contact contactDtoToContact(ContactDto contactDto);

    @Mapping(target = "customer", ignore = true)
    void updateContactFromDto(ContactDto dto, @MappingTarget Contact entity);
}


