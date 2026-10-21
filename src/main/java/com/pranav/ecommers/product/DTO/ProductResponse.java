package com.pranav.ecommers.product.DTO;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String category;
    private Integer StockQuantity;
    private String imageUrl;
    private BigDecimal Price;
    private Boolean Active;
}
