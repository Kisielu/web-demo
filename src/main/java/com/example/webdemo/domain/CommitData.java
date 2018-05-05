package com.example.webdemo.domain;

import org.springframework.stereotype.Component;

@Component
public class CommitData {

    private String url;
    private SingleCommit commit;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SingleCommit getCommit() {
        return commit;
    }

    public void setCommit(SingleCommit commit) {
        this.commit = commit;
    }
}
