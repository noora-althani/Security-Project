package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.DataBase;
import model.Member;
import model.MemberList;
import model.role;
import model.test;

public class LoginController {

	private static  Member user;
	private static ArrayList<role> userRoles;
	
	private static test t;
	
	public static test getTestObj() {
		return t;
	}
	
	public static  Member getUser() {
		return user;
	}

	public static ArrayList<role> getUserRoles(){
		return userRoles;
	}
 
	@FXML
	private TextField userID;

	@FXML
	private PasswordField password;

	@FXML
	private Button loginButton;


	@FXML
	void login(ActionEvent event) throws IOException {
		System.out.println("Login Button Pressed");

		//user inputs
		String userIDText = userID.getText();
		String pass = password.getText();
		int userID = 0;

		//Handling input validation//start
		//if nothing is typed
		if(userIDText.equals("") || pass.equals("")) {
			System.out.println("Enter in Id and password fields");

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Login Action Not Passed");
			alert.setHeaderText("Caution!");
			alert.setContentText("Either ID or Password Field Was Empty... please enter something");
			alert.show();

			return;

		}
		try {//if a string is typed in place of a number
			userID = Integer.parseInt(userIDText);
		} catch (NumberFormatException e) {
			System.out.println(e);
			return;
		}
		//Handling input validation//end
		
		
		System.out.println("Inputted user id is : "+ userID+" password: "+pass);

		t = new test();//loads info from file
		
		MemberList m = t.getMembersList();
		m.display();
		
		
		//AUTHENTICATE USER (1)
		user = t.Authenticate(userID, pass);
		System.out.println("user is: " +user);
		
		if(user == null) {//if inputed user id is not present
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Login Action Not Passed");
			alert.setHeaderText("Caution!");
			alert.setContentText("Authentication Failed");
			alert.show();

			return;
		}

		if(this.user != null) {
			//RETRIEVE AND SET USER ROLES IN LIST  
			userRoles = t.getRolesByUserId(userID);
			
			//close current page
			((Window) loginButton.getScene().getWindow()).hide();
			//redirect to page
			Stage stage = new Stage();
			Pane root = FXMLLoader.load(getClass().getResource("/view/LoginRoleView.fxml"));
			stage.setScene(new Scene(root));
			stage.setTitle("Choose Role Page");
			stage.show();
		}

	}


	
	@FXML
	void handelExitButton(ActionEvent event) {
		System.out.println("Exited");

		Platform.exit();
	}

}
