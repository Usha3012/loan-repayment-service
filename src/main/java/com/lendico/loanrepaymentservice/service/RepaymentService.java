package com.lendico.loanrepaymentservice.service;

import com.lendico.loanrepaymentservice.calculator.AnnuityCalculator;
import com.lendico.loanrepaymentservice.calculator.InterestCalculator;
import com.lendico.loanrepaymentservice.calculator.PrincipalCalculator;
import com.lendico.loanrepaymentservice.dto.LoanDTO;
import com.lendico.loanrepaymentservice.dto.RepaymentPlanDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RepaymentService{
    private final AnnuityCalculator annuityCalculator;
    private final InterestCalculator interestCalculator;
    private final PrincipalCalculator principalCalculator;

    /**
     * Generate Repayment Schedule
     *
     * @param loan
     * @return List<RepaymentPlanDTO>
     */
    public List<RepaymentPlanDTO> generateRepaymentPlans(LoanDTO loan){
        List<RepaymentPlanDTO> repayments = new ArrayList<>();
        BigDecimal nominalRate = loan.getNominalRate();
        Integer duration = loan.getDuration();
        OffsetDateTime startDate = loan.getStartDate();
        BigDecimal annuity = annuityCalculator
                .calculateAnnuity(loan.getLoanAmount(), nominalRate.doubleValue(), duration);
        BigDecimal outstandingPrincipal = loan.getLoanAmount();
        for (int i = 0; i < duration; i++) {
            RepaymentPlanDTO planDTO = generateRepaymentPlan(outstandingPrincipal, nominalRate, annuity, startDate);
            repayments.add(planDTO);
            //last month and calculated principal is less than outstanding
            if (i == duration - 1 && planDTO.getPrincipal().compareTo(planDTO.getInitialOutstandingPrincipal()) < 0) {
                planDTO.setPrincipal(planDTO.getInitialOutstandingPrincipal());
                planDTO.setRemainingOutstandingPrincipal(BigDecimal.ZERO);
                planDTO.setBorrowerPaymentAmount(planDTO.getPrincipal().add(planDTO.getInterest()));
            }
            outstandingPrincipal = planDTO.getRemainingOutstandingPrincipal();
            startDate = startDate.plusMonths(1);
        }
        return repayments;
    }

    private RepaymentPlanDTO generateRepaymentPlan(BigDecimal outstandingPrincipal, BigDecimal nominalRate,
                                                   BigDecimal annuity, OffsetDateTime startDate){

        BigDecimal interest = interestCalculator
                .calculateInterest(nominalRate.doubleValue(), outstandingPrincipal);
        BigDecimal principal = principalCalculator.calculatePrincipal(annuity, interest);
        // if principal payable is greater than outstanding principal use outstanding principal
        principal = principal.compareTo(outstandingPrincipal) > 0 ? outstandingPrincipal : principal;
        BigDecimal remainingOutStandingPrincipal = principalCalculator.calculateRemainingOutStandingPrincipal(principal
                , outstandingPrincipal);

        return RepaymentPlanDTO.builder()
                .borrowerPaymentAmount(principal.add(interest))
                .date(startDate)
                .principal(principal)
                .initialOutstandingPrincipal(outstandingPrincipal)
                .interest(interest)
                .remainingOutstandingPrincipal(remainingOutStandingPrincipal)
                .build();
    }
}
