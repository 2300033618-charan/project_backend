package com.mywallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mywallet.model.Customer;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
   
    boolean existsByMobileNumber(String mobileNumber);
    Optional<Customer> findByMobileNumber(String mobileNumber);
}
