package com.zacharykarpinski.luminousonion.repository;

import com.zacharykarpinski.luminousonion.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query("""
    SELECT f.severity, CAST(count(f.id) as INTEGER)
    FROM Organization o INNER JOIN o.products p INNER JOIN p.sources s INNER JOIN s.findings f
    WHERE o.id = :id AND s.archived = false
    GROUP BY f.severity""")
    List<List<Object>> getOrgFindingsCountSummary(@Param("id") Long id);

}
