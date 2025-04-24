package service;

import com.mic.SpringBootProductsMainApplication;
import com.mic.dto.ProductDto;
import com.mic.exception.InvalidProductDataException;
import com.mic.exception.NotFoundProductException;
import com.mic.model.Product;
import com.mic.repository.ProductRepository;
import com.mic.service.ProductService;
import com.mic.utils.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//extendemos de esta clase para test unitarios del propio service. ¿Si fuese con up de spring usariamos SpringbootTest?
@ExtendWith(MockitoExtension.class)
public class ProductsServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;
    @Test
    void testSaveProduct() {
        ProductDto productDto = new ProductDto("Producto", "Descripción", 100.0);

        Product product = new Product("Producto", "Descripción", 100.0);
        Product savedProduct = new Product("Producto", "Descripción", 100.0);
        ProductDto expectedProductDto = new ProductDto("Producto", "Descripción", 100.0);

        when(productMapper.productDtoToProduct(productDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(productMapper.productToProductDto(savedProduct)).thenReturn(expectedProductDto);

        ProductDto result = productService.saveProduct(productDto);

        // Assert
        assertNotNull(result);
        assertEquals("Producto", result.getTitle());
        assertEquals("Descripción", result.getDescription());
        assertEquals(100.0, result.getPrice());
        //Verificación de métodos
        verify(productRepository).save(product);
        verify(productMapper).productDtoToProduct(productDto);
        verify(productMapper).productToProductDto(savedProduct);
    }

    @Test
    public void testGetUsers(){
        //inicializacion y definicion
        Product product1 = new Product("Producto 1", "Descripción producto 1", 100.0);
        Product product2 = new Product("Producto 2", "Descripción producto 2", 200.0);
        List<Product> productList = List.of(product1, product2);

        //ProductDtos
        ProductDto dto1 = new ProductDto("Producto 1", "Descripción producto 1", 100.0);
        ProductDto dto2 = new ProductDto("Producto 2", "Descripción producto 2", 200.0);
        List<ProductDto> expectedDtoList = List.of(dto1, dto2);

        when(productRepository.findAll()).thenReturn(productList);
        when(productMapper.productToProductDto(product1)).thenReturn(dto1);
        when(productMapper.productToProductDto(product2)).thenReturn(dto2);

        //llamamos el método del service
        List<ProductDto> result = productService.getProducts();

        //Asserts
        assertEquals(2, result.size()); //aseguramos que retorne el número exacto de resultados
        //aseguramos que para cada producto retorne bien su title
        assertEquals("Producto 1", result.get(0).getTitle());
        assertEquals("Producto 2", result.get(1).getTitle());

        //Verificación de métodos
        verify(productRepository).findAll();
        verify(productMapper).productToProductDto(product1);
        verify(productMapper).productToProductDto(product2);
    }

    @Test
    void testInvalidProductDataException() {
        //inicializacion y definicion
        ProductDto productDto = ProductDto.builder()
                .title(null)
                .description("Descripción")
                .price(100.0)
                .build();
        InvalidProductDataException exception = assertThrows(InvalidProductDataException.class, () -> {
            productService.saveProduct(productDto);
        });

        assertEquals("El producto/servicio debe tener un nombre.", exception.getMessage());
    }

    @Test
    void testNotFoundProductException() {
        ProductDto productDto = ProductDto.builder()
                .title("")
                .description("Descripción")
                .price(100.0)
                .build();

        //Assert
        InvalidProductDataException exception = assertThrows(InvalidProductDataException.class, () -> {
            productService.saveProduct(productDto);
        });

        assertEquals("El producto/servicio debe tener un nombre.", exception.getMessage());
    }

    @Test
    void testZeroException() {
        ProductDto productDto = ProductDto.builder()
                .title("Producto")
                .description("Descripción")
                .price(0.0)
                .build();

        //Asserts
        InvalidProductDataException exception = assertThrows(InvalidProductDataException.class, () -> {
            productService.saveProduct(productDto);
        });

        assertEquals("El precio del producto debe ser mayor que 0.", exception.getMessage());
    }

    @Test
    void testNegativePriceException() {
        ProductDto productDto = ProductDto.builder()
                .title("Producto")
                .description("Descripción")
                .price(-100.0)
                .build();


        InvalidProductDataException exception = assertThrows(InvalidProductDataException.class, () -> {
            productService.saveProduct(productDto);
        });

        assertEquals("El precio del producto debe ser mayor que 0.", exception.getMessage());
    }

    @Test
    void testUpdateProduct(){
        Long id = 1L;
        Product productInBD = Product.builder()
                .id(id)
                .title("Static Web")
                .description("Static Web")
                .price(650.00).build();

        ProductDto productDtoInput = ProductDto.builder()
                .title("Static Web")
                .description("Web Estática con 3 páginas diferentes: Inicio, servicios, contacto")
                .price(700.00).build();

        Product updateProduct = Product.builder()
                .id(id)
                .title("Static Web")
                .description("Web Estática con 3 páginas diferentes: Inicio, servicios, contacto")
                .price(700.00).build();

        ProductDto productDtoOutput = ProductDto.builder()
                .title("Static Web")
                .description("Web Estática con 3 páginas diferentes: Inicio, servicios, contacto")
                .price(700.00).build();

        when(productRepository.findById(id)).thenReturn(Optional.of(productInBD));
        when(productMapper.updateProduct(productInBD, productDtoInput)).thenReturn(updateProduct);
        when(productMapper.productToProductDto(updateProduct)).thenReturn(productDtoOutput);

        ProductDto pDto = productService.updateProduct(id, productDtoInput);
        assertEquals("Web Estática con 3 páginas diferentes: Inicio, servicios, contacto", pDto.getDescription());

        //Verificación de métodos
        verify(productRepository).findById(id);
        verify(productMapper).updateProduct(productInBD, productDtoInput);
        verify(productRepository).save(updateProduct);
        verify(productMapper).productToProductDto(updateProduct);
    }

    @Test
    void testDeleteProduct(){
        Long id = 1L;
        Product product = Product.builder()
                .id(id)
                .title("Static Web")
                .description("Static Web")
                .price(650.00).build();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        productService.deleteProduct(id);

        //Verificamos que se han llamado a los métodos correctamente
        verify(productRepository).findById(id);
        verify(productRepository).deleteById(id);

    }

    @Test
    void testDeleteProductNotFoundProductException() {
        Long id = 99L;

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        NotFoundProductException exception = assertThrows(
                NotFoundProductException.class,
                () -> productService.deleteProduct(id)
        );

        assertEquals("No existe ningún producto con ese ID: 99", exception.getMessage());

        verify(productRepository).findById(id);
        verify(productRepository, never()).deleteById(any());
    }
}
