package com.chekh.cafemanager.authorization;

import com.chekh.cafemanager.authorization.entity.CafeUser;
import com.chekh.cafemanager.authorization.service.CafeUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private CafeUserRepository cafeUserRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        CafeUser cafeUser = cafeUserRepository.findByUsername(s);

        if (cafeUser == null) throw new UsernameNotFoundException("username " + s + " not found");

        return new User(cafeUser.getUsername(), cafeUser.getPassword(), cafeUser.getRoles());
    }
}
