package com.walmart.products.controllers;

import com.walmart.products.dtos.ProductRecordDto;
import com.walmart.products.models.ProductModel;
import com.walmart.products.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired // Dependency injection can also be done inside the constructor instead of the Autowired annotation
    ProductRepository productRepository;

    // @Valid : it acts to validate our body, to check if all necessary properties are coming in the request?
    @PostMapping("/products")
    public ResponseEntity<ProductModel> save(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel); // initialize our model with the json properties in the request OBS: it could be also used a objectMapper if the properties in the request is not mirroring our model
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel)); // save() comes from the repository type
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping("/products/{id}")
    public  ResponseEntity<Object> getOne(@PathVariable(value="id") UUID id) {
        Optional<ProductModel> productO = productRepository.findById(id);
        return productO.<ResponseEntity<Object>>map(productModel -> ResponseEntity.status(HttpStatus.OK).body(productModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found"));
    }
}
