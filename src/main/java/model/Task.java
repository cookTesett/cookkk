package model;

import java.util.*;

public class Task {
    private String dishName;
    private String requiredExpertise;
    private String ingredients;
    private String deadline; // صيغة: YYYY-MM-DD
    private String status; // "pending", "assigned", "in progress", "completed"
    private boolean assigned;
    private Chef assignedChef;

    public Task(String dishName, String requiredExpertise, String ingredients, String deadline) {
        this.dishName = dishName;
        this.requiredExpertise = requiredExpertise;
        this.ingredients = ingredients;
        this.deadline = deadline;
        this.status = "pending";
        this.assigned = false;
        this.assignedChef = null;
    }

    // getters
    public String getDishName() {
        return dishName;
    }

    public String getRequiredExpertise() {
        return requiredExpertise;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public Chef getAssignedChef() {
        return assignedChef;
    }

    // setters
    public void setStatus(String status) {
        this.status = status;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public void setAssignedChef(Chef assignedChef) {
        this.assignedChef = assignedChef;
    }
}
