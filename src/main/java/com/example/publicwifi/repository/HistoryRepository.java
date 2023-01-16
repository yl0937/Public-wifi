package com.example.publicwifi.repository;

import com.example.publicwifi.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    Optional<History> findFirstByOrderByIdDesc();


}
