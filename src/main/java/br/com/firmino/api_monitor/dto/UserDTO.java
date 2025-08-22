package br.com.firmino.api_monitor.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String displayName;
}
