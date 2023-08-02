import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleObjectProperty<LocalDate> dueDate;
    private SimpleIntegerProperty priority;
    private double progress;

    public Task(String name, String description, LocalDate dueDate, int priority) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.priority = new SimpleIntegerProperty(priority);

        // Add a listener to the priority property to update the string representation when priority changes
        this.priority.addListener((observable, oldValue, newValue) -> updateStringRepresentation());
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public LocalDate getDueDate() {
        return dueDate.get();
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate.set(dueDate);
    }

    public int getPriority() {
        return priority.get();
    }

    public void setPriority(int priority) {
        this.priority.set(priority);
    }
    
    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    // Helper method to update the string representation of the task
    private void updateStringRepresentation() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M-dd-yyyy");
        String newString = name.get() + " | " + description.get() + " | Due: " + dueDate.get().format(dateFormatter) + " | Priority: " + priority.get()
        + " | Progress: " + progress + "%";
        name.set(newString);
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M-dd-yyyy");
        return name.get() + " | " + description.get() + " | Due: " + dueDate.get().format(dateFormatter) + " | Priority: " + priority.get() + " | Progress: " + progress + "%";
    }

}

