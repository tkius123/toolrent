package com.example.toolrent.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ToolCharge {
    private String toolType;
    private int dailyChargeInCent;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;
}
