package com.lendico.loanrepaymentservice.calculator;

import com.lendico.loanrepaymentservice.exception.InvalidParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class InterestCalculator{
    private static final Integer DAYS_IN_MONTH = 30;
    private static final Integer DAYS_IN_YEAR = 360;

    /**
     * Interest = round(Rate * Days in Month *  Outstanding Principal) / Days in Year
     *
     * @param nominalRate                nominal rate
     * @param outStandingPrincipalAmount outstanding principal
     * @return interest  :Interest
     */
    public BigDecimal calculateInterest(double nominalRate, BigDecimal outStandingPrincipalAmount){

        if (nominalRate <= 0 || outStandingPrincipalAmount.equals(BigDecimal.ZERO)) {
            log.error("nominalRate {},outStandingPrincipal {} not valid", nominalRate, outStandingPrincipalAmount);
            throw new InvalidParameterException("nominalRate cannot be 0 or negative and initialOutstandingPrincipal can not be 0");
        }
        double interest = nominalRate * DAYS_IN_MONTH * outStandingPrincipalAmount.doubleValue() / DAYS_IN_YEAR;
        BigDecimal monthlyInterest = BigDecimal.valueOf(interest);
        monthlyInterest =
                monthlyInterest.divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return monthlyInterest;
    }

}
