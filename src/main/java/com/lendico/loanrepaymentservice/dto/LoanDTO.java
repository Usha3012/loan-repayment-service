package com.lendico.loanrepaymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanDTO{

    @NotNull
    @Min(1)
    private Integer duration;

    @NotNull
    private BigDecimal nominalRate;

    @NotNull
    @DecimalMin("1")
    private BigDecimal loanAmount;

    @NotNull
    private OffsetDateTime startDate;
}
