# Tasck Tracker
Tack tracker is a simple command line interface(CLI) based aplication writtien in Java featuring CRUD operations that allow you
to manage your tasks easy. This project implements object-programing principles, files managment and user input handling. 

## 🎮 Features
 - **CRUD Operations**
   - Add, Update, and Delete tasks
   - Mark a task as in progress or done
   - List all tasks
   - List all tasks that are done
   - List all tasks that are not done
   - List all tasks that are in progress
 - **Input Validation** and error handling
 - **Modular Desing** using object-oriented programming

## 🚀 How to Use

### Prerequisites
 - Java 8 or higher

### Compilation and Execution
You can build the project yourself with Gradle, run java TaskManagerCLI in a 
command-line after compiling it with ```javac TaskManagerCLI.java```, or by using an IDE. 
But if you just want to try it out, here are some instructions to execute the .jar file...
### Windows
 - Add the full path of ```task-tracker.bat``` to your system PATH, or simply navigate to the folder containing the file and execute ```task-tracker.bat <command>```.
 - Linux/macOS

### Linux/macOS
 - Add the full path of ```task-tracker.sh``` to your system PATH by adding it to your ```~/.bashrc``` or ```~/.zshrc``` file, or simply execute ```./task-tracker.sh <command>``` from the folder where the file is located.
 - Make sure the script has execute permissions: ```chmod +x task-tracker.sh```

##🔧 How to Use
### Usage:
 - java TaskManagerCLI <command> [arguments]
 - task-tracker <command> [arguments]
 - ./task-tracker.bat <command> [arguments]
 ### Available commands:
 - add \<description>
 - update \<id> <description>
 - delete \<id>
 - mark-in-progress \<id>
 - mark-done \<id>"
 - list [all, todo, done, in-progress]
