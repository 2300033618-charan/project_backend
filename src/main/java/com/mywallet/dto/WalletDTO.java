package com.mywallet.dto;

import com.mywallet.model.Customer;

public class WalletDTO {
    private Integer walletId;
    private double balance;
    private Customer customer;

    // Constructors
    public WalletDTO() {}

    public WalletDTO(Integer walletId, double balance, Customer customer) {
        this.walletId = walletId;
        this.balance = balance;
        this.customer = customer;
    }

    // Getters and Setters
    public Integer getWalletId() {
        return walletId;
    }

    public void setWalletId(Integer walletId) {
        this.walletId = walletId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}