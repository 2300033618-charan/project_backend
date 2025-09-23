package com.mywallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mywallet.model.BillPayment;

public interface BillPaymentRepository extends JpaRepository<BillPayment, Integer> {
	// Use walletId instead of id in the method signature
    List<BillPayment> findByWallet_WalletId(Integer walletId); // Correct method to find bills by walletId

    // Use the correct reference for mobileNumber via Customer
    List<BillPayment> findByCustomer_MobileNumber(String mobileNumber); // Correct method to find bills by customer's mobileNumber

}
