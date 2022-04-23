package com.calcoa.pricing.operations;

import com.calcoa.pricing.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@AllArgsConstructor
public class PricingOperation {

    private final CustomerRepository customerRepository;

    private final ServiceRepository serviceRepository;

    public Float pricingPerCustomer(String customerId, String startDate, String endDate) {
        Customer customerInfo = customerRepository.getById(customerId);

        return priceForServiceA(customerInfo, dateConvertedFromStringToLocalDate(startDate), dateConvertedFromStringToLocalDate(endDate)) +
                priceForServiceB(customerInfo, dateConvertedFromStringToLocalDate(startDate), dateConvertedFromStringToLocalDate(endDate)) +
                priceForServiceC(customerInfo, dateConvertedFromStringToLocalDate(startDate), dateConvertedFromStringToLocalDate(endDate));
    }

    private float priceForServiceA(Customer customer, LocalDate startDate, LocalDate endDate) {

        float costForServiceA = 0;
        long numberOfBilledDays = countBilledDays(serviceRepository.getById("serviceA").getType(),
                startDate, endDate, customer.getNumberOfFreeDays());

        if (customer.getPriceServiceA()!=null) {
            if (customer.getDiscountServiceA()!=null) {
                return customer.getDiscountServiceA() * customer.getPriceServiceA() * numberOfBilledDays;
            } else {
                return customer.getPriceServiceA() * numberOfBilledDays;
            }
        }
        return costForServiceA;
    }

    private float priceForServiceB(Customer customer, LocalDate startDate, LocalDate endDate) {
        float costForServiceB = 0;
        long numberOfBilledDays = countBilledDays(serviceRepository.getById("serviceB").getType(),
                startDate, endDate, customer.getNumberOfFreeDays());

        if (customer.getPriceServiceB()!=null) {
            if (customer.getDiscountServiceB()!=null) {
                return customer.getDiscountServiceB() * customer.getDiscountServiceB() * numberOfBilledDays;
            } else {
                return customer.getPriceServiceB() * numberOfBilledDays;
            }
        }
        return costForServiceB;
    }

    private float priceForServiceC(Customer customer, LocalDate startDate, LocalDate endDate) {
        float costForServiceC = 0;
        long numberOfBilledDays = countBilledDays(serviceRepository.getById("serviceC").getType(),
                startDate, endDate, customer.getNumberOfFreeDays());

        if (customer.getPriceServiceC()!=null) {
            if (customer.getDiscountServiceC()!=null) {
                return customer.getDiscountServiceC() * customer.getPriceServiceC() * numberOfBilledDays;
            } else {
                return customer.getPriceServiceC() * numberOfBilledDays;
            }
        }
        return costForServiceC;
    }

    //To add "priceForServiceD" copy&paste and refactor one of the previous A,B or C. Tables need to be updated, too

    private LocalDate dateConvertedFromStringToLocalDate(String dateToConvert) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateToConvert, formatter);
    }

    private long countBilledDays(String serviceType, LocalDate startDate, LocalDate endDate, int numberOfFreeDays) {
        long numberOfBusinessDays = countBusinessDays(startDate, endDate);
        long numberOfCalendarDays = DAYS.between(startDate,endDate);
        if (serviceType.equals("business_day")) {
            return numberOfBusinessDays - numberOfFreeDays;
        }
        return numberOfCalendarDays - numberOfFreeDays;
    }

    private int countBusinessDays(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Invalid dates");
        }
        Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;

        return (int) startDate.datesUntil(endDate)
                .filter(isWeekend.negate()).count();
    }
}
