package com.chekh.cafemanager.authorization.service;

import com.chekh.cafemanager.authorization.entity.CafeUser;
import com.chekh.cafemanager.authorization.entity.CafeUserRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CafeUserService {
    @Autowired
    private CafeUserRepository cafeUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CafeUserRoleRepository cafeUserRoleRepository;

    public void addUser(CreateCafeUserRequest request) {
        cafeUserRepository.save(mapDtoToEntity(request));
    }

    private CafeUser mapDtoToEntity(CreateCafeUserRequest request) {
        CafeUser cafeUser = new CafeUser(request.getUsername(), bCryptPasswordEncoder.encode(request.getPassword()));
        cafeUser.getRoles().addAll(cafeUserRoleRepository.findAll());
        return cafeUser;
    }

    public boolean isWaiter(String userId) {
        CafeUser user = cafeUserRepository.getOne(userId);

        if (user == null) return false;

        return user.getRoles().stream().filter(it -> it.getType() == CafeUserRoleType.WAITER).collect(Collectors.toList()).size() > 0;
    }

    public CafeUser get(String waiterId) {
        return cafeUserRepository.getOne(waiterId);
    }
}
