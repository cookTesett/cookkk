package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer {
    private String id;
    private String name;
    private List<String> dietaryRestrictions;
    private List<String> orderHistory;

    // كونستركتور جديد يولد id تلقائي
    public Customer(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.orderHistory = new ArrayList<>();
        this.dietaryRestrictions = new ArrayList<>();
    }

    // الكونستركتور القديم مع id و name (اختياري)
    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
        this.orderHistory = new ArrayList<>();
        this.dietaryRestrictions = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(List<String> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public List<String> getOrderHistory() {
        return orderHistory;
    }

    public void addOrder(String meal) {
        this.orderHistory.add(meal);
    }
}
