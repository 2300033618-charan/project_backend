package com.mywallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mywallet.model.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Integer> {
    Optional<Beneficiary> findByMobileNumber(String mobileNumber);
    List<Beneficiary> findByCustomer_MobileNumber(String customerMobileNumber);
    void deleteByCustomerMobileNumber(String mobileNumber);
}
