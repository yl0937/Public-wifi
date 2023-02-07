package com.zerobase.weather.controller;

import com.zerobase.weather.domain.Diary;
import com.zerobase.weather.service.DiaryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService){
        this.diaryService = diaryService;
    }


    @ApiOperation(value = "일기 텍스트와 날씨를 이용해서 DB에 일기 저장",notes = "이것은 노트")
    @PostMapping("/create/diary")
    void createDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestBody String text){
    diaryService.createDiary(date,text);
    }

    @ApiOperation(value = "선택한 모든 날짜의 모든 일기를 가져옵니다.")
    @GetMapping("/read/diary")
    List<Diary> readDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return diaryService.readDiary(date);
    }

    @ApiOperation(value = "선택한 기간중의 모든 일기 데이터를 가져옵니다.")
    @GetMapping("/read/diaries")
    List<Diary> readDiaris(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @ApiParam(value = "조회할 기간의 첫번째날", example = "2023-02-06")LocalDate startDate,
                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @ApiParam(value = "조회할 기간의 마지막날", example = "2023-02-07") LocalDate endDate){
        return diaryService.readDiaries(startDate,endDate);
    }

    @ApiOperation(value = "선택한 날짜의 첫번째 데이터의 일기를 수정합니다.")
    @PutMapping("/update/diary")
    void updateDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestBody String text){
         diaryService.updateDiary(date,text);
    }

    @ApiOperation(value = "선택한 날짜의 모든 데이터를 삭제합니다.")
    @DeleteMapping("/delete/diary")
    void updateDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        diaryService.deleteDiary(date);
    }

}
