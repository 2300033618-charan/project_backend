package com.mywallet.service;

import com.mywallet.exceptions.BeneficiaryException;
import com.mywallet.model.Beneficiary;

import java.util.List;

public interface BeneficiaryService {

    Beneficiary addBeneficiary(Beneficiary beneficiary) throws BeneficiaryException;

    Beneficiary getBeneficiaryByMobile(String mobile) throws BeneficiaryException;

    Beneficiary deleteBeneficiaryById(Integer beneficiaryId) throws BeneficiaryException;

    List<Beneficiary> getBeneficiariesByCustomerMobile(String customerMobile) throws BeneficiaryException;
}
