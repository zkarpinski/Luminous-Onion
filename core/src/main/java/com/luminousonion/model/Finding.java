package com.luminousonion.model;

import com.luminousonion.model.shared.FindingSeverity;
import com.luminousonion.model.shared.FindingStatus;
import com.luminousonion.model.shared.FindingType;
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
    protected String description;
    private String shortDescription;
    @Enumerated(EnumType.STRING)
    private FindingStatus status = FindingStatus.NEW;

    // Package Data Members
    protected String packageName;
    protected String packagePath;
    protected String packageVersionFound;
    protected String packageVersionFixed;
    protected String purl;

    // Vulnerability Details
    private String findingIdentifier;
    @Enumerated(EnumType.STRING)
    private FindingSeverity severity = FindingSeverity.UNKNOWN;
    @Enumerated(EnumType.STRING)
    private FindingType findingType;
    private String primaryUrl;

    // Record keeping
    private String originalSeverity;

    // Date and times
    @CreationTimestamp
    private Date createTimestamp;
    @UpdateTimestamp
    private Date lastUpdateTimestamp;


    @ManyToOne
    @JoinColumn(name="source_id")
    private Source source;

    public Finding() {}
    public Finding(String title) {
        this.title = title;
    }


    public void setDescription(String s) {
        int maxSize = 1024;
        if (s != null) {
            // Truncate the description if it's too large
            this.description = s.length() > maxSize ? s.substring(0, maxSize) : s;
        }
    }

    public boolean isValid() {
        return (source != null && severity != null && findingIdentifier != null );
    }

}
