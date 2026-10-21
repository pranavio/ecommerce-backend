package com.pranav.ecommers.product.DTO;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductRequest {
    private String name;
    private String description;
    private String category;
    private Integer stockQuantity;
    private String imageUrl;
    private BigDecimal price;
}
