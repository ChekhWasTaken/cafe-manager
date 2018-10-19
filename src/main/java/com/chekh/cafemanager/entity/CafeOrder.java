package com.chekh.cafemanager.entity;

import com.chekh.cafemanager.misc.OrderStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CafeOrder {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, updatable = false, columnDefinition = "CHAR(64)")
    private String id;

    @ManyToOne(targetEntity = CafeTable.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "cafeTable", nullable = false, referencedColumnName = "id")
    private CafeTable cafeTable;

    @Column(nullable = false)
    private OrderStatus orderStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ProductInOrder> orderProducts;
}
