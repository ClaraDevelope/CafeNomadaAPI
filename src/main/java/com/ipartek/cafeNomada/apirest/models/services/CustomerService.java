package com.ipartek.cafeNomada.apirest.models.services;

import org.springframework.stereotype.Service;

import com.ipartek.cafeNomada.apirest.models.dao.CustomerRepository;
import com.ipartek.cafeNomada.apirest.models.entities.Customer;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

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

    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Ya existe un cliente con ese correo electrónico");
        }
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer updateCustomer(Customer updatedCustomer) {
        return customerRepository.save(updatedCustomer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
