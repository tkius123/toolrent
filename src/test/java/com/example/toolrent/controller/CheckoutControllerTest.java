package com.example.toolrent.controller;

import com.example.toolrent.dto.CheckoutDTO;
import com.example.toolrent.model.RentalAgreement;
import com.example.toolrent.repository.ToolChargeRepository;
import com.example.toolrent.repository.ToolRepository;
import com.example.toolrent.service.CheckoutService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckoutControllerTest {

    @Test
    void checkout() {

        CheckoutService checkoutService = new CheckoutService(new ToolRepository(), new ToolChargeRepository());

        CheckoutController checkoutController = new CheckoutController(checkoutService);

        CheckoutDTO checkoutDTO1 = mock(CheckoutDTO.class);
        when(checkoutDTO1.getDiscountPercentage()).thenReturn(101);
        assertThrows(IllegalStateException.class, () -> checkoutController.checkout(checkoutDTO1));

        CheckoutDTO checkoutDTO2 = mock(CheckoutDTO.class);
        when(checkoutDTO2.getToolCode()).thenReturn("LADW");
        when(checkoutDTO2.getRentalDayCount()).thenReturn(3);
        when(checkoutDTO2.getDiscountPercentage()).thenReturn(10);
        when(checkoutDTO2.getCheckoutDate()).thenReturn("7/2/20");
        RentalAgreement rentalAgreement2 = checkoutController.checkout(checkoutDTO2);
        assertEquals(rentalAgreement2.getDueDate(), LocalDate.of(2020, 7, 5));
        assertEquals(rentalAgreement2.getChargeDays(), 2);
        assertEquals(rentalAgreement2.getFinalChargeInCent(), 358);

        CheckoutDTO checkoutDTO3 = mock(CheckoutDTO.class);
        when(checkoutDTO3.getToolCode()).thenReturn("CHNS");
        when(checkoutDTO3.getRentalDayCount()).thenReturn(5);
        when(checkoutDTO3.getDiscountPercentage()).thenReturn(25);
        when(checkoutDTO3.getCheckoutDate()).thenReturn("7/2/15");
        RentalAgreement rentalAgreement3 = checkoutController.checkout(checkoutDTO3);
        assertEquals(rentalAgreement3.getDueDate(), LocalDate.of(2015, 7, 7));
        assertEquals(rentalAgreement3.getChargeDays(), 4);
        assertEquals(rentalAgreement3.getFinalChargeInCent(), 447);

        CheckoutDTO checkoutDTO4 = mock(CheckoutDTO.class);
        when(checkoutDTO4.getToolCode()).thenReturn("JAKD");
        when(checkoutDTO4.getRentalDayCount()).thenReturn(6);
        when(checkoutDTO4.getDiscountPercentage()).thenReturn(0);
        when(checkoutDTO4.getCheckoutDate()).thenReturn("9/3/15");
        RentalAgreement rentalAgreement4 = checkoutController.checkout(checkoutDTO4);
        assertEquals(rentalAgreement4.getDueDate(), LocalDate.of(2015, 9, 9));
        assertEquals(rentalAgreement4.getChargeDays(), 3);
        assertEquals(rentalAgreement4.getFinalChargeInCent(), 897);

        CheckoutDTO checkoutDTO5 = mock(CheckoutDTO.class);
        when(checkoutDTO5.getToolCode()).thenReturn("JAKR");
        when(checkoutDTO5.getRentalDayCount()).thenReturn(9);
        when(checkoutDTO5.getDiscountPercentage()).thenReturn(0);
        when(checkoutDTO5.getCheckoutDate()).thenReturn("7/2/15");
        RentalAgreement rentalAgreement5 = checkoutController.checkout(checkoutDTO5);
        assertEquals(rentalAgreement5.getDueDate(), LocalDate.of(2015, 7, 11));
        assertEquals(rentalAgreement5.getChargeDays(), 6);
        assertEquals(rentalAgreement5.getFinalChargeInCent(), 1794);

        CheckoutDTO checkoutDTO6 = mock(CheckoutDTO.class);
        when(checkoutDTO6.getToolCode()).thenReturn("JAKR");
        when(checkoutDTO6.getRentalDayCount()).thenReturn(4);
        when(checkoutDTO6.getDiscountPercentage()).thenReturn(50);
        when(checkoutDTO6.getCheckoutDate()).thenReturn("7/2/20");
        RentalAgreement rentalAgreement6 = checkoutController.checkout(checkoutDTO6);
        assertEquals(rentalAgreement6.getDueDate(), LocalDate.of(2020, 7, 6));
        assertEquals(rentalAgreement6.getChargeDays(), 2);
        assertEquals(rentalAgreement6.getFinalChargeInCent(), 299);
    }
}