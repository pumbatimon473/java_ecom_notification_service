package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ProductInCartDto {
    private Long productId;
    private String name;
    private BigDecimal price;
}
