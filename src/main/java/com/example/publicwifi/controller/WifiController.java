package com.example.publicwifi.controller;
import java.io.InputStreamReader;
import java.net.*;
import java.io.BufferedReader;

import com.example.publicwifi.domain.History;
import com.example.publicwifi.dto.CreateHistory;
import com.example.publicwifi.dto.CreateWifiInfo;
import com.example.publicwifi.repository.HistoryRepository;
import com.example.publicwifi.repository.WifiInfoRepository;
import com.example.publicwifi.service.HistoryService;
import com.example.publicwifi.service.WifiInfoService;
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
    private final WifiInfoRepository wifiInfoRepository;


    private final HistoryService historyService;
    private final WifiInfoService wifiInfoService;
    @RequestMapping(value = "/history")
    public String redirect() {
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
        try{
            for(int i =1;i<1001;i++)
            {
                URL url = new URL("http://openapi.seoul.go.kr:8088/426d79594a656c6939314971576673/json/TbPublicWifiInfo/"+i+"/"+i+"/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");
                BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
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
                }
                rd.close();
                conn.disconnect();
                JSONParser jsonParser = new JSONParser();
                Object obj = jsonParser.parse(sb.toString());
                JSONObject jsonObject = (JSONObject) obj;
                String TbPublicWifiInfo = (String) jsonObject.get("TbPublicWifiInfo").toString();


                Object obj1 = jsonParser.parse(TbPublicWifiInfo);
                JSONObject jsonObject1 = (JSONObject) obj1;
                JSONArray jsonArray = (JSONArray) jsonObject1.get("row");

                Object obj2 = jsonArray.get(0);
                JSONObject jsonObject2 = (JSONObject) obj2;

                String X_SWIFI_INOUT_DOOR = (String) jsonObject2.get("X_SWIFI_INOUT_DOOR");
                String X_SWIFI_INSTL_FLOOR = (String) jsonObject2.get("X_SWIFI_INSTL_FLOOR");
                String X_SWIFI_INSTL_MBY = (String) jsonObject2.get("X_SWIFI_INSTL_MBY");
                String X_SWIFI_REMARS3 = (String) jsonObject2.get("X_SWIFI_REMARS3");
                String X_SWIFI_INSTL_TY = (String) jsonObject2.get("X_SWIFI_INSTL_TY");
                String X_SWIFI_MGR_NO = (String) jsonObject2.get("X_SWIFI_MGR_NO");
                String X_SWIFI_WRDOFC = (String) jsonObject2.get("X_SWIFI_WRDOFC");
                String X_SWIFI_ADRES1 = (String) jsonObject2.get("X_SWIFI_ADRES1");
                String X_SWIFI_ADRES2 = (String) jsonObject2.get("X_SWIFI_ADRES2");
                String X_SWIFI_CMCWR = (String) jsonObject2.get("X_SWIFI_CMCWR");
                String WORK_DTTM = (String) jsonObject2.get("WORK_DTTM");
                String X_SWIFI_SVC_SE = (String) jsonObject2.get("X_SWIFI_SVC_SE");
                String X_SWIFI_MAIN_NM = (String) jsonObject2.get("X_SWIFI_MAIN_NM");
                String LNT = (String) jsonObject2.get("LNT");
                String X_SWIFI_CNSTC_YEAR = (String) jsonObject2.get("X_SWIFI_CNSTC_YEAR");
                String LAT = (String) jsonObject2.get("LAT");
                CreateWifiInfo.Response.from(
                        wifiInfoService.createWifiInfo(
                        X_SWIFI_INOUT_DOOR,
                        X_SWIFI_INSTL_FLOOR,
                        X_SWIFI_INSTL_MBY,
                        X_SWIFI_REMARS3,
                        X_SWIFI_INSTL_TY,
                        X_SWIFI_MGR_NO,
                        X_SWIFI_WRDOFC,
                        X_SWIFI_ADRES1,
                        X_SWIFI_ADRES2,
                        X_SWIFI_CMCWR,
                        WORK_DTTM,
                        X_SWIFI_SVC_SE,
                        X_SWIFI_MAIN_NM,
                        LNT,
                        X_SWIFI_CNSTC_YEAR,
                        LAT
                        )
                );



            }




            return "SUCCESS";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
