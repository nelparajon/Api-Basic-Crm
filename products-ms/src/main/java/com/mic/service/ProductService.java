package com.mic.service;

import com.mic.dto.ProductDto;
import com.mic.exception.InvalidProductDataException;
import com.mic.exception.NotFoundProductException;
import com.mic.model.Product;
import com.mic.repository.ProductRepository;
import com.mic.utils.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDto saveProduct(ProductDto productDto){
        if (productDto.getTitle() == null || productDto.getTitle().isEmpty()) {
            throw new InvalidProductDataException("El producto/servicio debe tener un nombre.");
        }
        if (productDto.getPrice() <= 0) {
            throw new InvalidProductDataException("El precio del producto debe ser mayor que 0.");
        }
        Product newProduct = productMapper.productDtoToProduct(productDto);
        Product savedProduct = productRepository.save(newProduct);

        return productMapper.productToProductDto(savedProduct);
    }

    public List<ProductDto> getProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(productMapper::productToProductDto)
                .toList();
    }

    public ProductDto updateProduct(Long id, ProductDto productDto){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundProductException("No existe ningún producto con ese ID: " + id));

        productMapper.updateProduct(productDto, product);
        productRepository.save(product);
        return productMapper.productToProductDto(product);
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundProductException("No existe ningún producto con ese ID: " + id));
        productRepository.deleteById(id);
    }

}
