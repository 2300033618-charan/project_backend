package com.mywallet.service;

import com.mywallet.model.Customer;
import com.mywallet.model.Wallet;
import com.mywallet.exceptions.CustomerException;
import com.mywallet.repository.BeneficiaryRepository;
import com.mywallet.repository.CustomerRepository;
import com.mywallet.repository.WalletRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepo;
    
    @Autowired
    private BeneficiaryRepository beneficiaryRepo;
    
    @Autowired
    private WalletRepository walletRepo;

    @Override
    @Transactional
    public Customer createCustomerAccount(Customer customer) throws CustomerException {
        // Initialize wallet if not set
        if (customer.getWallet() == null) {
            customer.setWallet(new Wallet());
        }
        
        // Validate mobile number doesn't exist
        if (customerRepo.existsById(customer.getMobileNumber())) {
            throw new CustomerException("Mobile number already registered");
        }
        
        return customerRepo.save(customer);
    }

    @Override
    public Customer getCustomerByMobile(String mobile) throws CustomerException {
        // Fetching the customer by mobile number and handling the case when customer is not found
        Optional<Customer> customerOptional = customerRepo.findByMobileNumber(mobile);

        return customerOptional.orElseThrow(() -> new CustomerException("Customer not found with Mobile: " + mobile));
    }


    @Override
    @Transactional
    public Customer updateCustomer(Customer customer) {
        // Exact match search (case sensitive)
        return customerRepo.findById(customer.getMobileNumber())
            .map(existing -> {
                existing.setName(customer.getName());
                existing.setPassword(customer.getPassword());
                return customerRepo.save(existing);
            })
            .orElseThrow(() -> new EntityNotFoundException(
                "Customer not found with mobile: " + customer.getMobileNumber()
            ));
    }
    @Override
    @Transactional
    public Customer deleteCustomer(String mobileNumber) throws CustomerException {
        Customer customer = customerRepo.findById(mobileNumber)
            .orElseThrow(() -> new CustomerException("Customer not found with mobile: " + mobileNumber));
        
        // 1. Delete all beneficiaries first
        beneficiaryRepo.deleteByCustomerMobileNumber(mobileNumber);
        
        // 2. Handle wallet if exists
        if (customer.getWallet() != null) {
            Wallet wallet = customer.getWallet();
            // Break bidirectional relationship
            wallet.setCustomer(null);
            customer.setWallet(null);
            customerRepo.save(customer); // Update customer first
            
            walletRepo.delete(wallet); // Then delete wallet
        }
        
        // 3. Finally delete the customer
        customerRepo.delete(customer);
		return customer;
    }
    
    @Override
    public boolean existsByMobile(String mobile) {
        return customerRepo.existsByMobileNumber(mobile);
    }
}
