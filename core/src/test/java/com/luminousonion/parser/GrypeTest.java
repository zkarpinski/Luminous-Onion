package com.luminousonion.parser;

import com.luminousonion.model.Finding;
import com.luminousonion.model.Source;
import com.luminousonion.model.shared.FindingSeverity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class GrypeTest {

    @Test
    void givenValidJsonFile_whenParsed_thenHaveFindings() {
        MultiPartFileMock mpfm = new MultiPartFileMock("Test",GRYPE_JSON);
        Source s = Grype.parse(mpfm);
        assertNotNull(s);

        assertEquals(1,s.getFindings().size());

        Finding firstFinding = s.getFindings().stream().findFirst().get();
        assertEquals("CVE-2007-2379",firstFinding.getFindingIdentifier());
        assertEquals(FindingSeverity.MEDIUM,firstFinding.getSeverity());
    }

    @Test
    void givenNoFindingJsonFile_whenParsed_thenHaveNoFindings() {
        MultiPartFileMock mpfm = new MultiPartFileMock("Test",GRYPE_JSON_NO_FINDINGS);
        Source s = Grype.parse(mpfm);
        assertNotNull(s);

        assertEquals(0,s.getFindings().size());
    }

    String GRYPE_JSON = """
        {
         "matches": [
          {
           "vulnerability": {
            "id": "CVE-2007-2379",
            "dataSource": "https://nvd.nist.gov/vuln/detail/CVE-2007-2379",
            "namespace": "nvd:cpe",
            "severity": "Medium",
            "description": "Desc"
           }
          }
         ]
        }
        """;

    String GRYPE_JSON_NO_FINDINGS = """
            {
             "matches": [
             ]
            }
            """;

    String EMPTY_JSON = "{}";

}
