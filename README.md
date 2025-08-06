# Task Tracker CLI

A simple and efficient command-line interface (CLI) task management application built in Java. This project demonstrates CRUD operations, file management, and object-oriented programming principles while providing an intuitive way to manage your daily tasks.

> 🔗 **Project from [roadmap.sh](https://roadmap.sh/projects/task-tracker)**

## ✨ Features

- **Complete CRUD Operations**
  -  Add new tasks with descriptions
  -  Update existing task descriptions
  -  Delete tasks by ID
  -  List tasks with various filtering options

- **Task Status Management**
  -  **TODO**: Newly created tasks (default status)
  -  **IN PROGRESS**: Tasks currently being worked on
  -  **DONE**: Completed tasks

- **Smart Filtering**
  - View all tasks
  - Filter by status (TODO, IN PROGRESS, DONE)
  - Clean, organized output

- **Data Persistence**
  - JSON file storage (`task.json`)
  - Automatic file creation and management
  - Preserves task history between sessions

- **Robust Design**
  - Input validation and comprehensive error handling
  - Object-oriented architecture with separation of concerns
  - Unique ID generation for each task
  - Timestamps for creation and updates

## 🚀 Quick Start

### Prerequisites

- **Java 8** or higher
- Command line interface (Terminal, Command Prompt, etc.)

### Installation Options

#### Option 1: Use Pre-built Executable (Recommended)

**Windows:**
```bash
# Navigate to project directory
cd path/to/task-tracker

# Run directly
task-tracker.bat <command> [arguments]

# Or add to PATH for global access
# Add the full path of task-tracker.bat to your system PATH
```

**Linux/macOS:**
```bash
# Navigate to project directory
cd path/to/task-tracker

# Make script executable
chmod +x task-tracker.sh

# Run directly
./task-tracker.sh <command> [arguments]

# Or add to PATH for global access
# Add the full path to your ~/.bashrc or ~/.zshrc:
# export PATH="$PATH:/full/path/to/task-tracker"
```

#### Option 2: Build from Source

```bash
# Clone the repository
git clone <repository-url>
cd task-tracker

# Build with Gradle
./gradlew build

# Run the application
java -cp build/libs/task-tracker-1.0-SNAPSHOT.jar dev.shoangenes.tasktracker.TaskManagerCLI <command> [arguments]
```

#### Option 3: Compile Manually

```bash
# Compile the Java files
javac -d build/classes src/main/java/dev/shoangenes/tasktracker/*.java

# Run the application
java -cp build/classes dev.shoangenes.tasktracker.TaskManagerCLI <command> [arguments]
```

## 📖 Usage

### Command Syntax
```bash
task-tracker <command> [arguments]
```

### Available Commands

| Command | Description | Usage |
|---------|-------------|-------|
| `add` | Create a new task | `task-tracker add "Buy groceries"` |
| `update` | Update task description | `task-tracker update 1 "Buy groceries and cook dinner"` |
| `delete` | Delete a task | `task-tracker delete 1` |
| `mark-in-progress` | Mark task as in progress | `task-tracker mark-in-progress 1` |
| `mark-done` | Mark task as completed | `task-tracker mark-done 1` |
| `list` | Display tasks | `task-tracker list all` |
| `help` | Show help information | `task-tracker help` |

### List Command Options

- `list all` - Show all tasks
- `list todo` - Show only TODO tasks
- `list in-progress` - Show only IN PROGRESS tasks  
- `list done` - Show only completed tasks

### Example Usage

```bash
# Add some tasks
task-tracker add "Complete Java project"
task-tracker add "Review code with team"
task-tracker add "Write documentation"

# Update a task
task-tracker update 1 "Complete Java project and add tests"

# Change task status
task-tracker mark-in-progress 1
task-tracker mark-done 2

# View tasks
task-tracker list all
task-tracker list todo
task-tracker list done

# Delete a task
task-tracker delete 3
```

## 🏗️ Project Structure

```
task-tracker/
├── src/
│   ├── main/java/dev/shoangenes/tasktracker/
│   │   ├── TaskManagerCLI.java      # Main CLI interface
│   │   ├── TaskManager.java         # Core task management logic
│   │   ├── Task.java               # Task entity class
│   │   ├── Status.java             # Task status enumeration
│   │   └── PrintMode.java          # Display mode enumeration
│   └── test/java/dev/shoangenes/tasktracker/
│       ├── TaskManagerTest.java     # TaskManager unit tests
│       └── TaskTest.java           # Task entity unit tests
├── build/
│   └── install/task-tracker/bin/
│       ├── task-tracker            # Unix executable script
│       └── task-tracker.bat        # Windows executable script
├── build.gradle                    # Gradle build configuration
├── task.json                      # JSON data storage (auto-generated)
└── README.md
```

## 🔧 Architecture

### Core Components

- **TaskManagerCLI**: Command-line interface handler and argument parser
- **TaskManager**: Business logic for task operations and JSON persistence
- **Task**: Entity class representing individual tasks with timestamps
- **Status**: Enumeration for task states (TODO, IN_PROGRESS, DONE)
- **PrintMode**: Enumeration for display filtering options

### Key Features

- **Unique ID Generation**: Automatic incremental ID assignment
- **JSON Persistence**: Automatic saving/loading from `task.json`
- **Error Handling**: Comprehensive validation and user feedback
- **Immutable Creation Time**: Tasks preserve their original creation timestamp
- **Flexible Updates**: Update descriptions and status independently

## 🧪 Testing

The project includes comprehensive unit tests:

```bash
# Run tests with Gradle
./gradlew test

# Run specific test class
./gradlew test --tests TaskManagerTest
```

Test Coverage:
- ✅ Task creation and property management
- ✅ CRUD operations validation
- ✅ JSON serialization/deserialization
- ✅ Status transitions
- ✅ Error handling scenarios

## 📄 License

This project is part of the [roadmap.sh](https://roadmap.sh/projects/task-tracker) learning path.

## 🎯 Learning Objectives

This project demonstrates:
- ✅ **Object-Oriented Programming**: Classes, encapsulation, inheritance
- ✅ **File I/O Operations**: JSON reading/writing, file management  
- ✅ **Error Handling**: Exception handling and user input validation
- ✅ **CLI Development**: Command parsing and user interaction
- ✅ **Testing**: Unit testing with JUnit
- ✅ **Build Tools**: Gradle build configuration
- ✅ **Data Persistence**: JSON-based storage system

---

💡 **Tip**: Add the executable to your system PATH for convenient access from anywhere in your terminal!
