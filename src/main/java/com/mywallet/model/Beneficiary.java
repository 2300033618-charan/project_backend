package com.mywallet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Beneficiary {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer beneficiaryId;

	    private String name;
	    private String mobileNumber;

	    @ManyToOne
	    @JoinColumn(name = "wallet_wallet_id")
	    private Wallet wallet;

	    @ManyToOne
	    @JoinColumn(name = "customer_mobile_number")
	    private Customer customer;

	    // Getters and Setters
	    public Integer getBeneficiaryId() {
	        return beneficiaryId;
	    }

	    public void setBeneficiaryId(Integer beneficiaryId) {
	        this.beneficiaryId = beneficiaryId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getMobileNumber() {
	        return mobileNumber;
	    }

	    public void setMobileNumber(String mobileNumber) {
	        this.mobileNumber = mobileNumber;
	    }

	    public Wallet getWallet() {
	        return wallet;
	    }

	    public void setWallet(Wallet wallet) {
	        this.wallet = wallet;
	    }

	    public Customer getCustomer() {
	        return customer;
	    }

	    public void setCustomer(Customer customer) {
	        this.customer = customer;
	    }
	}

    
    
