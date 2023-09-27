package com.luminousonion.model.shared;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape=JsonFormat.Shape.STRING)
public enum FindingType {
    OTHER,
    OS,
    PACKAGES,
    SNIPPET,
    BINARY
}
