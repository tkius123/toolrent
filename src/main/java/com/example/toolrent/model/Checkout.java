package com.example.toolrent.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Checkout {
    private String toolCode;
    private int rentalDayCount;
    private int discountPercentage;
    private LocalDate checkoutDate;
}
