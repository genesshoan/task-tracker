package dev.shoangenes.tasktracker;

public enum Status {
    TODO, IN_PROGRESS, DONE;

    @Override
    public String toString() {
        return this.name().toLowerCase().replace("_", " ");
    }
}