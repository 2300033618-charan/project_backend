package com.mywallet.service;

import com.mywallet.exceptions.WalletException;
import com.mywallet.model.Customer;
import com.mywallet.model.Wallet;
import com.mywallet.repository.CustomerRepository;
import com.mywallet.repository.WalletRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletRepository walletRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Wallet createWallet(String mobileNumber, double initialBalance) {
        // Find customer by mobile number (returns Optional<Customer>)
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new IllegalArgumentException("Customer with mobile " + mobileNumber + " not found"));

        // Ensure the customer does not already have a wallet
        if (customer.getWallet() != null) {
            throw new IllegalStateException("Customer already has a wallet");
        }

        // Create a new wallet for the customer
        Wallet wallet = new Wallet();
        wallet.setBalance(initialBalance);
        wallet.setCustomer(customer);

        // Set the wallet for the customer (this will establish the relationship in both directions)
        customer.setWallet(wallet);

        // Save the new wallet (this will automatically save the associated customer due to cascading)
        return walletRepository.save(wallet);
    }

    public Wallet getWalletByMobile(String mobileNumber) {
        return walletRepository.findByCustomerMobileNumber(mobileNumber)
            .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));
    }
    public Wallet updateWallet(Wallet wallet) throws WalletException {
        if (!walletRepository.existsById(wallet.getWalletId())) {
            throw new WalletException("Wallet not found");
        }
        return walletRepository.save(wallet);
    }

    public void deleteWallet(String mobileNumber) throws WalletException {
        Wallet wallet = getWalletByMobile(mobileNumber);
        walletRepository.delete(wallet);
    }

    @Transactional
    public void transferFunds(String fromMobile, String toMobile, double amount) {
        Wallet fromWallet = getWalletByMobile(fromMobile);
        Wallet toWallet = getWalletByMobile(toMobile);
        
        if (fromWallet.getBalance() < amount) {
            throw new IllegalStateException("Insufficient funds");
        }
        
        fromWallet.setBalance(fromWallet.getBalance() - amount);
        toWallet.setBalance(toWallet.getBalance() + amount);
        
        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);
    }
    @Override
    public Wallet updateBalance(String mobileNumber, double amount) {
        Wallet wallet = getWalletByMobile(mobileNumber);
        wallet.setBalance(amount);
        return walletRepository.save(wallet);
    }


}