package com.zacharykarpinski.luminousonion;

import com.zacharykarpinski.luminousonion.integration.SnykIntegration;
import com.zacharykarpinski.luminousonion.model.Finding;
import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.repository.FindingRepository;
import com.zacharykarpinski.luminousonion.repository.SourceRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FindingTest {

    @Autowired
    FindingRepository findingRepository;
    @Autowired
    SourceRepository sourceRepository;

    @Test
    @Disabled
    public void testCreateNewFinding() {
        Source source = new Source();
        source.setTool("Test Tool");
        sourceRepository.save(source);


        Finding finding = new Finding();
        finding.setDescription("Test finding");
        finding.setSourceTool("Test Tool");
        finding.setSource(source);

        Finding s = findingRepository.save(finding);
        assert findingRepository.findById(s.getId()).isPresent();
    }
}
