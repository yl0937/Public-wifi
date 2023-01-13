package com.example.publicwifi.controller;
import java.io.InputStreamReader;
import java.net.*;
import java.io.BufferedReader;

import com.example.publicwifi.domain.History;
import com.example.publicwifi.dto.CreateHistory;
import com.example.publicwifi.repository.HistoryRepository;
import com.example.publicwifi.service.HistoryService;
import com.mysql.cj.xdevapi.JsonArray;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class WifiController {
    private final HistoryRepository historyRepository;


    private final HistoryService historyService;

    @GetMapping(value = "/history")
    public String getHistoryPage(){
        return "history";
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

    @GetMapping("wifi-test")
    @ResponseBody
    public String loadJsonFromApi(HttpServletRequest request){
        String result = "";
        try{
            URL url = new URL("http://openapi.seoul.go.kr:8088/426d79594a656c6939314971576673/json/TbPublicWifiInfo/1/1/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
            result = bf.readLine();
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
//                System.out.println(rd.readLine());
////                System.out.println(rd.readLine());
            }
            rd.close();
            conn.disconnect();
            System.out.println(sb.toString());

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(sb.toString());
            JSONObject jsonObject = (JSONObject) obj;
            String TbPublicWifiInfo = (String) jsonObject.get("TbPublicWifiInfo").toString();


            Object obj1 = jsonParser.parse(TbPublicWifiInfo);
            JSONObject jsonObject1 = (JSONObject) obj1;
            System.out.println(TbPublicWifiInfo);

            JSONObject jsonObject2 = new JSONObject(jsonObject1);
//            JSONArray jsonArray = jsonObject2.get("row");
//            String row = (String) jsonObject1.get("row").toString();
//            JSONObject jsonArray = (JSONObject) jsonObject1.get("row");
//            System.out.println(jsonArray);
            return "X_SWIFI_MGR_NO";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
