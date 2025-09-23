package com.mywallet.repository;

import com.mywallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Optional<Wallet> findByCustomerMobileNumber(String mobileNumber);
    Optional<Wallet> findByWalletId(Integer walletId);
}
