package com.walmart.products.repositories;

import com.walmart.products.models.RequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository // When extending JpaRepostitory, this annotation is not necessary
public interface RequestRepository extends JpaRepository<RequestModel, UUID> {

}
