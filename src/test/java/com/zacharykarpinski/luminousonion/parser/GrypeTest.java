package com.zacharykarpinski.luminousonion.parser;

import com.zacharykarpinski.luminousonion.model.Source;
import com.zacharykarpinski.luminousonion.parsers.Grype;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GrypeTest {

    @Test
    void givenValidJsonFile_whenParsed_thenHaveFindings() {
        MultiPartFileMock mpfm = new MultiPartFileMock("Test",GRYPE_JSON);
        Source s = Grype.parse(mpfm);
        assertNotNull(s);

        assertEquals(1,s.getFindings().size());
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
