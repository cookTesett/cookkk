package model;

import java.util.*;

public class InventorySystem {
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<PurchaseOrder> purchaseOrders = new ArrayList<>();
    private Set<KitchenManager> loggedInManagers = new HashSet<>();
    private List<Ingredient> suggestedRestockList = new ArrayList<>();
    private Map<KitchenManager, List<String>> notifications = new HashMap<>();

    public void login(KitchenManager manager) {
        loggedInManagers.add(manager);
    }

    public boolean isUserLoggedIn(KitchenManager manager) {
        return loggedInManagers.contains(manager);
    }

    public void loadInventoryDashboard() {
        // simulate dashboard loading
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredients;
    }

    public List<Ingredient> getIngredientsBelowThreshold() {
        List<Ingredient> lowStock = new ArrayList<>();
        for (Ingredient ing : ingredients) {
            if (ing.isLowStock()) {
                lowStock.add(ing);
            }
        }
        return lowStock;
    }

    public boolean checkAndSuggestRestocking() {
        suggestedRestockList = getIngredientsBelowThreshold();
        return !suggestedRestockList.isEmpty();
    }

    public List<Ingredient> getSuggestedRestockList() {
        return suggestedRestockList;
    }

    public boolean notifyKitchenManager(KitchenManager manager, List<Ingredient> ingredientsToNotify) {
        List<String> messages = new ArrayList<>();
        for (Ingredient ing : ingredientsToNotify) {
            messages.add("Restock needed for: " + ing.getName());
        }
        notifications.put(manager, messages);
        return true;
    }

    public boolean hasNotificationFor(KitchenManager manager) {
        return notifications.containsKey(manager) && !notifications.get(manager).isEmpty();
    }

    public Map<String, Double> fetchRealTimePricesFor(List<Ingredient> lowStockIngredients) {
        Map<String, Double> prices = new HashMap<>();
        for (Ingredient ing : lowStockIngredients) {
            prices.put(ing.getName(), 2.0 + Math.random() * 5); // simulate random price
        }
        return prices;
    }

    public List<PurchaseOrder> autoGeneratePurchaseOrders() {
        List<PurchaseOrder> orders = new ArrayList<>();
        for (Ingredient ing : ingredients) {
            if (ing.isCriticallyLow()) {
                orders.add(new PurchaseOrder(ing, 20)); // default order amount
            }
        }
        this.purchaseOrders = orders;
        return orders;
    }

    public boolean sendPurchaseOrderToSupplier(PurchaseOrder order) {
        // simulate sending purchase order
        return true;
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }
}
