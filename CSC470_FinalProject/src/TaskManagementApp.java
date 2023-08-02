import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.util.Duration;

public class TaskManagementApp extends Application {

    private ListView<Task> taskListView;
    private TextField taskNameField;
    private TextArea taskDescriptionField;
    private DatePicker datePicker;
    private Button createTaskButton;
    private Button saveTaskButton;
    private String originalTaskName;
    private String originalTaskDescription;
    private LocalDate originalDueDate;
    private int originalPriority;
    private ComboBox<Integer> priorityComboBox;
    private ListView<Task> completedTasksListView;
    private Label notificationLabel;

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M-dd-yyyy");
    
    // Helper method to show an alert dialog with the given message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) {
    	
        // Dashboard Scene
        taskListView = new ListView<>();

        // Add a title above the ListView
        Label titleLabel = new Label("Dashboard");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Create an HBox to hold the titleLabel and center it
        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);

        // Create a VBox for the title and apply margins to it
        VBox titleVBox = new VBox(10, titleBox);
        titleVBox.setPadding(new Insets(20));

        // Create a VBox for the taskListView and apply margins to it
        VBox taskListVBox = new VBox(10, taskListView);
        taskListVBox.setPadding(new Insets(20));

        // Use BorderPane to position the titleVBox above the taskListVBox and the buttons at the bottom
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(titleVBox);
        borderPane.setCenter(taskListVBox);
        borderPane.setStyle("-fx-background-color: darkgray;");

        // Button to create a new task
        Button newTaskButton = createStyledButton("Create New Task");

        // Button to view the statistics screen
        Button statisticsButton = createStyledButton("View Statistics");

        // Create an HBox to hold the newTaskButton and statisticsButton
        HBox bottomButtonsBox = new HBox(10, newTaskButton, statisticsButton);
        borderPane.setBottom(bottomButtonsBox);
        bottomButtonsBox.setAlignment(Pos.CENTER);
        bottomButtonsBox.setSpacing(200);
        bottomButtonsBox.setPadding(new Insets(50));

        Scene dashboardScene = new Scene(borderPane, 600, 620);
        
        // Create the notification label and set its initial properties
        notificationLabel = new Label();
        notificationLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        notificationLabel.setTextFill(Color.WHITE);
        notificationLabel.setOpacity(0.0); // Make the label initially transparent

        // Add the notification label to the VBox for the title
        titleVBox.getChildren().add(notificationLabel);

        // Task Details Scene
        taskNameField = new TextField();
        taskDescriptionField = new TextArea();
        datePicker = new DatePicker();

        Slider progressSlider = new Slider();
        progressSlider.setMin(0);
        progressSlider.setMax(100);
        progressSlider.setBlockIncrement(10); // Move at 10% increments
        progressSlider.setShowTickLabels(true); // Display tick labels (0%, 10%, 20%, ..., 100%)
        progressSlider.setShowTickMarks(true); // Display tick marks
        progressSlider.setMajorTickUnit(10); // Major tick unit (10%)
        progressSlider.setValue(0); // Set the initial progress value to 0%

        // Create a Label to display the progress value
        Label progressLabel = new Label("Progress: 0%");

        // Add a listener to update the progressLabel when the slider value changes
        progressSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Round the progress value to the nearest 10% to eliminate decimals
            double roundedValue = Math.round(newValue.doubleValue() / 10.0) * 10;
            progressSlider.setValue(roundedValue); // Set the rounded value back to the slider

            progressLabel.setText("Progress: " + (int) roundedValue + "%");
        });

        // Create the ComboBox with priority options (1 to 10)
        priorityComboBox = new ComboBox<>();
        for (int i = 1; i <= 10; i++) {
            priorityComboBox.getItems().add(i);
        }
        priorityComboBox.setValue(1); // Set the default priority to 1

        VBox taskDetailsLayout = new VBox(10,
                new Label("Task Name: "), taskNameField,
                new Label("Description: "), taskDescriptionField,
                new Label("Due Date: "), datePicker,
                new Label("Progress: "), progressSlider, progressLabel
        );

        // Create a new instance for the task details scene
        Button cancelTaskDetailsButton = createStyledButton("Cancel");
        HBox taskDetailsButtons = new HBox(10, saveTaskButton = createStyledButton("Save"), cancelTaskDetailsButton);
        taskDetailsLayout.getChildren().add(taskDetailsButtons);

        Scene taskDetailsScene = new Scene(taskDetailsLayout, 400, 500);

        // Set the action for the "Cancel" button in the task details scene
        cancelTaskDetailsButton.setOnAction(event -> {
            // Revert to the original task information
            taskNameField.setText(originalTaskName);
            taskDescriptionField.setText(originalTaskDescription);
            datePicker.setValue(originalDueDate);
            priorityComboBox.setValue(originalPriority);

            // Switch back to the dashboard scene
            primaryStage.setScene(dashboardScene);
        });

        // Task Creation Scene
        TextField newTaskNameField = new TextField();
        TextArea newTaskDescriptionField = new TextArea();
        DatePicker newDatePicker = new DatePicker();

        // Create the ComboBox with priority options (1 to 10)
        priorityComboBox = new ComboBox<>();
        for (int i = 1; i <= 10; i++) {
            priorityComboBox.getItems().add(i);
        }
        priorityComboBox.setValue(1); // Set the default priority to 1

        VBox taskCreationLayout = new VBox(10,
                new Label("Task Name: "), newTaskNameField,
                new Label("Description: "), newTaskDescriptionField,
                new Label("Due Date: "), newDatePicker,
                new Label("Priority: "), priorityComboBox // Add the ComboBox to the layout
        );

        Button cancelTaskCreationButton = createStyledButton("Cancel");
        HBox taskCreationButtons = new HBox(10, createTaskButton = createStyledButton("Create"), cancelTaskCreationButton);
        taskCreationLayout.getChildren().add(taskCreationButtons);

        Scene taskCreationScene = new Scene(taskCreationLayout, 400, 500);

        // Set the action for the "Cancel" button in the task creation scene
        cancelTaskCreationButton.setOnAction(event -> {
            // Clear the input fields for the next task creation
            newTaskNameField.clear();
            newTaskDescriptionField.clear();
            newDatePicker.setValue(null); // Reset the DatePicker

            // Switch back to the dashboard scene without adding a new task
            primaryStage.setScene(dashboardScene);
        });

        // Statistics Scene using GridPane
        GridPane statisticsLayout = new GridPane();
        statisticsLayout.setVgap(10);
        statisticsLayout.setHgap(10);
        statisticsLayout.setPadding(new Insets(20));
        statisticsLayout.setStyle("-fx-background-color: darkgray;");

        // Statistics Labels
        Label completedTasksLabel = new Label("Completed Tasks: 0");
        completedTasksLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        GridPane.setConstraints(completedTasksLabel, 0, 0);

        // Create the ListView for completed tasks here
        completedTasksListView = new ListView<>();
        completedTasksListView.setPrefHeight(400);
        completedTasksListView.setPrefWidth(600);
        GridPane.setConstraints(completedTasksListView, 0, 1);

        // Button to go back to the dashboard from the statistics scene
        Button backToDashboardButton = createStyledButton("Back to Dashboard");
        GridPane.setConstraints(backToDashboardButton, 0, 2);

        // Add the labels, completed tasks list, and back button to the statistics layout
        statisticsLayout.getChildren().addAll(completedTasksLabel, completedTasksListView, backToDashboardButton);

        // Calculate and update statistics when switching to the statistics scene
        primaryStage.setOnShowing(event -> {
            int completedTasks = completedTasksListView.getItems().size(); // Get completed tasks count from completedTasksListView
            completedTasksLabel.setText("Completed Tasks: " + completedTasks);
        });

        Scene statisticsScene = new Scene(statisticsLayout, 600, 620);
        
        // Set the action for the "Back to Dashboard" button
        backToDashboardButton.setOnAction(event -> {
            // Switch back to the dashboard scene
            primaryStage.setScene(dashboardScene);
        });

        // Set the action for the "Create" button
        createTaskButton.setOnAction(event -> {
            // Create a new task and add it to the task list on the dashboard
            String newTaskName = newTaskNameField.getText();
            String newTaskDescription = newTaskDescriptionField.getText();
            LocalDate newTaskDueDate = newDatePicker.getValue();
            int newTaskPriority = priorityComboBox.getValue();
            
            // Validate input: Check if any required fields are empty
            if (newTaskName.isEmpty() || newTaskDescription.isEmpty() || newTaskDueDate == null) {
                showAlert("Please fill in all the required fields.");
                return; // Stop the action if any required fields are empty
            }

            // Create a new task and add it to the task list on the dashboard
            Task newTask = new Task(newTaskName, newTaskDescription, newTaskDueDate, newTaskPriority);
            taskListView.getItems().add(newTask);
            
            // Show the task creation notification
            showNotification("Task has been created!");

            // Clear the input fields for the next task creation
            newTaskNameField.clear();
            newTaskDescriptionField.clear();
            newDatePicker.setValue(null); // Reset the DatePicker

            // Switch back to the dashboard scene
            primaryStage.setScene(dashboardScene);
        });

        // Navigation between scenes
        taskListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                // Get the selected task from the taskListView
                Task selectedTask = taskListView.getSelectionModel().getSelectedItem();

                // Save the original task information
                originalTaskName = selectedTask.getName();
                originalTaskDescription = selectedTask.getDescription();
                originalDueDate = selectedTask.getDueDate();
                originalPriority = selectedTask.getPriority();

                // Pre-fill the fields in the task details scene with the selected task information
                taskNameField.setText(originalTaskName);
                taskDescriptionField.setText(originalTaskDescription);
                datePicker.setValue(originalDueDate);
                priorityComboBox.setValue(originalPriority);

                primaryStage.setScene(taskDetailsScene);
            }
        });

        // Set the action for the "Save" button in the task details scene
        saveTaskButton.setOnAction(event -> {
            // Get the current task information from the fields
            String editedTaskName = taskNameField.getText();
            String editedTaskDescription = taskDescriptionField.getText();
            LocalDate editedDueDate = datePicker.getValue();
            double editedProgress = progressSlider.getValue();
            
            // Validate input: Check if any required fields are empty
            if (editedTaskName.isEmpty() || editedTaskDescription.isEmpty() || editedDueDate == null) {
                showAlert("Please fill in all the required fields.");
                return; // Stop the action if any required fields are empty
            }


            // Update the selected task in the tasks list with the edited information
            int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
            Task selectedTask = taskListView.getItems().get(selectedIndex);

            // Check if the task is marked as completed
            if (editedProgress == 100) {
                // Remove the task from the dashboard list and add it to the completed tasks list
                taskListView.getItems().remove(selectedIndex);
                completedTasksListView.getItems().add(selectedTask);
            }

            // Update the selected task with the edited information
            selectedTask.setName(editedTaskName);
            selectedTask.setDescription(editedTaskDescription);
            selectedTask.setDueDate(editedDueDate);
            selectedTask.setProgress(editedProgress);

            // Refresh the ListViews to update the display
            taskListView.refresh();
            completedTasksListView.refresh();

            // Recalculate and update statistics in the statistics scene
            int completedTasks = completedTasksListView.getItems().size(); // Get completed tasks count from completedTasksListView
            completedTasksLabel.setText("Completed Tasks: " + completedTasks);

            // Switch back to the dashboard scene
            primaryStage.setScene(dashboardScene);
            
            // Show the task creation notification
            showNotification("Task has been updated!");
        });

        // Set the action for the "Create New Task" button
        newTaskButton.setOnAction(event -> {
            // Switch to the task creation scene when the button is clicked
            primaryStage.setScene(taskCreationScene);
        });

        // Set the action for the "View Statistics" button
        statisticsButton.setOnAction(event -> {
            // Calculate and update statistics when switching to the statistics scene
            int completedTasks = completedTasksListView.getItems().size(); // Get completed tasks count from completedTasksListView
            completedTasksLabel.setText("Completed Tasks: " + completedTasks);

            // Switch to the statistics scene when the button is clicked
            primaryStage.setScene(statisticsScene);
        });

        // Styling for all scenes
        String sceneStyle = "-fx-background-color: darkgray;";

        // Apply styles to scenes
        dashboardScene.getRoot().setStyle(sceneStyle);
        taskDetailsScene.getRoot().setStyle(sceneStyle);
        taskCreationScene.getRoot().setStyle(sceneStyle);
        statisticsScene.getRoot().setStyle(sceneStyle);

        // Set the initial scene to the dashboard
        primaryStage.setScene(dashboardScene);
        primaryStage.setTitle("Task Management Application");
        primaryStage.show();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        button.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(3);
        dropShadow.setOffsetY(3);
        button.setEffect(dropShadow);

        String hoverEffectStyle = "-fx-background-color: #4F4F4F; -fx-text-fill: white;";
        button.setOnMouseEntered(e -> button.setStyle(hoverEffectStyle));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: black; -fx-text-fill: white;"));

        return button;
    }
    
    // Method to show a notification
    private void showNotification(String message) {
        notificationLabel.setText(message);
        notificationLabel.setOpacity(1.0);

        // Create a FadeTransition to fade away the notification after 2 seconds
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), notificationLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            // Hide the notification after it fades away
            notificationLabel.setOpacity(0.0);
        });
        fadeOut.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/* Honesty Statement: I hereby pledge that I did not utilize any outside sources such as ChatGPT or any other
website or AI to help or aide the conceptualization or completion of this assignment. I
also acknowledge that violating this statement will result in a plagiarism violation and I
will be subjected to the penalties outlined in the syllabus. */
