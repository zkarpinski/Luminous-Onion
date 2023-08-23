package com.zacharykarpinski.luminousonion.repository;

import com.zacharykarpinski.luminousonion.model.Finding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FindingRepository extends JpaRepository<Finding,Long> {

}
