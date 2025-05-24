Feature: Scheduling and Task Management

  As a kitchen manager
  I want to assign tasks to chefs based on their workload and expertise
  So that I can ensure balanced workloads and efficient kitchen operations

  As a chef
  I want to receive notifications about my assigned cooking tasks
  So that I can prepare meals on time

  As a system
  I want to track task status and deadlines
  So that the kitchen manager can monitor progress and make adjustments as needed

  Scenario: Kitchen manager assigns a task to a chef based on workload and expertise
    Given I am a kitchen manager
    And there are pending tasks with required expertise
    When I assign a cooking task to chef "Ali" with expertise in "Vegetarian Dishes"
    Then the system should match the task to the chef’s profile
    And assign the task if workload is acceptable
    And notify chef "Ali" with task details

  Scenario: Chef receives task notification
    Given I am a chef with a newly assigned task
    When I log into the system
    Then I should see the list of my assigned tasks
    And each task should include dish name, ingredients, and deadline

  Scenario: System tracks task status and deadlines
    Given tasks are assigned to chefs
    When a task is marked as "in progress" or "completed"
    Then the system should update the task status accordingly
    And alert the kitchen manager if deadlines are at risk

  Scenario: System prioritizes tasks based on meal deadlines
    Given there are multiple tasks with different deadlines
    When the system arranges the task list
    Then tasks with nearest deadlines should appear at the top
    And chefs should be notified of urgent tasks

  Scenario: Chef views prioritized task list
    Given I am a chef with multiple tasks
    When I view my task dashboard
    Then I should see tasks sorted by deadline
    And each task should have a visible due time indicator
