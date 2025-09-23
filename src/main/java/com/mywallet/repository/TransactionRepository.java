package com.mywallet.repository;

import com.mywallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    // Custom query methods based on related entity fields
	List<Transaction> findByWallet_WalletId(Integer walletId);



    List<Transaction> findByWallet_Customer_MobileNumber(String mobileNumber);
}
