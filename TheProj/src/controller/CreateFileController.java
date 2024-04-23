package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.DataBase;
import model.Member;
import model.MemberList;
import model.files;
import model.filesList;
import model.role;
import model.roleList;
import model.zPermissions;

public class CreateFileController implements Initializable{
	private Member user;
	private role role;

	private MemberList members;
	private roleList roles;
	private filesList resources;

	zPermissions p = new zPermissions();

	DataBase db;
	
    @FXML
    private Button createButton;

    @FXML
    private TextField fileIdField;

    @FXML
    private TextField fileDescriptionField;

    @FXML
    private TextField fileNameField;

    @FXML
    void handleCreateFile(ActionEvent event) {
    	//get inputs
    	String fileName = fileNameField.getText();
    	String fileDescription = fileDescriptionField.getText();
    	int id = Integer.parseInt(fileIdField.getText());
    	
    	if(fileDescriptionField.getText() == null) {
    		fileDescription = "";
    	}
    	
    	//create file object
    	files newFile = new files(id, fileName, fileDescription);
    	
    	//add to resources list
    	resources.insertSt2(newFile);
    	
    	//save
    	db.save();
    	
    	//confirmation
    	showAlert("New File: "+fileName+", is Added Successfully");
    	
    	//go back to menu
		try {
			//close current page
			((Window) createButton.getScene().getWindow()).hide();

			//redirect to main page 
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MenuView.fxml"));     
			Parent root = (Parent)fxmlLoader.load(); 
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Main Page");
			stage.show();

		} catch (IOException e) {

			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Platform.runLater(() -> {
			//load files
			db = new DataBase(members, roles, resources);
			try {
				db.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			members = db.getAllMembers();
			roles = db.getAllRoles();
			resources = db.getAllResources();
			
			
			
		});
	}
    
	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
