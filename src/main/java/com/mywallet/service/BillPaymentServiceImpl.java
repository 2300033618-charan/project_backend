package com.mywallet.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mywallet.exceptions.BillPaymentException;
import com.mywallet.model.BillPayment;
import com.mywallet.model.Customer;
import com.mywallet.repository.BillPaymentRepository;
import com.mywallet.repository.CustomerRepository;

@Service
public class BillPaymentServiceImpl implements BillPaymentService {

    @Autowired
    private BillPaymentRepository billPaymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public BillPayment addBillPayment(BillPayment bill) throws BillPaymentException {
        Optional<Customer> customerOpt = customerRepository.findByMobileNumber(bill.getCustomer().getMobileNumber());

        if (!customerOpt.isPresent()) {
            throw new BillPaymentException("Customer with mobile number " + bill.getCustomer().getMobileNumber() + " not found.");
        }

        bill.setCustomer(customerOpt.get());
        return billPaymentRepository.save(bill);
    }

    @Override
    public List<BillPayment> viewAllBills(Integer walletId) throws BillPaymentException {
        List<BillPayment> bills = billPaymentRepository.findByWallet_WalletId(walletId);  // Correct method call

        if (bills.isEmpty()) {
            throw new BillPaymentException("No bills found for wallet ID " + walletId);
        }

        return bills;
    }

    @Override
    public List<BillPayment> getBillsByCustomerMobile(String mobile) throws BillPaymentException {
        List<BillPayment> bills = billPaymentRepository.findByCustomer_MobileNumber(mobile);  // Correct method call

        if (bills.isEmpty()) {
            throw new BillPaymentException("No bills found for customer with mobile number " + mobile);
        }

        return bills;
    }

    @Override
    public BillPayment getBillById(Integer billId) throws BillPaymentException {
        return billPaymentRepository.findById(billId)
                .orElseThrow(() -> new BillPaymentException("Bill payment not found for ID " + billId));
    }

    @Override
    public void deleteBillById(Integer billId) throws BillPaymentException {
        BillPayment billPayment = billPaymentRepository.findById(billId)
                .orElseThrow(() -> new BillPaymentException("Bill payment not found for ID " + billId));

        billPaymentRepository.delete(billPayment);
    }
}
