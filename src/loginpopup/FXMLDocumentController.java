/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginpopup;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rewil
 */
public class FXMLDocumentController implements Initializable {
    
    private String Oauth = "";
    private String UserID = "";
    
    private Stage stage = null;
    
    @FXML TextField OauthField;
    @FXML TextField UserIDField;
    
    @FXML
    public void confirmBtnAction() {
        Oauth = OauthField.getText();
        UserID = UserIDField.getText();
        stage.close();
    }
    
    public String getOauth() {
        return Oauth;
    }
    public String getUserID() {
        return UserID;
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
