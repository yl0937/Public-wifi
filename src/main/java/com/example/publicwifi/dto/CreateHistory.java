package com.example.publicwifi.dto;

import com.example.publicwifi.domain.History;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class CreateHistory {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request{
        @NotNull
        private Double x;
        private Double y;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private Long id;

        private Double x;
        private Double y;

        private LocalDateTime createdAt;

        public static Response from(HistoryDto historyDto){
            return Response.builder()
                    .id(historyDto.getId())
                    .x(historyDto.getX())
                    .y(historyDto.getY())
                    .createdAt(historyDto.getCreatedAt())
                    .build();
        }
    }
}
