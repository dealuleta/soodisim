package com.soodisim.demosoodisim.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {

    private Long userId;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    private String role;
    private int points;

    public UserDTO(Long userId, String name, String email, String role, int points) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.points = points;
    }

    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public int getPoints() { return points; }
}
