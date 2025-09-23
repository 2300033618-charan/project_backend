package com.mywallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mywallet.dto.CreateWalletDTO;
import com.mywallet.dto.TransferDTO;
import com.mywallet.dto.UpdateBalanceDTO;
import com.mywallet.dto.WalletDTO;
import com.mywallet.model.Wallet;
import com.mywallet.service.WalletService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<WalletDTO> createWallet(@jakarta.validation.Valid @RequestBody CreateWalletDTO createWalletDTO) {
        Wallet wallet = walletService.createWallet(
            createWalletDTO.getMobileNumber(),
            createWalletDTO.getInitialBalance()
        );
        WalletDTO walletDTO = convertToDTO(wallet);
        return ResponseEntity.status(HttpStatus.CREATED).body(walletDTO);
    }

    @GetMapping("/{mobileNumber}")
    public ResponseEntity<WalletDTO> getWallet(@PathVariable String mobileNumber) {
        Wallet wallet = walletService.getWalletByMobile(mobileNumber);
        WalletDTO walletDTO = convertToDTO(wallet);
        return ResponseEntity.ok(walletDTO);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@Valid @RequestBody TransferDTO transferDTO) {
        walletService.transferFunds(
            transferDTO.getFromMobile(),
            transferDTO.getToMobile(),
            transferDTO.getAmount()
        );
        return ResponseEntity.ok("Transfer successful");
    }

    // Helper method to convert Wallet to WalletDTO
    private WalletDTO convertToDTO(Wallet wallet) {
        return new WalletDTO(
            wallet.getWalletId(),
            wallet.getBalance(),
            wallet.getCustomer()
        );
    }
    
    @PutMapping
    public ResponseEntity<WalletDTO> updateWallet(@Valid @RequestBody Wallet wallet) {
        try {
            Wallet updatedWallet = walletService.updateWallet(wallet);
            WalletDTO walletDTO = convertToDTO(updatedWallet);
            return ResponseEntity.ok(walletDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @DeleteMapping("/{mobileNumber}")
    public ResponseEntity<String> deleteWallet(@PathVariable String mobileNumber) {
        try {
            walletService.deleteWallet(mobileNumber);
            return ResponseEntity.ok("Wallet deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Wallet deletion failed: " + e.getMessage());
        }
    }
    
    @PutMapping("/{mobileNumber}/balance")
    public ResponseEntity<WalletDTO> updateBalance(
            @PathVariable String mobileNumber,
            @RequestBody UpdateBalanceDTO updateBalanceDTO) {
        Wallet updatedWallet = walletService.updateBalance(mobileNumber, updateBalanceDTO.getAmount());
        WalletDTO walletDTO = convertToDTO(updatedWallet);
        return ResponseEntity.ok(walletDTO);
    }

}