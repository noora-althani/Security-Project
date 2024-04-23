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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class DisplayFilesController implements Initializable{
	private Member user;
	private role role;

	private MemberList members;
	private roleList roles;
	private filesList resources;

	zPermissions p = new zPermissions();

	DataBase db;

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
			
			System.out.println("RESOURCES DISPLAYED HERE FROM FILES ---------------");
			resources.display();

			//set label
			headerLabel.setText("Files That "+role.getRoleDescription()+" Role Can View");

//			//get all files 
//			ArrayList<Integer> filesIds = new ArrayList<Integer>();
//			filesIds = resources.getFilesIds();
			resourcesArray = new ArrayList<files>();
//
//			for (Integer id : filesIds) {
//				files fileObj = resources.searchSt2(id);
//				resourcesArray.add(fileObj);
//			}

			//get files that this persons role can see
			ArrayList<Integer> rolePermissions = role.getAllPermissions();//get permissions of that role
			System.out.println("ROLES OF THAT PERMISSION ARE:");
			for (Integer i : rolePermissions) {
				System.out.println(i);
			}
			
			
			
			for (int id : rolePermissions) { 

				if (id>10) { //an id greater than 10 is a file resource. 
					System.out.println( resources.searchSt2(id) ); 
					files fileObjet = resources.searchSt2(id);
					resourcesArray.add(fileObjet);
				} 
			}
			
			Set<files> uniqueResources = new HashSet<>(resourcesArray);
			ArrayList<files> resourcessArray = new ArrayList<>(uniqueResources);
			


			ArrayList<files> nonNullFiles = new ArrayList<files>();
			for (files files : resourcessArray) {
				if(files != null) {
					nonNullFiles.add(files);
				}
			}
			resourcessArray = nonNullFiles;

			//displaying in table //(int iD, String fileName, String fileDescription)
			ObservableList<files> filesList = FXCollections.observableArrayList(resourcessArray);

			fileIdCol.setCellValueFactory(new PropertyValueFactory<files, Integer>("iD"));
			fileNameCol.setCellValueFactory(new PropertyValueFactory<files, String>("fileName"));
			fileConetentCol.setCellValueFactory(new PropertyValueFactory<files, String>("fileDescription"));

			resourcesTable.setItems(filesList);

		});
	}

}
