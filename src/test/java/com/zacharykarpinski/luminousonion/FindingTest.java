package com.zacharykarpinski.luminousonion;

import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.repository.FindingRepository;
import com.zacharykarpinski.luminousonion.repository.SourceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class FindingTest {

    @Autowired
    FindingRepository findingRepository;
    @Autowired
    SourceRepository sourceRepository;

    @Test
    public void testCreateNewFinding() {
        String toolName = "Test Tool";
        String findingDescription = "Test Description";
        Source source = new Source();
        source.setTool(toolName);
        sourceRepository.save(source);


        Finding finding = new Finding();
        finding.setDescription(findingDescription);
        finding.setSourceTool(toolName);
        finding.setSource(source);

        Long findingId = findingRepository.save(finding).getId();
        assert findingRepository.findById(findingId).isPresent();
        assertEquals("Finding description don't match.",
                findingRepository.findById(findingId).get().getDescription(),findingDescription);
    }
}
