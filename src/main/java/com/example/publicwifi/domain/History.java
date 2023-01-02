package com.example.publicwifi.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class History {
    @Id
    @GeneratedValue
    private Long id;

    private Double x;
    private Double y;

    public String getAll() {
        List<String> res = new ArrayList<String>();
        res.add(getId().toString());
        res.add(getX().toString());
        res.add(getY().toString());
        res.add(getCreatedAt().toString());
        return res.toString();
    }

    @CreatedDate
    private LocalDateTime createdAt;
}
