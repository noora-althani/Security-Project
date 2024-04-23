package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.DataBase;
import model.Member;
import model.MemberList;
import model.role;
import model.roleList;

public class RemoveRoleController implements Initializable{
	public MemberList members;
	public roleList roles;
	public DataBase files;

	ArrayList<Integer> rolesIds;

	//(int iD, String roleDescription, ArrayList<Integer> allPermissions, int startingHour, int endHour)
	@FXML
	private TableView<role> rolesTable;

	@FXML
	private TableColumn<role, Integer> roleIdCol;

	@FXML
	private TableColumn<role, String> descriptionCol;

	@FXML
	private TableColumn<role, ArrayList<Integer>> permissionsCol;

	@FXML
	private TableColumn<role, Integer> startHourCol;

	@FXML
	private TableColumn<role, Integer> endHourCol;

	@FXML
	private Button backButton;

	@FXML
	void handleBackButton(ActionEvent event) {
		//go back to menu page
		try {
			//close current page
			((Window) backButton.getScene().getWindow()).hide();

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


		Platform.runLater(() -> {

			//load file here
			files = new DataBase(members, roles);
			try {
				files.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			members = files.getAllMembers(); 
			roles = files.getAllRoles(); 

			//get roles as array list -> to traverse
			rolesIds = roles.getRoleIds();
			ArrayList<role> arrayRolesList = new ArrayList<role>();

			for (Integer id : rolesIds) {//traversing roles list (getting arraylist of type roles)
				role roleObj = roles.searchSt2(id);
				arrayRolesList.add(roleObj);
			}
			
	

			//displaying in table
			ObservableList<role> rolesList = FXCollections.observableArrayList(arrayRolesList);
			
			roleIdCol.setCellValueFactory(new PropertyValueFactory<role, Integer>("iD"));
			descriptionCol.setCellValueFactory(new PropertyValueFactory<role, String>("roleDescription"));
			permissionsCol.setCellValueFactory(new PropertyValueFactory<role, ArrayList<Integer>>("allPermissions"));
			startHourCol.setCellValueFactory(new PropertyValueFactory<role, Integer>("startingHour"));
			endHourCol.setCellValueFactory(new PropertyValueFactory<role, Integer>("endHour"));

			rolesTable.setItems(rolesList);

			//add event handler for mouse clicks
			rolesTable.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
				if (event.getButton().equals(javafx.scene.input.MouseButton.PRIMARY)) {
					int index = rolesTable.getSelectionModel().getSelectedIndex();
					role r = rolesTable.getItems().get(index);

					System.out.println(r.getRoleDescription());

					//show confirmation (true->remove role)
					if(showConfirmationAlert("Are you sure you want to remove this role permenantly: "+r.getRoleDescription())) {
						removeRole(r);
						files.save();
						//remove from observable list
						rolesList.remove(r);
					}else {
						showAlert("Process Canceled");
					}
					


				}
			});

		});
	}

	public void removeRole(role r) {
		//remove from roles chaining by tree
		roles.deleteSt2(r.getID());
		//remove from rolesId array list
		rolesIds.removeIf(id -> id == r.getID());
		roles.setRolesIdsList(rolesIds);
		//confirmations
		System.out.println("Role Deleted Successfully");
		showAlert("Role ["+ r.getRoleDescription()+", ID: "+r.getID()+"] Has been Deleted Succsesfully");
	}

	//CONFIRMATION ALERT
	public static boolean showConfirmationAlert(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText(message);

		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() && result.get() == ButtonType.OK;
	}
	
	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Menu Action");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
