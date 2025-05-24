package stepdefinitions;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import model.*;

import java.util.Map;

public class BillingSystemSteps {

    private BillingSystem billingSystem;
    private Customer customer;
    private Transaction transaction;
    private Invoice invoice;
    private FinancialReport financialReport;
    private String errorMessage;

    @Given("the billing system is initialized")
    public void billing_system_initialized() {
        billingSystem = new BillingSystem();
    }

    @Given("there are existing customers and transactions in the system")
    public void existing_customers_and_transactions() {
        // إضافة عملاء ومعاملات تجريبية
        billingSystem.addCustomer(new Customer("customer1", "Bisan N"));
        billingSystem.addCustomer(new Customer("customer2", "Aleen Y"));
        billingSystem.addTransaction(new Transaction("transaction1", "customer1", 50.00, "2025-4-1", 5.00));
        billingSystem.addTransaction(new Transaction("transaction2", "customer2", 40.00, "2025-4-6", 12.50));
    }

    @Given("a customer with ID {string}")
    public void given_customer_with_id(String customerId) {
        customer = billingSystem.getCustomerById(customerId);
        assertNotNull(customer, "Customer should exist");
    }

    @Given("the customer has completed a transaction with ID {string}")
    public void given_customer_transaction(String transactionId) {
        transaction = billingSystem.getTransactionById(transactionId);
        assertNotNull(transaction, "Transaction should exist");
    }

    @When("the system generates an invoice for transaction {string}")
    public void generate_invoice_for_transaction(String transactionId) {
        try {
            invoice = billingSystem.generateInvoice(transactionId);
            errorMessage = null;
        } catch (Exception e) {
            invoice = null;
            errorMessage = e.getMessage();
        }
    }

    @When("the system tries to generate an invoice for {string}")
    public void the_system_tries_to_generate_an_invoice_for(String transactionId) {
        try {
            invoice = billingSystem.generateInvoice(transactionId);
            errorMessage = null;
        } catch (Exception e) {
            invoice = null;
            errorMessage = e.getMessage();
        }
    }

    @Then("an invoice PDF document should be created")
    public void invoice_pdf_created() {
        assertNotNull(invoice);
        assertTrue(invoice.isPdfGenerated());
    }

    @Then("the invoice should contain:")
    public void invoice_should_contain(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> expected = dataTable.asMap(String.class, String.class);
        assertEquals(expected.get("Customer Name"), invoice.getCustomerName());
        assertEquals(expected.get("Transaction ID"), invoice.getTransactionId());
        assertEquals(Double.parseDouble(expected.get("Amount")), invoice.getAmount());
        assertEquals(expected.get("Date"), invoice.getDate());
        assertEquals(Double.parseDouble(expected.get("Tax Amount")), invoice.getTaxAmount());
        assertEquals(Double.parseDouble(expected.get("Total Amount")), invoice.getTotalAmount());
    }

    @Given("an administrator is authenticated")
    public void admin_authenticated() {
        boolean auth = billingSystem.authenticateAdmin("adminUser");
        assertTrue(auth);
    }

    @When("a financial report is generated for date {string}")
    public void generate_financial_report_for_date(String date) {
        financialReport = billingSystem.generateDailyReport(date);
        assertNotNull(financialReport);
    }

    @Then("the financial report for date {string} should contain:")
    public void financial_report_should_contain(String date, io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> expected = dataTable.asMap(String.class, String.class);
        assertEquals(Integer.parseInt(expected.get("Total Transaction")), financialReport.getTotalTransactions());
        assertEquals(Double.parseDouble(expected.get("Total Revenue")), financialReport.getTotalRevenue());
        assertEquals(Double.parseDouble(expected.get("Total Tax")), financialReport.getTotalTax());
        assertEquals(Double.parseDouble(expected.get("Net  Revenue")), financialReport.getNetRevenue());
    }

    @When("a financial report is generated for month {string}")
    public void generate_monthly_report(String month) {
        financialReport = billingSystem.generateMonthlyReport(month);
        assertNotNull(financialReport);
    }

    @Then("the monthly financial report for {string} should contain:")
    public void monthly_report_should_contain(String month, io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> expected = dataTable.asMap(String.class, String.class);
        assertEquals(Integer.parseInt(expected.get("Total Transaction")), financialReport.getTotalTransactions());
        assertEquals(Double.parseDouble(expected.get("Total Revenue")), financialReport.getTotalRevenue());
        assertEquals(Double.parseDouble(expected.get("Total Tax")), financialReport.getTotalTax());
        assertEquals(Double.parseDouble(expected.get("Net  Revenue")), financialReport.getNetRevenue());
        assertEquals(expected.get("Most Active Customer"), financialReport.getMostActiveCustomer());
        assertEquals(expected.get("Highest Value Transaction"), financialReport.getHighestValueTransaction());
    }

    @When("a financial report is generated from {string} to {string}")
    public void generate_date_range_report(String fromDate, String toDate) {
        financialReport = billingSystem.generateDateRangeReport(fromDate, toDate);
        assertNotNull(financialReport);
    }

    @Then("the date range financial report should contain summary statistics")
    public void date_range_report_summary() {
        assertTrue(financialReport.hasSummaryStatistics());
    }

    @Then("the report should support export in PDF format")
    public void report_support_pdf_export() {
        assertTrue(financialReport.isExportableToPdf());
    }

    @Given("customer {string} exists")
    public void customer_exists(String customerId) {
        customer = billingSystem.getCustomerById(customerId);
        assertNotNull(customer, "Customer should exist");
    }

    @Given("transaction {string} does not exist")
    public void transaction_does_not_exist(String transactionId) {
        transaction = billingSystem.getTransactionById(transactionId);
        assertNull(transaction, "Transaction should not exist");
    }

    @Then("an error message {string} should appear")
    public void error_message_should_appear(String expectedMessage) {
        assertEquals(expectedMessage, errorMessage);
    }
}
