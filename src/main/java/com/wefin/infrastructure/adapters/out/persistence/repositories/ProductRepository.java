package com.wefin.infrastructure.adapters.out.persistence.repositories;

import com.wefin.infrastructure.adapters.out.persistence.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
