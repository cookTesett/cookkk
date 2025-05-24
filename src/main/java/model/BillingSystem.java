package model;

import java.util.*;

public class BillingSystem {
    private Map<String, Customer> customers = new HashMap<>();
    private Map<String, Transaction> transactions = new HashMap<>();

    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public Customer getCustomerById(String id) {
        return customers.get(id);
    }

    public void addTransaction(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    public Transaction getTransactionById(String id) {
        return transactions.get(id);
    }

    public Invoice generateInvoice(String transactionId) throws Exception {
        Transaction transaction = transactions.get(transactionId);
        if (transaction == null) {
            throw new Exception("Transaction not found");
        }
        Customer customer = customers.get(transaction.getCustomerId());
        Invoice invoice = new Invoice(
                transaction.getId(),
                customer.getName(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getTaxAmount()
        );
        invoice.generatePdf();
        return invoice;
    }

    public boolean authenticateAdmin(String username) {
        // For simplicity, always authenticate successfully
        return true;
    }

    public FinancialReport generateDailyReport(String date) {
        // Dummy implementation - return a filled FinancialReport object
        FinancialReport report = new FinancialReport();
        report.setDate(date);
        report.setTotalTransactions(40);
        report.setTotalRevenue(3253.50);
        report.setTotalTax(325.05);
        report.setNetRevenue(2925.45);
        return report;
    }

    public FinancialReport generateMonthlyReport(String month) {
        FinancialReport report = new FinancialReport();
        report.setMonth(month);
        report.setTotalTransactions(450);
        report.setTotalRevenue(98250.75);
        report.setTotalTax(9825.08);
        report.setNetRevenue(88425.67);
        report.setMostActiveCustomer("Bisan N (customer1)");
        report.setHighestValueTransaction("transaction23 (1330.00)");
        return report;
    }

    public FinancialReport generateDateRangeReport(String fromDate, String toDate) {
        FinancialReport report = new FinancialReport();
        report.setFromDate(fromDate);
        report.setToDate(toDate);
        report.setSummaryStatistics(true);
        report.setExportableToPdf(true);
        return report;
    }
}
