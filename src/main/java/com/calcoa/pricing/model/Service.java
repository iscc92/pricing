package com.calcoa.pricing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="Service")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service {

    @Id
    public String name;
    public Float price;
    public String type;
}
