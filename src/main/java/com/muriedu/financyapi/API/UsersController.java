package com.muriedu.financyapi.API;

import com.muriedu.financyapi.DTOs.JWTAuthRequestDTO;
import com.muriedu.financyapi.DTOs.JWTAuthResponseDTO;
import com.muriedu.financyapi.DTOs.UserDTO;
import com.muriedu.financyapi.DTOs.UserRequestDTO;
import com.muriedu.financyapi.domain.entities.UserEntity;
import com.muriedu.financyapi.domain.services.implementations.ApplicationDefaultService;
import com.muriedu.financyapi.security.jwt.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/financyapi/users")
@RequiredArgsConstructor
public class UsersController {

    private final ApplicationDefaultService applicationService;
    private final JwtService jwtService;

    private String recoverToken(HttpHeaders request) {
        String header = request.get("Authorization").get(0);
        if (header == null) return null;
        if (header.startsWith("Bearer")) return header.replace("Bearer ", "");
        return null;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public UserDTO create(@RequestBody @Valid UserRequestDTO newUser){
        UserEntity createdUser = applicationService.createNewUser(newUser);
        return UserDTO.builder()
                .name(createdUser.getName())
                .login(createdUser.getLogin())
                .build();
    }

    @PostMapping("/auth")
    @ResponseStatus(OK)
    public JWTAuthResponseDTO auth (@RequestBody JWTAuthRequestDTO credentials){
        return applicationService.authUser(credentials);
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void delete(@RequestHeader HttpHeaders headers){
        String login = jwtService.getUserLogin(recoverToken(headers));
        applicationService.deleteUser(login);
    }

    @PatchMapping
    @ResponseStatus(NO_CONTENT)
    public void update(@RequestBody UserDTO user, @RequestHeader HttpHeaders headers){
        String login = jwtService.getUserLogin(recoverToken(headers));
        applicationService.updateUser(user, login);
    }




}
