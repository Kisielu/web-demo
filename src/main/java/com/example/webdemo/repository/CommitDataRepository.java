package com.example.webdemo.repository;

import com.example.webdemo.domain.CommitData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitDataRepository extends JpaRepository<CommitData, Long> {
}
