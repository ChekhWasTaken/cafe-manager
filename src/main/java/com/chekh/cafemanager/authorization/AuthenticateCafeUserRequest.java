package com.chekh.cafemanager.authorization;

import lombok.Data;

@Data
public class AuthenticateCafeUserRequest {
    private String username;
    private String password;
}
