package ru.rodionov.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import ru.rodionov.controller.api.AuthControllerApi;
import ru.rodionov.dto.jwt.JwtResponse;
import ru.rodionov.dto.jwt.LoginRequest;
import ru.rodionov.security.jwt.JwtService;
import ru.rodionov.service.impl.UserServiceImpl;

@Controller
@RequiredArgsConstructor
public class AuthController implements AuthControllerApi {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userDetailsService;

    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        String jwt = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
