package com.walmart.products.repositories;

import com.walmart.products.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository // When extending JpaRepostitory, this annotation is not necessary
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {

}
