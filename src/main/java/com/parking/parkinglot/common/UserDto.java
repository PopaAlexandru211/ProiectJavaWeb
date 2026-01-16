package com.parking.parkinglot.common;

public class UserDto {
    private Long id;
    private String username;
    private String email;

    public UserDto(Long id,String username, String email) {
        this.username = username;
        this.email = email;
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}