package com.example.publicwifi.repository;

import com.example.publicwifi.domain.WifiInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WifiInfoRepository extends JpaRepository<WifiInfo,Long> {
    Optional<WifiInfo> findFirstByOrderByIdDesc();

}
