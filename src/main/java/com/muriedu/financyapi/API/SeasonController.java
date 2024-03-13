package com.muriedu.financyapi.API;

import com.muriedu.financyapi.DTOs.SeasonResponseDTO;
import com.muriedu.financyapi.DTOs.SeasonDTO;
import com.muriedu.financyapi.domain.entities.SeasonEntity;
import com.muriedu.financyapi.domain.entities.UserEntity;
import com.muriedu.financyapi.domain.services.implementations.SeasonDefaultService;
import com.muriedu.financyapi.domain.services.implementations.UserFinancydbService;
import com.muriedu.financyapi.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("financyapi/seasons")
public class SeasonController {


    @Autowired
    private SeasonDefaultService seasonService;

    @Autowired
    private UserFinancydbService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public SeasonResponseDTO getSeasonByDate(@RequestBody SeasonDTO seasonDTO, @RequestHeader HttpHeaders headers){
        String login = jwtService.getUserLogin(userService.recoverToken(headers));
        UserEntity user = userService.loadByLogin(login);
        SeasonEntity season = seasonService.getSeasonByDate(user, seasonDTO);
        return SeasonResponseDTO.builder()
                .id(season.getId().toString())
                .month(season.getMonth())
                .year(season.getYear())
                .build();
    }

    @GetMapping("/all")
    public List<SeasonResponseDTO> getAllSeasonByUser(@RequestHeader HttpHeaders headers){
        String login = jwtService.getUserLogin(userService.recoverToken(headers));
        UserEntity user = userService.loadByLogin(login);
        return seasonService.getAllSeasons(user).stream()
                .map(season ->
                        SeasonResponseDTO.builder()
                        .month(season.getMonth())
                        .year(season.getYear())
                        .id(season.getId().toString())
                        .build()
                ).toList();
    }


}
