package com.mywallet.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class BillPayment {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer billId;

	    @NotEmpty(message = "Bill type cannot be empty")
	    private String billType;

	    @NotNull(message = "Amount cannot be null")
	    private Double amount;

	    private LocalDateTime paymentDate;

	    @ManyToOne
	    @JoinColumn(name = "wallet_id")
	    private Wallet wallet;

	    @ManyToOne
	    private Customer customer;  // Add a relationship to Customer if required for mobile number

	    // Getters and Setters

	    public Integer getBillId() {
	        return billId;
	    }

	    public void setBillId(Integer billId) {
	        this.billId = billId;
	    }

	    public String getBillType() {
	        return billType;
	    }

	    public void setBillType(String billType) {
	        this.billType = billType;
	    }

	    public Double getAmount() {
	        return amount;
	    }

	    public void setAmount(Double amount) {
	        this.amount = amount;
	    }

	    public LocalDateTime getPaymentDate() {
	        return paymentDate;
	    }

	    public void setPaymentDate(LocalDateTime paymentDate) {
	        this.paymentDate = paymentDate;
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

    
    
