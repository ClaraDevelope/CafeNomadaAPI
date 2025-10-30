package com.ipartek.cafeNomada.apirest.models.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ipartek.cafeNomada.apirest.models.dao.CustomerRepository;
import com.ipartek.cafeNomada.apirest.models.dto.AuthResponse;
import com.ipartek.cafeNomada.apirest.models.dto.LoginRequest;
import com.ipartek.cafeNomada.apirest.models.dto.RegisterRequest;
import com.ipartek.cafeNomada.apirest.models.entities.Customer;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Constructor inyectando el repositorio de clientes o la dependencia.
    public AuthService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public AuthResponse register(RegisterRequest request) {
    // Verificar si el email ya está registrado
    if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
        throw new IllegalArgumentException("El email ya está en uso");
    }

    // Crear un nuevo cliente
    Customer customer = new Customer();

    //datos obligatorios para el registro
    customer.setFirstName(request.getFirstName());
    customer.setLastName(request.getLastName());
    customer.setEmail(request.getEmail());
    customer.setPassword(passwordEncoder.encode(request.getPassword()));

    //datos no obligatorios
    customer.setAddress(request.getAddress());
    customer.setPhone(request.getPhone());
    customer.setCity(request.getCity());
    customer.setCountry(request.getCountry());

    // Guardar el nuevo cliente
    Customer saved = customerRepository.save(customer);

    // Devolvemos la respuesta de autenticación
    return new AuthResponse(
        saved.getId(),
        saved.getEmail(),
        saved.getFirstName(),
        saved.getLastName()
    );
    
    }


    public AuthResponse login(LoginRequest request) {
        // 1) Buscar por email 
        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email no encontrado"));

        // 2) Verificar la contraseña

        boolean ok = passwordEncoder.matches(request.getPassword(), customer.getPassword());
        if (!ok) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        // 3) Devolver la respuesta de autenticación
        return new AuthResponse(
            customer.getId(),
            customer.getEmail(),
            customer.getFirstName(),
            customer.getLastName()
        );
    }
}