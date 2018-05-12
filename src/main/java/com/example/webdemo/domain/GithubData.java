package com.example.webdemo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class GithubData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private OwnerData owner;
    @JsonProperty("full_name")
    @Column(unique = true)
    private String fullName;
    private String description;
    private String url;
    @JsonProperty("commits_url")
    private String commitsUrl;
    @JsonProperty("watchers_count")
    private Integer watchersCount;
    @JsonIgnore
    private String error;
}
