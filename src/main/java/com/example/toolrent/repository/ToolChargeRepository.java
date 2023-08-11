package com.example.toolrent.repository;

import com.example.toolrent.model.ToolCharge;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ToolChargeRepository {

    private final Map<String, ToolCharge> toolChargeMap = new HashMap<>();

    public ToolChargeRepository() {
        this.add(new ToolCharge("Ladder", 199, true, true, false));
        this.add(new ToolCharge("Chainsaw", 149, true, false, true));
        this.add(new ToolCharge("Jackhammer", 299, true, false, false));

    }

    public ToolCharge get(String toolType) {
        return toolChargeMap.get(toolType);
    }

    public ToolCharge add(ToolCharge toolCharge) {
        return toolChargeMap.put(toolCharge.getToolType(), toolCharge);
    }
}
