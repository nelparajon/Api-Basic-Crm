package com.mic.dto;

import com.mic.dto.ProductDto;

import java.util.List;

public class ResponseProductApi {

    private String message;
    private ProductDto productDto;
    private List<ProductDto> products;

    public ResponseProductApi(String message, ProductDto productDto) {
        this.message = message;
        this.productDto = productDto;
        this.products = null;
    }

    public ResponseProductApi(String message, List<ProductDto> products){
        this.message = message;
        this.products = products;
        this.productDto = null;
    }

    public ResponseProductApi(String message) {
        this.message = message;
        this.productDto = null;
        this.products = null;
    }

    public ResponseProductApi() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
