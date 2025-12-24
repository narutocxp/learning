package com.shopping.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DateTimeTool {
    private static final Map<String, String> COUNTRY_MAP = Map.of(
            "CN", "Asia/Shanghai",
            "US", "America/New_York",
            "UK", "Europe/London",
            "JP", "Asia/Tokyo"
    );

    @Tool(description = "国家时间查询工具", name = "getCountryTime")
    public String getCurrentDateTimeByCountry(@ToolParam(description = "country") String country) {
        ZoneId zoneId = ZoneId.of(COUNTRY_MAP.getOrDefault(country, "UTC"));
        return ZonedDateTime.now(zoneId)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
    }

    @Tool(description = "获取所有支持的国家列表")
    public List<String> getSupportedCountries() {
        return new ArrayList<>(COUNTRY_MAP.keySet());
    }
}