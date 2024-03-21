package com.muriedu.financyapi.domain.services.implementations;

import com.muriedu.financyapi.DTOs.*;
import com.muriedu.financyapi.domain.entities.*;
import com.muriedu.financyapi.domain.enums.AllocationTypes;
import com.muriedu.financyapi.domain.services.ApplicationService;
import com.muriedu.financyapi.exceptions.SetCashException;
import com.muriedu.financyapi.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ApplicationDefaultService implements ApplicationService {

    private final UserFinancydbService userService;
    private final SeasonDefaultService seasonService;
    private final CashDefaultService cashService;
    private final AllocationDefaultService allocationService;
    private final OperationDefaultService operationService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // USER OPERATIONS __________________________________________________

    @Override
    public UserEntity createNewUser(UserRequestDTO newUser) {
        UserEntity userToSave = UserEntity.builder()
                .name(newUser.getName())
                .login(newUser.getLogin())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .build();

            UserEntity savedUser = userService.create(userToSave);
            SeasonEntity savedSeason = seasonService.create(savedUser);
            CashEntity savedCash = cashService.create(savedSeason);
            allocationService.create(savedCash, AllocationTypes.EARNS);
            allocationService.create(savedCash, AllocationTypes.SPENDS);

            return savedUser;
    }

    @Override
    public JWTAuthResponseDTO authUser(JWTAuthRequestDTO credentials) {
        JWTAuthResponseDTO token = userService.auth(credentials);
        UserEntity user = userService.loadByLogin(token.getLogin());
        if (!verifyIfExistsSeasonNow(user)) seasonService.create(user);
        return token;
    }

    @Override
    public void deleteUser(String login) {
        UserEntity userToDelete = userService.loadByLogin(login);
        List<SeasonEntity> seasonsToDelete = seasonService.getAllSeasons(userToDelete);
        List<CashEntity> cashesToDelete = seasonsToDelete.stream().map(cashService::getBySeason).toList();

        List<AllocationEntity> allocationsToDelete = new ArrayList<>();
        cashesToDelete.forEach(cash -> {
            allocationsToDelete.addAll(allocationService.getAllByCash(cash));
        });

        List<OperationEntity> operationsToDelete = new ArrayList<>();
        allocationsToDelete.forEach(allocation -> {
            operationsToDelete.addAll(operationService.getAllByAllocation(allocation));
        });

        operationService.deleteAll(operationsToDelete);
        allocationService.deleteAll(allocationsToDelete);
        cashService.deleteAll(cashesToDelete);
        seasonService.deleteAll(seasonsToDelete);
        userService.delete(userToDelete.getLogin());

    }

    @Override
    public void updateUser(UserDTO userDTO, String crrLogin) {
        userService.update(userDTO, crrLogin);
    }

    @Override
    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    // SEASON OPERATIONS ________________________________________________

    @Override
    public List<SeasonResponseDTO> getAllUserSeason(String login) {
        UserEntity user = userService.loadByLogin(login);
        return seasonService.getAllSeasons(user).stream().map(season -> {
            return SeasonResponseDTO.builder()
                    .id(season.getId().toString())
                    .month(season.getMonth())
                    .year(season.getYear())
                    .build();
        }).toList();
    }

    @Override
    public SeasonResponseDTO getCurrentSeason(String login) {
        UserEntity user = userService.loadByLogin(login);
        SeasonEntity season = getEntityOfCurrentSeason(user);
        return SeasonResponseDTO.builder()
                .id(season.getId().toString())
                .month(season.getMonth())
                .year(season.getYear())
                .build();
    }

    @Override
    public SeasonDataDTO getSeasonData(String login, String SeasonId) {
        UserEntity user = userService.loadByLogin(login);
        SeasonEntity season = seasonService.getById(user, UUID.fromString(SeasonId));
        CashEntity cash = cashService.getBySeason(season);
        List<AllocationEntity> allocations = allocationService.getAllByCash(cash);

        CashDataDTO cashData = CashDataDTO.builder()
                .id(cash.getId().toString())
                .cash(cash.getCash())
                .total(cash.getTotal())
                .build();
        List<AllocationDataDTO> allocationsData = allocations.stream().map(allocation -> {
            return AllocationDataDTO.builder()
                    .id(allocation.getId().toString())
                    .name(allocation.getName())
                    .crrCash(allocation.getCrrCash())
                    .total(allocation.getTotal())
                    .build();
        }).toList();

        return SeasonDataDTO.builder()
                .month(season.getMonth())
                .year(season.getYear())
                .cash(cashData)
                .allocations(allocationsData)
                .build();

    }

    // CASH OPERATIONS __________________________________________________

    @Override
    public void setCash(String login, String cashId, BigDecimal valueToSet) {
        UserEntity user = userService.loadByLogin(login);
        SeasonEntity crrSeason = getEntityOfCurrentSeason(user);
        CashEntity cash = cashService.getBySeason(crrSeason);

        if(cash.getTotal().compareTo(valueToSet) < 0) cash.setNonAllocated(valueToSet);
        else throw new SetCashException("The value need to be higher then allocated cash");

    }








    //APPLICATION SERVICE TOOLS

    private boolean verifyIfExistsSeasonNow(UserEntity user){
        LocalDateTime now = LocalDateTime.now();
        SeasonDTO seasonNow = SeasonDTO.builder()
                .month(now.getMonthValue())
                .year(now.getYear())
                .build();
        try {
            seasonService.getSeasonByDate(user, seasonNow);
            return true;
        } catch (Exception e){
            return false;
        }


    };

    private SeasonEntity getEntityOfCurrentSeason(UserEntity user){
        LocalDateTime now = LocalDateTime.now();
        SeasonDTO crrSeason = SeasonDTO.builder()
                .month(now.getMonthValue())
                .year(now.getYear())
                .build();

        return seasonService.getSeasonByDate(user, crrSeason);
    }

}
