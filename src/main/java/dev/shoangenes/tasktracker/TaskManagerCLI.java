package dev.shoangenes.tasktracker;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TaskManagerCLI {
    public static void main(String[] args) {
        try {
            TaskManager taskManager = new TaskManager();

            if (args.length < 1) {
                System.out.println("Usage: task-tracker <command> [arguments]");
                return;
            }

            String command = args[0];

            try {
                switch (command) {
                    case "help" -> handleHelp(args);
                    case "add" -> handleAdd(taskManager, args);
                    case "update" -> handleUpdate(taskManager, args);
                    case "delete" -> handleDelete(taskManager, args);
                    case "mark-in-progress" -> handleMarkInProgress(taskManager, args);
                    case "mark-done" -> handleMarkDone(taskManager, args);
                    case "list" -> handleList(taskManager, args);
                    default -> System.out.println("Unknown command, enter 'help' to display all commands");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Error: " + e.getMessage());
            }

            taskManager.writeTasksToJson();
        } catch (TaskStorageException e) {
            System.out.println("Fatal error: " + e.getMessage());
            System.exit(1);
        }
    }


    /**
     * Parses the ID from a string and prints an error message if the parsing fails.
     *
     * @param idStr the string to parse as an ID
     * @return an Optional containing the parsed ID, or empty if parsing failed
     */
    private static Optional<Integer> parseIdOrPrintError(String idStr) {
        try {
            return Optional.of(Integer.parseInt(idStr));
        } catch (NumberFormatException e) {
            System.out.println("Error: ID must be a valid number. Got: '" + idStr + "'");
            return Optional.empty();
        }
    }

    /**
     * Handles the 'help' command.
     * If no arguments are provided, it prints the help message.
     *
     * @param args the command line arguments
     */
    private static void handleHelp(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: task-tracker help [without arguments]");
            return;
        }
        printHelp();
    }

    /**
     * Handles the 'add' command to add a new task.
     * If the description is missing, it prints an error message.
     *
     * @param taskManager the TaskManager instance to manage tasks
     * @param args the command line arguments
     */
    private static void handleAdd(TaskManager taskManager, String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: task-tracker <description>");
            return;
        }
        String description = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        taskManager.addTask(description);
        System.out.println("Task added successfully.");
    }

    /**
     * Handles the 'update' command to update an existing task.
     * If the ID or description is missing, it prints an error message.
     *
     * @param taskManager the TaskManager instance to manage tasks
     * @param args the command line arguments
     */
    private static void handleUpdate(TaskManager taskManager, String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: task-tracker update <id> <description>");
            return;
        }
        Optional<Integer> parsedId = parseIdOrPrintError(args[1]);
        if (parsedId.isEmpty()) return;
        int id = parsedId.get();
        taskManager.updateTask(id, args[2]);
        System.out.println("Task with id " + id + " updated.");
    }

    /**
     * Handles the 'delete' command to delete an existing task.
     * If the ID is missing or invalid, it prints an error message.
     *
     * @param taskManager the TaskManager instance to manage tasks
     * @param args the command line arguments
     */
    private static void handleDelete(TaskManager taskManager, String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: task-tracker delete <id>");
            return;
        }
        Optional<Integer> parsedId = parseIdOrPrintError(args[1]);
        if (parsedId.isEmpty()) return;
        int id = parsedId.get();
        taskManager.deleteTask(id);
        System.out.println("Task with id " + id + " deleted.");
    }

    /**
     * Handles the 'mark-in-progress' command to mark a task as in progress.
     * If the ID is missing or invalid, it prints an error message.
     *
     * @param taskManager the TaskManager instance to manage tasks
     * @param args the command line arguments
     */
    private static void handleMarkInProgress(TaskManager taskManager, String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: task-tracker mark-in-progress <id>");
            return;
        }
        Optional<Integer> parsedId = parseIdOrPrintError(args[1]);
        if (parsedId.isEmpty()) return;
        int id = parsedId.get();
        taskManager.markInProgress(id);
        System.out.println("Task with id " + id + " marked in progress.");
    }

    /**
     * Handles the 'mark-done' command to mark a task as done.
     * If the ID is missing or invalid, it prints an error message.
     *
     * @param taskManager the TaskManager instance to manage tasks
     * @param args the command line arguments
     */
    private static void handleMarkDone(TaskManager taskManager, String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: task-tracker mark-done <id>");
            return;
        }
        Optional<Integer> parsedId = parseIdOrPrintError(args[1]);
        if (parsedId.isEmpty()) return;
        int id = parsedId.get();
        taskManager.markDone(id);
        System.out.println("Task with id " + id + " marked done.");
    }

    /**
     * Handles the 'list' command to print tasks based on the specified mode.
     * If the mode is missing or invalid, it prints an error message.
     *
     * @param taskManager the TaskManager instance to manage tasks
     * @param args the command line arguments
     */
    private static void handleList(TaskManager taskManager, String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: task-tracker list <mode: ALL, TODO, DONE, IN-PROGRESS>");
            return;
        }
        try {
            PrintMode printMode = PrintMode.valueOf(args[1].toUpperCase());
            taskManager.printTask(printMode);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Unknown printMode '" + args[1] + "'");
        }
    }

    /**
     * Prints the help message with usage instructions and available commands.
     */
    private static void printHelp(){
        System.out.println("Usage:");
        System.out.println("\tjava TaskManagerCLI <command> [arguments]");
        System.out.println("Or:");
        System.out.println("\ttask-tracker <command> [arguments]");
        System.out.println();
        System.out.println("Available commands:");
        System.out.println("\tadd <description>");
        System.out.println("\tupdate <id> <description>");
        System.out.println("\tdelete <id>");
        System.out.println("\tmark-in-progress <id>");
        System.out.println("\tmark-done <id>");
        System.out.println("\tlist [status]");
    }
}
