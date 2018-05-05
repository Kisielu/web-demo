package com.example.webdemo.service;

import com.example.webdemo.domain.CommitData;
import com.example.webdemo.domain.GithubData;
import com.example.webdemo.errorHandling.SDAException;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GitRepoService {

    private final static String URL = "https://api.github.com/repos/{owner}/{repo}";

    private RestTemplate restTemplate;

    @Autowired
    public GitRepoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GithubData getRepoByUserAndRepoName(String username, String repositoryName) {
        try {
            GithubData response = restTemplate.getForObject(URL,
                    GithubData.class, username, repositoryName);
            return response;
        } catch (HttpClientErrorException ex) {
            GithubData errorResponse = new GithubData();
            errorResponse.setError(ex.getMessage());
            return errorResponse;
        }
    }

    public List<CommitData> getCommitsByUserAndRepoName(String username, String repositoryName) {
        try {
            CommitData[] response = restTemplate.getForObject(URL + "/commits",
                    CommitData[].class, username, repositoryName);
            List<CommitData> commitDataList = Arrays.asList(response);
            return commitDataList.subList(0, 3);
        } catch (HttpClientErrorException ex) {
            throw new SDAException(ex.getMessage());
        }
    }
}
