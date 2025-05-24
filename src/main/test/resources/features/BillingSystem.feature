Feature: Billing System - Invoice generation and financial reporting
  As part of billing system, I want to manage invoices and financial reports
  So that customers can receive proper invoices and administrators can track business performance

  Background:
    Given the billing system is initialized
    And there are existing customers and transactions in the system

  Scenario Outline: Generate invoice for a customer
    Given a customer with ID "<CustomerID>"
    And the customer has completed a transaction with ID "<TransactionID>"
    When the system generates an invoice for transaction "<TransactionID>"
    Then an invoice PDF document should be created
    And the invoice should contain:
      | Field          | Value           |
      | Customer Name  | <CustomerName>  |
      | Transaction ID | <TransactionID> |
      | Amount        | <Amount>         |
      | Date           | <Date>          |
      | Tax Amount     | <TaxAmount>     |
      | Total Amount   | <TotalAmount>   |

    Examples:
      | CustomerID | TransactionID | CustomerName | Amount | Date     | TaxAmount | TotalAmount |
      | customer1  | transaction1  | Bisan N      | 50.00  | 2025-4-1 | 5.00      | 55.00       |
      | customer2  | transaction2  | Aleen Y      | 40.00  | 2025-4-6 | 12.50     | 52.50       |

  Scenario: Generate daily financial report
    Given an administrator is authenticated
    When a financial report is generated for date "2025-4-1"
    Then the financial report for date "2025-4-1" should contain:
      | Field             | Value     |
      | Total Transaction | 40        |
      | Total Revenue     | 3253.50   |
      | Total Tax         | 325.05    |
      | Net  Revenue      | 2925.45   |

  Scenario: Generate monthly financial report
    Given an administrator is authenticated
    When a financial report is generated for month "February 2025"
    Then the monthly financial report for "February 2025" should contain:
      | Field                  | Value                   |
      | Total Transaction       | 450                     |
      | Total Revenue           | 98250.75                |
      | Total Tax               | 9825.08                 |
      | Net  Revenue            | 88425.67                |
      | Most Active Customer    | Bisan N (customer1)     |
      | Highest Value Transaction | transaction23 (1330.00)|

  Scenario: Generate customer date range financial report
    Given an administrator is authenticated
    When a financial report is generated from "2025-4-1" to "2025-4-20"
    Then the date range financial report should contain summary statistics
    And the report should support export in PDF format

  Scenario: Fail to generate invoice for invalid transaction
    Given customer "customer1" exists
    And transaction "invalid_transaction" does not exist
    When the system tries to generate an invoice for "invalid_transaction"
    Then an error message "Transaction not found" should appear
