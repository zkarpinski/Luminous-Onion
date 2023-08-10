package com.zacharykarpinski.luminousonion.repository;

import com.zacharykarpinski.luminousonion.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin
public interface SourceRepository extends JpaRepository<Source, Long> {

    @Query("SELECT NEW com.zacharykarpinski.luminousonion.model.Source(s.id, s.tool) FROM Source s WHERE s.product.id = :id")
    List<Source> getSourcesByProduct(Long id);

}
