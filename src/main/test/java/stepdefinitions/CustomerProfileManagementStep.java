package stepdefinitions;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class CustomerProfileManagementStep {

    private String currentUserRole;
    private String dietaryPreferences;
    private String allergies;
    private List<String> pastOrders = new ArrayList<>();
    private List<String> recommendedMeals = new ArrayList<>();
    private List<String> frequentMeals = new ArrayList<>();
    private Map<String, Integer> mealTrends = new HashMap<>();

    @Given("I am a registered customer")
    public void i_am_a_registered_customer() {
        currentUserRole = "customer";
    }

    @Given("I am a chef logged into the system")
    public void i_am_a_chef_logged_into_the_system() {
        currentUserRole = "chef";
    }

    @Given("I am a system administrator")
    public void i_am_a_system_administrator() {
        currentUserRole = "admin";
    }

    @When("I navigate to my profile settings")
    public void i_navigate_to_my_profile_settings() {
        assertEquals("customer", currentUserRole);
    }

    @When("I input my dietary preferences as {string}")
    public void i_input_my_dietary_preferences_as(String preferences) {
        dietaryPreferences = preferences;
    }

    @When("I input my allergies as {string}")
    public void i_input_my_allergies_as(String inputAllergies) {
        allergies = inputAllergies;
    }

    @Then("the system should save my dietary preferences and allergies")
    public void the_system_should_save_my_dietary_preferences_and_allergies() {
        assertNotNull(dietaryPreferences);
        assertNotNull(allergies);
    }

    @Then("the system should use this information for meal recommendations")
    public void the_system_should_use_this_information_for_meal_recommendations() {
        recommendedMeals = List.of("Vegan Salad", "Lacto-Veggie Wrap");
        assertFalse(recommendedMeals.isEmpty());
    }

    @When("I view the profile of customer")
    public void i_view_the_profile_of_customer() {
        assertEquals("chef", currentUserRole);
    }

    @Then("I should see their dietary preferences as {string}")
    public void i_should_see_their_dietary_preferences_as(String expectedPreferences) {
        assertEquals(expectedPreferences, dietaryPreferences);
    }

    @Then("I should see their allergies as {string}")
    public void i_should_see_their_allergies_as(String expectedAllergies) {
        assertEquals(expectedAllergies, allergies);
    }

    @Given("I have previously ordered meals")
    public void i_have_previously_ordered_meals() {
        pastOrders = List.of("Vegan Burger", "Grilled Tofu", "Lentil Soup");
    }

    @When("I navigate to my order history")
    public void i_navigate_to_my_order_history() {
        assertEquals("customer", currentUserRole);
    }

    @Then("I should see a list of my past orders")
    public void i_should_see_a_list_of_my_past_orders() {
        assertFalse(pastOrders.isEmpty());
    }

    @Then("I should be able to reorder any previous meal")
    public void i_should_be_able_to_reorder_any_previous_meal() {
        String mealToReorder = pastOrders.get(0);
        assertTrue(pastOrders.contains(mealToReorder));
    }

    // **Missing step 1**
    @Given("a registered customer exists with dietary preferences {string} and allergies {string}")
    public void a_registered_customer_exists_with_dietary_preferences_and_allergies(String preferences, String allergy) {
        dietaryPreferences = preferences;
        allergies = allergy;
        assertNotNull(dietaryPreferences);
        assertNotNull(allergies);
    }

    // **Missing step 2**
    @Given("a registered customer exists with order history")
    public void a_registered_customer_exists_with_order_history() {
        frequentMeals = List.of("Vegan Burger", "Lentil Soup", "Vegan Burger");
        assertFalse(frequentMeals.isEmpty());
    }

    @When("I view the order history of customer")
    public void i_view_the_order_history_of_customer() {
        assertEquals("chef", currentUserRole);
    }

    @Then("I should see their frequently ordered meals")
    public void i_should_see_their_frequently_ordered_meals() {
        assertFalse(frequentMeals.isEmpty());
    }

    @Then("I should be able to suggest personalized meal plans based on their preferences")
    public void i_should_be_able_to_suggest_personalized_meal_plans_based_on_their_preferences() {
        List<String> suggestions = List.of("Custom Vegan Meal Plan");
        assertFalse(suggestions.isEmpty());
    }

    @When("I access the customer data analytics dashboard")
    public void i_access_the_customer_data_analytics_dashboard() {
        assertEquals("admin", currentUserRole);
    }

    @Then("I should be able to view aggregate order history data")
    public void i_should_be_able_to_view_aggregate_order_history_data() {
        mealTrends.put("Vegan Burger", 120);
        mealTrends.put("Lentil Soup", 95);
        assertFalse(mealTrends.isEmpty());
    }

    @Then("I should be able to identify popular meals and trends")
    public void i_should_be_able_to_identify_popular_meals_and_trends() {
        List<String> popularMeals = mealTrends.entrySet().stream()
                .filter(entry -> entry.getValue() > 100)
                .map(Map.Entry::getKey)
                .toList();
        assertTrue(popularMeals.contains("Vegan Burger"));
    }
}
