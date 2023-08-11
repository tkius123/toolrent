package com.example.toolrent.util;

import com.example.toolrent.dto.CheckoutDTO;
import com.example.toolrent.model.Checkout;
import com.example.toolrent.model.DayType;
import org.springframework.cglib.core.Local;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class CheckoutUtil {

    public static LocalDate after(LocalDate fromDate, int daysLater) {
        return fromDate.plusDays(daysLater);
    }

    public static long getChargeDays(
            boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge,
            LocalDate checkoutDate, int rentalDayCount) {
        LocalDate startDate = checkoutDate.plusDays(1);
        LocalDate endDate = startDate.plusDays(rentalDayCount);
        return startDate.datesUntil(endDate).filter(localDate ->
            dayTypeOf(localDate) == DayType.Holiday && holidayCharge ||
            dayTypeOf(localDate) == DayType.WeekEnd && weekendCharge ||
            dayTypeOf(localDate) == DayType.WeekDay && weekdayCharge
        ).count();
    }

    public static DayType dayTypeOf(LocalDate date) {
        if(isHoliday(date)) return DayType.Holiday;
        return isWeekEnd(date) ? DayType.WeekEnd : DayType.WeekDay;
    }

    public static boolean isHoliday(LocalDate date) {
        return date.getMonth() == Month.JULY && date.getDayOfMonth() == 4 ||
                date.getMonth() == Month.SEPTEMBER && date.getDayOfWeek() == DayOfWeek.MONDAY && date.minusDays(7).getMonth() == Month.AUGUST;
    }

    public static boolean isWeekEnd(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static Checkout convertToCheckout(CheckoutDTO checkoutDTO) {
        LocalDate checkoutDate = toLocalDate(checkoutDTO.getCheckoutDate());
        if(checkoutDate == null) throw new RuntimeException("Invalid date: " + checkoutDTO.getCheckoutDate());

        return new Checkout(
                checkoutDTO.getToolCode(), checkoutDTO.getRentalDayCount(), checkoutDTO.getDiscountPercentage(), checkoutDate);
    }

    public static LocalDate toLocalDate(String str) {
        String[] tokens = str.split("/");
        if(tokens.length == 3) {
            return LocalDate.of(Integer.parseInt(tokens[2]) + 2000, Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
        }
        return null;
    }
}
