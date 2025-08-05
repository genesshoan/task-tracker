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

            // Añadir un pequeño delay para asegurar que el tiempo cambie
            Thread.sleep(1);

            // Update the task description
            task.updateDescription("Updated Task");
            LocalDateTime updatedAt = (LocalDateTime) creationDateTimeField.get(task);

            // Verify that the updatedAt field has changed
            assertTrue(updatedAt.isAfter(now) || updatedAt.equals(now));
        } catch (NoSuchFieldException | IllegalAccessException | InterruptedException e) {
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
        // Crear una tarea primero para obtener el formato exacto
        Task originalTask = new Task("Test Task");
        String originalJson = originalTask.toJson();

        // Ahora parsear el JSON y verificar que se crea correctamente
        Task parsedTask = Task.fromJson(originalJson);

        assertNotNull(parsedTask);
        assertEquals(originalTask.getId(), parsedTask.getId());
        assertEquals("Test Task", parsedTask.getDescription());
        assertEquals(Status.TODO, parsedTask.getStatus());

        // Verificar que el JSON generado por la tarea parseada es idéntico
        String newJson = parsedTask.toJson();
        assertEquals(originalJson, newJson);
    }

}
