package com.zacharykarpinski.luminousonion.parser;

import com.zacharykarpinski.luminousonion.model.shared.FindingSeverity;
import com.zacharykarpinski.luminousonion.model.shared.FindingType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractParserFinding {
    // Members **should** match the Finding model
    protected String title;
    protected String description;

    protected String packageName;
    protected String packagePath;
    protected String packageVersionFound;
    protected String packageVersionFixed;
    protected String purl;

    protected String findingIdentifier;
    protected FindingSeverity severity;
    @Enumerated(EnumType.STRING)
    protected FindingType findingType;
    protected String originalSeverity;
    protected String primaryUrl;

    public String getTitle() {
        return "%s:%s | %s".formatted(packageName, packageVersionFound, findingIdentifier);
    }

    public void setSeverity(String severity) {
        this.originalSeverity = severity;
        this.severity = switch (severity.toUpperCase()) {
            case ("CRITICAL") -> FindingSeverity.CRITICAL;
            case ("HIGH") -> FindingSeverity.HIGH;
            case ("MEDIUM") -> FindingSeverity.MEDIUM;
            case ("LOW") -> FindingSeverity.LOW;
            default -> FindingSeverity.INFORMATIONAL;
        };
    }

}
