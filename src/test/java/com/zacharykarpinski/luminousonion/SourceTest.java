package com.zacharykarpinski.luminousonion;

import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.repository.FindingRepository;
import com.zacharykarpinski.luminousonion.repository.SourceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class SourceTest {

    @Autowired
    FindingRepository findingRepository;
    @Autowired
    SourceRepository sourceRepository;

    @Test
    void whenSourceIsDeleted_thenDeleteFindings() {
        createTestSourceWithFinding();

        Source s = sourceRepository.findById(1L).get();
        sourceRepository.delete(s);

        //TODO convert to assertEquals once error is figured out
        assertTrue("Source count does not equal 0 after deletion",sourceRepository.count()==0);
        assertTrue("Finding count does not equal 0 after deletion",findingRepository.count()==0);

    }

    private void createTestSourceWithFinding() {
        String toolName = "Test Tool";
        String findingDescription = "Test Description";

        Finding finding = new Finding();
        finding.setDescription(findingDescription);

        Source source = new Source();
        source.setTool(toolName);
        source.addFinding(finding);
        sourceRepository.save(source);

        assert sourceRepository.findById(1L).isPresent();
        assert findingRepository.findById(1L).isPresent();

    }
}
