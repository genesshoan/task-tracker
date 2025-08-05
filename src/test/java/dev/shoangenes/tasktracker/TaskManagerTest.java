package dev.shoangenes.tasktracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class TaskManagerTest {
    private static final Path FILE_PATH = Path.of("task.json");

    @BeforeEach
    public void cleanFile() throws IOException {
        Files.deleteIfExists(FILE_PATH);

        try {
            Field idField = Task.class.getDeclaredField("lastIdSaved");
            idField.setAccessible(true);
            idField.setInt(null, 0);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Error");
        }
    }

    @AfterEach
    public void cleanUp() throws IOException {
        Files.deleteIfExists(FILE_PATH);
    }

    @Test
    public void testAdd() {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask("Study Java");
        taskManager.addTask("Go to the University");
        taskManager.addTask("Tell Jimmy about that");

        assertEquals("Study Java", taskManager.getTaskById(1).getDescription());
        assertEquals("Go to the University", taskManager.getTaskById(2).getDescription());
        assertEquals("Tell Jimmy about that", taskManager.getTaskById(3).getDescription());
    }

    @Test
    public void testDelete() {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask("Study Java");
        taskManager.addTask("Go to the University");
        taskManager.addTask("Tell Jimmy about that");

        taskManager.deleteTask(1);
        taskManager.deleteTask(2);
        taskManager.deleteTask(3);
        assertTrue(taskManager.isEmpty());

        assertThrows(NoSuchElementException.class, () -> taskManager.deleteTask(20));
    }

    @Test
    public void testUpdate() {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask("Study Java");
        taskManager.addTask("Go to the University");
        taskManager.addTask("Tell Jimmy about that");

        taskManager.updateTask(1, "Check");
        assertEquals("Check", taskManager.getTaskById(1).getDescription());
        assertEquals(1, taskManager.getTaskById(1).getId());

        taskManager.updateTask(2, "Check");
        assertEquals("Check", taskManager.getTaskById(2).getDescription());
        assertEquals(2, taskManager.getTaskById(2).getId());

        taskManager.updateTask(3, "Check");
        assertEquals("Check", taskManager.getTaskById(3).getDescription());
        assertEquals(3, taskManager.getTaskById(3).getId());

        assertThrows(NoSuchElementException.class, () -> taskManager.updateTask(20, "Check"));
    }

    @Test
    public void testMarkAs() {
        TaskManager taskManager = new TaskManager();
        taskManager.addTask("Study Java");
        taskManager.addTask("Go to the University");
        taskManager.addTask("Tell Jimmy about that");

        taskManager.markDone(1);
        taskManager.markInProgress(2);

        assertThrows(NoSuchElementException.class, () -> taskManager.markDone(10));
        assertThrows(NoSuchElementException.class, () -> taskManager.markInProgress(20));
        assertEquals(Status.DONE, taskManager.getTaskById(1).getStatus());
        assertEquals(Status.IN_PROGRESS, taskManager.getTaskById(2).getStatus());
        assertEquals(Status.TODO, taskManager.getTaskById(3).getStatus());
    }

    @Test
    public void testWriteTasksToJsonAndReadTasksFromJson() {
        TaskManager manager = new TaskManager();
        manager.addTask("Task 1");
        manager.addTask("Task 2");

        // Escribir las tareas al JSON
        manager.writeTasksToJson();

        assertTrue(Files.exists(FILE_PATH), "El archivo JSON debe existir después de escribir.");

        // Crear un nuevo manager que cargará las tareas del JSON
        TaskManager newManager = new TaskManager();

        // Verificar que las tareas se cargaron correctamente
        assertFalse(newManager.isEmpty());
        assertNotNull(newManager.getTaskById(1));
        assertNotNull(newManager.getTaskById(2));
        assertEquals("Task 1", newManager.getTaskById(1).getDescription());
        assertEquals("Task 2", newManager.getTaskById(2).getDescription());
    }

    @Test
    public void testReadTasksFromJsonWhenFileDoesNotExist() throws IOException {
        Files.deleteIfExists(FILE_PATH);
        TaskManager manager = new TaskManager();
        HashMap<Integer, Task> tasks = manager.readTasksFromJson();
        assertTrue(tasks.isEmpty(), "Debe devolver un HashMap vacío si el archivo no existe.");
    }
}
