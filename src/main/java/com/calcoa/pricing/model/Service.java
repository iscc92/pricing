package com.calcoa.pricing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    public Float price;
    @Column(name="type")
    public String type;
}
