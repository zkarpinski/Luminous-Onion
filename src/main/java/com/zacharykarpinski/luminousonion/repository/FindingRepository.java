package com.zacharykarpinski.luminousonion.repository;

import com.zacharykarpinski.luminousonion.model.Finding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FindingRepository extends JpaRepository<Finding,Long> {

}
