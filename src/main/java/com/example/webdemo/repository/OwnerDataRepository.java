package com.example.webdemo.repository;

import com.example.webdemo.domain.OwnerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerDataRepository extends JpaRepository<OwnerData, Long> {

    OwnerData getByLogin(String login);

    boolean existsByLogin(String login);
}
