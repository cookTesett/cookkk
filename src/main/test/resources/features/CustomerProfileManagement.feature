Feature: Customer Profile Management
  As a meal service platform
  I want to manage customer profiles
  So that I can provide personalized meal experiences

  Scenario: Customer inputs dietary preferences and allergies
    Given I am a registered customer
    When I navigate to my profile settings
    And I input my dietary preferences as "vegan, Lacto Vegetarian , Ovo Vegetarian , Pollotarian , Flexitarian, no dairy"
    And I input my allergies as "Gluten intolerance or senolerance , Cow’s milk ,Tree nuts(as Brazil nuts, almonds)"
    Then the system should save my dietary preferences and allergies
    And the system should use this information for meal recommendations

  Scenario: Chef views customer dietary restrictions
    Given I am a chef logged into the system
    And a registered customer exists with dietary preferences "vegan, Lacto Vegetarian , Ovo Vegetarian , Pollotarian , Flexitarian, no dairy" and allergies "Gluten intolerance or senolerance , Cow’s milk ,Tree nuts(as Brazil nuts, almonds)"
    When I view the profile of customer
    Then I should see their dietary preferences as "vegan, Lacto Vegetarian , Ovo Vegetarian , Pollotarian , Flexitarian, no dairy"
    And I should see their allergies as "Gluten intolerance or senolerance , Cow’s milk ,Tree nuts(as Brazil nuts, almonds)"

  Scenario: Customer views past meal orders
    Given I am a registered customer
    And I have previously ordered meals
    When I navigate to my order history
    Then I should see a list of my past orders
    And I should be able to reorder any previous meal

  Scenario: Chef accesses customer order history for meal planning
    Given I am a chef logged into the system
    And a registered customer exists with order history
    When I view the order history of customer
    Then I should see their frequently ordered meals
    And I should be able to suggest personalized meal plans based on their preferences

  Scenario: Administrator analyzes customer trends
    Given I am a system administrator
    When I access the customer data analytics dashboard
    Then I should be able to view aggregate order history data
    And I should be able to identify popular meals and trends
