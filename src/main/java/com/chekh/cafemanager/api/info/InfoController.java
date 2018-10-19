package com.chekh.cafemanager.api.info;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    @PreAuthorize("hasRole('MANAGER')")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public InfoResponseDto getInfo() {
        return new InfoResponseDto("hello");
    }
}
