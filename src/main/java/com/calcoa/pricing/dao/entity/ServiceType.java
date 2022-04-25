package com.calcoa.pricing.dao.entity;

import com.calcoa.pricing.dao.ServiceBillingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "services")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceType {

    @Id
    private String name;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private ServiceBillingType type;
}
