package model;

import java.util.*;

public class KitchenManager {
    private String name;  // اسم مدير المطبخ
    private NotificationService notificationService;
    private boolean deadlineAlertSent = false;

    // الكونستركتر مع الاسم وخدمة الإشعارات
    public KitchenManager(String name, NotificationService notificationService) {
        this.name = name;
        this.notificationService = notificationService;
    }


    // Getter للاسم
    public String getName() {
        return name;
    }

    // تعيين مهمة للشيف إذا كان مؤهل
    public boolean assignTaskToChef(Task task, Chef chef) {
        if (!chef.getExpertise().equalsIgnoreCase(task.getRequiredExpertise())) {
            return false;
        }
        return chef.assignTask(task);
    }

    // مراقبة المهام والتنبيه إذا اقترب موعد التسليم
    public void monitorTasksForDeadlines(Collection<Chef> chefs) {
        for (Chef chef : chefs) {
            for (Task task : chef.getAssignedTasks()) {
                if (isDeadlineAtRisk(task.getDeadline()) && !task.getStatus().equalsIgnoreCase("completed")) {
                    notificationService.sendNotification(
                            name,  // استخدام اسم المدير في الإشعار
                            "Deadline at risk for task: " + task.getDishName() + " assigned to " + chef.getName()
                    );
                    deadlineAlertSent = true;
                }
            }
        }
    }

    // التحقق من مدى قرب الموعد النهائي
    private boolean isDeadlineAtRisk(String deadline) {
        try {
            java.time.LocalDate deadlineDate = java.time.LocalDate.parse(deadline);
            java.time.LocalDate today = java.time.LocalDate.now();
            long diffDays = java.time.temporal.ChronoUnit.DAYS.between(today, deadlineDate);
            return diffDays >= 0 && diffDays <= 2;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasSentDeadlineAlerts() {
        return deadlineAlertSent;
    }
}
