package com.example.webdemo.service;

import com.example.webdemo.domain.CommitData;
import com.example.webdemo.domain.GithubData;
import com.example.webdemo.domain.OwnerData;
import com.example.webdemo.errorHandling.SDAException;
import com.example.webdemo.repository.GithubDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitRepoServiceTest {

    private final static String URL = "https://api.github.com/repos/{owner}/{repo}";
    private final static String USER_NAME = "username";
    private final static String REPO_NAME = "repoName";
    private final static String FULL_NAME = USER_NAME + '/' + REPO_NAME;



    @Mock
    private GithubDataRepository githubDataRepository;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private GitRepoService gitRepoService;

    @Test
    public void shouldReturnValidResponseForQuery() {
        // given
        OwnerData ownerData = new OwnerData();
        ownerData.setLogin("test_login");
        ownerData.setSiteAdmin(false);

        GithubData githubData = new GithubData();
        githubData.setFullName("test_name");
        githubData.setOwner(ownerData);
        githubData.setDescription("test_description");

        when(restTemplate.getForObject(any(String.class), eq(GithubData.class),
                any(String.class), any(String.class))).thenReturn(githubData);
        when(githubDataRepository.existsByFullName(FULL_NAME)).thenReturn(false);
        // when
        GithubData underTest = gitRepoService.getRepoByUserAndRepoName(USER_NAME,
                REPO_NAME);
        // then
        assertThat(underTest.getFullName()).isEqualTo(githubData.getFullName());
    }

    @Test
    public void shouldGetErrorWhen4xxFromGithub() {
        // given
        String errorMessage = "test_error";

        when(restTemplate.getForObject(URL, GithubData.class,
                "testUser", "testRepo"))
                .thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN, errorMessage));
        //when
        GithubData underTest = gitRepoService.getRepoByUserAndRepoName("testUser",
                "testRepo");
        //then
        assertThat(underTest.getError())
                .isEqualTo(HttpStatus.FORBIDDEN.value() + " " + errorMessage);
    }

    @Test
    public void shouldReturnListOfCommits() {
        //given
        String url1 ="url_1";
        String url2 ="url_2";

        CommitData[] data = new CommitData[3];
        data[0] = new CommitData();
        data[1] = new CommitData();
        data[1].setUrl(url1);
        data[2] = new CommitData();
        data[2].setUrl(url2);

        when(restTemplate.getForObject(URL + "/commits", CommitData[].class,
                "testUser", "testRepo")).thenReturn(data);
        //when
        List<CommitData> underTest = gitRepoService
                .getCommitsByUserAndRepoName("testUser", "testRepo");
        //then
        assertThat(underTest.size()).isEqualTo(data.length);
        assertThat(underTest.stream()
                .map(CommitData::getUrl)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()))
                .containsExactlyInAnyOrder(url1, url2);
    }

    @Test(expected = SDAException.class)
    public void shouldThrowSDAException() {
        //given
        String errorMessage = "test_error";

        when(restTemplate.getForObject(URL + "/commits", CommitData[].class,
                "testUser", "testRepo"))
                .thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN, errorMessage));
        //when
        gitRepoService.getCommitsByUserAndRepoName("testUser",
                "testRepo");
    }
}
