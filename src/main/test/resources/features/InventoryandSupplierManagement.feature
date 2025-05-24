Feature: Inventory and Supplier Management

 Scenario: Kitchen manager views real-time stock levels
  Given I am a kitchen manager logged into the system
  When I navigate to the inventory dashboard
  Then I should see a list of all ingredients with their current stock levels
  And I should be notified if any item is below the threshold

 Scenario: System suggests restocking for low-stock items
  Given some ingredients have fallen below the minimum stock threshold
  When the system scans the inventory
  Then it should suggest restocking those ingredients
  And notify the kitchen manager with a list of items to reorder

 Scenario: Kitchen manager fetches real-time ingredient prices from suppliers
  Given I am a kitchen manager logged into the system
  When I request current prices for low-stock ingredients
  Then the system should fetch and display real-time prices from integrated suppliers

 Scenario: System auto-generates purchase order for critically low items
  Given some items are critically low in stock
  When the system performs a stock check
  Then it should automatically generate a purchase order
  And send it to the appropriate supplier for fulfillment
