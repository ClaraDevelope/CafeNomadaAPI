package com.ipartek.cafeNomada.apirest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.cafeNomada.apirest.models.dto.AuthResponse;
import com.ipartek.cafeNomada.apirest.models.dto.LoginRequest;
import com.ipartek.cafeNomada.apirest.models.dto.RegisterRequest;
import com.ipartek.cafeNomada.apirest.models.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    //Constructor inyectando el servicio como dependencia
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    //ResponseEntity<?> para manejar diferentes tipos de respuestas HTTP
    //RequestBody para mapear el cuerpo de la solicitud al objeto RegisterRequest
    //Manejo de excepciones para devolver respuestas adecuadas
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        try {
            AuthResponse response = authService.register(request);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
           
        }
    }


    @PostMapping("/login")
    //Valid para validar el objeto LoginRequest antes de procesarlo
    //Manejo de excepciones para devolver respuestas adecuadas
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
