package com.luminousonion.parser;

import com.luminousonion.model.Finding;
import com.luminousonion.model.Source;
import com.luminousonion.model.shared.FindingSeverity;
import com.luminousonion.model.shared.SourceTool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class TrivyTest {

    @Test
    void givenValidJsonFile_whenParsed_thenHaveFindings() {
        MultiPartFileMock mpfm = new MultiPartFileMock("Test",TRIVY_JSON);
        Source s = Trivy.parse(mpfm);
        assertNotNull(s);
        assertEquals(2,s.getFindings().size());
        Assertions.assertEquals(SourceTool.AQUA_TRIVY,s.getTool());
        Assertions.assertEquals("webgoat/goatandwolf",s.getTarget());
        Assertions.assertEquals("image",s.getTargetType());

        Finding firstFinding = s.getFindings().stream().findFirst().get();
        assertEquals("CVE-2011-3374",firstFinding.getFindingIdentifier());
        assertEquals(FindingSeverity.LOW,firstFinding.getSeverity());
    }

    @Test
    void givenNoFindingJsonFile_whenParsed_thenHaveNoFindings() {
        MultiPartFileMock mpfm = new MultiPartFileMock("Test",TRIVY_JSON_NO_FINDINGS);
        Source s = Trivy.parse(mpfm);
        assertNotNull(s);
        assertEquals(0,s.getFindings().size());
    }

    String TRIVY_JSON = """
            {
              "SchemaVersion": 2,
              "ArtifactName": "webgoat/goatandwolf",
              "ArtifactType": "container_image",
             "Results": [
              {
                  "Target": "webgoat/goatandwolf (debian 11.0)",
                  "Class": "os-pkgs",
                  "Type": "debian",
                  "Vulnerabilities": [
                    {
                      "VulnerabilityID": "CVE-2011-3374",
                      "PkgID": "apt@2.2.4",
                      "PkgName": "apt",
                      "InstalledVersion": "2.2.4",
                      "SeveritySource": "debian",
                      "PrimaryURL": "https://avd.aquasec.com/nvd/cve-2011-3374",
                      "DataSource": {
                        "ID": "debian",
                        "Name": "Debian Security Tracker",
                        "URL": "https://salsa.debian.org/security-tracker-team/security-tracker"
                      },
                      "Title": "It was found that apt-key in apt, all versions, do not correctly valid ...",
                      "Description": "It was found that apt-key in apt, all versions, do not correctly validate gpg keys with the master keyring, leading to a potential man-in-the-middle attack.",
                      "Severity": "LOW",
                      "CweIDs": [
                        "CWE-347"
                      ],
                      "CVSS": {
                        "nvd": {
                          "V2Vector": "AV:N/AC:M/Au:N/C:N/I:P/A:N",
                          "V3Vector": "CVSS:3.1/AV:N/AC:H/PR:N/UI:N/S:U/C:N/I:L/A:N",
                          "V2Score": 4.3,
                          "V3Score": 3.7
                        }
                      },
                      "PublishedDate": "2019-11-26T00:15:00Z",
                      "LastModifiedDate": "2021-02-09T16:08:00Z"
                    },
                    {
                      "VulnerabilityID": "CVE-2011-3374",
                      "PkgID": "apt-utils@2.2.4",
                      "PkgName": "apt-utils",
                      "InstalledVersion": "2.2.4",
                      "SeveritySource": "debian",
                      "PrimaryURL": "https://avd.aquasec.com/nvd/cve-2011-3374",
                      "DataSource": {
                        "ID": "debian",
                        "Name": "Debian Security Tracker",
                        "URL": "https://salsa.debian.org/security-tracker-team/security-tracker"
                      },
                      "Title": "It was found that apt-key in apt, all versions, do not correctly valid ...",
                      "Description": "It was found that apt-key in apt, all versions, do not correctly validate gpg keys with the master keyring, leading to a potential man-in-the-middle attack.",
                      "Severity": "LOW",
                      "CweIDs": [
                        "CWE-347"
                      ],
                      "CVSS": {
                        "nvd": {
                          "V2Vector": "AV:N/AC:M/Au:N/C:N/I:P/A:N",
                          "V3Vector": "CVSS:3.1/AV:N/AC:H/PR:N/UI:N/S:U/C:N/I:L/A:N",
                          "V2Score": 4.3,
                          "V3Score": 3.7
                        }
                      },
                      "PublishedDate": "2019-11-26T00:15:00Z",
                      "LastModifiedDate": "2021-02-09T16:08:00Z"
                    }
                  ]
              }
             ]
            }
            """;

    String TRIVY_JSON_NO_FINDINGS = """
            {
             "matches": [
             ]
            }
            """;

    String EMPTY_JSON = "{}";

}
