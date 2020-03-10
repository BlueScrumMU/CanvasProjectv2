/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasproject2;

import edu.ksu.canvas.model.Course;
import edu.ksu.canvas.model.assignment.Assignment;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rewil
 */
public class FXMLDocumentController implements Initializable {

    @FXML ComboBox courseCombo;
    @FXML ComboBox assignmentCombo;
    
    @FXML TextField nameField;
    @FXML TextField idField;
    @FXML TextArea bodyArea;
    @FXML TextField startField;
    @FXML TextField dueField;
    @FXML TextField endField;
    
    private CanvasInterface canvas = new CanvasInterface();
    private String userID = "";
    
  //------------------------------------------------------------------------------------
    //<editor-fold desc="Course List">
    
    List<Course> courses = null;
    String courseID = "";
    List<Assignment> assignments = null;
    
    public void setCourseList() {
        try {
            ObservableList<String> items = FXCollections.observableArrayList();
            if(userID.isEmpty()) courses = canvas.getCourses();
            else courses = canvas.getCourses(userID);
            for(Course c : courses){
                items.add(c.getName() + " - " + c.getId());
            }

            courseID = "";
            courseCombo.setItems(items);
            courseCombo.setValue(null);
            assignmentCombo.setValue(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML public void listAssignments() {
        try {
            CourseInterface ci = canvas.getCourseInterface("" + courses.get(courseCombo.getItems().indexOf(courseCombo.getValue())).getId());
            courseID = "" + ci.getID();
            ObservableList<String> items = FXCollections.observableArrayList();
            assignments = ci.getAssignments();
            for(Assignment a : assignments) {
                items.add(a.getName());
            }
            assignmentCombo.setItems(items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML public void viewAssignment() {
        try {
            Assignment a = assignments.get(assignmentCombo.getItems().indexOf(assignmentCombo.getValue()));
            nameField.setText(a.getName());
            idField.setText("" + a.getId());
            bodyArea.setText(a.getDescription());
            startField.setText("" + a.getUnlockAt());
            dueField.setText("" + a.getDueAt());
            endField.setText("" + a.getLockAt());
        } catch (Exception e) {
            e.printStackTrace();
            clearAssignmentFields();
        }
    }
    
    private void clearAssignmentFields() {
        nameField.setText("");
        idField.setText("");
        bodyArea.setText("");
        startField.setText("");
        dueField.setText("");
        endField.setText("");
    }
    
    //</editor-fold>
  //------------------------------------------------------------------------------------
    //<editor-fold desc="Oauth Login">
    
    private Stage OauthLoginStage = new Stage();
    private loginpopup.FXMLDocumentController OauthController = null;
            
    public void openOauthLogin() {
        FXMLLoader OauthLoader = new FXMLLoader(loginpopup.LoginPopup.class.getResource("FXMLDocument.fxml"));
        try {
            Parent root = OauthLoader.load();
            OauthController = (loginpopup.FXMLDocumentController) OauthLoader.getController();
            OauthController.setStage(OauthLoginStage);
            OauthLoginStage.setScene(new Scene(root));
            OauthLoginStage.setTitle("Oauth Login");
            OauthLoginStage.setAlwaysOnTop(true);
            OauthLoginStage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    @FXML public void setOauth() {
        setInterfaceOauth();
    }
    
    public void setInterfaceOauth() {
        openOauthLogin();
        try {
            canvas = new CanvasInterface(OauthController.getOauth());
            userID = OauthController.getUserID();
        } catch (Exception e) {
            e.printStackTrace();
            canvas = new CanvasInterface();
            userID = "";
        }
        setCourseList();
    }
    
    //</editor-fold>
  //------------------------------------------------------------------------------------
    //<editor-fold desc="Assignment Creation">
    
    private Stage creatorStage = new Stage();
    private AssignmentWindow.FXMLDocumentController creatorController = null;
    
    public void openAssignmentCreator() {
        FXMLLoader creatorLoader = new FXMLLoader(AssignmentWindow.AssignmentWindow.class.getResource("FXMLDocument.fxml"));
        try {
            Parent root = creatorLoader.load();
            creatorController = (AssignmentWindow.FXMLDocumentController) creatorLoader.getController();
            creatorController.setStage(creatorStage);
            creatorStage.setScene(new Scene(root));
            creatorStage.setTitle("Assignment Creator");
            creatorStage.setAlwaysOnTop(true);
            creatorStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML public void createAssignment() {
        if(courseID.isEmpty()) return;
        
        openAssignmentCreator();
        Assignment a = creatorController.getAssignment();
        CourseInterface ci = canvas.getCourseInterface(courseID);
        ci.createAssignment(a);
    }
    
    //</editor-fold>
  //------------------------------------------------------------------------------------
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setCourseList();
    }    
    
}
