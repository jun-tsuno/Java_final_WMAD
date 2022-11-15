package home;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbUtile.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HomeModel {

    Connection conn = null;
    private ObservableList<TaskLists> taskLists;
    private ObservableList<MembersLists> membersLists;

    public HomeModel() {
        this.conn = dbConnection.getConnection();

        if(this.conn == null) {
            System.exit(0);
        }
    }

    // connection to task list
    public ObservableList<TaskLists> getTaskLists() {
        String query = "SELECT * FROM tasklists_tbl";

        try {
            this.taskLists = FXCollections.observableArrayList();

            ResultSet resultSet = conn.createStatement().executeQuery(query);

            while(resultSet.next()) {
                this.taskLists.add( new TaskLists(
                    resultSet.getString(1),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
                ));
            }
            return taskLists;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // connection to members list
    public ObservableList<MembersLists> getMembersList() {
        System.out.println("heyyyyyyyyy");
        String query = "SELECT * FROM login_tbl";

        try {
            this.membersLists = FXCollections.observableArrayList();

            ResultSet resultSet = conn.createStatement().executeQuery(query);

            while(resultSet.next()) {
                this.membersLists.add( new MembersLists(
                    resultSet.getString(1),
                    resultSet.getString(3)
                ));
            }
            return membersLists;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addTask(String task, String dueDate, String status, String priority, String assignees) {
        String query = "INSERT INTO tasklists_tbl (task, dueDate, status, priority, assignees) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(query);

            statement.setString(1, task);
            statement.setString(2, dueDate);
            statement.setString(3, status);
            statement.setString(4, priority);
            statement.setString(5, assignees);

            statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void editTask(String id, String task, String dueDate, String status, String priority, String assignees) {
        String sql = "UPDATE tasklists_tbl SET task = ?, dueDate = ?, status = ?, priority = ?, assignees = ? WHERE id = ?";
        PreparedStatement statement = null;

        try {
            Connection conn = dbConnection.getConnection();
            statement = conn.prepareStatement(sql);

            statement.setString(1, task);
            statement.setString(2, dueDate);
            statement.setString(3, status);
            statement.setString(4, priority);
            statement.setString(5, assignees);
            statement.setInt(6, Integer.parseInt(id));

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteTask(String id) {

        String sql = "DELETE FROM tasklists_tbl WHERE id = ?";
        PreparedStatement statement = null;

        try {
            Connection conn = dbConnection.getConnection();
            statement = conn.prepareStatement(sql);

            statement.setInt(1, Integer.parseInt(id));

            statement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}