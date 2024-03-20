package com.muriedu.financyapi.domain.services.implementations;

import com.muriedu.financyapi.DTOs.*;
import com.muriedu.financyapi.domain.entities.*;
import com.muriedu.financyapi.domain.enums.AllocationTypes;
import com.muriedu.financyapi.domain.services.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ApplicationDefaultService implements ApplicationService {

    private final UserFinancydbService userService;
    private final SeasonDefaultService seasonService;
    private final CashDefaultService cashService;
    private final AllocationDefaultService allocationService;
    private final OperationDefaultService operationService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

}
