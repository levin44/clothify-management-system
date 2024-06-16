package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartTbl {
    private Integer productId;
    private String productName;
    private Double unitPrice;
    private Integer qty;
    private Double total;

}
