package com.luna.test.dto;

import com.luna.test.model.Product;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemLineResponse extends Product{
     private Double qty;
}
