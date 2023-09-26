package com.luminousonion.repository;

import com.luminousonion.model.Finding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FindingRepository extends JpaRepository<Finding,Long> {

    Finding findFindingByPackageName(String packageName);

    List<Finding> findBySource_ArchivedFalse();
    List<Finding> findBySource_Product_Id(long productId);
    List<Finding> findBySource_Product_IdAndSource_ArchivedFalse(long productId);


}
