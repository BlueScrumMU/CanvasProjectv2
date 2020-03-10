/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AssaignmentWindow;

import edu.ksu.canvas.model.assignment.Assignment;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ande404
 */
public class FXMLDocumentController implements Initializable {

    private Stage stage = null;
    private ZoneId defaultZoneId = ZoneId.systemDefault();
    
    @FXML TextField titleField;
    @FXML DatePicker startDateField;
    @FXML DatePicker endDateField;
    @FXML DatePicker dueDateField;
    @FXML TextArea descAreaField;
    @FXML Button createAsgnBtn;
    
    @FXML
    public void createAsgnBtn(){
        
        Assignment a = new Assignment();
        
        a.setName(titleField.getText());
        a.setUnlockAt(Date.from(startDateField.getValue().atStartOfDay(defaultZoneId).toInstant()));
        a.setLockAt(Date.from(endDateField.getValue().atStartOfDay(defaultZoneId).toInstant()));
        a.setDueAt(Date.from(dueDateField.getValue().atStartOfDay(defaultZoneId).toInstant()));
        a.setDescription(descAreaField.getText());
               
        
       stage.close(); 
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Assignment a = new Assignment();
        
    }    
    
}
