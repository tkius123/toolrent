package com.example.toolrent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckoutDTO {
    private String toolCode;
    private int rentalDayCount;
    private int discountPercentage;
    private String checkoutDate;
}
