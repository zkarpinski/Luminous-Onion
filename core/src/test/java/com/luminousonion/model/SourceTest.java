package com.luminousonion.model;

import com.luminousonion.repository.FindingRepository;
import com.luminousonion.repository.SourceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(showSql = false)
public class SourceTest {

    @Autowired
    FindingRepository findingRepository;
    @Autowired
    SourceRepository sourceRepository;

    @Test
    void whenSourceIsDeleted_thenDeleteFindings() {
        long sourceID = createTestSourceWithFinding();

        Source s = sourceRepository.findById(sourceID).get();
        sourceRepository.delete(s);

        assertEquals(0,sourceRepository.count(), "Source count does not equal 0 after deletion");
        assertEquals(0,findingRepository.count(), "Finding count does not equal 0 after deletion");

    }

    private long createTestSourceWithFinding() {
        String toolName = "AQUA_TRIVY";
        String findingDescription = "Test Description";

        Finding finding = new Finding();
        finding.setDescription(findingDescription);

        Source source = new Source();
        source.setTool(toolName);
        source.addFinding(finding);
        Long sID = sourceRepository.save(source).getId();

        assert sourceRepository.findById(sID).isPresent();

        return sID;

    }
}
