package model;

import java.util.HashSet;
import java.util.Set;

public class NotificationService {
    private Set<String> notifiedUsers;

    public NotificationService() {
        this.notifiedUsers = new HashSet<>();
    }

    public void sendNotification(String userName, String message) {
        // في الواقع هنا نرسل رسالة، لكن الآن مجرد تسجيل التنبيه
        System.out.println("Notification to " + userName + ": " + message);
        notifiedUsers.add(userName);
    }

    public boolean hasSentNotificationTo(String userName) {
        return notifiedUsers.contains(userName);
    }
}
