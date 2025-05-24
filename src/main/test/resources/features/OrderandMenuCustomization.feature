Feature: 3. Order Customization and Substitution Management

  3.1 Customers create and customize meal orders
  As a customer,
  I want to create and customize meal orders by selecting from available ingredients
  So that my order reflects my preferences and dietary needs

  3.2 Manage ingredient substitutions and approvals
  As a system,
  I want to handle ingredient substitutions for unavailable or restricted items
  So that customers get appropriate alternatives and chefs are informed for approval

  Scenario: Customer places a custom meal order with allowed ingredients
    Given the customer is logged in
    When they choose ingredients "Brown Rice, Kale, Grilled Chicken"
    Then the system confirms the order is valid
    And the order is sent to the kitchen for preparation

  Scenario: System blocks order with incompatible or unavailable ingredients
    Given the customer is logged in
    When they choose ingredients "Shellfish, Blue Cheese"
    Then the system rejects the order due to incompatible or unavailable ingredients
    And suggests revising the ingredient selection

  Scenario: System offers substitutions for restricted ingredients
    Given the customer has a gluten intolerance
    When they order "Wheat Bread"
    Then the system suggests "Gluten-Free Bread" as a substitution
    And informs the customer about the substitution

  Scenario: Chef receives alerts for ingredient substitutions
    Given an ingredient substitution was applied to an order
    When the chef reviews the order
    Then the chef is alerted about the substitution
    And can approve or modify the substitution before meal preparation
