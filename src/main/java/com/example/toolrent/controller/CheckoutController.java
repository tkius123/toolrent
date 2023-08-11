package com.example.toolrent.controller;

import com.example.toolrent.dto.CheckoutDTO;
import com.example.toolrent.model.Checkout;
import com.example.toolrent.model.RentalAgreement;
import com.example.toolrent.service.CheckoutService;
import com.example.toolrent.util.CheckoutUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@Api(tags = "Checkout Controller")
public class CheckoutController {
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/checkout")
    @ApiOperation(value = "Checkout a rental", notes = "This endpoint processes the checkout of a rental.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Checkout successful"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "Rental not found")
    })
    public RentalAgreement checkout(@RequestBody CheckoutDTO checkoutDTO) {
        log.info("New checkout dto: {}", checkoutDTO);
        if(checkoutDTO.getRentalDayCount() < 1)
            throw new IllegalStateException("Rental Day less than 1: " + checkoutDTO.getRentalDayCount());

        if(checkoutDTO.getDiscountPercentage() < 0 || checkoutDTO.getDiscountPercentage() > 100)
            throw new IllegalStateException("Discount Percentage not in [0, 100]: " + checkoutDTO.getDiscountPercentage());
        Checkout checkout = CheckoutUtil.convertToCheckout(checkoutDTO);

        log.info("Checkout: {}", checkout);
        return checkoutService.makeRentalAgreement(checkout);
    }
}
