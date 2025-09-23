package com.mywallet.controller;

import com.mywallet.model.BillPayment;
import com.mywallet.service.BillPaymentService;
import com.mywallet.exceptions.BillPaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billpayments")
public class BillPaymentController {

    @Autowired
    private BillPaymentService billPaymentService;

    // Endpoint to add a new bill payment
    @PostMapping("/add")
    public ResponseEntity<BillPayment> addBillPayment(@RequestBody BillPayment billPayment) {
        try {
            BillPayment savedBillPayment = billPaymentService.addBillPayment(billPayment);
            return new ResponseEntity<>(savedBillPayment, HttpStatus.CREATED);
        } catch (BillPaymentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to view all bills for a specific wallet
    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<?> viewAllBills(@PathVariable Integer walletId) {
        try {
            List<BillPayment> bills = billPaymentService.viewAllBills(walletId);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } catch (BillPaymentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to get all bills for a customer based on mobile number
    @GetMapping("/customer/{mobile}")
    public ResponseEntity<?> getBillsByCustomerMobile(@PathVariable String mobile) {
        try {
            List<BillPayment> bills = billPaymentService.getBillsByCustomerMobile(mobile);
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } catch (BillPaymentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to get a specific bill payment by ID
    @GetMapping("/{billId}")
    public ResponseEntity<?> getBillById(@PathVariable Integer billId) {
        try {
            BillPayment billPayment = billPaymentService.getBillById(billId);
            return new ResponseEntity<>(billPayment, HttpStatus.OK);
        } catch (BillPaymentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to delete a bill payment by ID
    @DeleteMapping("/{billId}")
    public ResponseEntity<?> deleteBillById(@PathVariable Integer billId) {
        try {
            billPaymentService.deleteBillById(billId);
            return new ResponseEntity<>("Bill payment deleted successfully", HttpStatus.OK);
        } catch (BillPaymentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
