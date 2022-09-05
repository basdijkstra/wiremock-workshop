package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class LoanRequest {

    private int customerId;
    private LoanDetails loanDetails;
}
