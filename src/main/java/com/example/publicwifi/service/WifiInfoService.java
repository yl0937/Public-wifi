package com.example.publicwifi.service;

import com.example.publicwifi.domain.WifiInfo;
import com.example.publicwifi.dto.WifiInfoDto;
import com.example.publicwifi.repository.WifiInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WifiInfoService {
    private final WifiInfoRepository wifiInfoRepository;

    @Transactional
    public WifiInfoDto createWifiInfo(
            String inoutdoor,
            String installFloor,
            String installmby,
            String remars3,
            String instlTy,
            String mgrNo,
            String wrdofc,
            String adres1,
            String adres2,
            String cmcwr,
            String dttm,
            String svcSe,
            String mainNm,
            Double lnt,
            String cnstcYear,
            Double lat
    ){
        return WifiInfoDto.fromEntity(
                wifiInfoRepository.save(WifiInfo.builder()
                        .inoutdoor(inoutdoor)
                        .installFloor(installFloor)
                        .installmby(installmby)
                        .remars3(remars3)
                        .instlTy(instlTy)
                        .mgrNo(mgrNo)
                        .wrdofc(wrdofc)
                        .adres1(adres1)
                        .adres2(adres2)
                        .cmcwr(cmcwr)
                        .dttm(dttm)
                        .svcSe(svcSe)
                        .mainNm(mainNm)
                        .lnt(lnt)
                        .cnstcYear(cnstcYear)
                        .lat(lat)
                        .build())
        );
    }
}
