package com.muriedu.financyapi.API;

import com.muriedu.financyapi.DTOs.JWTAuthRequestDTO;
import com.muriedu.financyapi.DTOs.JWTAuthResponseDTO;
import com.muriedu.financyapi.DTOs.UserRequestDTO;
import com.muriedu.financyapi.DTOs.UserDTO;
import com.muriedu.financyapi.domain.entities.UserEntity;
import com.muriedu.financyapi.domain.services.implementations.SeasonDefaultService;
import com.muriedu.financyapi.domain.services.implementations.UserFinancydbService;
import com.muriedu.financyapi.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/financyapi/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserFinancydbService userService;
    private final SeasonDefaultService seasonService;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(CREATED)
    public UserDTO create(@RequestBody @Valid UserRequestDTO newUser){
        UserEntity createdUser = userService.create(newUser);
        seasonService.create(createdUser);
        return UserDTO.builder()
                .name(createdUser.getName())
                .login(createdUser.getLogin())
                .build();
    }

    @PostMapping("/auth")
    @ResponseStatus(OK)
    public JWTAuthResponseDTO auth (@RequestBody JWTAuthRequestDTO credentials){
        return userService.auth(credentials);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void delete(@RequestHeader HttpHeaders headers){
        String login = jwtService.getUserLogin(userService.recoverToken(headers));
        userService.delete(login);
    }

    @PatchMapping
    @ResponseStatus(NO_CONTENT)
    public void update(@RequestBody UserDTO user, @RequestHeader HttpHeaders headers){
        String login = jwtService.getUserLogin(userService.recoverToken(headers));
        userService.update(user, login);
    }




}
