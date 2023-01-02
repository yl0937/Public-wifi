package com.example.publicwifi.service;

import com.example.publicwifi.domain.History;
import com.example.publicwifi.dto.HistoryDto;
import com.example.publicwifi.repository.HistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    @Transactional
    public HistoryDto createHistory(Double x,Double y){

        return HistoryDto.fromEntity(
                historyRepository.save(History.builder()
                        .x(x)
                        .y(y)
                        .createdAt(LocalDateTime.now())
                        .build())
        );
    }
}
