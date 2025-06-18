package models;

public class LoanRequest {

    private int customerId;
    private LoanDetails loanDetails;

    public LoanRequest() {
        this.customerId = 0;
        this.loanDetails = new LoanDetails();
    }

    public LoanRequest(int customerId, LoanDetails loanDetails) {
        this.customerId = customerId;
        this.loanDetails = loanDetails;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LoanDetails getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(LoanDetails loanDetails) {
        this.loanDetails = loanDetails;
    }
}
