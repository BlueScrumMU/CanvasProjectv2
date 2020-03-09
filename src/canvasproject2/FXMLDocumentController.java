/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasproject2;

import edu.ksu.canvas.model.Course;
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
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rewil
 */
public class FXMLDocumentController implements Initializable {

    @FXML ListView courseList;
    
    private CanvasInterface canvas = new CanvasInterface();
    private String userID = "";
    
    @FXML public void setOauth() {
        setInterfaceOauth();
    }
    
    public void setInterfaceOauth() {
        openOauthLogin();
        canvas = new CanvasInterface(OauthController.getOauth());
        userID = OauthController.getUserID();
        setCourseList();
    }
    
    public void setCourseList() {
        ObservableList<String> items = FXCollections.observableArrayList();
        List<Course> courses = null;
        if(userID.isEmpty()) courses = canvas.getCourses();
        else courses = canvas.getCourses(userID);
        for(Course c : courses){
            items.add(c.getName() + " - " + c.getId());
        }
        
        courseList.setItems(items);
    }
    
  //------------------------------------------------------------------------------------
    
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
