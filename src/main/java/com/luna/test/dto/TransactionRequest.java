package com.luna.test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TransactionRequest {

    private String invoiceNo;

    @NotBlank(message = "invoiceDate is required")
    private String invoiceDate;

    @NotBlank(message = "note is required")
    private String note;

    @NotNull(message = "itemLines is required")
    private List<ItemLineRequest> itemLines;

}
