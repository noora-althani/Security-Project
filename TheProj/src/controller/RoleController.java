package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Member;
import model.role;
import model.test;

public class RoleController implements Initializable {
	public Member m;
	public ArrayList<role> userRoles;
	private static test t;

	public static test getTestObj() {
		return t;
	}

	//	use it when using the passing method in LoginController class
	//	public void setUser(Member user){
	//		this.m = user;
	//	}

	@FXML
	private ListView<role> userRolesList;

	@FXML
	private Label myLabel;

	role currentRole;

	public void initialize(URL arg0, ResourceBundle arg1) {

		//to get information from previous page, we must write this [made everything static]
		Platform.runLater(() -> {

			m = LoginController.getUser();
			userRoles = LoginController.getUserRoles();
			t = LoginController.getTestObj();

			myLabel.setText("Welcome "+ m.getName()+", please choose a role:");

			//show roles as list 
			System.out.println("use obj is: " + m);
			for (role r : userRoles) {
				System.out.println(r);
			}

			//display them in the list view
			userRolesList.getItems().addAll(userRoles);
			userRolesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<role>() {

				@Override
				public void changed(ObservableValue<? extends role> arg0, role arg1, role arg2) {
					currentRole = userRolesList.getSelectionModel().getSelectedItem();
					
					System.out.println("Selected Item: "+ currentRole);

					try {

						//close current page
						((Window) userRolesList.getScene().getWindow()).hide();
						//redirect to page

						//a method to pass
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MenuView.fxml"));     
						Parent root = (Parent)fxmlLoader.load(); 
						//passing things
						MenuController controller = fxmlLoader.<MenuController>getController();
						controller.setUser(m);
						controller.setUserRole(currentRole);
						//show user permissions
						Stage stage = new Stage();
						stage.setScene(new Scene(root));
						stage.setTitle("Main Page");
						stage.show();

					} catch (IOException e) {

						e.printStackTrace();
					}

				}

			});

		});
	}
}
