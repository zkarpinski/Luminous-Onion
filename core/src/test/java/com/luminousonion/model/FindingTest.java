package com.luminousonion.model;

import com.luminousonion.LuminousOnionApplication;
import com.luminousonion.repository.FindingRepository;
import com.luminousonion.repository.SourceRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@Disabled
@DataJpaTest(showSql = false)
@ContextConfiguration(classes = LuminousOnionApplication.class)
class FindingTest {

    @Autowired
    FindingRepository findingRepository;
    @Autowired
    SourceRepository sourceRepository;

    @Test
    void testCreateNewFinding() {
        String toolName = "AQUA_TRIVY";
        String findingDescription = "Test Description";
        Source source = new Source();
        source.setTool(toolName);
        sourceRepository.save(source);


        Finding finding = new Finding();
        finding.setDescription(findingDescription);
        finding.setSource(source);

        Long findingId = findingRepository.save(finding).getId();
        assertTrue(findingRepository.findById(findingId).isPresent());
        assertEquals("Finding description don't match.",
                findingRepository.findById(findingId).get().getDescription(),findingDescription);
    }
}
