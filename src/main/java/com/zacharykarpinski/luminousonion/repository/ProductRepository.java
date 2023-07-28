package com.zacharykarpinski.luminousonion.repository;

import com.zacharykarpinski.luminousonion.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin
public interface ProductRepository extends JpaRepository<Product, Long> {

}
