package com.mic.controller;

import com.mic.dto.ProductDto;
import com.mic.dto.ResponseProductApi;
import com.mic.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseProductApi> createProduct(@RequestBody @Valid ProductDto productDto){
        System.out.println("PRODUCTO DTO: " + productDto);
        ProductDto createdProductDto = productService.saveProduct(productDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseProductApi("Producto creado con éxito", createdProductDto));
    }

    @GetMapping
    public ResponseEntity<ResponseProductApi> getAllProducts() {
        List<ProductDto> products = productService.getProducts();
        String message = products.isEmpty() ? "No hay productos o servicios" : "Productos o Servicios encontrados";
        return ResponseEntity.ok(new ResponseProductApi(message, products));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseProductApi> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDto updatedProductDto){
        ProductDto productDto = productService.updateProduct(id, updatedProductDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseProductApi("Producto actualizado con éxito", productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseProductApi> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseProductApi("Producto eliminado con éxito"));
    }

}
