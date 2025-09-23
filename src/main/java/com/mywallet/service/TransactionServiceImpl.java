package com.mywallet.service;

import com.mywallet.model.Transaction;
import com.mywallet.model.Wallet;
import com.mywallet.exceptions.TransactionException;
import com.mywallet.exceptions.WalletException;
import com.mywallet.repository.TransactionRepository;
import com.mywallet.repository.WalletRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {


	    @Autowired
	    private TransactionRepository txnRepo;

	    @Autowired
	    private WalletRepository walletRepo;

	    public Transaction addTransaction(Transaction txn) throws WalletException {
	        Integer walletId = txn.getWallet().getWalletId();
	        Wallet wallet = walletRepo.findById(walletId)
	                .orElseThrow(() -> new WalletException("Wallet not found"));

	        txn.setWallet(wallet);
	        return txnRepo.save(txn);
	    }

	    public Transaction getTransactionById(Integer id) throws TransactionException {
	        return txnRepo.findById(id)
	                .orElseThrow(() -> new TransactionException("Transaction not found"));
	    }

	    public List<Transaction> viewAllTransactions() {
	        return txnRepo.findAll();
	    }

	    public List<Transaction> viewAllTransactionsByWalletId(Integer walletId) {
	        return txnRepo.findByWallet_WalletId(walletId);
	    }


    @Override
    public void deleteTransaction(Integer txnId) throws TransactionException {
        Transaction txn = getTransactionById(txnId);
        txnRepo.delete(txn);
    }

	@Override
	public List<Transaction> viewAllTransactionsByMobile(String mobile) throws TransactionException {
		// TODO Auto-generated method stub
		return null;
	}
}
