package com.muriedu.financyapi.API;

import com.muriedu.financyapi.DTOs.JWTAuthRequestDTO;
import com.muriedu.financyapi.DTOs.JWTAuthResponseDTO;
import com.muriedu.financyapi.DTOs.UserRequestDTO;
import com.muriedu.financyapi.DTOs.UserDTO;
import com.muriedu.financyapi.domain.entities.UserEntity;
import com.muriedu.financyapi.domain.services.implementations.UserFinancydbService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/financyapi/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserFinancydbService userService;

    @PostMapping
    @ResponseStatus(CREATED)
    public UserDTO create(@RequestBody @Valid UserRequestDTO newUser){
        UserEntity createdUser = userService.create(newUser);
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
    public void delete(@RequestBody UserDTO user){
        userService.delete(user.getLogin());
    }

}
