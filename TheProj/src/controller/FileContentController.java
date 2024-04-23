package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class FileContentController implements Initializable{
	private Member user;
	private static role role;

	private MemberList members;
	private roleList roles;
	private filesList resources;

	zPermissions p = new zPermissions();

	DataBase db;
	
	static files file;

	public void setFileObj(files file) {
		this.file = file;
	}
	
	public void setRole(role role) {
		this.role = role;
	}

    @FXML
    private Button updateButton;

    @FXML
    private Label headerLabel;

    @FXML
    private TextArea contentTextArea;

    @FXML
    private Button backButton;

   
    @FXML
    void handleBackButton(ActionEvent event) {
    	
		try {
			//close current page
			((Window) backButton.getScene().getWindow()).hide();

			//redirect to main page 
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UpdateFileView.fxml"));     
			Parent root = (Parent)fxmlLoader.load(); 
			UpdateFileController controller = fxmlLoader.getController();
			controller.setUesrRole(role);

			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Update File Page");
			stage.show();

		} catch (IOException e) {

			e.printStackTrace();
		}
    }
    
   
    @FXML
    void handleUpdateButton(ActionEvent event) {
    	String newContents = contentTextArea.getText();
    	System.out.println("You typed: "+ newContents);
    	
    	//update file objects content
    	file.setFileDescription(newContents);
    	
    	//update files list
    	resources.deleteSt2(file.getID());
    	resources.insertSt2(new files(file.getID(), file.getFileName(),file.getFileDescription()));
    	
    	//Save them in files list (resources)
    	db.save();
    	
    	//show confirmation and go back
    	showAlert("File ("+file.getFileName()+") Is Updated Successfully and Saved.");
    	
    	try {
			//close current page
			((Window) backButton.getScene().getWindow()).hide();

			//redirect to main page 
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UpdateFileView.fxml"));     
			Parent root = (Parent)fxmlLoader.load(); 
			UpdateFileController controller = fxmlLoader.getController();
			controller.setUesrRole(role);

			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Update File Page");
			stage.show();

		} catch (IOException e) {

			e.printStackTrace();
		}
    }
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {//initializing the page (view list with permissions) 


		Platform.runLater(() -> {

			//load file here
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

			contentTextArea.setText(file.getFileDescription());
			
			headerLabel.setText("Contents of File: "+file.getFileName());
		});
	}

    private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Menu Action");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}


}
