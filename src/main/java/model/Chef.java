package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Chef {
    private String id;
    private String name;
    private String expertise;
    private List<String> currentOrder;
    private String substitutionAlert;

    private List<Task> assignedTasks;

    public static final int MAX_WORKLOAD = 5;

    private NotificationService notificationService;

    public Chef() {
        this.assignedTasks = new ArrayList<>();
    }

    // تم تعديل هذا الكونستركتور ليطابق الاستعمال في الـ StepDefinitions
    public Chef(String name, String expertise, NotificationService notificationService) {
        this.id = name; // أو غيره لو عندك طريقة توليد ID
        this.name = name;
        this.expertise = expertise;
        this.assignedTasks = new ArrayList<>();
        this.notificationService = notificationService;
    }

    public String getExpertise() {
        return expertise;
    }

    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void receiveOrder(List<String> ingredients) {
        this.currentOrder = ingredients;
    }

    public List<String> getCurrentOrder() {
        return currentOrder;
    }

    public void receiveSubstitutionAlert(String substitution) {
        this.substitutionAlert = substitution;
    }

    public String getSubstitutionAlert() {
        return substitutionAlert;
    }

    public boolean approveSubstitution(String substitution) {
        if (substitution == null || substitutionAlert == null) {
            return false;
        }
        return substitution.equals(substitutionAlert);
    }

    public boolean assignTask(Task task) {
        if (assignedTasks.size() >= MAX_WORKLOAD) {
            return false;
        }
        assignedTasks.add(task);
        task.setAssigned(true);
        task.setAssignedChef(this);
        task.setStatus("assigned");

        if (notificationService != null) {
            String message = "New task assigned: " + task.getDishName() +
                    "\nIngredients: " + task.getIngredients() +
                    "\nDeadline: " + task.getDeadline();
            notificationService.sendNotification(name, message);
        }

        return true;
    }

    public void sortTasksByDeadline() {
        assignedTasks.sort(Comparator.comparing(Task::getDeadline));
    }
}
