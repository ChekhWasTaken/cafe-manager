package com.chekh.cafemanager.listener;

import com.chekh.cafemanager.authorization.entity.CafeUserRole;
import com.chekh.cafemanager.authorization.entity.CafeUserRoleType;
import com.chekh.cafemanager.authorization.service.CafeUserRoleRepository;
import com.chekh.cafemanager.entity.CafeTable;
import com.chekh.cafemanager.entity.Product;
import com.chekh.cafemanager.service.product.ProductRepository;
import com.chekh.cafemanager.service.table.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventListener {
    String[] ids = new String[]{"eddce792-973b-4687-a4f6-fb0cb3943acf", "07f27932-2fc9-4f78-90e6-11e0fed58af4", "74dba261-46b0-41f1-8cfd-c6edb025f56d"};

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CafeUserRoleRepository cafeUserRoleRepository;

    @EventListener({ContextRefreshedEvent.class})
    public void onContextRefreshedEvent() {
        tableRepository.save(new CafeTable("a33813eb-7d98-431a-b7a4-7b1df23e0ecd"));

        for (int i = 0; i < ids.length; i++) {
            Product product = new Product(ids[i], "Product #" + i);
            productRepository.save(product);
        }

        CafeUserRole waiter = new CafeUserRole();
        CafeUserRole manager = new CafeUserRole();

        waiter.setType(CafeUserRoleType.WAITER);
        manager.setType(CafeUserRoleType.MANAGER);

        cafeUserRoleRepository.save(waiter);
        cafeUserRoleRepository.save(manager);
    }
}
