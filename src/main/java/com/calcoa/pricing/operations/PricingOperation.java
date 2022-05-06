package com.calcoa.pricing.operations;

import com.calcoa.pricing.dao.ServiceBillingType;
import com.calcoa.pricing.dao.entity.Contract;
import com.calcoa.pricing.dao.entity.Customer;
import com.calcoa.pricing.exceptions.CustomerNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@AllArgsConstructor
public class PricingOperation {

    private final CustomerRepository customerRepository;

    public BigDecimal pricingPerCustomer(String customerId, String startDateString, String endDateString) {
        Customer customerInfo = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        LocalDate startDate = dateConvertedFromStringToLocalDate(startDateString);
        LocalDate endDate = dateConvertedFromStringToLocalDate(endDateString);

        LocalDate billingStartDate = customerInfo.getStartDate().plusDays(customerInfo.getNumberOfFreeDays());
        LocalDate effectiveStartDate = selectAppropriateEarlierDate(billingStartDate, startDate);

        return customerInfo.getContracts().stream()
                .map(contract -> priceForContract(contract, effectiveStartDate, endDate))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal priceForContract(Contract contract, LocalDate startDate, LocalDate endDate) {
        LocalDate billingStartDate = selectAppropriateEarlierDate(startDate, contract.getStartDate());

        BigDecimal numberOfBilledDays = countBilledDays(contract.getService().getType(), billingStartDate, endDate);

        BigDecimal numberOfDiscountedDays;
        BigDecimal discountedDaysTotal;
        if (checkIfDiscountApplies(contract)) {
            LocalDate discountStartDate = selectAppropriateEarlierDate(startDate, contract.getDiscountStartDate());

            LocalDate discountEndDate = selectAppropriateLaterDate(endDate, contract.getDiscountEndDate());

            numberOfDiscountedDays = countBilledDays(contract.getService().getType(), discountStartDate, discountEndDate);

            discountedDaysTotal = contract.getService().getPrice()
                    .multiply(BigDecimal.ONE.subtract(contract.getDiscountPercentageValue()))
                    .multiply(numberOfDiscountedDays);
        } else {
            numberOfDiscountedDays = BigDecimal.ZERO;
            discountedDaysTotal = BigDecimal.ZERO;
        }
        BigDecimal fullPriceDays = numberOfBilledDays.subtract(numberOfDiscountedDays);
        BigDecimal fullPriceDaysTotal = contract.getService().getPrice()
                .multiply(fullPriceDays);

        return fullPriceDaysTotal.add(discountedDaysTotal).setScale(2, RoundingMode.HALF_UP);
    }

    private LocalDate selectAppropriateEarlierDate(LocalDate dateA, LocalDate dateB) {
        if (dateA.isAfter(dateB)) {
            return dateA;
        } else {
            return dateB;
        }
    }

    private LocalDate selectAppropriateLaterDate(LocalDate dateA, LocalDate dateB) {
        if (dateA.isAfter(dateB)) {
            return dateB;
        } else {
            return dateA;
        }
    }

    private Boolean checkIfDiscountApplies(Contract contract) {
    return contract.getDiscountStartDate() != null && contract.getDiscountEndDate() != null && contract.getDiscountPercentageValue() != null; }

    private LocalDate dateConvertedFromStringToLocalDate(String dateToConvert) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateToConvert, formatter);
    }

    private BigDecimal countBilledDays(ServiceBillingType serviceType, LocalDate startDate, LocalDate endDate) {
        if (serviceType == ServiceBillingType.BUSINESS_DAY) {
            return countBusinessDays(startDate, endDate);
        } else {
            return new BigDecimal(DAYS.between(startDate, endDate));
        }
    }

    private BigDecimal countBusinessDays(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Invalid dates");
        }
        Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;

        return new BigDecimal(startDate.datesUntil(endDate)
                .filter(isWeekend.negate()).count());
    }
}
