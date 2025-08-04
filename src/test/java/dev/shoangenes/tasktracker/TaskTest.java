package dev.shoangenes.tasktracker;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import dev.shoangenes.tasktracker.Status;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    @Test
    public void testTaskCreation() {
        Task task = new Task("Test Task");
        assertNotNull(task);
        assertEquals("Test Task", task.getDescription());
        assertEquals(Status.TODO, task.getStatus());
    }

    @Test
    public void testUpdateDescription() {
        Task task = new Task("Initial Task");
        try {
            Field creationDateTimeField = Task.class.getDeclaredField("updatedAt");
            creationDateTimeField.setAccessible(true);
            LocalDateTime now = (LocalDateTime) creationDateTimeField.get(task);

            // Update the task description
            task.updateDescription("Updated Task");
            LocalDateTime updatedAt = (LocalDateTime) creationDateTimeField.get(task);

            // Verify that the updatedAt field has changed
            assertNotEquals(now, updatedAt);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Field 'updatedAt' not found in Task class or is not accessible.");
        }
        // Verify the description and updatedAt field
        assertEquals("Updated Task", task.getDescription());
    }

    @Test
    public void testUpdateStatus() {
        Task task = new Task("New Task");
        Status initialStatus = task.getStatus();
        task.markAsDone();
        assertEquals(Status.DONE, task.getStatus());
        task.markAsInProgress();
        assertEquals(Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    public void testFromJsonAndToJson() {
        String json = "{\"id\":1,\"description\":\"Test Task\",\"status\":\"TODO\",\"createdAt\":\"2023-10-01T12:00:00\",\"updatedAt\":\"2023-10-01T12:00:00\"}";
        Task task = Task.fromJson(json);
        assertNotNull(task);
        assertEquals(1, task.getId());
        assertEquals("Test Task", task.getDescription());
        assertEquals(Status.TODO, task.getStatus());

        String jsonOutput = task.toJson();
        assertEquals(json, jsonOutput);
    }

}
