package com.lendico.loanrepaymentservice.calculator;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PrincipalCalculator{

    public BigDecimal calculatePrincipal(BigDecimal annuity, BigDecimal interest){
        BigDecimal monthlyPrincipal = annuity.subtract(interest);
        monthlyPrincipal = monthlyPrincipal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return monthlyPrincipal;
    }

    public BigDecimal calculateRemainingOutStandingPrincipal(BigDecimal principal, BigDecimal outStandingPrincipal){
        BigDecimal remainingOutstandingPrincipal;
        remainingOutstandingPrincipal = outStandingPrincipal.subtract(principal);

        if (remainingOutstandingPrincipal.compareTo(BigDecimal.ZERO) < 0) {
            remainingOutstandingPrincipal = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        }

        return remainingOutstandingPrincipal;
    }
}
