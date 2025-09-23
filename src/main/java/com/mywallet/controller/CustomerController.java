package com.mywallet.controller;

import com.mywallet.exceptions.CustomerException;
import com.mywallet.model.Customer;
import com.mywallet.service.CustomerService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@jakarta.validation.Valid @RequestBody Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                   .body(errorResponse("Validation error: " + bindingResult.getFieldError().getDefaultMessage()));
        }

        try {
            if (customerService.existsByMobile(customer.getMobileNumber())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                       .body(errorResponse("Mobile number already registered"));
            }

            Customer createdCustomer = customerService.createCustomerAccount(customer);
            return ResponseEntity.status(HttpStatus.CREATED)
                   .body(successResponse("Customer created successfully", createdCustomer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(errorResponse("Failed to create customer: " + e.getMessage()));
        }
    }

    @GetMapping("/{mobileNumber}")
    public ResponseEntity<?> getCustomer(@PathVariable String mobileNumber) {
        try {
            Customer customer = customerService.getCustomerByMobile(mobileNumber);
            return ResponseEntity.ok(successResponse("Customer retrieved successfully", customer));
        } catch (CustomerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(errorResponse("Customer not found"));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody Customer customer) {
        try {
            Customer updated = customerService.updateCustomer(customer);
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Updated successfully",
                "customer", updated
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("status", "error", "message", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String mobileNumber) {
        try {
            // Trim and validate mobile number format
            mobileNumber = mobileNumber.trim();
            if (!mobileNumber.matches("\\d{10}")) {
                return ResponseEntity.badRequest()
                       .body(Map.of("error", "Invalid mobile number format"));
            }

            customerService.deleteCustomer(mobileNumber);
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Customer and all related data deleted successfully"
            ));
        } catch (CustomerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
           
			// Log the full error for debugging
            logger.error("Delete failed for customer " + mobileNumber, e);
            return ResponseEntity.internalServerError()
                   .body(Map.of(
                       "error", "Could not delete customer",
                       "details", e.getMessage() != null ? e.getMessage() : "Refer to server logs"
                   ));
        }
    }

    // Helper methods for consistent responses
    private Map<String, Object> successResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);
        return response;
    }

    private Map<String, Object> successResponse(String message, Customer customer) {
        Map<String, Object> response = successResponse(message);
        response.put("customer", customer);
        return response;
    }

    private Map<String, Object> errorResponse(String error) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", error);
        return response;
    }
}