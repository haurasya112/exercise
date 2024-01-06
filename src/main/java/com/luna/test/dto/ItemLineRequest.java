package com.luna.test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class ItemLineRequest {

    String id;
    Double qty;
}
