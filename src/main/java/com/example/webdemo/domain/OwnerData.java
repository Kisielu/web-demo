package com.example.webdemo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class OwnerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String login;
//    @JsonProperty("site_admin")
    private Boolean site_admin;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Boolean getSite_admin() {
        return site_admin;
    }

    public void setSite_admin(Boolean site_admin) {
        this.site_admin = site_admin;
    }
}
