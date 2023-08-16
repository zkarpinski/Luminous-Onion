package com.zacharykarpinski.luminousonion.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape=JsonFormat.Shape.STRING)
public enum FindingSeverity {
    CRITICAL,
    HIGH,
    MEDIUM,
    LOW,
    INFORMATIONAL;
}
