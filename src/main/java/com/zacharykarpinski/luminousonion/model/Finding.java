package com.zacharykarpinski.luminousonion.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class Finding {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="sourceId",nullable = false)
    private Source source;

    @Column(nullable = false, length = 255)
    private String description;

}
