package com.calcoa.pricing.operations;

import com.calcoa.pricing.exceptions.CustomerNotFoundException;
import com.calcoa.pricing.model.Customer;
import com.calcoa.pricing.model.ServiceType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Predicate;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@AllArgsConstructor
public class PricingOperation {

    private final CustomerRepository customerRepository;

    private final ServiceRepository serviceRepository;

    public BigDecimal pricingPerCustomer(String customerId, String startDate, String endDate) {
        Customer customerInfo = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not Found!"));

        return priceForServiceA(customerInfo, dateConvertedFromStringToLocalDate(startDate), dateConvertedFromStringToLocalDate(endDate))
                .add(priceForServiceB(customerInfo, dateConvertedFromStringToLocalDate(startDate), dateConvertedFromStringToLocalDate(endDate)))
                .add(priceForServiceC(customerInfo, dateConvertedFromStringToLocalDate(startDate), dateConvertedFromStringToLocalDate(endDate)));
    }

    private BigDecimal priceForServiceA(Customer customer, LocalDate startDate, LocalDate endDate) {
        Optional<ServiceType> serviceA = serviceRepository.findById("serviceA");

        if (serviceA.isPresent()) {
            BigDecimal numberOfBilledDays = countBilledDays(serviceA.get().getType(),
                    startDate, endDate, customer.getNumberOfFreeDays());
            if (customer.getPriceServiceA() != null) {
                if (customer.getDiscountServiceA() != null) {
                    return customer.getDiscountServiceA().multiply(customer.getPriceServiceA()).multiply(numberOfBilledDays);
                } else {
                    return customer.getPriceServiceA().multiply(numberOfBilledDays);
                }
            }
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal priceForServiceB(Customer customer, LocalDate startDate, LocalDate endDate) {
        Optional<ServiceType> serviceB = serviceRepository.findById("serviceB");

        if (serviceB.isPresent()) {
            BigDecimal numberOfBilledDays = countBilledDays(serviceB.get().getType(),
                    startDate, endDate, customer.getNumberOfFreeDays());
            if (customer.getPriceServiceB() != null) {
                if (customer.getDiscountServiceB() != null) {
                    return customer.getDiscountServiceB().multiply(customer.getDiscountServiceB()).multiply(numberOfBilledDays);
                } else {
                    return customer.getPriceServiceB().multiply(numberOfBilledDays);
                }
            }
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal priceForServiceC(Customer customer, LocalDate startDate, LocalDate endDate) {
        Optional<ServiceType> serviceC = serviceRepository.findById("serviceC");

        if (serviceC.isPresent()) {
            BigDecimal numberOfBilledDays = countBilledDays(serviceC.get().getType(),
                    startDate, endDate, customer.getNumberOfFreeDays());
            if (customer.getPriceServiceC() != null) {
                if (customer.getDiscountServiceC() != null) {
                    return customer.getDiscountServiceC().multiply(customer.getPriceServiceC()).multiply(numberOfBilledDays);
                } else {
                    return customer.getPriceServiceC().multiply(numberOfBilledDays);
                }
            }
        }
        return BigDecimal.ZERO;
    }

    //To add "priceForServiceD" copy&paste and refactor one of the previous A,B or C. Tables need to be updated, too

    private LocalDate dateConvertedFromStringToLocalDate(String dateToConvert) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateToConvert, formatter);
    }

    private BigDecimal countBilledDays(String serviceType, LocalDate startDate, LocalDate endDate, int numberOfFreeDays) {
        BigDecimal numberOfBusinessDays = countBusinessDays(startDate, endDate);
        BigDecimal numberOfCalendarDays = new BigDecimal(DAYS.between(startDate, endDate));
        if (serviceType.equals("business_day")) {
            return numberOfBusinessDays.subtract(BigDecimal.valueOf(numberOfFreeDays));
        }
        return numberOfCalendarDays.subtract(BigDecimal.valueOf(numberOfFreeDays));
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
