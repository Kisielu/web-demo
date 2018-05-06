package com.example.webdemo.repository;

import com.example.webdemo.domain.AuthorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorDataRepository extends JpaRepository<AuthorData, Long> {
}
