package com.calcoa.pricing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PricingResponseDTO {
    private BigDecimal billingPrice;

}
