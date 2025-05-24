package model;

public class Transaction {
    private String id;
    private String customerId;
    private double amount;
    private String date;
    private double taxAmount;

    public Transaction(String id, String customerId, double amount, String date, double taxAmount) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.date = date;
        this.taxAmount = taxAmount;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public double getTaxAmount() {
        return taxAmount;
    }
}
