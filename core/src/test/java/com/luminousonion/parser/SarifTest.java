package com.luminousonion.parser;

import com.luminousonion.model.Finding;
import com.luminousonion.model.Source;
import com.luminousonion.model.shared.SourceTool;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SarifTest {

    @Test
    void givenValidJsonFile_whenParsed_thenHaveFindings() {
        MultiPartFileMock mpfm = new MultiPartFileMock("Test",SARIFV2_JSON);
        Source s = Sarif.parse(mpfm);
        assertNotNull(s);

        assertEquals(1,s.getFindings().size());
        assertEquals(SourceTool.DOCKER_SCOUT, s.getTool());
        assertEquals("0.24.1",s.getToolVersion());

        Finding firstFinding = s.getFindings().stream().findFirst().get();
        assertEquals("1.4.5",firstFinding.getPackageVersionFound());
        assertEquals("xstream",firstFinding.getPackageName());
    }

    String SARIFV2_JSON = """
            {
              "version": "2.1.0",
              "$schema": "https://json.schemastore.org/sarif-2.1.0.json",
              "runs": [
                {
                  "tool": {
                    "driver": {
                      "fullName": "Docker Scout",
                      "informationUri": "https://docker.com/products/docker-scout",
                      "name": "docker scout",
                      "rules": [
                        {
                          "id": "GMS-2022-9109",
                          "name": "OsPackageVulnerability",
                          "shortDescription": {
                            "text": "GMS-2022-9109: OWASP Top Ten 2017 Category A9 - Using Components with Known Vulnerabilities"
                          },
                          "helpUri": "https://scout.docker.com/v/GMS-2022-9109",
                          "help": {
                            "text": "Those using Xstream to seralize XML data may be vulnerable to Denial of Service attacks (DOS). If the parser is running on user supplied input, an attacker may supply content that causes the parser to crash by stackoverflow. This effect may support a denial of service attack.\\n",
                            "markdown": "\\u003e Those using Xstream to seralize XML data may be vulnerable to Denial of Service attacks (DOS). If the parser is running on user supplied input, an attacker may supply content that causes the parser to crash by stackoverflow. This effect may support a denial of service attack.\\n\\n|                |                                                  |\\n|----------------|--------------------------------------------------|\\n| Package        | pkg:maven/com.thoughtworks.xstream/xstream@1.4.5 |\\n| Affected range | \\u003c=1.4.19                                         |\\n| Fixed version  | not fixed                                        |\\n"
                          },
                          "properties": {
                            "affected_version": "\\u003c=1.4.19",
                            "cvssV3_severity": "UNSPECIFIED",
                            "fixed_version": "not fixed",
                            "purl": "pkg:maven/com.thoughtworks.xstream/xstream@1.4.5",
                            "security-severity": "3.1",
                            "tags": [
                              "UNSPECIFIED"
                            ]
                          }
                        }
                      ]
                      , "version": "0.24.1"
                    }
                  }
                }
              ]
            }
        """;
}
