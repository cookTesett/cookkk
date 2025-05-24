package model;

public class Invoice {
    private String transactionId;
    private String customerName;
    private double amount;
    private String date;
    private double taxAmount;
    private double totalAmount;
    private boolean pdfGenerated = false;

    public Invoice(String transactionId, String customerName, double amount, String date, double taxAmount){
        this.transactionId = transactionId;
        this.customerName = customerName;
        this.amount = amount;
        this.date = date;
        this.taxAmount = taxAmount;
        this.totalAmount = amount + taxAmount;
    }

    public void generatePdf(){
        // Dummy PDF generation simulation
        pdfGenerated = true;
    }

    public boolean isPdfGenerated(){
        return pdfGenerated;
    }

    // getters
    public String getTransactionId() { return transactionId; }
    public String getCustomerName() { return customerName; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
    public double getTaxAmount() { return taxAmount; }
    public double getTotalAmount() { return totalAmount; }
}
