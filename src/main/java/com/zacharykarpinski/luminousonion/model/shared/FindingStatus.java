package com.zacharykarpinski.luminousonion.model.shared;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape=JsonFormat.Shape.STRING)
public enum FindingStatus {
    NEW ("New"),
    TRIAGING ("Triaging"),
    WIP ("In Progress"),
    IGNORED ("Ignored"),
    ACCEPTED ("Accepted"),
    RESOLVED ("Resolved"),
    ARCHIVED ("Archived"),
    DUPLICATE ("Duplicate");

    private final String name;

    private FindingStatus(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    @Override
    public String toString() {
        return this.name;
    }
}