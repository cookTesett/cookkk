package stepdefinitions;

import io.cucumber.java.en.*;

import model.Chef;
import model.KitchenManager;
import model.NotificationService;
import model.Task;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class SchedulingandTaskManagementStep {

    private String currentUserRole;
    private Map<String, Chef> chefs = new HashMap<>();
    private List<Task> pendingTasks = new ArrayList<>();
    private KitchenManager kitchenManager;
    private NotificationService notificationService = new NotificationService();

    // Helper method to get date offset by days from today
    private String getDatePlusDays(int days) {
        return java.time.LocalDate.now().plusDays(days).toString();
    }

    // Scenario 1: Kitchen manager assigns a task to chef based on workload and expertise
    @Given("I am a kitchen manager")
    public void i_am_a_kitchen_manager() {
        currentUserRole = "kitchen_manager";
        kitchenManager = new KitchenManager("manager1", notificationService);  // تم التصحيح هنا
    }

    @And("there are pending tasks with required expertise")
    public void there_are_pending_tasks_with_required_expertise() {
        // نضيف tasks وهمية بتاريخ ديناميكي
        pendingTasks.add(new Task("Make Veggie Salad", "Vegetarian Dishes", "Ingredients A, B", getDatePlusDays(3)));
        pendingTasks.add(new Task("Cook Beef Steak", "Meat Dishes", "Ingredients X, Y", getDatePlusDays(4)));
    }

    @When("I assign a cooking task to chef {string} with expertise in {string}")
    public void i_assign_a_cooking_task_to_chef_with_expertise_in(String chefName, String expertise) {
        Chef chef = chefs.get(chefName);
        if (chef == null) {
            chef = new Chef(chefName, expertise, notificationService);
            chefs.put(chefName, chef);
        }

        Optional<Task> taskToAssign = pendingTasks.stream()
                .filter(t -> t.getRequiredExpertise().equalsIgnoreCase(expertise) && !t.isAssigned())
                .findFirst();

        assertTrue(taskToAssign.isPresent(), "No suitable task found for expertise: " + expertise);

        Task task = taskToAssign.get();
        boolean assigned = kitchenManager.assignTaskToChef(task, chef);
        assertTrue(assigned, "Task assignment failed due to workload or other reasons");
    }

    @Then("the system should match the task to the chef’s profile")
    public void the_system_should_match_the_task_to_the_chef_s_profile() {
        for (Task t : pendingTasks) {
            if (t.isAssigned()) {
                Chef chef = t.getAssignedChef();
                assertEquals(chef.getExpertise(), t.getRequiredExpertise());
            }
        }
    }

    @Then("assign the task if workload is acceptable")
    public void assign_the_task_if_workload_is_acceptable() {
        for (Chef chef : chefs.values()) {
            assertTrue(chef.getAssignedTasks().size() <= Chef.MAX_WORKLOAD);
        }
    }

    @Then("notify chef {string} with task details")
    public void notify_chef_with_task_details(String chefName) {
        Chef chef = chefs.get(chefName);
        assertNotNull(chef);
        assertTrue(notificationService.hasSentNotificationTo(chefName));
    }

    // Scenario 2: Chef receives task notification
    @Given("I am a chef with a newly assigned task")
    public void i_am_a_chef_with_a_newly_assigned_task() {
        currentUserRole = "chef";
        Chef chef = new Chef("Ali", "Vegetarian Dishes", notificationService);
        Task task = new Task("Make Veggie Salad", "Vegetarian Dishes", "Lettuce, Tomato", getDatePlusDays(1));
        chef.assignTask(task);
        chefs.put("Ali", chef);
        notificationService.sendNotification("Ali", "New task assigned: " + task.getDishName());
    }

    @When("I log into the system")
    public void i_log_into_the_system() {
        assertEquals("chef", currentUserRole);
    }

    @Then("I should see the list of my assigned tasks")
    public void i_should_see_the_list_of_my_assigned_tasks() {
        Chef chef = chefs.get("Ali");
        assertNotNull(chef);
        assertFalse(chef.getAssignedTasks().isEmpty());
    }

    @And("each task should include dish name, ingredients, and deadline")
    public void each_task_should_include_dish_name_ingredients_and_deadline() {
        Chef chef = chefs.get("Ali");
        for (Task task : chef.getAssignedTasks()) {
            assertNotNull(task.getDishName());
            assertNotNull(task.getIngredients());
            assertNotNull(task.getDeadline());
        }
    }

    // Scenario 3: System tracks task status and deadlines
    @Given("tasks are assigned to chefs")
    public void tasks_are_assigned_to_chefs() {
        Chef chef = new Chef("Ali", "Vegetarian Dishes", notificationService);
        Task task = new Task("Make Veggie Salad", "Vegetarian Dishes", "Lettuce, Tomato", getDatePlusDays(1));
        chef.assignTask(task);
        task.setStatus("assigned");
        chefs.put("Ali", chef);
    }

    @When("a task is marked as {string} or {string}")
    public void a_task_is_marked_as_or(String status1, String status2) {
        Chef chef = chefs.get("Ali");
        Task task = chef.getAssignedTasks().get(0);
        if (status1.equalsIgnoreCase("in progress") || status2.equalsIgnoreCase("in progress")) {
            task.setStatus("in progress");
        } else if (status1.equalsIgnoreCase("completed") || status2.equalsIgnoreCase("completed")) {
            task.setStatus("completed");
        }
    }

    @Then("the system should update the task status accordingly")
    public void the_system_should_update_the_task_status_accordingly() {
        Chef chef = chefs.get("Ali");
        Task task = chef.getAssignedTasks().get(0);
        assertTrue(task.getStatus().equals("in progress") || task.getStatus().equals("completed"));
    }

    @And("alert the kitchen manager if deadlines are at risk")
    public void alert_the_kitchen_manager_if_deadlines_are_at_risk() {
        kitchenManager = new KitchenManager("manager1", notificationService);  // تصحيح هنا أيضاً
        kitchenManager.monitorTasksForDeadlines(chefs.values());
        assertTrue(kitchenManager.hasSentDeadlineAlerts());
    }

    // Scenario 4: System prioritizes tasks based on meal deadlines
    @Given("there are multiple tasks with different deadlines")
    public void there_are_multiple_tasks_with_different_deadlines() {
        Task t1 = new Task("Salad", "Vegetarian Dishes", "Lettuce", getDatePlusDays(2));
        Task t2 = new Task("Steak", "Meat Dishes", "Beef", getDatePlusDays(1));
        Task t3 = new Task("Soup", "Vegetarian Dishes", "Vegetables", getDatePlusDays(3));
        pendingTasks = new ArrayList<>();
        pendingTasks.add(t1);
        pendingTasks.add(t2);
        pendingTasks.add(t3);
    }

    @When("the system arranges the task list")
    public void the_system_arranges_the_task_list() {
        pendingTasks.sort(Comparator.comparing(Task::getDeadline));
    }

    @Then("tasks with nearest deadlines should appear at the top")
    public void tasks_with_nearest_deadlines_should_appear_at_the_top() {
        String earliestTask = pendingTasks.get(0).getDishName();
        assertEquals("Steak", earliestTask);
    }

    @And("chefs should be notified of urgent tasks")
    public void chefs_should_be_notified_of_urgent_tasks() {
        for (Chef chef : chefs.values()) {
            notificationService.sendNotification(chef.getName(), "Urgent task approaching deadline!");
        }
        for (Chef chef : chefs.values()) {
            assertTrue(notificationService.hasSentNotificationTo(chef.getName()));
        }
    }

    // Scenario 5: Chef views prioritized task list
    @Given("I am a chef with multiple tasks")
    public void i_am_a_chef_with_multiple_tasks() {
        Chef chef = new Chef("Ali", "Vegetarian Dishes", notificationService);
        chef.assignTask(new Task("Salad", "Vegetarian Dishes", "Lettuce", getDatePlusDays(2)));
        chef.assignTask(new Task("Soup", "Vegetarian Dishes", "Vegetables", getDatePlusDays(3)));
        chef.assignTask(new Task("Steak", "Meat Dishes", "Beef", getDatePlusDays(1)));
        chefs.put("Ali", chef);
    }

    @When("I view my task dashboard")
    public void i_view_my_task_dashboard() {
        Chef chef = chefs.get("Ali");
        chef.sortTasksByDeadline();
    }

    @Then("I should see tasks sorted by deadline")
    public void i_should_see_tasks_sorted_by_deadline() {
        Chef chef = chefs.get("Ali");
        List<Task> tasks = chef.getAssignedTasks();
        assertTrue(tasks.get(0).getDeadline().compareTo(tasks.get(1).getDeadline()) <= 0);
        assertTrue(tasks.get(1).getDeadline().compareTo(tasks.get(2).getDeadline()) <= 0);
    }

    @And("each task should have a visible due time indicator")
    public void each_task_should_have_a_visible_due_time_indicator() {
        Chef chef = chefs.get("Ali");
        for (Task task : chef.getAssignedTasks()) {
            assertNotNull(task.getDeadline());
        }
    }
}
