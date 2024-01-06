package com.luna.test.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @org.hibernate.annotations.GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    private String sku;

    private Double stock;

    @NotBlank(message = "Item name is required")
    private String itemName;

    @NotBlank(message = "Uom is required")
    private String uom;

    @NotBlank(message = "Category is required")
    private String category;

    @Column(name = "item_cost")
    @NotNull(message = "Item cost is required")
    private Double itemCost;

    @Column(name = "item_price")
    @NotNull(message = "Item price is required")
    private Double itemPrice;

    @Column(name = "tax_id")
    @NotBlank(message = "Tax Id is required")
    private String taxId;

}
