package com.muriedu.financyapi.API;

import com.muriedu.financyapi.domain.services.implementations.SeasonDefaultService;
import com.muriedu.financyapi.domain.services.implementations.UserFinancydbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("financyapi/seasons")
public class SeasonController {

    @Autowired
    private SeasonDefaultService seasonService;

    @Autowired
    private UserFinancydbService userService;

//    @GetMapping
//    public List<SeasonResponseDTO> findAllSeasons(String login){
//        UserEntity user = userService.loadByLogin(login);
//
//        return seasonService.getAllSeasons(user).stream().map((entity) -> {
//                    return SeasonResponseDTO.builder()
//                            .month(entity.getMonth())
//                            .year(entity.getYear())
//                            .build();
//                })
//                .toList();
//    }


}
