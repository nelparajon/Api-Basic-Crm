package com.mic.exception;

import com.mic.dto.ErrorProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Excepción para controlar si se introducen datos inválido específicos
    @ExceptionHandler(InvalidProductDataException.class)
    public ResponseEntity<ErrorProductResponse> handleInvalidProductDataException(InvalidProductDataException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorProductResponse(e.getMessage()));
    }

    //Excepción para controlar si no se encuentra un producto en la BD
    @ExceptionHandler(NotFoundProductException.class)
    public ResponseEntity<ErrorProductResponse> handleNotFoundProductException(NotFoundProductException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorProductResponse(e.getMessage()));
    }

    //Excepción para controlar si los datos desde el front no son válidos y @Valid falla
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorProductResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorProductResponse(errors.toString()));
    }
}
