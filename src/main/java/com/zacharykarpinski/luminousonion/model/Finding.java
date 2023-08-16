package com.zacharykarpinski.luminousonion.model;

import com.zacharykarpinski.luminousonion.model.shared.FindingSeverity;
import com.zacharykarpinski.luminousonion.model.shared.FindingStatus;
import com.zacharykarpinski.luminousonion.model.shared.FindingType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
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
    @Enumerated(EnumType.STRING)
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
    @Enumerated(EnumType.STRING)
    private FindingType findingType;
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


    @ManyToOne
    @JoinColumn(name="sourceId")
    private Source source;


    public void setDescription(String s) {
        int maxSize = 1024;
        if (s != null) {
            // Truncate the description if it's too large
            // TODO Fix this
            this.description = s.length() > maxSize ? s.substring(0, maxSize) : s;
        }

    }

}
