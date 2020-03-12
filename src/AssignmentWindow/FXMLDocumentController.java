/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AssignmentWindow;

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
    private Assignment a = new Assignment();
    
    @FXML TextField titleField;
    @FXML DatePicker startDateField;
    @FXML DatePicker endDateField;
    @FXML DatePicker dueDateField;
    @FXML TextArea descAreaField;
    @FXML Button createAsgnBtn;
    
    @FXML
    public void createAsgnBtn(){
        a.setName(titleField.getText());
        a.setUnlockAt(convertLocalToDate(startDateField.getValue()));
        a.setLockAt(convertLocalToDate(endDateField.getValue()));
        a.setDueAt(convertLocalToDate(dueDateField.getValue()));
        a.setDescription(descAreaField.getText());
                     
       stage.close(); 
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
    public Assignment getAssignment(){
        return a;
    }
    
    private Date convertLocalToDate(LocalDate ld) {
        return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
