package com.mywallet.service;

import com.mywallet.exceptions.BeneficiaryException;
import com.mywallet.model.Beneficiary;
import com.mywallet.repository.BeneficiaryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

    @Autowired
    private BeneficiaryRepository beneRepo;

    @Override
    public Beneficiary addBeneficiary(Beneficiary beneficiary) throws BeneficiaryException {
        Optional<Beneficiary> existing = beneRepo.findByMobileNumber(beneficiary.getMobileNumber());
        if (existing.isPresent()) {
            throw new BeneficiaryException("Beneficiary with same mobile already exists.");
        }
        return beneRepo.save(beneficiary);
    }

    @Override
    public Beneficiary getBeneficiaryByMobile(String mobile) throws BeneficiaryException {
        return beneRepo.findByMobileNumber(mobile)
                .orElseThrow(() -> new BeneficiaryException("Beneficiary not found with mobile: " + mobile));
    }

    @Override
    public Beneficiary deleteBeneficiaryById(Integer beneficiaryId) throws BeneficiaryException {
        Beneficiary existing = beneRepo.findById(beneficiaryId)
                .orElseThrow(() -> new BeneficiaryException("Beneficiary not found with ID: " + beneficiaryId));
        beneRepo.delete(existing);
        return existing;
    }

    @Override
    public List<Beneficiary> getBeneficiariesByCustomerMobile(String customerMobile) throws BeneficiaryException {
        List<Beneficiary> list = beneRepo.findByCustomer_MobileNumber(customerMobile);
        if (list.isEmpty()) {
            throw new BeneficiaryException("No beneficiaries found for mobile: " + customerMobile);
        }
        return list;
    }
}
