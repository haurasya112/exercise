package com.luna.test.service.serviceImpl;

import com.luna.test.dto.*;
import com.luna.test.model.*;
import com.luna.test.repository.ProductRepository;
import com.luna.test.repository.TaxRepository;
import com.luna.test.repository.TransactionItemLineRepository;
import com.luna.test.repository.TransactionRepository;
import com.luna.test.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionItemLineRepository transactionItemLineRepository;
    private final ProductRepository productRepository;
    private final TaxRepository taxRepository;

    @Override
    public TransactionResponse getTransactionById(String id,boolean isSale) {
        try {
            return transactionRepository.findByIdAndIsSale(id,isSale)
                    .map(this::mapToTransactionResponse)
                    .orElseThrow(() -> new RuntimeException("Purchase transaction not found"));
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving purchase transaction", e);
        }
    }

    @Override
    public List<TransactionResponse> getAllPurchaseTransactions(boolean isSale) {
        try {
            List<Transaction> transactions = transactionRepository.findByIsSale(isSale);
            return transactions.stream()
                    .map(this::mapToTransactionResponse)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Error retrieving purchase transactions", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionResponse createPurchase(TransactionRequest transactionRequest) {
        return processTransaction(transactionRequest, true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionResponse createSale(TransactionRequest transactionRequest) {
        return processTransaction(transactionRequest, false);
    }

    private TransactionResponse processTransaction(TransactionRequest transactionRequest, boolean isPurchase) {
        try {
            Transaction transaction = createTransaction(transactionRequest);
            transaction.setSale(!isPurchase);
            transaction = transactionRepository.save(transaction);

            List<Product> updatedProducts = new ArrayList<>();
            List<TransactionItemLine> transactionItemLines = new ArrayList<>();
            Double totalBeforeTax = 0.0;
            Double total = 0.0;

            for (ItemLineRequest itemLineRequest : transactionRequest.getItemLines()) {
                Product product = productRepository.findById(itemLineRequest.getId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                Double stockChange = isPurchase ? itemLineRequest.getQty() : -itemLineRequest.getQty();
                product.setStock(Math.max(0, product.getStock() + stockChange));
                updatedProducts.add(product);

                TransactionItemLine transactionItemLine = createTransactionItemLine(transaction, itemLineRequest, product);
                transactionItemLines.add(transactionItemLine);

                Tax productTax = taxRepository.findById(product.getTaxId())
                        .orElseThrow(() -> new RuntimeException("Tax not found for product with ID: " + product.getId()));

                totalBeforeTax += product.getItemPrice() * itemLineRequest.getQty();
                total += calculateTotalWithTax(product, productTax, itemLineRequest.getQty());
            }

            productRepository.saveAll(updatedProducts);

            transactionItemLineRepository.saveAll(transactionItemLines);

            transaction.setItemLines(transactionItemLines);
            transaction.setTotalBeforeTax(totalBeforeTax);
            transaction.setTotal(total);
            transactionRepository.save(transaction);

            return mapToTransactionResponse(transaction);
        } catch (Exception e) {
            throw new RuntimeException("Error processing transaction", e);
        }
    }

    private TransactionItemLine createTransactionItemLine(Transaction transaction, ItemLineRequest itemLineRequest, Product product) {
        TransactionItemLine transactionItemLine = new TransactionItemLine();
        transactionItemLine.setProduct(product);
        transactionItemLine.setQty(itemLineRequest.getQty());
        transactionItemLine.setTransaction(transaction);
        return transactionItemLine;
    }

    private Double calculateTotalWithTax(Product product, Tax productTax, Double quantity) {
        return (product.getItemPrice() + (productTax.getRate() / 100) * product.getItemPrice()) * quantity;
    }

    private TransactionResponse mapToTransactionResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setInvoiceNo(transaction.getInvoiceNo());
        response.setInvoiceDate(transaction.getInvoiceDate().toString());
        response.setNote(transaction.getNote());

        List<ItemLineResponse> itemLines = new ArrayList<>();
        for (TransactionItemLine transactionItemLine : transaction.getItemLines()) {
            ItemLineResponse itemLineResponse = new ItemLineResponse();
            Product product = transactionItemLine.getProduct();
            itemLineResponse.setId(product.getId());
            itemLineResponse.setQty(transactionItemLine.getQty());
            itemLineResponse.setSku(product.getSku());
            itemLineResponse.setStock(product.getStock());
            itemLineResponse.setItemName(product.getItemName());
            itemLineResponse.setUom(product.getUom());
            itemLineResponse.setCategory(product.getCategory());
            itemLineResponse.setItemCost(product.getItemCost());
            itemLineResponse.setItemPrice(product.getItemPrice());
            itemLineResponse.setTaxId(product.getTaxId());
            itemLines.add(itemLineResponse);
        }

        response.setItemLines(itemLines);
        response.setTotalBeforeTax(transaction.getTotalBeforeTax());
        response.setTotal(transaction.getTotal());
        return response;
    }

    private Transaction createTransaction(TransactionRequest transactionRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");

        Transaction transaction = new Transaction();
        transaction.setInvoiceNo(transactionRequest.getInvoiceNo() != null && !transactionRequest.getInvoiceNo().isEmpty() ?
                transactionRequest.getInvoiceNo() : String.format("%05d", Math.round(Math.random() * 100000)));
        transaction.setInvoiceDate(LocalDateTime.parse(transactionRequest.getInvoiceDate(), formatter));
        transaction.setNote(transactionRequest.getNote());
        return transaction;
    }

    @Override
    public void voidTransaction(String transactionId, boolean isSale) {
        Transaction transaction = transactionRepository.findByIdAndIsSale(transactionId,isSale)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (transaction.getStatus() == null || !transaction.getStatus().equals("voided")) {
            transaction.setStatus("voided");
            transactionRepository.save(transaction);

            adjustProductStock(transaction, isSale);
        } else {
            throw new RuntimeException("Transaction is already voided");
        }
    }

    private void adjustProductStock(Transaction transaction, boolean isSale) {
        List<TransactionItemLine> itemLines = transaction.getItemLines();

        itemLines.forEach(itemLine -> {
            Product product = itemLine.getProduct();
            double stockChange = isSale ? itemLine.getQty() : -itemLine.getQty();
            product.setStock(Math.max(0, product.getStock() + stockChange));
        });

        productRepository.saveAll(itemLines.stream().map(TransactionItemLine::getProduct).collect(Collectors.toList()));
    }
}
