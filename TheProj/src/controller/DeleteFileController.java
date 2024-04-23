package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class DeleteFileController implements Initializable {

	private Member user;
	private static role role;

	private MemberList members;
	private roleList roles;
	private filesList resources;

	zPermissions p = new zPermissions();

	DataBase db;

	static files file;

	ArrayList<Integer> filesIds;

	public void setUesrRole(role role) {
		this.role = role;
	}


	ArrayList<files> resourcesArray;

	@FXML
	private TableView<files> resourcesTable;

	@FXML
	private TableColumn<files, Integer> fileIdCol;

	@FXML
	private TableColumn<files, String> fileNameCol;

	@FXML
	private TableColumn<files, String> fileConetentCol;

	@FXML
	private Button backButton;

	@FXML
	private Label headerLabel;

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

			System.out.println("resources from db");
			resources.display();

			//set label
			headerLabel.setText("Files That "+role.getRoleDescription()+" Role Can Delete:");

			resourcesArray = new ArrayList<files>();
			//get files that this persons role can see
			ArrayList<Integer> rolePermissions = role.getAllPermissions();//get permissions of that role
			System.out.println("ROLES OF THAT PERMISSION ARE:");
			for (Integer i : rolePermissions) {
				System.out.println(i);
			}


			for (int id : rolePermissions) { 

				if (id>10) { //an id greater than 10 is a file resource. 
					files fileObjet = resources.searchSt2(id);
					resourcesArray.add(fileObjet);
				} 
			}
			
			HashSet<files> uniqueResourcesSet = new HashSet<>(resourcesArray);
			ArrayList<files> uniqueResources = new ArrayList<>(uniqueResourcesSet); 
			
			resourcesArray = uniqueResources;
			
			ArrayList<files> nonNullFiles = new ArrayList<files>();
			for (files file : resourcesArray) {
				if(file != null) {
					nonNullFiles.add(file);
				}
			}

			//displaying in table 
			ObservableList<files> filesList = FXCollections.observableArrayList(nonNullFiles);

			
			fileIdCol.setCellValueFactory(new PropertyValueFactory<files, Integer>("iD"));
			fileNameCol.setCellValueFactory(new PropertyValueFactory<files, String>("fileName"));
			fileConetentCol.setCellValueFactory(new PropertyValueFactory<files, String>("fileDescription"));

			resourcesTable.setItems(filesList);


			// Add event handler for mouse clicks
			resourcesTable.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
				if (event.getButton().equals(javafx.scene.input.MouseButton.PRIMARY)) {
					int index = resourcesTable.getSelectionModel().getSelectedIndex();
					files file = resourcesTable.getItems().get(index);

					System.out.println(file);

					//delete item and show confirmation + update observable list
					//show confirmation (true->remove role)
					if(file != null) {
						if(showConfirmationAlert("Are you sure you want to remove this File permenantly: "+file.getFileName())) {
							removeMember(file);
							db.save();
							//remove from observable list
							filesList.remove(file);
						}else {
							showAlert("Process Canceled");
						}
					}


				}
			});

		});
	}

	public void removeMember(files f) {
		filesIds = resources.getFilesIds(); 
		System.out.println("IDS OF FILES");
		for (Integer i : filesIds) {
			System.out.println(i);
		}
		System.out.println("Resources files");
		for (files file : resourcesArray) {
			System.out.println(file);
		}

		resourcesArray.remove(f);


		//remove from roles chaining by tree
		resources.deleteSt2(f.getID());
		//remove from rolesId array list
		filesIds.removeIf(id -> id == f.getID());
		resources.setFilesIdsList(filesIds);
		//confirmations
		System.out.println("File Deleted Successfully");
		showAlert("File ["+ f.getFileName()+", ID: "+f.getID()+"] Has been Deleted Succsesfully");
	}

	//----------------------------------

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
