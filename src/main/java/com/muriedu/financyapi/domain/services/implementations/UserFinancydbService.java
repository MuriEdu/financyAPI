package com.muriedu.financyapi.domain.services.implementations;

import com.muriedu.financyapi.DTOs.JWTAuthRequestDTO;
import com.muriedu.financyapi.DTOs.JWTAuthResponseDTO;
import com.muriedu.financyapi.DTOs.UserDTO;
import com.muriedu.financyapi.DTOs.UserRequestDTO;
import com.muriedu.financyapi.domain.entities.UserEntity;
import com.muriedu.financyapi.domain.repositories.UsersRepository;
import com.muriedu.financyapi.domain.services.UserService;
import com.muriedu.financyapi.exceptions.DataNotFoundedException;
import com.muriedu.financyapi.exceptions.InvalidCredentialException;
import com.muriedu.financyapi.exceptions.UserCreationException;
import com.muriedu.financyapi.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserFinancydbService implements UserService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SeasonDefaultService seasonService;


    @Override
    public UserEntity create(UserRequestDTO newUser) {
        UserEntity userToCreate = UserEntity.builder()
                .name(newUser.getName())
                .login(newUser.getLogin())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .build();

        try {
            return usersRepository.save(userToCreate);
        } catch (DataIntegrityViolationException ex){
            throw new UserCreationException("This login already exists");
        }
    }

    @Override
    public JWTAuthResponseDTO auth(JWTAuthRequestDTO credentials) {
        UserEntity user = loadByLogin(credentials.getLogin());
        boolean isVerified = passwordEncoder.matches(credentials.getPassword(), user.getPassword());
        if (isVerified) {
            String token = jwtService.generateToken(user);
            seasonService.create(user);
            return JWTAuthResponseDTO.builder()
                    .token(token)
                    .login(user.getLogin())
                    .name(user.getName())
                    .build();
        }
        throw new InvalidCredentialException("Incorrect password");
    }

    @Override
    public UserEntity loadByLogin(String login) {

        return usersRepository.findByLogin(login)
                .orElseThrow(() -> new DataNotFoundedException("User not founded"));

    }

    @Override
    public void delete(String login) {
        UserEntity user = loadByLogin(login);
        seasonService.deleteAllUserSeasons(user);
        usersRepository.delete(user);
    }

    @Override
    public void update(UserDTO user, String login) {

        UserEntity oldUser = loadByLogin(login);

        if(user.getLogin() != null) oldUser.setLogin(user.getLogin());
        if(user.getName() != null) oldUser.setName(user.getName());

        try {
            usersRepository.save(oldUser);
        } catch (Exception ex){
            throw new UserCreationException("This login already exists");
        }

    }

    @Override
    public String recoverToken(HttpHeaders request) {
        String header = request.get("Authorization").get(0);
        if (header == null) return null;
        if (header.startsWith("Bearer")) return header.replace("Bearer ", "");
        return null;
    }

}
