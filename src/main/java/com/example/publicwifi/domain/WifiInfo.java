package com.example.publicwifi.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class WifiInfo {

    @Id
    @GeneratedValue
    private Long id;

    //"x_swifi_inout_door":"실내",
    private String inoutdoor;
    // "x_swifi_instl_floor":""
    private String installFloor;
    // ,"x_swifi_instl_mby":"서울시(AP)"
    private String installmby;
    // ,"x_swifi_remars3":"",
    private String remars3;
    // "x_swifi_instl_ty":"7-1. 공공 - 행정"
    private String instlTy;
    // ,"x_swifi_mgr_no":"ARI00001"
    private String mgrNo;
    // ,"x_swifi_wrdofc":"서대문구"
    private String wrdofc;
    // ,"x_swifi_adres1":"서소문로 51"
    private String adres1;
    // ,"x_swifi_adres2":"본관 1F",
    private String adres2;
    // "x_swifi_cmcwr":"수도사업소자가망",
    private String cmcwr;
    // "work_dttm":1673661490000,
    private String dttm;
    // "x_swifi_svc_se":"공공WiFi",
    private String svcSe;
    // "x_swifi_main_nm":"상수도사업본부",
    private String mainNm;
    // "lnt":"37.561924",
    private  String lnt;
    // "x_swifi_cnstc_year":"2014",
    private String cnstcYear;
    // "lat":"126.96675"
    private String lat;
    public String getAll(){
        List<String> res = new ArrayList<String>();
        res.add(getId().toString());
        res.add(getInoutdoor());
        res.add(getInstallFloor());
        res.add(getInstallmby());
        res.add(getRemars3());
        res.add(getInstlTy());
        res.add(getMgrNo());
        res.add(getWrdofc());
        res.add(getAdres1());
        res.add(getAdres2());
        res.add(getCmcwr());
        res.add(getDttm());
        res.add(getSvcSe());
        res.add(getMainNm());
        res.add(getLnt());
        res.add(getCnstcYear());
        res.add(getLat());
        return res.toString();
    }

}
