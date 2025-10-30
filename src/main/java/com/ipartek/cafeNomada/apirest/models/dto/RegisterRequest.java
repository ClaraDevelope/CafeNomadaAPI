package com.ipartek.cafeNomada.apirest.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    // Obligatorios (registro simple)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "El nombre no puede superar 80 caracteres")
    private String firstName;
    private String lastName;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    @Size(max = 120, message = "El email no puede superar 120 caracteres")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max= 30, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    // Opcionales (registro para el checkout del carrito de compra)
    @Size(max = 200, message = "La dirección no puede superar 200 caracteres")
    private String address;
    
    @Size(max = 80, message = "La ciudad no puede superar 80 caracteres")
    private String city;
    
    @Size(max = 80, message = "El país no puede superar 80 caracteres")
    private String country;
    
    @Size(max = 20, message = "El teléfono no puede superar 20 caracteres")
    private String phone;


    // Getters y Setters

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
