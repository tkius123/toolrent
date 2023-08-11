package com.example.toolrent.repository;

import com.example.toolrent.model.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class ToolRepository {
    private final Map<String, Tool> toolMap = new HashMap<>();

    public ToolRepository() {
        log.info("ToolRepository initialized");

        this.add(new Tool("CHNS", "Chainsaw", "Stihl"));
        this.add(new Tool("LADW", "Ladder", "Werner"));
        this.add(new Tool("JAKD", "Jackhammer", "DeWalt"));
        this.add(new Tool("JAKR", "Jackhammer", "Ridgid"));
    }

    public Tool get(String toolCode) {
        return toolMap.get(toolCode);
    }

    public Tool add(Tool tool) {
        return toolMap.put(tool.getToolCode(), tool);
    }
}
