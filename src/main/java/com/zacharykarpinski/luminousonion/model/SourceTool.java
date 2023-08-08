package com.zacharykarpinski.luminousonion.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape=JsonFormat.Shape.STRING)
public enum SourceTool {
    ANCORE_GRYPE ("Grype"),
    AQUA_TRIVY ("Trivy"),
    SNYK_SCA ("Snyk");





    private final String name;

    private SourceTool(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
