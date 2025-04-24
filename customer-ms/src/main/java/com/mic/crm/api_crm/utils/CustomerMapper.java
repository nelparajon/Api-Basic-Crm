package com.mic.crm.api_crm.utils;
import com.mic.crm.api_crm.dto.CustomerDto;
import com.mic.crm.api_crm.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = ContactMapper.class)
public interface CustomerMapper {
    @Mapping(source = "contactos", target = "contactos")
    CustomerDto customerToCustomerDto(Customer customer);
    @Mapping(source = "id", target = "id")
    Customer customerDtoToCustomer(CustomerDto customerDto);
}
