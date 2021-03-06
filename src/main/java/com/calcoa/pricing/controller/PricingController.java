package com.calcoa.pricing.controller;

import com.calcoa.pricing.dto.PricingResponseDTO;
import com.calcoa.pricing.operations.PricingOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pricing")
@Tag(name = "Pricing Rest Interface", description = "Obtain billing values for services")
public class PricingController {

    private final PricingOperation pricingOperation;

    public PricingController(PricingOperation pricingOperation) {
        this.pricingOperation = pricingOperation;
    }

    @Operation(summary = "Services cost",
            description = "Billing value by customer, between startDate and endDate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operation succeeded"),
            @ApiResponse(responseCode = "400", description = "input parameters not allowed")}
    )
    @PostMapping(value = "/{customerId}", produces = "application/json")
    public ResponseEntity<PricingResponseDTO> getPricingPerCustomerId(@PathVariable(name = "customerId") String customerId,
                                                                      @RequestParam(name = "startDate") String startDate,
                                                                      @RequestParam(name = "endDate") String endDate) {

        return ResponseEntity
                .ok(pricingOperation.pricingPerCustomer(customerId, startDate, endDate));
    }

}
