package com.calcoa.pricing.controller;

import com.calcoa.pricing.operations.PricingOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;

@RestController
public class PricingController {

    @Autowired
    private PricingOperation pricingOperation;

    @GetMapping("/pricing")
    public ResponseEntity<String> getPricingPerCustomerId(@RequestParam(name = "customerId") String customerId,
                                                          @RequestParam(name = "startDate") String startDate,
                                                          @RequestParam(name = "endDate") String endDate) {

        return ResponseEntity
                .ok(HttpURLConnection.HTTP_OK
                        + ": " + pricingOperation.pricingPerCustomer(customerId, startDate, endDate));
    }

}
