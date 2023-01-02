package com.example.publicwifi.dto;

import com.example.publicwifi.domain.History;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryDto {
    private Long id;
    private Double x;

    private Double y;

    private LocalDateTime createdAt;

    public static HistoryDto fromEntity(History history){
        return HistoryDto.builder()
                .id(history.getId())
                .x(history.getX())
                .y(history.getY())
                .createdAt(history.getCreatedAt())
                .build();
    }
}
