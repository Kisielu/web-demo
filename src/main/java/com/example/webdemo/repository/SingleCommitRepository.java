package com.example.webdemo.repository;

import com.example.webdemo.domain.SingleCommit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleCommitRepository extends JpaRepository<SingleCommit, Long> {
}
