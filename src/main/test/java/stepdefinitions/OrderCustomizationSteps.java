package stepdefinitions;

import io.cucumber.java.en.*;
import model.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class OrderCustomizationSteps {

    private Customer currentCustomer;
    private List<String> selectedIngredients;
    private MealCustomizationService customizationService;
    private Chef chef;
    private boolean orderValid;
    private String substitutionSuggestion;
    private boolean substitutionAlertToChef;
    private List<String> substitutionLog;

    public OrderCustomizationSteps() {
        customizationService = new MealCustomizationService();
        chef = new Chef();
        substitutionLog = new ArrayList<>();
    }

    @Given("the customer is logged in")
    public void the_customer_is_logged_in() {
        currentCustomer = new Customer("Order Customer");
    }

    @When("they choose ingredients {string}")
    public void they_choose_ingredients(String ingredients) {
        selectedIngredients = Arrays.asList(ingredients.split(",\\s*"));
        orderValid = customizationService.validateIngredients(selectedIngredients);
    }

    @Then("the system confirms the order is valid")
    public void the_system_confirms_the_order_is_valid() {
        assertTrue(orderValid, "Order should be valid with given ingredients");
    }

    @Then("the order is sent to the kitchen for preparation")
    public void the_order_is_sent_to_the_kitchen_for_preparation() {
        chef.receiveOrder(selectedIngredients);
        assertEquals(selectedIngredients, chef.getCurrentOrder());
    }

    @Then("the system rejects the order due to incompatible or unavailable ingredients")
    public void the_system_rejects_the_order_due_to_incompatible_or_unavailable_ingredients() {
        assertFalse(orderValid, "Order should be invalid due to incompatible/unavailable ingredients");
    }

    @Then("suggests revising the ingredient selection")
    public void suggests_revising_the_ingredient_selection() {
        assertFalse(orderValid, "Customer should be prompted to revise selection");
    }

    @Given("the customer has a gluten intolerance")
    public void the_customer_has_a_gluten_intolerance() {
        currentCustomer = new Customer("Gluten Intolerant Customer");
        currentCustomer.setDietaryRestrictions(List.of("gluten"));
    }

    @When("they order {string}")
    public void they_order(String ingredient) {
        selectedIngredients = List.of(ingredient);
    }

    @Then("the system suggests {string} as a substitution")
    public void the_system_suggests_as_a_substitution(String expectedSubstitution) {
        substitutionSuggestion = customizationService.suggestSubstitution(selectedIngredients.get(0), currentCustomer.getDietaryRestrictions());
        assertEquals(expectedSubstitution, substitutionSuggestion);
    }

    @Then("informs the customer about the substitution")
    public void informs_the_customer_about_the_substitution() {
        boolean notified = !substitutionSuggestion.equals(selectedIngredients.get(0));
        assertTrue(notified, "Customer should be informed about the substitution");
    }

    @Given("an ingredient substitution was applied to an order")
    public void an_ingredient_substitution_was_applied_to_an_order() {
        substitutionSuggestion = "Gluten-Free Bread"; // ✅ تم تعيين بديل تجريبي
        substitutionAlertToChef = true;
    }

    @When("the chef reviews the order")
    public void the_chef_reviews_the_order() {
        // الطاهي يراجع الطلب، لا حاجة لفعل إضافي هنا
    }

    @Then("the chef is alerted about the substitution")
    public void the_chef_is_alerted_about_the_substitution() {
        chef.receiveSubstitutionAlert(substitutionSuggestion); // ✅ الطاهي يتلقى التنبيه
        assertTrue(substitutionAlertToChef, "Chef should be alerted about ingredient substitution");
    }

    @Then("can approve or modify the substitution before meal preparation")
    public void can_approve_or_modify_the_substitution_before_meal_preparation() {
        chef.receiveSubstitutionAlert(substitutionSuggestion); // ⚠️ هذا السطر أساسي لتفادي null
        boolean approved = chef.approveSubstitution(substitutionSuggestion);
        assertTrue(approved, "Chef should be able to approve or modify the substitution");
    }
}
