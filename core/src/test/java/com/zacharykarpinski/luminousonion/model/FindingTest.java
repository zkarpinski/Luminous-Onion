package com.zacharykarpinski.luminousonion.model;

import com.zacharykarpinski.luminousonion.LuminousOnionApplication;
import com.zacharykarpinski.luminousonion.repository.FindingRepository;
import com.zacharykarpinski.luminousonion.repository.SourceRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = false)
@ContextConfiguration(classes = LuminousOnionApplication.class)
public class FindingTest {

    @Autowired
    FindingRepository findingRepository;
    @Autowired
    SourceRepository sourceRepository;

    @Test
    public void testCreateNewFinding() {
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
