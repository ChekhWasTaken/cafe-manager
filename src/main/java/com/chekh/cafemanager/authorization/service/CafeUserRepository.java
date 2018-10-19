package com.chekh.cafemanager.authorization.service;

import com.chekh.cafemanager.authorization.entity.CafeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeUserRepository extends JpaRepository<CafeUser, String> {
    CafeUser findByUsername(String username);
}
