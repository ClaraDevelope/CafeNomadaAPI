package com.ipartek.cafeNomada.apirest.models.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ipartek.cafeNomada.apirest.models.dao.CustomerRepository;
import com.ipartek.cafeNomada.apirest.models.entities.Customer;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Inyección por constructor
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // --- CRUD básico ---

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Ya existe un cliente con ese correo electrónico");
        }
        if (customer.getPassword() == null || customer.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        // hash antes de guardar
        customer.setPassword(encoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer incoming) {
        Customer existing = customerRepository.findById(incoming.getId())
                .orElseThrow(() -> new IllegalArgumentException("El cliente no existe"));

        // si cambia email, valida unicidad
        if (incoming.getEmail() != null && !incoming.getEmail().equals(existing.getEmail())) {
            if (customerRepository.existsByEmail(incoming.getEmail())) {
                throw new IllegalArgumentException("Ese email ya está registrado");
            }
            existing.setEmail(incoming.getEmail());
        }

        // copia campos actualizables (solo si vienen no nulos)
        if (incoming.getFirstName() != null) existing.setFirstName(incoming.getFirstName());
        if (incoming.getLastName()  != null) existing.setLastName(incoming.getLastName());
        if (incoming.getAddress()   != null) existing.setAddress(incoming.getAddress());
        if (incoming.getCity()      != null) existing.setCity(incoming.getCity());
        if (incoming.getCountry()   != null) existing.setCountry(incoming.getCountry());
        if (incoming.getPhone()     != null) existing.setPhone(incoming.getPhone());

        // si viene password no vacía -> re-hashear; si no viene, se conserva la anterior
        if (incoming.getPassword() != null && !incoming.getPassword().isBlank()) {
            existing.setPassword(encoder.encode(incoming.getPassword()));
        }

        return customerRepository.save(existing);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("El cliente no existe");
        }
        customerRepository.deleteById(id);
    }
}
