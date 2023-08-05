package com.zacharykarpinski.luminousonion.repository;

import com.zacharykarpinski.luminousonion.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT COUNT(f.id) FROM Product p INNER JOIN p.sources s INNER JOIN s.findings f WHERE p.id = :id")
    int getProductFindingsCountSummary(@Param("id") Long id);

}
