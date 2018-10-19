package com.chekh.cafemanager.authorization.service;

import com.chekh.cafemanager.authorization.entity.CafeUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeUserRoleRepository extends JpaRepository<CafeUserRole, String> {
}
