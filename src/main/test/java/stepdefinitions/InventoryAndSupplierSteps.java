package stepdefinitions;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

import model.*;
import java.util.*;

public class InventoryAndSupplierSteps {

    private InventorySystem inventorySystem;
    private KitchenManager kitchenManager;
    private List<Ingredient> lowStockIngredients;
    private List<PurchaseOrder> generatedPurchaseOrders;
    private Map<String, Double> supplierPrices;
    private boolean restockingSuggested;
    private boolean purchaseOrderGenerated;

    // نهيئ النظام والمكونات هنا مع تسجيل الدخول
    @Given("I am a kitchen manager logged into the system")
    public void kitchen_manager_logged_in() {
        kitchenManager = new KitchenManager("manager1", new NotificationService()); // اضفت خدمة اشعارات
        inventorySystem = new InventorySystem();

        // أضف مكونات حتى تظهر القائمة (يمكن تعديل الكميات حسب الحاجة)
        inventorySystem.addIngredient(new Ingredient("Tomato", 20, 10));
        inventorySystem.addIngredient(new Ingredient("Onion", 15, 10));
        inventorySystem.addIngredient(new Ingredient("Lettuce", 5, 5));
        inventorySystem.addIngredient(new Ingredient("Cheese", 8, 10));
        inventorySystem.addIngredient(new Ingredient("Milk", 12, 10));

        inventorySystem.login(kitchenManager);
        assertTrue(inventorySystem.isUserLoggedIn(kitchenManager));
    }

    @When("I navigate to the inventory dashboard")
    public void navigate_to_inventory_dashboard() {
        inventorySystem.loadInventoryDashboard();
    }

    @Then("I should see a list of all ingredients with their current stock levels")
    public void see_all_ingredients_with_stock_levels() {
        List<Ingredient> allIngredients = inventorySystem.getAllIngredients();
        assertNotNull(allIngredients, "Ingredient list should not be null");
        assertFalse(allIngredients.isEmpty(), "Ingredient list should not be empty");
        for (Ingredient ingredient : allIngredients) {
            assertNotNull(ingredient.getName());
            assertTrue(ingredient.getStockLevel() >= 0);
        }
    }

    @Then("I should be notified if any item is below the threshold")
    public void notify_if_items_below_threshold() {
        List<Ingredient> lowStock = inventorySystem.getIngredientsBelowThreshold();
        if (!lowStock.isEmpty()) {
            inventorySystem.notifyKitchenManager(kitchenManager, lowStock);
            assertTrue(inventorySystem.hasNotificationFor(kitchenManager));
        }
    }

    @Given("some ingredients have fallen below the minimum stock threshold")
    public void ingredients_below_threshold() {
        // هنا ننشئ النظام من جديد مع مكونات أقل من العتبة
        inventorySystem = new InventorySystem();
        inventorySystem.addIngredient(new Ingredient("Tomato", 3, 10));  // stock 3, threshold 10 (منخفض)
        inventorySystem.addIngredient(new Ingredient("Onion", 5, 10));   // منخفض أيضاً
        inventorySystem.addIngredient(new Ingredient("Lettuce", 1, 5));  // منخفض
        kitchenManager = new KitchenManager("manager1", new NotificationService());
        inventorySystem.login(kitchenManager);

        lowStockIngredients = inventorySystem.getIngredientsBelowThreshold();
        assertFalse(lowStockIngredients.isEmpty());
    }

    @When("the system scans the inventory")
    public void system_scans_inventory() {
        restockingSuggested = inventorySystem.checkAndSuggestRestocking();
    }

    @Then("it should suggest restocking those ingredients")
    public void suggest_restocking() {
        assertTrue(restockingSuggested);
        List<Ingredient> suggested = inventorySystem.getSuggestedRestockList();
        assertNotNull(suggested);
        assertEquals(lowStockIngredients.size(), suggested.size());
    }

    @Then("notify the kitchen manager with a list of items to reorder")
    public void notify_manager_with_reorder_list() {
        boolean notified = inventorySystem.notifyKitchenManager(kitchenManager, lowStockIngredients);
        assertTrue(notified);
        assertTrue(inventorySystem.hasNotificationFor(kitchenManager));
    }

    @When("I request current prices for low-stock ingredients")
    public void request_prices_for_low_stock() {
        // تأكد من تهيئة lowStockIngredients (إذا لم تكن مهيأة في السيناريو الحالي)
        if (lowStockIngredients == null || lowStockIngredients.isEmpty()) {
            lowStockIngredients = inventorySystem.getIngredientsBelowThreshold();
        }
        assertNotNull(lowStockIngredients);
        assertFalse(lowStockIngredients.isEmpty(), "Low stock ingredients list should not be empty");

        supplierPrices = inventorySystem.fetchRealTimePricesFor(lowStockIngredients);
        assertNotNull(supplierPrices);
    }

    @Then("the system should fetch and display real-time prices from integrated suppliers")
    public void system_displays_supplier_prices() {
        assertFalse(supplierPrices.isEmpty());
        for (Ingredient ing : lowStockIngredients) {
            assertTrue(supplierPrices.containsKey(ing.getName()));
            assertTrue(supplierPrices.get(ing.getName()) > 0);
        }
    }

    @Given("some items are critically low in stock")
    public void critically_low_stock_items() {
        inventorySystem = new InventorySystem();
        inventorySystem.addIngredient(new Ingredient("Cheese", 1, 10, true));  // critically low flag
        inventorySystem.addIngredient(new Ingredient("Milk", 2, 10, true));
        kitchenManager = new KitchenManager("manager1", new NotificationService());
        inventorySystem.login(kitchenManager);
    }

    @When("the system performs a stock check")
    public void system_performs_stock_check() {
        generatedPurchaseOrders = inventorySystem.autoGeneratePurchaseOrders();
        purchaseOrderGenerated = !generatedPurchaseOrders.isEmpty();
    }

    @Then("it should automatically generate a purchase order")
    public void auto_generate_purchase_order() {
        assertTrue(purchaseOrderGenerated);
        assertNotNull(generatedPurchaseOrders);
        assertTrue(generatedPurchaseOrders.size() > 0);
    }

    @Then("send it to the appropriate supplier for fulfillment")
    public void send_purchase_order_to_supplier() {
        for (PurchaseOrder po : generatedPurchaseOrders) {
            boolean sent = inventorySystem.sendPurchaseOrderToSupplier(po);
            assertTrue(sent);
        }
    }
}
