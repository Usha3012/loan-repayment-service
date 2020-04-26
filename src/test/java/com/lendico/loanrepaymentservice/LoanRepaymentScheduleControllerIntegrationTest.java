package com.lendico.loanrepaymentservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendico.loanrepaymentservice.dto.LoanDTO;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = LoanRepaymentServiceApplication.class)
public class LoanRepaymentScheduleControllerIntegrationTest{
    @LocalServerPort
    private int port;
    @Autowired
    ObjectMapper objectMapper;
    @BeforeEach
    public void setUp(){
        RestAssured.port = port;
    }

    @Test
    public void testValidPaymentPlan() throws JsonProcessingException{
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setDuration(24);
        loanDTO.setLoanAmount(new BigDecimal("5000"));
        loanDTO.setNominalRate(new BigDecimal("5"));
        loanDTO.setStartDate(OffsetDateTime.parse("2018-01-01T00:00:01Z"));
        given()
                .contentType("application/json")
                .body(objectMapper.writeValueAsString(loanDTO))
                .log().all()
        .when()
                .post("/generate-plan")
                .prettyPeek()
        .then()
                .statusCode(200)
                .body("$",Matchers.hasSize(24));


    }

    @Test
    public void testInValidPaymentPlan() throws JsonProcessingException{
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setDuration(24);
        loanDTO.setLoanAmount(new BigDecimal("5000"));
        loanDTO.setNominalRate(new BigDecimal("-5"));
        loanDTO.setStartDate(OffsetDateTime.parse("2018-01-01T00:00:01Z"));
        given()
                .contentType("application/json")
                .body(objectMapper.writeValueAsString(loanDTO))
                .log().all()
                .when()
                .post("/generate-plan")
                .prettyPeek()
                .then()
                .statusCode(400);
    }

    @Test
    public void testInValidAmountPaymentPlan() throws JsonProcessingException{
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setDuration(24);
        loanDTO.setLoanAmount(new BigDecimal("-5000"));
        loanDTO.setNominalRate(new BigDecimal("-5"));
        loanDTO.setStartDate(OffsetDateTime.parse("2018-01-01T00:00:01Z"));
        given()
                .contentType("application/json")
                .body(objectMapper.writeValueAsString(loanDTO))
                .log().all()
                .when()
                .post("/generate-plan")
                .prettyPeek()
                .then()
                .statusCode(400);
    }


}
