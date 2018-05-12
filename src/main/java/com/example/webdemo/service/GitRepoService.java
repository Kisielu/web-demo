package com.example.webdemo.service;

import com.example.webdemo.domain.CommitData;
import com.example.webdemo.domain.GithubData;
import com.example.webdemo.errorHandling.SDAException;
import com.example.webdemo.repository.GithubDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GitRepoService {

    private final static String URL = "https://api.github.com/repos/{owner}/{repo}";

    private RestTemplate restTemplate;
    private GithubDataRepository githubDataRepository;

    @Autowired
    public GitRepoService(RestTemplate restTemplate,
                          GithubDataRepository githubDataRepository) {
        this.restTemplate = restTemplate;
        this.githubDataRepository = githubDataRepository;
    }

    @Transactional
    public GithubData getRepoByUserAndRepoName(String username, String repositoryName) {
        try {
            GithubData response;
            String fullName = String.format("%s/%s", username, repositoryName);
            if (githubDataRepository.existsByFullName(fullName)) {
                response = githubDataRepository.getByFullName(fullName);
            } else {
                response = restTemplate.getForObject(URL,
                        GithubData.class, username, repositoryName);
                githubDataRepository.save(response);
            }
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
            return commitDataList.size() > 3 ? commitDataList.subList(0,3)
                    : commitDataList;
        } catch (HttpClientErrorException ex) {
            throw new SDAException(ex.getMessage());
        }
    }
}
