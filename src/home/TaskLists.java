package home;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TaskLists {
    private StringProperty id;
    private StringProperty task;
    private StringProperty dueDate;
    private StringProperty status;
    private StringProperty priority;
    private StringProperty assignees;

    public TaskLists(String id, String task, String dueDate, String status,
            String priority, String assignees) {
        this.id = new SimpleStringProperty(id);
        this.task = new SimpleStringProperty(task);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.status = new SimpleStringProperty(status);
        this.priority = new SimpleStringProperty(priority);
        this.assignees = new SimpleStringProperty(assignees);
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(StringProperty id) {
        this.id = id;
    }

    public StringProperty taskProperty() {
        return task;
    }

    public void setTask(StringProperty task) {
        this.task = task;
    }

    public StringProperty dueDateProperty() {
        return dueDate;
    }

    public void setDueDate(StringProperty dueDate) {
        this.dueDate = dueDate;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(StringProperty status) {
        this.status = status;
    }

    public StringProperty priorityProperty() {
        return priority;
    }

    public void setPriority(StringProperty priority) {
        this.priority = priority;
    }

    public StringProperty assigneesProperty() {
        return assignees;
    }

    public void setAssignees(StringProperty assignees) {
        this.assignees = assignees;
    }
}
