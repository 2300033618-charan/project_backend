package com.mywallet.service;

import com.mywallet.model.Wallet;

import com.mywallet.exceptions.WalletException;


public interface WalletService {

    Wallet getWalletByMobile(String mobileNumber) throws WalletException;
    Wallet updateWallet(Wallet wallet) throws WalletException;
    void deleteWallet(String mobileNumber) throws WalletException;

	Wallet createWallet(String mobileNumber, double initialBalance);

	void transferFunds(String fromMobile, String toMobile, double amount);
	public Wallet updateBalance(String mobileNumber, double amount);

}
