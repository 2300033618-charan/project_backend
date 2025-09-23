package com.mywallet.controller;

import com.mywallet.model.Transaction;
import com.mywallet.service.TransactionService;
import com.mywallet.exceptions.TransactionException;
import com.mywallet.exceptions.WalletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService txnService;

    @PostMapping("/add")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction txn) throws WalletException, TransactionException {
        return new ResponseEntity<>(txnService.addTransaction(txn), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Integer id) throws TransactionException {
        return ResponseEntity.ok(txnService.getTransactionById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() throws TransactionException {
        return ResponseEntity.ok(txnService.viewAllTransactions());
    }

    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<List<Transaction>> getTransactionsByWallet(@PathVariable Integer walletId) throws TransactionException {
        return ResponseEntity.ok(txnService.viewAllTransactionsByWalletId(walletId));
    }
}
