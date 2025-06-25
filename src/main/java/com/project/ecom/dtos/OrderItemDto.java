package com.project.ecom.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderItemDto {
    private ProductInCartDto product;
    private Integer quantity;
}
