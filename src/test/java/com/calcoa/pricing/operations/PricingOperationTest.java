package com.calcoa.pricing.operations;

import com.calcoa.pricing.dao.ServiceBillingType;
import com.calcoa.pricing.dao.entity.Contract;
import com.calcoa.pricing.dao.entity.Customer;
import com.calcoa.pricing.dao.entity.ServiceType;
import com.calcoa.pricing.dto.PricingResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class PricingOperationTest {

    @InjectMocks
    private PricingOperation pricingOperation;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void test_pricingPerCustomer_CustomerX() {
        //Given
        String customerId = "X";
        String startDate = "2019-09-20";
        String endDate = "2019-10-01";
        LocalDate discountStartDate = LocalDate.parse("2019-09-22");
        LocalDate discountEndDate = LocalDate.parse("2019-09-24");

        ServiceType serviceA = ServiceType.builder()
                .price(BigDecimal.valueOf(0.2))
                .type(ServiceBillingType.BUSINESS_DAY)
                .build();

        ServiceType serviceC = ServiceType.builder()
                .price(BigDecimal.valueOf(0.4))
                .type(ServiceBillingType.CALENDAR_DAY)
                .build();

        Contract contractForServiceA = Contract.builder()
                .service(serviceA)
                .startDate(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();

        Contract contractForServiceC = Contract.builder()
                .service(serviceC)
                .startDate(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .discountStartDate(discountStartDate)
                .discountEndDate(discountEndDate)
                .discountPercentageValue(BigDecimal.valueOf(0.2))
                .build();

        Customer customer = Customer.builder()
                .customerID(customerId)
                .numberOfFreeDays(0)
                .startDate(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .contracts(List.of(contractForServiceA, contractForServiceC))
                .build();

        Mockito.when(customerRepository.findById(eq(customerId))).thenReturn(Optional.of(customer));


        //When
        PricingResponseDTO actual = pricingOperation.pricingPerCustomer(customerId, startDate, endDate);

        //Then
        Assertions.assertEquals(BigDecimal.valueOf(5.64), actual.getBillingPrice());
    }

    @Test
    void test_pricingPerCustomer_CustomerY() {
        //Given
        String customerId = "Y";
        String startDate = "2018-01-01";
        String endDate = "2019-10-01";
        LocalDate discountStartDate = LocalDate.parse(startDate);
        LocalDate discountEndDate = LocalDate.parse("2042-09-24");

        ServiceType serviceB = ServiceType.builder()
                .price(BigDecimal.valueOf(0.24))
                .type(ServiceBillingType.BUSINESS_DAY)
                .build();

        ServiceType serviceC = ServiceType.builder()
                .price(BigDecimal.valueOf(0.4))
                .type(ServiceBillingType.CALENDAR_DAY)
                .build();

        Contract contractForServiceB = Contract.builder()
                .service(serviceB)
                .startDate(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .discountStartDate(discountStartDate)
                .discountEndDate(discountEndDate)
                .discountPercentageValue(BigDecimal.valueOf(0.3))
                .build();

        Contract contractForServiceC = Contract.builder()
                .service(serviceC)
                .startDate(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .discountStartDate(discountStartDate)
                .discountEndDate(discountEndDate)
                .discountPercentageValue(BigDecimal.valueOf(0.3))
                .build();

        Customer customer = Customer.builder()
                .customerID(customerId)
                .numberOfFreeDays(200)
                .startDate(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .contracts(List.of(contractForServiceB, contractForServiceC))
                .build();

        Mockito.when(customerRepository.findById(eq(customerId))).thenReturn(Optional.of(customer));


        //When
        PricingResponseDTO actual = pricingOperation.pricingPerCustomer(customerId, startDate, endDate);

        //Then
        Assertions.assertEquals(BigDecimal.valueOf(175.06), actual.getBillingPrice());
    }
}
