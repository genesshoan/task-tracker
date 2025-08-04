package dev.shoangenes.tasktracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Task {
    private static int lastIdSaved = 0;
    private final int id;
    private String description;
    private Status status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Constructor for Task.
     * This constructor initializes a new task with a unique ID, description, default status (TODO), and timestamps for creation and last update.
     * The ID is automatically incremented from the last saved ID to ensure uniqueness.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.id = ++lastIdSaved;
        this.description = description;
        this.status = Status.TODO; // Default status
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Private constructor for Task used by the fromJson method.
     * This constructor is used to create a Task object from JSON data, ensuring that the ID is unique and the status is correctly set.
     *
     * @param id the unique identifier for the task
     * @param description the description of the task
     * @param status the current status of the task
     * @param createdAt the timestamp when the task was created
     * @param updatedAt the timestamp when the task was last updated
     */
    private Task(int id, String description, Status status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Gets the unique identifier of the task.
     *
     * @return the unique identifier of the task
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the description of the task.
     * @return the description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description of the task.
     * This method sets a new description for the task and updates the last modified timestamp to the current time.
     *
     * @param description the new description for the task
     */
    public void updateDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Gets the current status of the task.
     * The status can be TODO, IN_PROGRESS, or DONE.
     * @return the current status of the task
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set the current status of the task as DONE.
     * This method updates the status of the task to DONE and sets the last modified timestamp to the current time.
     */
    public void markAsDone() {
        this.status = Status.DONE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Set the current status of the task as IN_PROGRESS.
     * This method updates the status of the task to IN_PROGRESS and sets the last modified timestamp to the current time.
     */
    public void markAsInProgress() {
        this.status =Status.IN_PROGRESS;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Converts a JSON string representation of a task into a Task object.
     * This method parses the JSON string, extracts the task details, and creates a new Task object.
     * If the ID in the JSON is less than the last saved ID, it increments the ID to ensure uniqueness.
     *
     * @param json the JSON string representation of a task
     * @return a Task object created from the JSON data
     */
    public static Task fromJson(String json) {
        json = json.replace("{", "").replace("}", "").replaceAll("\"", "");
        String[] line = json.split(",");

        String idStr = line[0].split(":")[1].trim();
        String description = line[1].split(":")[1].trim();
        String statusStr = line[2].split(":")[1].trim();
        String createdAtStr = line[3].split(":", 2)[1].trim();
        String updatedAtStr = line[4].split(":", 2)[1].trim();

        int id = Integer.parseInt(idStr);
        if (id < lastIdSaved) {
            id = ++lastIdSaved;
        }
        Status status = Status.valueOf(statusStr.toUpperCase().replace(" ", "_"));
        LocalDateTime createdAt = LocalDateTime.parse(createdAtStr, formatter);
        LocalDateTime updatedAt = LocalDateTime.parse(updatedAtStr, formatter);

        return new Task(id,description,status,createdAt,updatedAt);
    }

    /**
     * Converts the Task object into a JSON string representation.
     * This method formats the task details into a JSON string, including the ID, description, status, and timestamps.
     *
     * @return a JSON string representation of the task
     */
    public String toJson() {
        return String.format(
                "{\"id\":%d,\"description\":\"%s\",\"status\":\"%s\",\"createdAt\":\"%s\",\"updatedAt\":\"%s\"}",
                id,
                description,
                status.toString().toUpperCase(),
                createdAt.format(formatter),
                updatedAt.format(formatter)
        );
    }

    /**
     * Returns a string representation of the Task object.
     * This method provides a human-readable format of the task details, including ID, description, status, and timestamps.
     *
     * @return a string representation of the task
     */
    @Override
    public String toString() {
        return "id: " + id +
                ", description: " + description +
                ", status: " + status +
                ", createdAt: " + createdAt +
                ", updatedAt: " + updatedAt;
    }
}