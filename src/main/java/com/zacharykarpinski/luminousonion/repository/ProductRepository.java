package com.zacharykarpinski.luminousonion.repository;

import com.zacharykarpinski.luminousonion.dto.ProductFindingsSummaryDTO;
import com.zacharykarpinski.luminousonion.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT NEW com.zacharykarpinski.luminousonion.dto.ProductFindingsSummaryDTO(0,0,0,0,CAST(count(f.id) as INTEGER )) FROM Product p INNER JOIN p.sources s INNER JOIN s.findings f WHERE p.id = :id")
    ProductFindingsSummaryDTO getProductFindingsCountSummaryOld(@Param("id") Long id);

    @Query("""
            SELECT f.severity, CAST(count(f.id) as INTEGER)
            FROM Product p INNER JOIN p.sources s INNER JOIN s.findings f
            WHERE p.id = :id AND s.archived = false 
            GROUP BY f.severity
            """)
    List<List<Object>> getProductFindingsCountSummary(@Param("id") Long id);

}
