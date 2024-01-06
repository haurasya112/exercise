package com.luna.test.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UpdateProductDto {

    private String itemName;
    private String uom;
    private String category;
    private Double itemCost;
    private Double itemPrice;
    private String taxId;
}
