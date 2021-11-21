package com.example.springboot.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.modal.Customer;
import com.example.springboot.modal.CustomerResponse;
import com.example.springboot.service.CustomerService;

@Controller
@RequestMapping(value = "/api/v1/")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/customer/", method = RequestMethod.GET) 
	public ResponseEntity<Customer> getAllCustomer() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity((List<Customer>) customerService.getAllCustomers(), headers, HttpStatus.OK);
	}

	@PostMapping("/customer/")
	public ResponseEntity<CustomerResponse> saveCustomer(@RequestBody Customer customer) {
		try {
			customerService.createCustomer(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity(new CustomerResponse("201", "CREATED"),headers, HttpStatus.CREATED);//ResponseEntity.ok().body(new CustomerResponse("200", "Deleted"));
	}

	@DeleteMapping("/customer/{id}")
	public ResponseEntity<CustomerResponse> deleteCustomerById(@PathVariable long id) {
		boolean isFailed = false;
		try {
			isFailed = customerService.deleteCustomer(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if (isFailed) {
			return new ResponseEntity(new CustomerResponse("500", "FAIL"),headers, HttpStatus.INTERNAL_SERVER_ERROR);//ResponseEntity.ok().body(new CustomerResponse("200", "Deleted"));
		} else {
			return new ResponseEntity(new CustomerResponse("200", "OK"),headers, HttpStatus.OK);//ResponseEntity.ok().body(new CustomerResponse("200", "Deleted"));
		}
	}
}
