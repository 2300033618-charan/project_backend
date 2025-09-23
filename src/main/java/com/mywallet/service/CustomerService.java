package com.mywallet.service;

import com.mywallet.model.Customer;
import com.mywallet.exceptions.CustomerException;

public interface CustomerService {
    public Customer createCustomerAccount(Customer customer) throws CustomerException;
    public Customer getCustomerByMobile(String mobile) throws CustomerException;
    public Customer updateCustomer(Customer customer) throws CustomerException;
    public Customer deleteCustomer(String mobile) throws CustomerException;
    public boolean existsByMobile(String mobile);
}
