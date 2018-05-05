package com.example.webdemo.web;

import com.example.webdemo.domain.CommitData;
import com.example.webdemo.domain.GithubData;
import com.example.webdemo.service.GitRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class GitRepoController {

    private GitRepoService gitRepoService;

    @Autowired
    public GitRepoController(GitRepoService gitRepoService) {
        this.gitRepoService = gitRepoService;
    }

    @GetMapping("/getRepository/{user}/{repositoryName}")
    public ResponseEntity<Object> getRepositoryByUserAndRepo(@PathVariable("user") String user,
                                                             @PathVariable("repositoryName") String repositoryName) {
        GithubData response = gitRepoService.getRepoByUserAndRepoName(user, repositoryName);
        if (response.getError() != null) {
            return new ResponseEntity<>(response.getError(), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getRepository/{user}/{repositoryName}/commits")
    public ResponseEntity<List<CommitData>> getCommitsForRepositoryByUserAndRepo(@PathVariable("user") String user,
                                                                           @PathVariable("repositoryName") String repositoryName) {
        List<CommitData> response = gitRepoService.getCommitsByUserAndRepoName(user, repositoryName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
