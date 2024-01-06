package com.luna.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private String id;
    private String invoiceNo;
    private String invoiceDate;
    private String note;
    private List<ItemLineResponse> itemLines;
    private Double totalBeforeTax;
    private Double total;
}
