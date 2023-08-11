package com.example.toolrent.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RentalAgreement {
    private String toolCode;
    private String toolType;
    private String toolBrand;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private int dailyRentalChargeInCent;
    private long chargeDays;
    private long preDiscountChargeInCent;
    private int discountPercentage;
    private long discountAmountInCent;
    private long finalChargeInCent;
}
