package com.calcoa.pricing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    public String customerID;
    public String name;
    public LocalDate freeDayStart;
    public LocalDate freeDayEnd;
    public Float priceServiceA;
    public Float priceServiceB;
    public Float priceServiceC;
    public Float discountServiceA;
    public Float discountServiceB;
    public Float discountServiceC;
    public LocalDate startingDateServiceA;
    public LocalDate startingDateServiceB;
    public LocalDate startingDateServiceC;
}
