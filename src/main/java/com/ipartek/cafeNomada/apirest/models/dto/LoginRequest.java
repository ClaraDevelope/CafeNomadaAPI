package com.ipartek.cafeNomada.apirest.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {
    @NotBlank(message = "El email es obligatorio")
    @Size(max = 120, message = "El email no puede superar 120 caracteres")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max= 30, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    // Getters y Setters
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
