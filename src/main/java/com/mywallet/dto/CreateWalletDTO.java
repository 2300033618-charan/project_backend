package com.mywallet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CreateWalletDTO {
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "[0-9]{10}", message = "Mobile number must be 10 digits")
    private String mobileNumber;
    
    @Min(value = 0, message = "Balance cannot be negative")
    private double initialBalance;

    // Constructors
    public CreateWalletDTO() {}

    public CreateWalletDTO(String mobileNumber, double initialBalance) {
        this.mobileNumber = mobileNumber;
        this.initialBalance = initialBalance;
    }

    // Getters and Setters
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }
}