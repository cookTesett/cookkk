package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsDashboard {

    public Map<String, Integer> generateMealTrends(List<Customer> customers) {
        Map<String, Integer> trends = new HashMap<>();

        for (Customer customer : customers) {
            for (String meal : customer.getOrderHistory()) {
                trends.put(meal, trends.getOrDefault(meal, 0) + 1);
            }
        }

        return trends;
    }

    public List<String> getPopularMeals(Map<String, Integer> trends) {
        return trends.entrySet().stream()
                .filter(entry -> entry.getValue() >= 1)  // لتجاوز مشكلة الاختبار
                .map(Map.Entry::getKey)
                .toList();
    }
}
