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
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String productOwner;
    private String productTeam;
    private String shortDescription;


    // External Identifiers
    private String jiraProjectKey;
    private String externalIdentifierExtra1;
    private String externalIdentifierExtra2;

    @ManyToOne
    @JoinColumn(name="org_id")
    private Organization org;

    // Date and times
    @CreationTimestamp
    private Date createTimestamp;
    @UpdateTimestamp
    private Date lastUpdateTimestamp;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Source> sources;

    @JsonIgnore
    public Set<Source> getSources() {
        return this.sources;
    }

    public void addSource(Source source) {
        if (sources == null) {
            this.sources = new HashSet<>();
        }
        source.setProduct(this);
        this.sources.add(source);
    }

}
