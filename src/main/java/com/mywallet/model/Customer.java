package com.mywallet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Customer {

    @Id
    @NotNull
    @jakarta.validation.constraints.Pattern(regexp = "[0-9]{10}", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotNull
    @Size(min = 3, message = "Name should have at least 3 characters")
    private String name;

    @NotNull
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;

    @OneToOne(cascade = CascadeType.ALL , orphanRemoval = true , mappedBy = "customer")
    @JsonIgnore
    private Wallet wallet;

    // Getters and setters
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Wallet getWallet() {
        return wallet;
    }
    public void setWallet(Wallet wallet) {
    	if (wallet == null) {
            if (this.wallet != null) {
                this.wallet.setCustomer(null);
            }
        } else {
            wallet.setCustomer(this);
        }
        this.wallet = wallet;
    }
}
