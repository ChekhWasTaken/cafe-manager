package com.chekh.cafemanager.service.table;

import com.chekh.cafemanager.authorization.entity.CafeUser;
import com.chekh.cafemanager.entity.CafeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<CafeTable, String> {
    List<CafeTable> findByWaiter(CafeUser waiter);
}
