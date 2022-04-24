package com.calcoa.pricing.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Contracts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contract {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn
    private ServiceType service;

    private LocalDate startDate;

    private BigDecimal discountPercentageValue;

    private LocalDate discountStartDate;

    private LocalDate discountEndDate;

}
