package com.luna.test.service;

import com.luna.test.dto.TransactionRequest;
import com.luna.test.dto.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse createPurchase(TransactionRequest transactionRequest);
    TransactionResponse createSale(TransactionRequest transactionRequest);
    void voidTransaction(String transactionId, boolean isSale);
    TransactionResponse getTransactionById(String transactionId, boolean isSale);
    List<TransactionResponse> getAllPurchaseTransactions(boolean isSale);
}
