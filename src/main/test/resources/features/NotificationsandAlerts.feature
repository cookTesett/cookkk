Feature: Notifications and Alerts
  As a system user
  I want to be notified promptly
  So that I can act on important updates

  Scenario: Delivery reminder sent to customer
    Given a customer account exists
    And a meal delivery is scheduled for the next day
    When delivery reminders are triggered
    Then the customer should get a delivery notification
    And the notification details should include:
      |     Field        |           Detail                |
      | Delivery Date    | Tomorrow's date                 |
      | Time Window      | My selected delivery time range |
      | Meal Items       | List of ordered meals           |

  Scenario: Chef receives preparation schedule
    Given a registered chef has meals assigned today
    When cooking notifications are triggered
    Then the chef should be notified with a prep schedule
    And the schedule should include:
      |        Field        |           Detail                |
      | Meal Items          | List of meals to prepare        |
      | Preparation Time    | Required start time             |
      | Deadline            | When meals need to be ready     |

  Scenario: Inventory warning sent to kitchen manager
    Given the kitchen manager monitors ingredient levels
    And some ingredients are below the minimum threshold
    When a stock check is performed
    Then a low stock alert should be issued
    And the alert message should contain:
      |     Field         |           Detail                |
      | Ingredient Name   | Name of low stock item          |
      | Current Quantity  | Remaining amount                |
      | Reorder Level     | Minimum required quantity       |
      | Supplier Info     | Contact for reordering          |

  Scenario: Critical stock warning sent to kitchen manager
    Given the manager is responsible for inventory
    And some items are critically low
    When a stock review is initiated
    Then a critical stock alert should be issued
    And the alert should be flagged as high priority
    And it should recommend immediate restocking
