package com.zacharykarpinski.luminousonion.model;

import com.zacharykarpinski.luminousonion.model.status.FindingStatus;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(nullable = true, length = 1024)
    private String description;
    private FindingStatus status = FindingStatus.NEW;

    // Package Data Members
    private String packageName;
    private String packagePath;
    private String packageVersionFound;
    private String packageVersionFixed;
    private String purl;

    // Vulnerability Details
    private String findingIdentifier;
    @Enumerated(EnumType.STRING)
    private FindingSeverity severity;
    private String primaryUrl;

    // Record keeping
    private String originalSeverity;

    //
    // TODO: Added duplicate of field

    // Date and times
    @CreationTimestamp
    private Date createTimestamp;
    @UpdateTimestamp
    private Date lastUpdateTimestamp;

    private FindingTypes findingType;

    @ManyToOne
    @JoinColumn(name="sourceId")
    private Source source;

    private enum FindingTypes{
        OTHER,
        OS,
        PACKAGES,
        SNIPPET
    }

    public void setDescription(String s) {
        int maxSize = 1024;
        if (s != null) {
            // Truncate the description if it's too large
            // TODO Fix this
            this.description = s.length() > maxSize ? s.substring(0, maxSize) : s;
        }

    }

}
