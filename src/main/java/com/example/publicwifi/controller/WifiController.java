package com.example.publicwifi.controller;

import com.example.publicwifi.domain.History;
import com.example.publicwifi.dto.CreateHistory;
import com.example.publicwifi.dto.HistoryDto;
import com.example.publicwifi.repository.HistoryRepository;
import com.example.publicwifi.service.HistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class WifiController {
    private final HistoryRepository historyRepository;

    private final HistoryService historyService;

    @GetMapping("/history")
    public String getHistoryPage(){
        return "redirect:/history.html";
    }

    @GetMapping("/home")
    public String getHomePage(){
        return "check";
    }

    @ResponseBody
    @PostMapping("/search")
    public CreateHistory.Response createHistory(
            @RequestBody @Valid CreateHistory.Request request
    ){

        return CreateHistory.Response.from(
                historyService.createHistory(
                        request.getX(),
                        request.getY()
                )
        );

    }

    @GetMapping("get-history")
    public String getHistory(){
        Long cnt = historyRepository.count();
        List<String> res = new ArrayList<String>();

        for(Long i = 1L; i<cnt+1; i++){
            List<History> histories = historyRepository.findAllById(Collections.singleton(i));
            res.add(histories.stream()
                    .map(History::getAll)
                    .collect(Collectors.toList()).toString());
        }
        return res.toString();
    }



}
