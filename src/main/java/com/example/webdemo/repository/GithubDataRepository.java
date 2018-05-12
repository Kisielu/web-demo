package com.example.webdemo.repository;

import com.example.webdemo.domain.GithubData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GithubDataRepository extends JpaRepository<GithubData, Long> {

    GithubData getByFullName(String fullName);

    boolean existsByFullName(String fullName);
}
