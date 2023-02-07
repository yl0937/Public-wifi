package com.zerobase.weather.service;
import com.zerobase.weather.Repository.DateWeatherRepository;
import com.zerobase.weather.WeatherApplication;
import com.zerobase.weather.domain.DateWeather;
import com.zerobase.weather.domain.Diary;
import com.zerobase.weather.Repository.DiaryRepository;



import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.json.simple.parser.JSONParser;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zerobase.weather.config.secret.Secret.openweathermap;

@Service
@Transactional(readOnly = true)
public class DiaryService {

    private String apiKey = openweathermap;

    private final DiaryRepository diaryRepository;
    private final DateWeatherRepository dateWeatherRepository;

    public DiaryService(DiaryRepository diaryRepository, DateWeatherRepository dateWeatherRepository) {
        this.diaryRepository = diaryRepository;
        this.dateWeatherRepository = dateWeatherRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(WeatherApplication.class);

    @Transactional
    @Scheduled(cron = "0 0 1 * * * ")
    public void saveWeatherDate(){
        dateWeatherRepository.save(getWeatherFromApi());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createDiary(LocalDate date, String text){
        logger.info("started to create diary");
        DateWeather dateWeather = getDateWeather(date);

        Diary nowDiary = new Diary();
        nowDiary.setDateWeather(dateWeather);
        nowDiary.setText(text);
        diaryRepository.save(nowDiary);
        logger.info("end to create diary");
    }

    private DateWeather getWeatherFromApi(){
        String weatherData = getWeatherString();

        Map<String, Object> parsedWeather = parseWeather(weatherData);
        DateWeather dateWeather = new DateWeather();
        dateWeather.setDate(LocalDate.now());
        dateWeather.setWeather(parsedWeather.get("main").toString());
        dateWeather.setIcon(parsedWeather.get("icon").toString());
        dateWeather.setTemperature((Double)parsedWeather.get("temp"));

        return dateWeather;
    }

    private DateWeather getDateWeather(LocalDate date){
        List<DateWeather> dateWeatherListFromDB = dateWeatherRepository.findAllByDate(date);
        if(dateWeatherListFromDB.size()==0){
            return getWeatherFromApi();
        }
        else {
            return dateWeatherListFromDB.get(0);
        }
    }

    @Transactional(readOnly = true)
    public List<Diary> readDiary(LocalDate date){
        logger.debug("read Diary");
        return diaryRepository.findAllByDate(date);
    }

    public List<Diary> readDiaries(LocalDate startDate,LocalDate endDate){
        return diaryRepository.findAllByDateBetween(startDate,endDate);
    }

    public void updateDiary(LocalDate date,String text){
        Diary nowDiary = diaryRepository.getFirstByDate(date);
        nowDiary.setText(text);
        diaryRepository.save(nowDiary);
    }

    public void deleteDiary(LocalDate date){
        diaryRepository.deleteAllByDate(date);

    }

    private String getWeatherString(){
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=" + apiKey;

        try{
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            BufferedReader br;
            if(responseCode==200){
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine=br.readLine())!=null){
                response.append(inputLine);
            }
            br.close();
            return response.toString();
        } catch (Exception e){
            return "failed to get response";
        }
    }

    private Map<String, Object> parseWeather(String jsonString){
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try{
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        }catch (ParseException e){
            throw new RuntimeException(e);
        }
        Map<String,Object> resultMap = new HashMap<>();

        JSONObject mainData = (JSONObject) jsonObject.get("main");
        resultMap.put("temp",mainData.get("temp"));
        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");

        JSONObject weatherData = (JSONObject) weatherArray.get(0);
        resultMap.put("main",weatherData.get("main"));
        resultMap.put("icon",weatherData.get("icon"));

        return resultMap;
    }
}
