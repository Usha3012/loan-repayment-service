package com.lendico.loanrepaymentservice.calculator;

import com.lendico.loanrepaymentservice.exception.InvalidParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class AnnuityCalculator{
    /**
     * https://financeformulas.net/Annuity_Payment_Formula.html
     * annuity = round(Rate * loanAmount) / 1-(1+Rate)^-durationInMonth
     *
     * @param Rate rate per period
     * @param loanAmount PresentValue
     * @param durationInMonth number of periods
     * @Return annuity  :Payment
     */
    private static final int MONTH_IN_YEAR = 12;

    public BigDecimal calculateAnnuity(BigDecimal loanAmount, double nominalRate, int durationInMonth){
        if (loanAmount.equals(BigDecimal.ZERO) || nominalRate <= 0 || durationInMonth <= 0) {
            log.error("loanAmount {},nominalRate {},duration {} invalid", loanAmount, nominalRate, durationInMonth);
            throw new InvalidParameterException("nominalRate or duration cannot be 0 or negative");
        }
        double nominalRateByMonth = (nominalRate / 100.0) / MONTH_IN_YEAR;

        double annuity = (loanAmount.doubleValue() * nominalRateByMonth) /
                (1 - Math.pow(1 + nominalRateByMonth, -durationInMonth));

        BigDecimal result = new BigDecimal(annuity);
        result = result.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return result;
    }

}
