package com.mic.crm.api_crm.dto;

import java.util.List;

/**
 * Class that provides a message and the Customers's Data Transport Object as response information when an API request is successful.
 */
public class CustomerResponseApi {

    private String message;
    private CustomerDto customerDto;
    private List<CustomerDto> customersList;

    public CustomerResponseApi() {
    }

    /**
     * Constructor to create an ApiResponse with a message and an UserDto.
     *
     * @param message The error message.
     * @param customerDto  The User Entity DTO.
     */

    public CustomerResponseApi(String message){
        this.message = message;
    }
    public CustomerResponseApi(String message, CustomerDto customerDto) {
        this.message = message;
        this.customerDto = customerDto;
    }

    public CustomerResponseApi(String message, List<CustomerDto> customersList) {
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

    public List<CustomerDto> getCustomersList() {
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
