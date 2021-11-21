package com.example.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot.modal.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
