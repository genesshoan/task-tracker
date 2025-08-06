package dev.shoangenes.tasktracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TaskManager {
    // File path where tasks are stored in JSON format
    private final Path FILE_PATH;
    // HashMap to store tasks with their unique IDs
    private final HashMap<Integer, Task> tasks;

    /**
     * Constructor for TaskManager.
     * Initializes the task manager and reads tasks from the JSON file.
     */
    public TaskManager() {
        FILE_PATH = getAppDataFolder();
        ensureDirectoryExists();
        tasks = readTasksFromJson();
    }

    /**
     * Returns the file path where tasks are stored.
     *
     * @return the Path object representing the tasks file
     */
    public Path getAppDataFolder() {
        String osName = System.getProperty("os.name").toLowerCase();
        String userDir = System.getProperty("user.dir");
        Path tasksDir;

        if (osName.contains("win")) {
            String baseDir = System.getenv("APPDATA");
            tasksDir = Paths.get(baseDir, "TaskTracker");
        } else if (osName.contains("mac")) {
            tasksDir = Paths.get(userDir, "Library", "Application Support", "TaskTracker");
        } else if (osName.contains("linux")) {
            String xdgConfig = System.getenv("XDG_CONFIG_HOME");
            if (xdgConfig != null) {
                tasksDir = Paths.get(xdgConfig, "task-tracker");
            } else {
                tasksDir = Paths.get(userDir, ".config", "task-tracker");
            }
        } else {
            throw new TaskStorageException("Unsupported OS: " + osName);
        }
        return tasksDir.resolve("tasks.json");
    }

    /**
     * Ensures that the directory for storing tasks exists.
     * If it does not exist, it creates the necessary directories.
     */
    private void ensureDirectoryExists() {
        try {
            Path taskTrackerDir = FILE_PATH.getParent();
            if (!Files.exists(taskTrackerDir)) {
                Files.createDirectories(taskTrackerDir);
            }
        } catch (IOException e) {
            throw new TaskStorageException("Unable to create task tracker directory", e);
        }
    }

    /**
     * Reads tasks from a JSON file and returns them as a HashMap.
     * If the file does not exist, it returns an empty HashMap.
     *
     * @return a HashMap containing tasks with their IDs as keys
     */
    public HashMap<Integer, Task> readTasksFromJson() {
        HashMap<Integer, Task> storedTask = new HashMap<>();

        if (!Files.exists(FILE_PATH)) {
            return storedTask;
        }

        try {
            String jsonContent = Files.readString(FILE_PATH);
            String cleaned = jsonContent.trim().replaceAll("\\s", "");
            if (!cleaned.isEmpty() && !cleaned.equals("[]")) {
                parseTasksFromJson(storedTask, jsonContent);
            }
        } catch (IOException e) {
            throw new TaskStorageException("Error reading tasks from JSON", e);
        }

        return storedTask;
    }

    /**
     * Pases tasks form JSON content and stores them in the provided HashMap.
     *
     * @param storedTask the HashMap to store tasks
     * @param jsonContent the JSON content as a String
     */
    private void parseTasksFromJson(HashMap<Integer, Task> storedTask, String jsonContent) {
        String[] taskList = jsonContent.replace("[", "").replace("]", "").split("},");

        for (String taskStr : taskList) {
            if (!taskStr.endsWith("}")) {
                taskStr = taskStr + "}";
            }
            Task newTask = Task.fromJson(taskStr);
            storedTask.put(newTask.getId(), newTask);
        }
    }

    public void writeTasksToJson() {
        StringBuilder auxSb = new StringBuilder();
        auxSb.append("[\n");
        Task[] arrayTasks = tasks.values().toArray(new Task[0]);
        for (int i = 0; i < arrayTasks.length; i++) {
            auxSb.append(arrayTasks[i].toJson());
            if (i < arrayTasks.length - 1) {
                auxSb.append(",\n");
            }
            else {
                auxSb.append("\n");
            }
        }
        auxSb.append("]");

        String jsonContent = auxSb.toString();
        try {
            Files.writeString(FILE_PATH, jsonContent);
        } catch (IOException e) {
            throw new TaskStorageException("Error writing tasks to JSON", e);
        }
    }

    /**
     * Adds a new task with the given description.
     * The task is assigned a unique ID and stored in the tasks HashMap.
     *
     * @param description the description of the task to be added
     */
    public void addTask(String description) {
        Task task = new Task(description);
        tasks.put(task.getId(), task);
    }

    /**
     * Updates the description of an existing task identified by its ID.
     * If the task does not exist, it throws a NoSuchElementException.
     *
     * @param id the unique identifier of the task to be updated
     * @param description the new description for the task
     * @throws NoSuchElementException if no task with the given ID exists
     */
    public void updateTask(int id, String description) {
        Task newTask = Optional.ofNullable(tasks.get(id)).orElseThrow(() -> new NoSuchElementException("No such task with id: " + id));
        newTask.updateDescription(description);
    }

    /**
     * Deletes a task identified by its ID.
     * If the task does not exist, it throws a NoSuchElementException.
     *
     * @param id the unique identifier of the task to be deleted
     * @throws NoSuchElementException if no task with the given ID exists
     */
    public void deleteTask(int id) {
        Optional.ofNullable(tasks.remove(id)).orElseThrow(() -> new NoSuchElementException("No such task with id: " + id));
    }

    /**
     * Marks a task as DONE based on its ID.
     * If the task does not exist, it throws a NoSuchElementException.
     *
     * @param id the unique identifier of the task to be marked as DONE
     * @throws NoSuchElementException if no task with the given ID exists
     */
    public void markDone(int id) {
        Task modifyTask = Optional.ofNullable(tasks.get(id)).orElseThrow(() -> new NoSuchElementException("No such task with id: " + id));
        modifyTask.markAsDone();
    }

    /**
     * Marks a task as IN_PROGRESS based on its ID.
     * If the task does not exist, it throws a NoSuchElementException.
     *
     * @param id the unique identifier of the task to be marked as IN_PROGRESS
     * @throws NoSuchElementException if no task with the given ID exists
     */
    public void markInProgress(int id) {
        Task modifyTask = Optional.ofNullable(tasks.get(id)).orElseThrow(() -> new NoSuchElementException("No such task with id: " + id));
        modifyTask.markAsInProgress();
    }

    /**
     * Prints tasks based on the specified print mode.
     * The print modes can be ALL, TODO, DONE, or IN_PROGRESS.
     *
     * @param printMode the mode in which tasks should be printed
     */
    public void printTask(PrintMode printMode) {
        for (Task task : tasks.values()) {
            switch (printMode) {
                case ALL -> System.out.println(task.toString());
                case TODO -> {
                    if (task.getStatus() == Status.TODO) System.out.println(task);
                }
                case DONE -> {
                    if (task.getStatus() == Status.DONE) System.out.println(task);
                }
                case IN_PROGRESS -> {
                    if (task.getStatus() == Status.IN_PROGRESS) System.out.println(task);
                }
            }
        }
    }

    /**
     * Retrieves a task by its unique ID.
     * If the task does not exist, it returns null.
     *
     * @param id the unique identifier of the task to be retrieved
     * @return the Task object if found, otherwise null
     */
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    /**
     * Checks if the task manager has any tasks.
     *
     * @return true if there are no tasks, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}
