package com.mywallet.service;

import com.mywallet.model.Transaction;
import com.mywallet.exceptions.TransactionException;

import java.util.List;

public interface TransactionService {

    Transaction addTransaction(Transaction txn) throws TransactionException;
    
    List<Transaction> viewAllTransactions() throws TransactionException;
    
    List<Transaction> viewAllTransactionsByWalletId(Integer walletId) throws TransactionException;

    List<Transaction> viewAllTransactionsByMobile(String mobile) throws TransactionException;

    Transaction getTransactionById(Integer txnId) throws TransactionException;

    void deleteTransaction(Integer txnId) throws TransactionException;
}
