package com.unilopers.cinema.controller;

import com.unilopers.cinema.dto.request.LoginRequestDTO;
import com.unilopers.cinema.dto.response.AuthResponseDTO;
import com.unilopers.cinema.model.Usuario;
import com.unilopers.cinema.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginDTO) {
        try {
            UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
            
            Authentication authentication = authenticationManager.authenticate(authToken);

            Usuario usuario = (Usuario) authentication.getPrincipal();

            String token = jwtTokenProvider.gerarToken(usuario);

            return ResponseEntity.ok(new AuthResponseDTO(token));
            
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).build();
        }
    }
}