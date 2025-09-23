package com.mywallet.controller;

import com.mywallet.model.BankAccount;
import com.mywallet.service.BankAccountService;
import com.mywallet.exceptions.BankAccountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bankaccounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankService;

    @PostMapping("/add")
    public ResponseEntity<?> addBankAccount(@RequestBody BankAccount account) {
        try {
            BankAccount saved = bankService.addAccount(account);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add account - Customer doesn't exist: " + e.getMessage());
        }
    }

    @GetMapping("/{mobile}")
    public ResponseEntity<?> getAccountsByMobile(@PathVariable String mobile) {
        try {
            List<BankAccount> accounts = bankService.viewAllAccounts(mobile);
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Accounts not found: " + e.getMessage());
        }
    }

    @DeleteMapping("/{accNo}")
    public ResponseEntity<?> deleteAccount(@PathVariable String accNo) {
        try {
            bankService.removeAccount(accNo);
            return ResponseEntity.ok("Account deleted successfully");
        } catch (BankAccountException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deletion failed: " + e.getMessage());
        }
    }

    @GetMapping("/account/{accNo}")
    public ResponseEntity<?> getAccount(@PathVariable String accNo) {
        try {
            BankAccount acc = bankService.getAccountByNumber(accNo);
            return ResponseEntity.ok(acc);
        } catch (BankAccountException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found: " + e.getMessage());
        }
    }
}
