package com.ipartek.cafeNomada.apirest.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.cafeNomada.apirest.models.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Buscar cliente por email (útil para login o registro)
    Optional<Customer> findByEmail(String email);

    // Comprobar si ya existe un cliente con un email
    boolean existsByEmail(String email);
}
