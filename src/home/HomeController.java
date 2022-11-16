package home;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HomeController implements Initializable {

    DatePicker datePicker = new DatePicker(LocalDate.now());

    TextField editTask = new TextField();
    DatePicker editDueDate = new DatePicker();
    TextField editAssignees = new TextField();
    ComboBox<String> editPriority = new ComboBox<String>();
    ComboBox<String> editStatus = new ComboBox<String>();

    @FXML
    private TextField task;
    @FXML
    private DatePicker dueDate;
    @FXML
    private TextField assignees;
    @FXML
    private ComboBox<String> priority;
    @FXML
    private ComboBox<String> status;

    // task list tab
    @FXML
    private TableView<TaskLists> taskListsTableView;
    @FXML
    private TableColumn<TaskLists, String> idColumn;
    @FXML
    private TableColumn<TaskLists, String> taskColumn;
    @FXML
    private TableColumn<TaskLists, String> dueDateColumn;
    @FXML
    private TableColumn<TaskLists, String> statusColumn;
    @FXML
    private TableColumn<TaskLists, String> priorityColumn;
    @FXML
    private TableColumn<TaskLists, String> assigneesColumn;

    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private Button logOutBtn;

    // member list tab
    @FXML
    private TableView<MembersLists> membersTableView;
    @FXML
    private TableColumn<MembersLists, String> memberIdColumn;
    @FXML
    private TableColumn<MembersLists, String> memberNameColumn;

    Dialog<ButtonType> dialog = null;
    Alert a =  new Alert(AlertType.NONE);

    // instantiate a model
    HomeModel homeModel = null;

    private String editIdString;
    private String editTaskString;
    private String editDueDateString;
    private String editStatusString;
    private String editAssigneesString;
    private String editPriorityString;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.homeModel = new HomeModel();
        priority.getItems().addAll("A", "B", "C");
        priority.getSelectionModel().select(0);
        status.getItems().addAll("Not Started", "In Progress", "Completed", "In Review", "Cancelled" );
        status.getSelectionModel().select(0);
        this.loadTaskLists();
        this.loadMembersList();

        editPriority.getItems().addAll("A", "B", "C");
        editStatus.getItems().addAll("Not Started", "In Progress", "Completed", "In Review", "Cancelled" );

        //disable delete / edit buttons
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);

        taskListsTableView.setOnMouseClicked(e -> {
            TaskLists selected = taskListsTableView.getSelectionModel().getSelectedItem();

            if(selected != null) {
                deleteBtn.setDisable(false);
                updateBtn.setDisable(false);

                editIdString = selected.idProperty().getValue();
                editTaskString = selected.taskProperty().getValue();
                editDueDateString = selected.dueDateProperty().getValue();
                editStatusString = selected.statusProperty().getValue();
                editPriorityString = selected.priorityProperty().getValue();
                editAssigneesString = selected.assigneesProperty().getValue();
            }

        });
    };

    @FXML
    public void loadTaskLists() {
        this.idColumn.setCellValueFactory(new PropertyValueFactory<TaskLists, String>("id"));
        this.taskColumn.setCellValueFactory(new PropertyValueFactory<TaskLists, String>("task"));
        this.dueDateColumn.setCellValueFactory(new PropertyValueFactory<TaskLists, String>("dueDate"));
        this.statusColumn.setCellValueFactory(new PropertyValueFactory<TaskLists, String>("status"));
        this.priorityColumn.setCellValueFactory(new PropertyValueFactory<TaskLists, String>("priority"));
        this.assigneesColumn.setCellValueFactory(new PropertyValueFactory<TaskLists, String>("assignees"));

        this.taskListsTableView.setItems(homeModel.getTaskLists());
    }

    @FXML
    private void loadMembersList() {
        this.memberIdColumn.setCellValueFactory(new PropertyValueFactory<MembersLists, String>("id"));
        this.memberNameColumn.setCellValueFactory(new PropertyValueFactory<MembersLists, String>("name"));

        this.membersTableView.setItems(homeModel.getMembersList());
    }

    //add task
    @FXML
    private void addTask(ActionEvent event) {
        if(this.task.getText().length() == 0 || this.dueDate.getValue() == null || this.status.getValue() == null || this.priority.getValue() == null || this.assignees.getText().length() == 0) {
            a.setAlertType(AlertType.ERROR);
            a.setContentText("All fields are required");
            a.show();
        } else {
            homeModel.addTask(this.task.getText(), this.dueDate.getValue().toString(), this.status.getValue().toString(), this.priority.getValue().toString(), this.assignees.getText());
            this.loadTaskLists();
            this.clearFields(null);
        }
    }

    //clear fields
    @FXML
    private void clearFields(ActionEvent event) {
        this.task.setText("");
        this.dueDate.setValue(null);
        this.status.getSelectionModel().select(0);
        this.priority.getSelectionModel().select(0);
        this.assignees.setText("");
    }

    //update
    @FXML
    private void editTask(ActionEvent event) {
        //create modal
        createModal();

        dialog.showAndWait().ifPresent(response -> {
            if(response.getButtonData().equals(ButtonData.OK_DONE)) {
                if(editTask.getText().length() == 0 || editDueDate.getValue() == null || editStatus.getValue() == null || editPriority.getValue() == null || editAssignees.getText().length() == 0) {
                    a.setAlertType(AlertType.ERROR);
                    a.setContentText("All fields are required");
                    a.show();
                } else {
                    homeModel.editTask(editIdString, editTask.getText(), editDueDate.getValue().toString(), editStatus.getValue().toString(), editPriority.getValue().toString(), editAssignees.getText());
                    editDueDate.setValue(null);
                    editStatus.setValue(null);
                    editPriority.setValue(null);
                    this.loadTaskLists();
                }
            }
        });
    }

    //delete employee
    @FXML
    private void deleteTask(ActionEvent event) {
        TaskLists selectedItem = taskListsTableView.getSelectionModel().getSelectedItem();

        // delete confirmation
        dialog = new Dialog<ButtonType>();
        dialog.setTitle("Confirmation");
        ButtonType deleteModalBtn = new ButtonType("Delete", ButtonData.OK_DONE);
        ButtonType cancelModalBtn = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        dialog.setContentText("Do you really want to delete this item form the Data Base?\n" + "This process cannot be undone.");

        dialog.getDialogPane().getButtonTypes().add(deleteModalBtn);
        dialog.getDialogPane().getButtonTypes().add(cancelModalBtn);

        dialog.showAndWait().ifPresent(response -> {
            if(response.getButtonData().equals(ButtonData.OK_DONE)) {
                //locally remove
                taskListsTableView.getItems().remove(selectedItem);
                //delete from DB
                homeModel.deleteTask(selectedItem.idProperty().getValue());
            }
        });
    }

    public void logOut(ActionEvent event) {
        loginPage();
    }

    // create a modal
    private void createModal() {
        dialog = new Dialog<ButtonType>();

        dialog.setTitle("Edit an Task");

        ButtonType editModalBtn = new ButtonType("Edit", ButtonData.OK_DONE);
        ButtonType cancelModalBtn = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        //set the content
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 10, 10, 10));

        editTask.setText(editTaskString);
        editDueDate.setPromptText(editDueDateString);
        editStatus.setPromptText(editStatusString);
        editPriority.setPromptText(editPriorityString);
        editAssignees.setText(editAssigneesString);

        gridPane.add(new Label("Task"), 0, 0);
        gridPane.add(editTask, 1, 0);
        gridPane.add(new Label("Due Date"), 0, 1);
        gridPane.add(editDueDate, 1, 1);
        gridPane.add(new Label("Status"), 0, 2);
        gridPane.add(editStatus, 1, 2);
        gridPane.add(new Label("Priority"), 0, 3);
        gridPane.add(editPriority, 1, 3);
        gridPane.add(new Label("Assignees"), 0, 4);
        gridPane.add(editAssignees, 1, 4);

        dialog.getDialogPane().setContent(gridPane);

        dialog.getDialogPane().getButtonTypes().add(editModalBtn);
        dialog.getDialogPane().getButtonTypes().add(cancelModalBtn);
    }

    @FXML
    public void loginPage(){

        Stage stage = (Stage) this.logOutBtn.getScene().getWindow();
        stage.close();

        Stage loginStage = new Stage();
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/login/Login.fxml")));

            loginStage.setScene(scene);
            loginStage.setTitle("Login Page");
            loginStage.setResizable(false);
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
