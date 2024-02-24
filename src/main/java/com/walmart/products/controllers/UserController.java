package com.walmart.products.controllers;

import com.walmart.products.dtos.UserRecordDto;
import com.walmart.products.enums.Roles;
import com.walmart.products.models.BranchModel;
import com.walmart.products.models.UserModel;
import com.walmart.products.repositories.BranchRepository;
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
public class UserController {

    @Autowired // Dependency injection can also be done inside the constructor instead of the Autowired annotation
    UserRepository userRepository;

    @Autowired
    private BranchRepository branchRepository; // Assuming you have a BranchRepository for saving Branch entities


    // @Valid : it acts to validate our body, to check if all necessary properties are coming in the request?
    @PostMapping("/users")
    public ResponseEntity<UserModel> save(@RequestBody @Valid UserRecordDto userRecordDto) {
        var userModel = new UserModel();

        BeanUtils.copyProperties(userRecordDto, userModel);

        BranchModel branch = branchRepository.findById(UUID.fromString(userRecordDto.branchId())).orElse(null);

        var role = Roles.valueOf(userRecordDto.role());

        userModel.setRole(role.getValue());
        userModel.setBranch(branch);

        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel)); // save() comes from the repository type
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    @GetMapping("/users/{id}")
    public  ResponseEntity<Object> getOne(@PathVariable(value="id") UUID id) {
        Optional<UserModel> branchO = userRepository.findById(id);
        return branchO.<ResponseEntity<Object>>map(userModel -> ResponseEntity.status(HttpStatus.OK).body(userModel)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    }

    @PutMapping("/users/{id}")
    public  ResponseEntity<Object> update(@PathVariable(value="id") UUID id, @RequestBody @Valid UserRecordDto userRecordDto) {
        Optional<UserModel> userO = userRepository.findById(id);
        if(userO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        var userModel = userO.get();
        BeanUtils.copyProperties(userRecordDto, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userModel));
    }

    @DeleteMapping("/users/{id}")
    public  ResponseEntity<Object> delete(@PathVariable(value="id") UUID id) {
        Optional<UserModel> userO = userRepository.findById(id);
        if(userO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userRepository.delete(userO.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
    }
}
