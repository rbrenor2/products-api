package com.walmart.products.controllers;

import com.walmart.products.dtos.BranchRecordDto;
import com.walmart.products.models.BranchModel;
import com.walmart.products.repositories.BranchRepository;
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
public class BranchController {

    @Autowired // Dependency injection can also be done inside the constructor instead of the Autowired annotation
    BranchRepository branchRepository;

    // @Valid : it acts to validate our body, to check if all necessary properties are coming in the request?
    @PostMapping("/branches")
    public ResponseEntity<BranchModel> save(@RequestBody @Valid BranchRecordDto branchRecordDto) {
        var branchModel = new BranchModel();
        BeanUtils.copyProperties(branchRecordDto, branchModel); // initialize our model with the json properties in the request OBS: it could be also used a objectMapper if the properties in the request is not mirroring our model
        return ResponseEntity.status(HttpStatus.CREATED).body(branchRepository.save(branchModel)); // save() comes from the repository type
    }

    @GetMapping("/branches")
    public ResponseEntity<List<BranchModel>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(branchRepository.findAll());
    }

    @GetMapping("/branches/{id}")
    public  ResponseEntity<Object> getOne(@PathVariable(value="id") UUID id) {
        Optional<BranchModel> branchO = branchRepository.findById(id);
        return branchO.<ResponseEntity<Object>>map(branchModel -> ResponseEntity.status(HttpStatus.OK).body(branchModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Branch not found"));
    }

    @PutMapping("/branches/{id}")
    public  ResponseEntity<Object> update(@PathVariable(value="id") UUID id, @RequestBody @Valid BranchRecordDto branchRecordDto) {
        Optional<BranchModel> branchO = branchRepository.findById(id);
        if(branchO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Branch not found");
        }

        var branchModel = branchO.get();
        BeanUtils.copyProperties(branchRecordDto, branchModel);
        return ResponseEntity.status(HttpStatus.OK).body(branchRepository.save(branchModel));
    }

    @DeleteMapping("/branches/{id}")
    public  ResponseEntity<Object> delete(@PathVariable(value="id") UUID id) {
        Optional<BranchModel> branchO = branchRepository.findById(id);
        if(branchO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Branch not found");
        }
        branchRepository.delete(branchO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Branch deleted successfully.");
    }
}
