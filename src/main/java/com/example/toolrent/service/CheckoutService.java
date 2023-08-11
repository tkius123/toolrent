package com.example.toolrent.service;

import com.example.toolrent.model.Checkout;
import com.example.toolrent.model.RentalAgreement;
import com.example.toolrent.model.Tool;
import com.example.toolrent.model.ToolCharge;
import com.example.toolrent.repository.ToolChargeRepository;
import com.example.toolrent.repository.ToolRepository;
import com.example.toolrent.util.CheckoutUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CheckoutService {

    private final ToolRepository toolRepository;
    private final ToolChargeRepository toolChargeRepository;

    public CheckoutService(ToolRepository toolRepository, ToolChargeRepository toolChargeRepository) {
        this.toolRepository = toolRepository;
        this.toolChargeRepository = toolChargeRepository;
    }

    public RentalAgreement makeRentalAgreement(Checkout checkout) {
        RentalAgreement rentalAgreement = new RentalAgreement();

        Tool tool = toolRepository.get(checkout.getToolCode());
        if(tool == null) {
            throw new IllegalStateException("Tool code does not exit: " + checkout.getToolCode());
        }

        rentalAgreement.setToolCode(tool.getToolCode());
        rentalAgreement.setToolType(tool.getToolType());
        rentalAgreement.setToolBrand(tool.getBrand());

        rentalAgreement.setRentalDays(checkout.getRentalDayCount());
        rentalAgreement.setCheckoutDate(checkout.getCheckoutDate());

        rentalAgreement.setDueDate(CheckoutUtil.after(checkout.getCheckoutDate(), checkout.getRentalDayCount()));

        ToolCharge toolCharge = toolChargeRepository.get(tool.getToolType());
        if(toolCharge == null) {
            throw new IllegalStateException("Tool charge info does not exit: " + tool.getToolType());
        }

        rentalAgreement.setDailyRentalChargeInCent(toolCharge.getDailyChargeInCent());
        rentalAgreement.setChargeDays(CheckoutUtil.getChargeDays(
                toolCharge.isWeekdayCharge(), toolCharge.isWeekendCharge(), toolCharge.isHolidayCharge(),
                checkout.getCheckoutDate(), checkout.getRentalDayCount()));

        rentalAgreement.setPreDiscountChargeInCent(toolCharge.getDailyChargeInCent() * rentalAgreement.getChargeDays());
        rentalAgreement.setDiscountPercentage(checkout.getDiscountPercentage());

        long value = checkout.getDiscountPercentage() * rentalAgreement.getPreDiscountChargeInCent();
        if((value % 100) / 10 > 4) rentalAgreement.setDiscountAmountInCent(value / 100 + 1);
        else rentalAgreement.setDiscountAmountInCent(value / 100);
        rentalAgreement.setFinalChargeInCent(
                rentalAgreement.getPreDiscountChargeInCent() - rentalAgreement.getDiscountAmountInCent());

        log.info("Rental Agreement: {}", rentalAgreement);
        return rentalAgreement;
    }
}
