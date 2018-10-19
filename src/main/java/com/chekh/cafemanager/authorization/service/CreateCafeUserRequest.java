package com.chekh.cafemanager.authorization.service;

import lombok.Data;

@Data
public class CreateCafeUserRequest {
    private String username;
    private String password;
}
