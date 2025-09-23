package com.mywallet.controller;

import com.mywallet.model.Beneficiary;
import com.mywallet.model.Customer;
import com.mywallet.model.Wallet;
import com.mywallet.repository.BeneficiaryRepository;
import com.mywallet.repository.CustomerRepository;
import com.mywallet.repository.WalletRepository;
import com.mywallet.service.BeneficiaryService;
import com.mywallet.exceptions.BeneficiaryException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/beneficiaries")
public class BeneficiaryController {

        @Autowired
        private BeneficiaryService beneficiaryService;
        
        @Autowired
        CustomerRepository customerRepository;
        
        @Autowired
        WalletRepository walletRepository;
        
        @Autowired
        BeneficiaryRepository beneficiaryRepository;

        @PostMapping("/add")
        public ResponseEntity<Beneficiary> addBeneficiary(@RequestBody Beneficiary beneficiary) {
            // Retrieve the wallet
            Wallet wallet = walletRepository.findById(beneficiary.getWallet().getWalletId()).orElse(null);

            // Retrieve the customer using the mobile number
            Optional<Customer> customerOpt = customerRepository.findByMobileNumber(beneficiary.getCustomer().getMobileNumber());

            if (wallet == null) {
                return ResponseEntity.badRequest().body(null);  // If wallet is not found, return bad request
            }

            if (customerOpt.isEmpty()) {
                return ResponseEntity.badRequest().body(null);  // If customer is not found, return bad request
            }

            // Get customer from Optional
            Customer customer = customerOpt.get();

            // Set the wallet and customer to the beneficiary
            beneficiary.setWallet(wallet);
            beneficiary.setCustomer(customer);

            // Save and return the beneficiary
            Beneficiary savedBeneficiary = beneficiaryRepository.save(beneficiary);
            return ResponseEntity.ok(savedBeneficiary);
        }

        @GetMapping("/mobile/{mobile}")
        public ResponseEntity<?> getBeneficiaryByMobile(@PathVariable String mobile) {
            try {
                Beneficiary beneficiary = beneficiaryService.getBeneficiaryByMobile(mobile);
                return new ResponseEntity<>(beneficiary, HttpStatus.OK);
            } catch (BeneficiaryException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }

        @GetMapping("/customer/{customerMobile}")
        public ResponseEntity<?> getAllBeneficiariesByCustomer(@PathVariable String customerMobile) {
            try {
                List<Beneficiary> list = beneficiaryService.getBeneficiariesByCustomerMobile(customerMobile);
                return new ResponseEntity<>(list, HttpStatus.OK);
            } catch (BeneficiaryException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }

        @DeleteMapping("/{beneficiaryId}")
        public ResponseEntity<?> deleteBeneficiary(@PathVariable Integer beneficiaryId) {
            try {
                Beneficiary deleted = beneficiaryService.deleteBeneficiaryById(beneficiaryId);
                return new ResponseEntity<>("Deleted successfully: " + deleted.getName(), HttpStatus.OK);
            } catch (BeneficiaryException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }

}



