/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasproject2;

import edu.ksu.canvas.model.Course;
import edu.ksu.canvas.model.assignment.Assignment;
import edu.ksu.canvas.model.assignment.AssignmentDate;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rewil
 */
public class FXMLDocumentController implements Initializable {
    
    //<editor-fold desc="Assignment View Vars">
    @FXML ComboBox courseCombo;
    @FXML ComboBox assignmentCombo;
    
    @FXML TextField nameField;
    @FXML TextField idField;
    @FXML TextArea bodyArea;
    @FXML TextField startField;
    @FXML TextField dueField;
    @FXML TextField endField;
    //</editor-fold>
    
    //<editor-fold desc="Mass Edit Vars">
    @FXML ComboBox courseMECombo;
    @FXML ListView agmtsMEList;
    
    @FXML CheckBox enablePublishMECheck;
    
    @FXML DatePicker startMEDate;
    @FXML DatePicker dueMEDate;
    @FXML DatePicker lockMEDate;
    
    @FXML CheckBox publishMECheck;
    //</editor-fold>
    
    private CanvasInterface canvas = new CanvasInterface();
    private String userID = "";
    
  //------------------------------------------------------------------------------------
    //<editor-fold desc="Mass edit">
    
    String courseIDME = "";
    List<Assignment> assignmentsME = null;
    
    @FXML public void listAssignmentsME() {
        try {
            CourseInterface ci = canvas.getCourseInterface("" + courses.get(courseMECombo.getItems().indexOf(courseMECombo.getValue())).getId());
            courseIDME = "" + ci.getID();
            ObservableList<String> items = FXCollections.observableArrayList();
            assignmentsME = ci.getAssignments();
            for(Assignment a : assignmentsME) {
                items.add(a.getName());
            }
            agmtsMEList.setItems(items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML public void pushME() {
        ArrayList<Assignment> items = new ArrayList<>();
        for(Object o : agmtsMEList.getSelectionModel().getSelectedIndices()) {
            int i = (Integer) o;
            items.add(assignmentsME.get(i));
        }
        for(Assignment a : items) {
            try{
                a.setUnlockAt(convertLocalToDate(startMEDate.getValue()));
            } catch (Exception e) {e.printStackTrace();}
            try{
                a.setDueAt(convertLocalToDate(dueMEDate.getValue()));
            } catch (Exception e) {e.printStackTrace();}
            try{
                a.setLockAt(convertLocalToDate(lockMEDate.getValue()));
            } catch (Exception e) {e.printStackTrace();}
            
            if(enablePublishMECheck.isSelected()) {
                a.setPublished(publishMECheck.isSelected());
            }
            canvas.flushAssignment(a);
        }
    }
    
    private Date convertLocalToDate(LocalDate ld) {
        return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    //</editor-fold> 
  //------------------------------------------------------------------------------------
    //<editor-fold desc="Assignment View">
    
    List<Course> courses = null;
    String courseIDAV = "";
    List<Assignment> assignmentsAV = null;
    
    public void setCourseList() {
        try {
            ObservableList<String> items = FXCollections.observableArrayList();
            if(userID.isEmpty()) courses = canvas.getCourses();
            else courses = canvas.getCourses(userID);
            for(Course c : courses){
                items.add(c.getName() + " - " + c.getId());
            }

            courseIDAV = "";
            courseCombo.setItems(items);
            courseMECombo.setItems(items);
            courseCombo.setValue(null);
            assignmentCombo.setValue(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML public void listAssignments() {
        try {
            CourseInterface ci = canvas.getCourseInterface("" + courses.get(courseCombo.getItems().indexOf(courseCombo.getValue())).getId());
            courseIDAV = "" + ci.getID();
            ObservableList<String> items = FXCollections.observableArrayList();
            assignmentsAV = ci.getAssignments();
            for(Assignment a : assignmentsAV) {
                items.add(a.getName());
            }
            assignmentCombo.setItems(items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML public void viewAssignment() {
        try {
            Assignment a = assignmentsAV.get(assignmentCombo.getItems().indexOf(assignmentCombo.getValue()));
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
        if(courseIDAV.isEmpty()) return;
        
        openAssignmentCreator();
        Assignment a = creatorController.getAssignment();
        CourseInterface ci = canvas.getCourseInterface(courseIDAV);
        ci.createAssignment(a);
        listAssignments();
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
        agmtsMEList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }    
    
}
