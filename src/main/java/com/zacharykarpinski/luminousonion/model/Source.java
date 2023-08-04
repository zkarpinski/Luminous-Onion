package com.zacharykarpinski.luminousonion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
public class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tool;
    private String toolVersion;
    private String target;
    private String targetType;

    @ManyToOne
    @JoinColumn(name="productId")
    private Product product;

    // Date and times
    @CreationTimestamp
    private Date createTimestamp;
    @UpdateTimestamp
    private Date lastUpdateTimestamp;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "source", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Finding> findings;

    public void addFinding(Finding f){
        if (this.findings == null)
            this.findings = new HashSet<>();

        findings.add(f);
        f.setSource(this);
    }


    @JsonIgnore
    public Set<Finding> getFindings() {
        return this.findings;
    }

    public void setFindings(Set<Finding> findings) {
        this.findings = findings;
        for (Finding f:findings) {
            f.setSource(this);
        }
    }

}
