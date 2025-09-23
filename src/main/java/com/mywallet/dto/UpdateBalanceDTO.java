package com.mywallet.dto;

public class UpdateBalanceDTO {
    private double amount;

    public UpdateBalanceDTO() {}

    public UpdateBalanceDTO(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
