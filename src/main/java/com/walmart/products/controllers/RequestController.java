package com.walmart.products.controllers;

import com.walmart.products.dtos.ProductRecordDto;
import com.walmart.products.dtos.RequestRecordDto;
import com.walmart.products.enums.Status;
import com.walmart.products.models.ProductModel;
import com.walmart.products.models.RequestModel;
import com.walmart.products.models.UserModel;
import com.walmart.products.repositories.BranchRepository;
import com.walmart.products.repositories.ProductRepository;
import com.walmart.products.repositories.RequestRepository;
import com.walmart.products.repositories.UserRepository;
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
public class RequestController {

    @Autowired // Dependency injection can also be done inside the constructor instead of the Autowired annotation
    RequestRepository requestRepository;

    @Autowired // Dependency injection can also be done inside the constructor instead of the Autowired annotation
    ProductRepository productRepository;

    @Autowired // Dependency injection can also be done inside the constructor instead of the Autowired annotation
    UserRepository userRepository;

    @Autowired // Dependency injection can also be done inside the constructor instead of the Autowired annotation
    BranchRepository branchRepository;

    // @Valid : it acts to validate our body, to check if all necessary properties are coming in the request?
    @PostMapping("/requests")
    public ResponseEntity<Object> save(@RequestBody @Valid RequestRecordDto requestRecordDto) {
        var requestModel = new RequestModel();

        // - Request *(userId, createdAt, updatedAt, status, newPrice, product, branchId)*
        // @NotNull String userId, @NotNull String productId, @NotNull BigDecimal newPrice

        Optional<UserModel> userO = userRepository.findById(UUID.fromString(requestRecordDto.userId()));

        if(userO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            var branchId = userO.get().getBranch().getId();
            var branchO = branchRepository.findById(branchId);

            if(branchO.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Branch assigned to the User was not found");
            } else {
                requestModel.setBranch(branchO.get());
                requestModel.setUser(userO.get());
            }
        }

        Optional<ProductModel> productO = productRepository.findById(UUID.fromString(requestRecordDto.productId()));

        if(productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } else {
            requestModel.setProduct(productO.get());
        }

        requestModel.setNewPrice(requestRecordDto.newPrice());
        requestModel.setOldPrice(productO.get().getPrice());

        return ResponseEntity.status(HttpStatus.CREATED).body(requestRepository.save(requestModel)); // save() comes from the repository type
    }

    @GetMapping("/requests")
    public ResponseEntity<List<RequestModel>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(requestRepository.findAll());
    }

    @GetMapping("/requests/{id}")
    public  ResponseEntity<Object> getOne(@PathVariable(value="id") UUID id) {
        Optional<RequestModel> requestO = requestRepository.findById(id);
        return requestO.<ResponseEntity<Object>>map(requestModel -> ResponseEntity.status(HttpStatus.OK).body(requestModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found"));
    }

    @PutMapping("/requests/{id}")
    public  ResponseEntity<Object> update(@PathVariable(value="id") UUID id, @RequestBody @Valid RequestRecordDto requestRecordDto) {
        Optional<RequestModel> requestO = requestRepository.findById(id);
        if(requestO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }

        var requestModel = requestO.get();

        var status = Status.valueOf(requestRecordDto.status());
        requestModel.setStatus(status);

        return ResponseEntity.status(HttpStatus.OK).body(requestRepository.save(requestModel));
    }

    @DeleteMapping("/requests/{id}")
    public  ResponseEntity<Object> delete(@PathVariable(value="id") UUID id) {
        Optional<RequestModel> requestO = requestRepository.findById(id);
        if(requestO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }
        requestRepository.delete(requestO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Request deleted successfully.");
    }

}
