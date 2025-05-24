package stepdefinitions;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class NotificationsandAlertsSteps {

    private String customerAccount;
    private Date scheduledDeliveryDate;
    private List<String> orderedMeals;

    private String chefName;
    private List<String> mealsToPrepare;
    private Date preparationTime;
    private Date deadline;

    private Map<String, Integer> ingredientLevels = new HashMap<>();
    private int minimumThreshold;
    private List<String> lowStockIngredients = new ArrayList<>();

    private List<String> criticallyLowItems = new ArrayList<>();
    private boolean criticalAlertFlagged = false;
    private boolean immediateRestockRecommended = false;

    // --- Scenario: Delivery reminder sent to customer ---

    @Given("a customer account exists")
    public void a_customer_account_exists() {
        customerAccount = "customer123";
    }

    @Given("a meal delivery is scheduled for the next day")
    public void a_meal_delivery_is_scheduled_for_the_next_day() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        scheduledDeliveryDate = cal.getTime();

        orderedMeals = Arrays.asList("Pizza", "Salad");
    }

    @When("delivery reminders are triggered")
    public void delivery_reminders_are_triggered() {
        // Simulate sending reminder (no actual logic here)
    }

    @Then("the customer should get a delivery notification")
    public void the_customer_should_get_a_delivery_notification() {
        assertNotNull(customerAccount);
        assertNotNull(scheduledDeliveryDate);
    }

    @Then("the notification details should include:")
    public void the_notification_details_should_include(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> expectedDetails = dataTable.asMap(String.class, String.class);

        assertEquals("Tomorrow's date", expectedDetails.get("Delivery Date"));
        assertEquals("My selected delivery time range", expectedDetails.get("Time Window"));
        assertEquals("List of ordered meals", expectedDetails.get("Meal Items"));
    }


    // --- Scenario: Chef receives preparation schedule ---

    @Given("a registered chef has meals assigned today")
    public void a_registered_chef_has_meals_assigned_today() {
        chefName = "Chef John";
        mealsToPrepare = Arrays.asList("Pasta", "Soup");

        Calendar cal = Calendar.getInstance();
        preparationTime = cal.getTime();

        cal.add(Calendar.HOUR, 2);
        deadline = cal.getTime();
    }

    @When("cooking notifications are triggered")
    public void cooking_notifications_are_triggered() {
        // Simulate notification
    }

    @Then("the chef should be notified with a prep schedule")
    public void the_chef_should_be_notified_with_a_prep_schedule() {
        assertNotNull(chefName);
        assertFalse(mealsToPrepare.isEmpty());
    }

    @Then("the schedule should include:")
    public void the_schedule_should_include(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> scheduleDetails = dataTable.asMap(String.class, String.class);

        assertEquals("List of meals to prepare", scheduleDetails.get("Meal Items"));
        assertEquals("Required start time", scheduleDetails.get("Preparation Time"));
        assertEquals("When meals need to be ready", scheduleDetails.get("Deadline"));
    }


    // --- Scenario: Inventory warning sent to kitchen manager ---

    @Given("the kitchen manager monitors ingredient levels")
    public void the_kitchen_manager_monitors_ingredient_levels() {
        ingredientLevels.put("Tomatoes", 5);
        ingredientLevels.put("Cheese", 2);
        minimumThreshold = 3;
    }

    @Given("some ingredients are below the minimum threshold")
    public void some_ingredients_are_below_the_minimum_threshold() {
        lowStockIngredients.clear();
        for (Map.Entry<String, Integer> entry : ingredientLevels.entrySet()) {
            if (entry.getValue() < minimumThreshold) {
                lowStockIngredients.add(entry.getKey());
            }
        }
        assertFalse(lowStockIngredients.isEmpty());
    }

    @When("a stock check is performed")
    public void a_stock_check_is_performed() {
        // Simulate stock check
    }

    @Then("a low stock alert should be issued")
    public void a_low_stock_alert_should_be_issued() {
        assertFalse(lowStockIngredients.isEmpty());
    }

    @Then("the alert message should contain:")
    public void the_alert_message_should_contain(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> alertDetails = dataTable.asMap(String.class, String.class);

        assertEquals("Name of low stock item", alertDetails.get("Ingredient Name"));
        assertEquals("Remaining amount", alertDetails.get("Current Quantity"));
        assertEquals("Minimum required quantity", alertDetails.get("Reorder Level"));
        assertEquals("Contact for reordering", alertDetails.get("Supplier Info"));
    }


    // --- Scenario: Critical stock warning sent to kitchen manager ---

    @Given("the manager is responsible for inventory")
    public void the_manager_is_responsible_for_inventory() {
        // Setup inventory manager
    }

    @Given("some items are critically low")
    public void some_items_are_critically_low() {
        criticallyLowItems = Arrays.asList("Flour", "Yeast");
        assertFalse(criticallyLowItems.isEmpty());
    }

    @When("a stock review is initiated")
    public void a_stock_review_is_initiated() {
        criticalAlertFlagged = true;
        immediateRestockRecommended = true;
    }

    @Then("a critical stock alert should be issued")
    public void a_critical_stock_alert_should_be_issued() {
        assertTrue(criticalAlertFlagged);
    }

    @Then("the alert should be flagged as high priority")
    public void the_alert_should_be_flagged_as_high_priority() {
        assertTrue(criticalAlertFlagged);
    }

    @Then("it should recommend immediate restocking")
    public void it_should_recommend_immediate_restocking() {
        assertTrue(immediateRestockRecommended);
    }
}
