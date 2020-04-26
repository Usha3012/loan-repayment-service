package com.lendico.loanrepaymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepaymentPlanDTO{

    private BigDecimal borrowerPaymentAmount;

    private OffsetDateTime date;

    private BigDecimal initialOutstandingPrincipal;

    private BigDecimal interest;

    private BigDecimal principal;

    private BigDecimal remainingOutstandingPrincipal;
}
