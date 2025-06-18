package models;

public class LoanDetails {

    private int amount;
    private int downPayment;
    private String status;

    public LoanDetails() {
        this.amount = 0;
        this.downPayment = 0;
        this.status = "new";
    }

    public LoanDetails(int amount, int downPayment, String status) {
        this.amount = amount;
        this.downPayment = downPayment;
        this.status = status;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(int downPayment) {
        this.downPayment = downPayment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
