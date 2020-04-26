## Loan Repayment Service

## How to Run

- Make Java8 available
- Run `mvn spring-boot:run`

## API
```json
POST /generate-plan 

Request BODY

{
"loanAmount": "5000",
"nominalRate": "5",
"duration": 24,
"startDate": "2018-01-01T00:00:01Z"
}

Response

[
    {
        "borrowerPaymentAmount": 219.36,
        "date": "2018-01-01T00:00:01Z",
        "initialOutstandingPrincipal": 5000,
        "interest": 20.83,
        "principal": 198.53,
        "remainingOutstandingPrincipal": 4801.47
    },
    {
        "borrowerPaymentAmount": 219.36,
        "date": "2018-02-01T00:00:01Z",
        "initialOutstandingPrincipal": 4801.47,
        "interest": 20.01,
        "principal": 199.35,
        "remainingOutstandingPrincipal": 4602.12
    },
   ....
   {
           "borrowerPaymentAmount": 219.28,
           "date": "2019-12-01T00:00:01Z",
           "initialOutstandingPrincipal": 218.37,
           "interest": 0.91,
           "principal": 218.37,
           "remainingOutstandingPrincipal": 0.00
    }
]

```
