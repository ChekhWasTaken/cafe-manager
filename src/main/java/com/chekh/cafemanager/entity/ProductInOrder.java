package com.chekh.cafemanager.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductInOrder {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, updatable = false, columnDefinition = "CHAR(64)")
    private String id;

    @ManyToOne(targetEntity = CafeOrder.class)
    @JoinColumn(name = "cafeOrder", referencedColumnName = "id", nullable = false)
    private CafeOrder cafeOrder;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product", referencedColumnName = "id", nullable = false)
    private Product product;

    @EqualsAndHashCode.Exclude
    private int count;
}
