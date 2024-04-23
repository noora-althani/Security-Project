package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import model.DataBase;
import model.Member;
import model.MemberList;
import model.role;
import model.roleList;
import model.zPermissions;

public class AddMemberController implements Initializable{
	private Member user;
	private role role;

	private MemberList members;
	public roleList roles;

	zPermissions p = new zPermissions();

	DataBase files;


	@FXML
	private Button addButton;

	@FXML
	private TextField memberNameField;

	@FXML
	private TextField memberIdField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Label rolesLabel;

	@FXML
	private ListView<role> rolesListView;
	ArrayList<role> selectedRoles = new ArrayList<role>();

	@FXML
	void handleAddMember(ActionEvent event) {
		//get inputs + validate
		//get unique selected roles
		Set<role> uniqueSelectedRoles = new HashSet<>(selectedRoles);//this is roles object list
		ArrayList<Integer> rolesID = new ArrayList<Integer>();//roles id only
		for (role r : uniqueSelectedRoles) {
			rolesID.add(r.getID());
		}

		String name = memberNameField.getText();
		String password = passwordField.getText();
		int id = Integer.parseInt(memberIdField.getText());


		//name validation
		if (!isValidName(name)) {
			showAlert("Name should not contain numbers. Enter a Valid Name.");
			return;
		}


		//create member Object 
		Member newMember = new Member(rolesID, id, name, password);
		//add new object to members list 
		members.insertSt2(newMember);

		//save
		files.save();

		//confirmation
		showAlert("The Member "+ name+" was Added Successfully");

		//go back to menu page
		try {
			//close current page
			((Window) rolesListView.getScene().getWindow()).hide();

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
	public void initialize(URL arg0, ResourceBundle arg1) {//initializing the page (view list with permissions) 


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

			//auto generate id in order
			int newId = members.getCounter()+1;
			memberIdField.setText(Integer.toString(newId));

			ObservableList<role> rolesList = FXCollections.observableArrayList();
			rolesListView.setItems(rolesList);

			//roles array list
			//get method to get list of roles object 
			//size -> how many elements (traversing) 
			//roles.getIds
			//if delete -> use setMembersIdsList to remove and set 
			ArrayList<Integer> rolesIds = new ArrayList<Integer>();
			rolesIds = this.roles.getRoleIds();
			ArrayList<role> arrayRolesList = new ArrayList<role>();

			for (Integer id : rolesIds) {
				role roleObj = this.roles.searchSt2(id);
				arrayRolesList.add(roleObj);
			}


			for (role r : arrayRolesList) {
				rolesList.add(r);
			}

			rolesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			rolesListView.setOnMouseClicked(new EventHandler<Event>() {//when an item is clicked in the view list -> handle event

				@Override
				public void handle(Event arg0) {
					ObservableList<role> selectedRolesFromView = rolesListView.getSelectionModel().getSelectedItems();

					for (role r : selectedRolesFromView) {
						System.out.println("Selected Role: "+ r);

						selectedRoles.add(r);//add it to a list
						rolesLabel.setText(rolesLabel.getText()+", "+r); //show as text what has been selected in the label
					}
				}	

			});

		});
	}

	//---------------------VALIDATION FUNCTION-------------------------------

	private boolean isValidName(String name) {
		String regex = ".*\\d.*";
		return !name.matches(regex); //return true if name does not contain numbers
	}

	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
