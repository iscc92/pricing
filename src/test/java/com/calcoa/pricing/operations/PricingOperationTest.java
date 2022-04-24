package com.calcoa.pricing.operations;

import com.calcoa.pricing.model.Customer;
import com.calcoa.pricing.model.ServiceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class PricingOperationTest {

    @InjectMocks
    private PricingOperation pricingOperation;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Test
    void test_pricingPerCustomer_CustomerY() {
        //Given
        String customerId = "Customer Y";
        String startDate = "2018-01-01";
        String endDate = "2019-10-01";
        BigDecimal expected = BigDecimal.valueOf(75.60).setScale(2, RoundingMode.DOWN);

        Customer customerToCalculateBillingPrice = Customer.builder()
                .customerID("Customer Y")
                .numberOfFreeDays(200)
                .priceServiceB(BigDecimal.valueOf(0.24))
                .priceServiceC(BigDecimal.valueOf(0.4))
                .discountServiceB(BigDecimal.valueOf(0.3))
                .discountServiceC(BigDecimal.valueOf(0.3))
                .startingDateServiceB(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .startingDateServiceC(LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
        Mockito.when(customerRepository.findById(eq(customerId)))
                .thenReturn(Optional.of(customerToCalculateBillingPrice));

        ServiceType serviceAMock = ServiceType.builder()
                .name("serviceA")
                .type("business_day")
                .build();
        Mockito.when(serviceRepository.findById("serviceA")).thenReturn(Optional.of(serviceAMock));

        ServiceType serviceBMock = ServiceType.builder()
                .name("serviceB")
                .type("business_day")
                .build();
        Mockito.when(serviceRepository.findById("serviceB")).thenReturn(Optional.of(serviceBMock));

        ServiceType serviceCMock = ServiceType.builder()
                .name("serviceC")
                .type("calendar_day")
                .build();
        Mockito.when(serviceRepository.findById("serviceC")).thenReturn(Optional.of(serviceCMock));

        //When
        BigDecimal actual = pricingOperation.pricingPerCustomer(customerId, startDate, endDate);

        //Then
        Assertions.assertEquals(expected, actual);
    }
}
