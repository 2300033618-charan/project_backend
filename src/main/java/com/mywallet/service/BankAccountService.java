package com.mywallet.service;

import com.mywallet.model.BankAccount;
import com.mywallet.exceptions.BankAccountException;

import java.util.List;

public interface BankAccountService {

    // Add a new bank account
    public BankAccount addAccount(BankAccount account);

    // Get account details by account number
    public BankAccount getAccountByNumber(String accNo) throws BankAccountException;

    // View all accounts linked with a customer mobile number
    public List<BankAccount> viewAllAccounts(String mobileNumber);

    // Remove a bank account using account number
    public void removeAccount(String accNo) throws BankAccountException;
}
