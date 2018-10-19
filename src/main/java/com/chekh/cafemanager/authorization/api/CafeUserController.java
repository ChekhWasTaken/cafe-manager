package com.chekh.cafemanager.authorization.api;

import com.chekh.cafemanager.authorization.service.CafeUserService;
import com.chekh.cafemanager.authorization.service.CreateCafeUserRequest;
import com.chekh.cafemanager.authorization.api.error.InvalidUserParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class CafeUserController {
    private static final int PASSWORD_LENGTH = 6;

    @Autowired
    private CafeUserService cafeUserService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody CreateCafeUserRequest request) {
        if (StringUtils.isEmpty(request.getUsername())) {
            throw new InvalidUserParameterException("username should not be empty");
        }

        if (StringUtils.isEmpty(request.getPassword())) {
            throw new InvalidUserParameterException("password should not be empty");
        }

        if (request.getPassword().length() < PASSWORD_LENGTH) {
            throw new InvalidUserParameterException(String.format("password length should be at least %d characters", PASSWORD_LENGTH));
        }

        cafeUserService.addUser(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
