package com.example.webdemo.service;

import com.example.webdemo.domain.CommitData;
import com.example.webdemo.domain.GithubData;
import com.example.webdemo.errorHandling.SDAException;
import com.example.webdemo.repository.CommitDataRepository;
import com.example.webdemo.repository.GithubDataRepository;
import com.example.webdemo.repository.OwnerDataRepository;
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
    private CommitDataRepository commitDataRepository;
    private OwnerDataRepository ownerDataRepository;

    @Autowired
    public GitRepoService(RestTemplate restTemplate,
                          GithubDataRepository githubDataRepository,
                          CommitDataRepository commitDataRepository,
                          OwnerDataRepository ownerDataRepository) {
        this.restTemplate = restTemplate;
        this.githubDataRepository = githubDataRepository;
        this.commitDataRepository = commitDataRepository;
        this.ownerDataRepository = ownerDataRepository;
    }

    @Transactional
    public GithubData getRepoByUserAndRepoName(String username, String repositoryName) {
        try {
            String fullName = getFullName(username, repositoryName);
            if (githubDataRepository.existsByFullName(fullName)) {
                return githubDataRepository.getByFullName(fullName);
            } else {
                GithubData response = restTemplate.getForObject(URL,
                        GithubData.class, username, repositoryName);
                if (ownerDataRepository.existsByLogin(response.getOwner().getLogin())) {
                    response.setOwner(ownerDataRepository.getByLogin(response.getOwner().getLogin()));
                }
                githubDataRepository.save(response);
                return response;
            }
        } catch (HttpClientErrorException ex) {
            GithubData errorResponse = new GithubData();
            errorResponse.setError(ex.getMessage());
            return errorResponse;
        }
    }

    public List<CommitData> getCommitsByUserAndRepoName(String username, String repositoryName) {
        try {
            String fullName = getFullName(username, repositoryName);
            if (commitDataRepository.existsByUrlContaining(fullName)) {
                return commitDataRepository.getAllByUrlContaining(fullName);
            } else {
                CommitData[] response = restTemplate.getForObject(URL + "/commits",
                        CommitData[].class, username, repositoryName);
                List<CommitData> commitDataList = Arrays.asList(response);
                commitDataList = commitDataList.size() > 3 ? commitDataList.subList(0, 3)
                        : commitDataList;
                commitDataRepository.saveAll(commitDataList);
                return commitDataList;
            }
        } catch (HttpClientErrorException ex) {
            throw new SDAException(ex.getMessage());
        }
    }


    private static String getFullName(String userName, String repoName) {
        return String.format("%s/%s", userName, repoName);
    }
}
