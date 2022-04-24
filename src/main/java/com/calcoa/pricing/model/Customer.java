package com.calcoa.pricing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @Column(name="customerid")
    private String customerID;
    @Column(name="name")
    private String name;
    @Column(name="number_free_days")
    private Integer numberOfFreeDays;
    @Column(name="price_service_a")
    private BigDecimal priceServiceA;
    @Column(name="price_service_b")
    private BigDecimal priceServiceB;
    @Column(name="price_service_c")
    private BigDecimal priceServiceC;
    @Column(name="discount_service_a")
    private BigDecimal discountServiceA;
    @Column(name="discount_service_b")
    private BigDecimal discountServiceB;
    @Column(name="discount_service_c")
    private BigDecimal discountServiceC;
    @Column(name="start_service_a")
    private LocalDate startingDateServiceA;
    @Column(name="start_service_b")
    private LocalDate startingDateServiceB;
    @Column(name="start_service_c")
    private LocalDate startingDateServiceC;
}
