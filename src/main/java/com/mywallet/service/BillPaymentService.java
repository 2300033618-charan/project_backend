package com.mywallet.service;

import com.mywallet.model.BillPayment;
import com.mywallet.exceptions.BillPaymentException;

import java.util.List;

public interface BillPaymentService {

    BillPayment addBillPayment(BillPayment bill) throws BillPaymentException;

    List<BillPayment> viewAllBills(Integer walletId) throws BillPaymentException;

    List<BillPayment> getBillsByCustomerMobile(String mobile) throws BillPaymentException;

    BillPayment getBillById(Integer billId) throws BillPaymentException;

    void deleteBillById(Integer billId) throws BillPaymentException;

}
