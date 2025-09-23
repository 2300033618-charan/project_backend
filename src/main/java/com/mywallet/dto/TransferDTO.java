package com.mywallet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class TransferDTO {
    @NotBlank(message = "Sender mobile number is required")
    @Pattern(regexp = "[0-9]{10}", message = "Mobile number must be 10 digits")
    private String fromMobile;
    
    @NotBlank(message = "Recipient mobile number is required")
    @Pattern(regexp = "[0-9]{10}", message = "Mobile number must be 10 digits")
    private String toMobile;
    
    @Min(value = 1, message = "Amount must be at least 1")
    private double amount;

    // Constructors, Getters and Setters
    public TransferDTO() {}

    public TransferDTO(String fromMobile, String toMobile, double amount) {
        this.fromMobile = fromMobile;
        this.toMobile = toMobile;
        this.amount = amount;
    }

    public String getFromMobile() {
        return fromMobile;
    }

    public void setFromMobile(String fromMobile) {
        this.fromMobile = fromMobile;
    }

    public String getToMobile() {
        return toMobile;
    }

    public void setToMobile(String toMobile) {
        this.toMobile = toMobile;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}