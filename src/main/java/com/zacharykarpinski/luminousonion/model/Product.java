package com.zacharykarpinski.luminousonion.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String productOwner;
    private String productTeam;


    // External Identifiers
    private String jiraProjectKey;
    private String pegaProductId;
    private String externalIdentifierExtra1;
    private String externalIdentifierExtra2;


    // Date and times
    @CreationTimestamp
    private Date createTimestamp;
    @UpdateTimestamp
    private Date lastUpdateTimestamp;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Source> sources;

}
