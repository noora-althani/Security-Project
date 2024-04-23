package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import com.sun.webkit.ContextMenu.ShowContext;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.DataBase;
import model.Member;
import model.MemberList;
import model.filesList;
import model.files;
import model.role;
import model.roleList;
import model.test;
import model.zPermissions;

public class AddRoleController implements Initializable {
	private Member user;
	private role role;

	private MemberList members;
	public roleList roles;
	private filesList resources;

	zPermissions p = new zPermissions();

	DataBase files;

	ArrayList<files> resourcesArray;


	//use it when using the passing method in LoginController class
	public void setUser(Member user){
		this.user = user;
	}

	public void setUserRole(role currentRole) {
		this.role = currentRole;
	}

	public void setRolesList(roleList roles) {
		this.roles = roles;
	}
	public void setMembersList(MemberList members) {
		// TODO Auto-generated method stub
		this.members = members;
	}


	@FXML
	private TextField roleDescriptionField;

	@FXML
	private TextField roleIdField;

	@FXML
	private ListView<String> permissionsListView;

	ArrayList<String> selectedPermissionsList = new ArrayList<String>();

	@FXML
	private TextField startTimeFeild;

	@FXML
	private TextField endTimeField;

	@FXML
	private Button addButton;

	@FXML
	private Label permissionsLabel;

	@FXML
	void handelAddRol(ActionEvent event) throws IOException {
		//validate inputs
		if(!validateInputFormat()) {
			return;
		}

		//get inputs from fxml
		Set<String> uniquePermissions = new HashSet<>(selectedPermissionsList); //unique values only
		ArrayList<Integer> selectedPermissionsId = permissionStringToIntList(uniquePermissions);//get its id (numbers in int)

		String roleDescription = roleDescriptionField.getText();
		int startHour = Integer.parseInt(startTimeFeild.getText());
		int endHour = Integer.parseInt(endTimeField.getText());
		int roleId = Integer.parseInt(roleIdField.getText());


		//create role object 
		role newRole = new role(roleId, roleDescription, selectedPermissionsId, startHour, endHour);
		//Then add role to roles list
		roles.insertSt2(newRole); 

		//save to files here
		files.save();

		//confirmation
		showAlert(roleDescription+" Role Added Successfully");

		try {
			//close current page
			((Window) permissionsListView.getScene().getWindow()).hide();

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
			files = new DataBase(members, roles,resources);
			try {
				files.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			members = files.getAllMembers(); 
			roles = files.getAllRoles(); 
			resources = files.getAllResources();

			//auto generate id in order
			int newId = roles.getSize()+1;
			roleIdField.setText(Integer.toString(newId));

			ObservableList<String> permissions = FXCollections.observableArrayList();
			permissionsListView.setItems(permissions);

			Set<String> uniquePermissions = new HashSet<>(p.getPermissions());//did this because p.getPermission is giving duplicate values i don't know why?
			for (String p : uniquePermissions) {
				permissions.add(p);
			}

			//get files 
			ArrayList<Integer> filesIds = new ArrayList<Integer>();
			filesIds = resources.getFilesIds();
			resourcesArray = new ArrayList<files>();

			System.out.println("PRINTING FILES");

			System.out.println("PRINTING IDS OF FILES");
			for (Integer id : filesIds) {
				files fileObj = resources.searchSt2(id);
				resourcesArray.add(fileObj);
			}
			
			HashSet<files> uniqueResourcesSet = new HashSet<>(resourcesArray);
			ArrayList<files> uniqueResources = new ArrayList<>(uniqueResourcesSet); 
			
			System.out.println("RESOURCES IN SYSTEM......................");
			for (files files : uniqueResources) {
				System.out.println(files);
			}
			System.out.println("END.....................");
			
			for (files f : uniqueResources) {
				permissions.add(f.getFileName());
				
			}
//			
//			for (Integer id : filesIds) {
//				files fileObj = resources.searchSt2(id);
//				resources.deleteSt2(id);
//			}
	
			
//			filesList uniqueResources2 = new filesList();
//			for (files files : uniqueResources) {
//				uniqueResources2.insertSt2(files);
//			}
//			files.setANewResourcesList(uniqueResources2);
//			files.save();
//			
//			uniqueResources2.display();
			
			

			permissionsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			permissionsListView.setOnMouseClicked(new EventHandler<Event>() {//when an item is clicked in the view list -> handle event

				@Override
				public void handle(Event arg0) {
					ObservableList<String> selectedPermissions = permissionsListView.getSelectionModel().getSelectedItems();

					for (String s : selectedPermissions) {
						System.out.println("Selected Permission: "+ s);

						selectedPermissionsList.add(s);//add it to a list
						permissionsLabel.setText(permissionsLabel.getText()+", "+s); //show as text what has been selected in the label
					}
				}	

			});

		});
	}


	//---------- getting integer values of chosen permissions (permission id) -----------
	public ArrayList<Integer> permissionStringToIntList(Set<String> selectedPermissions) {

		ArrayList<Integer> intList = new ArrayList<>();

		for (String permission : selectedPermissions) {
			int intValue = getIntValue(permission);

			if (intValue != -1) {
				intList.add(intValue);
			}
			
			for (files f : resourcesArray) {//check resources name if its the same as the one pressed on. if yes add its id.
				if(f.getFileName().equals(permission)) {
					intList.add(f.getID());
				}
			}
		}
		return intList;
	}

	private static int getIntValue(String permission) {
		if (permission.equals("Add new role [write]")) {
			return 1;
		} else if (permission.equals("remove role [write]")) {
			return 2;
		} else if (permission.equals("view roles list [read]")) {
			return 3;
		} else if (permission.equals("Add new member [write]")) {
			return 4;
		} else if (permission.equals("remove member [write]")) {
			return 5;
		} else if (permission.equals("view members list [read]")) {
			return 6;
		}else if(permission.equals("add new resource")) {
			return 7;
		}else if(permission.equals("remove resource")) {
			return 8;
		}else if(permission.equals("view resource")) {
			return 9;
		}else if(permission.equals("update resource")) {
			return 10;
		}
		else {
			return -1; 
		}
	}

	//	thePermissions.add( 7, "add new resource");
	//	thePermissions.add( 8, "remove resource");
	//	thePermissions.add( 9, "view resource"); 
	//	thePermissions.add( 10, "update resource");

	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Add Role Action");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private boolean validateInputFormat() {

		if (!roleIdField.getText().matches("\\d+")) { 
			showAlert("Role ID must be a number.");
			return false;
		}


		if (!startTimeFeild.getText().matches("\\d+")) { 
			showAlert("Start time must be a number.");
			return false;
		}


		if (!endTimeField.getText().matches("\\d+")) { 
			showAlert("End time must be a number.");
			return false;
		}

		return true;
	}

}
