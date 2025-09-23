package com.mywallet.service;

import com.mywallet.model.BankAccount;
import com.mywallet.repository.BankAccountRepository;
import com.mywallet.exceptions.BankAccountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankRepo;

    @Override
    public BankAccount addAccount(BankAccount account) {
        return bankRepo.save(account);
    }

    @Override
    public BankAccount getAccountByNumber(String accNo) throws BankAccountException {
        return bankRepo.findById(accNo)
                .orElseThrow(() -> new BankAccountException("Bank Account not found: " + accNo));
    }

    @Override
    public List<BankAccount> viewAllAccounts(String mobileNumber) {
        return bankRepo.findByCustomerMobileNumber(mobileNumber);
    }

    @Override
    public void removeAccount(String accNo) throws BankAccountException {
        BankAccount acc = getAccountByNumber(accNo);
        bankRepo.delete(acc);
    }
}
