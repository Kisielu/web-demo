package com.example.webdemo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.stereotype.Component;

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
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private OwnerData owner;
    private String full_name;
    private String description;
    private String url;
    private String commits_url;
    private Integer watchers_count;
    @JsonIgnore
    private String error;
}
