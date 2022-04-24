package com.calcoa.pricing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name="services")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service {

    @Id
    @Column(name="name")
    public String name;
    @Column(name="price")
    public BigDecimal price;
    @Column(name="type")
    public String type;
}
