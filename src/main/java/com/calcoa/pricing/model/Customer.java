package com.calcoa.pricing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @Column(name="customerid")
    private String customerID;
    @Column(name="name")
    private String name;
    @Column(name="number_free_days")
    private Integer numberOfFreeDays;
    @Column(name="price_service_a")
    private Float priceServiceA;
    @Column(name="price_service_b")
    private Float priceServiceB;
    @Column(name="price_service_c")
    private Float priceServiceC;
    @Column(name="discount_service_a")
    private Float discountServiceA;
    @Column(name="discount_service_b")
    private Float discountServiceB;
    @Column(name="discount_service_c")
    private Float discountServiceC;
    @Column(name="start_service_a")
    private LocalDate startingDateServiceA;
    @Column(name="start_service_b")
    private LocalDate startingDateServiceB;
    @Column(name="start_service_c")
    private LocalDate startingDateServiceC;
}
