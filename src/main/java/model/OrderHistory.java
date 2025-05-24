package model;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory {
    private List<String> orders = new ArrayList<>();

    public void addOrder(String meal) {
        orders.add(meal);
    }

    public List<String> getOrders() {
        return orders;
    }

    public List<String> getFrequentMeals() {
        // Mock logic: return top 2 meals
        return orders.stream().limit(2).toList();
    }
}
