package com.zacharykarpinski.luminousonion.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Data
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Finding {
    @Id
    @GeneratedValue
    private Long id;

    private String findingIdentifier;

    @ManyToOne
    @JoinColumn(name="sourceId")//,nullable = false)
    private Source source;

    private String title;
    @Column(nullable = true, length = 1024)
    private String description;

    // Package Data Members
    private String packageName;
    private String packagePath;
    private String packageVersionFound;
    private String packageVersionFixed;

    private String sourceText;
    private String sourceTool;

    // Date and times
    @CreationTimestamp
    private Date createTimestamp;
    @UpdateTimestamp
    private Date lastUpdateTimestamp;

    private FindingTypes findingType;

    private enum FindingTypes{
        other,
        os,
        packages,
        snippet
    }

    public void setDescription(String s) {
        // Truncate the description if it's too large
        // TODO Fix this
        this.description = s.length() > 1024 ? s.substring(0,1024) : s;

    }

}
