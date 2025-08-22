package br.com.firmino.api_monitor.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiServiceStatusDTO {
    private Long id;
    private String name;
    private String url;
    private String description;
    private String serviceType;

    private String status;
    private int statusCode;
    private long responseTime;
    private LocalDateTime lastCheck;
}


