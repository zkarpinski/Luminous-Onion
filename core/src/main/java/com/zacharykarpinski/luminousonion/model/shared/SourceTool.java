package com.zacharykarpinski.luminousonion.model.shared;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape=JsonFormat.Shape.STRING)
public enum SourceTool {
    ANCORE_GRYPE ("Grype"),
    AQUA_TRIVY ("Trivy"),
    DOCKER_SCOUT ("Docker Scout");
    //OTHER_EXTERNAL ("Other - Externally found"),
    //OTHER_INTERNAL ("Other - Internally found"),
    //SNYK_SCA ("Snyk");


    private final String name;

    private SourceTool(String s) {
        name = s;
    }

    public boolean equalsName(String altName) {
        return name.equals(altName);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
