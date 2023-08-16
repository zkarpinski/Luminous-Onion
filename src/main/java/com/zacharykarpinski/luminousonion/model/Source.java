package com.zacharykarpinski.luminousonion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zacharykarpinski.luminousonion.model.shared.SourceTool;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private SourceTool tool;
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

    // Overload setTool with a string param
    public void setTool(String tool) {
        this.tool = SourceTool.valueOf(tool);
    }
    public void setTool(SourceTool tool) {
        this.tool = tool;
    }


    public Source() {}
    public Source(SourceTool tool) {
        this.setTool(tool);
    }
    public Source(long id, SourceTool tool) {
        this.id = id;
        this.setTool(tool);
    }


}
