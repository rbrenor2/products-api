package com.walmart.products.controllers;

import com.walmart.products.dtos.BranchRecordDto;
import com.walmart.products.dtos.RoleRecordDto;
import com.walmart.products.models.RoleModel;
import com.walmart.products.repositories.BranchRepository;
import com.walmart.products.repositories.RoleRepository;
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
public class RoleController {

    @Autowired // Dependency injection can also be done inside the constructor instead of the Autowired annotation
    RoleRepository roleRepository;

    // @Valid : it acts to validate our body, to check if all necessary properties are coming in the request?
    @PostMapping("/roles")
    public ResponseEntity<RoleModel> save(@RequestBody @Valid RoleRecordDto roleRecordDto) {
        var roleModel = new RoleModel();
        BeanUtils.copyProperties(roleRecordDto, roleModel); // initialize our model with the json properties in the request OBS: it could be also used a objectMapper if the properties in the request is not mirroring our model
        return ResponseEntity.status(HttpStatus.CREATED).body(roleRepository.save(roleModel)); // save() comes from the repository type
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleModel>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(roleRepository.findAll());
    }
}
