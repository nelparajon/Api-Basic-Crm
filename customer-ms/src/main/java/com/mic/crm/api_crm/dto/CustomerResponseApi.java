package com.mic.crm.api_crm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Class that provides a message and the Customers's Data Transport Object as response information when an API request is successful.
 */
@JsonInclude(JsonInclude.Include.NON_NULL) //evitar nulls de esta clase en las respuestas, como customersList al mostrar solo un customer
public class CustomerResponseApi {

    private String message;
    private CustomerDto customerDto;
    private Page<CustomerDto> customersList;

    public CustomerResponseApi() {
    }

    /**
     * Constructor to create an ApiResponse with a message.
     *
     * @param message The api message.
     *
     */

    public CustomerResponseApi(String message){
        this.message = message;
    }
    /**
     * Constructor to create an ApiResponse with a message and a CustomerDto.
     *
     * @param message The error message.
     * @param customerDto
     */
    public CustomerResponseApi(String message, CustomerDto customerDto) {
        this.message = message;
        this.customerDto = customerDto;
    }
    /**
     * Constructor to create an ApiResponse with a message and a List od customers, pageable.
     *
     * @param message The error message.
     * @param List<CustomerDto>
     */
    public CustomerResponseApi(String message, Page<CustomerDto> customersList) {
        this.message = message;
        this.customersList = customersList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    public Page<CustomerDto> getCustomersList() {
        return customersList;
    }

    @Override
    public String toString() {
        return "ResponseApi{" +
                "message='" + message + '\'' +
                ", customerDto=" + customerDto +
                '}';
    }


}
