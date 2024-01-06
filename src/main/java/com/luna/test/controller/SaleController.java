package com.luna.test.controller;

import com.luna.test.dto.TransactionRequest;
import com.luna.test.dto.TransactionResponse;
import com.luna.test.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {

    private final TransactionService transactionService;

    @PostMapping()
    public ResponseEntity<?> createSale(@RequestBody @Valid TransactionRequest transactionRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", bindingResult.getFieldErrors().get(0).getDefaultMessage()));
        }
        try {
            TransactionResponse response = transactionService.createSale(transactionRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/void/{id}")
    public ResponseEntity<?> voidSaleTransaction(@PathVariable String id) {
        try {
            transactionService.voidTransaction(id, true);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error voiding sales transaction"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSaleTransaction(@PathVariable String id) {
        try {
            TransactionResponse transaction = transactionService.getTransactionById(id,true);
            return ResponseEntity.ok(transaction);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Sale transaction not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal Server Error"));
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllSaleTransactions() {
        try {
            List<TransactionResponse> transactions = transactionService.getAllPurchaseTransactions(true);
            return ResponseEntity.ok(Map.of("data", transactions));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Internal Server Error"));
        }
    }
}
