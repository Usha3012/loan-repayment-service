package com.lendico.loanrepaymentservice.controller;

import com.lendico.loanrepaymentservice.dto.LoanDTO;
import com.lendico.loanrepaymentservice.dto.RepaymentPlanDTO;
import com.lendico.loanrepaymentservice.service.RepaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoanRepaymentScheduleController{
    private final RepaymentService repaymentService;

    @RequestMapping(value = "/generate-plan", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RepaymentPlanDTO>> generateSchedule(@RequestBody @Valid LoanDTO loanDTO){
        return ResponseEntity.ok(repaymentService.generateRepaymentPlans(loanDTO));
    }

}
