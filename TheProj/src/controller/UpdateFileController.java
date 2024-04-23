package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
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

public class UpdateFileController implements Initializable{
	private Member user;
	private static role role;

	private MemberList members;
	private roleList roles;
	private filesList resources;

	zPermissions p = new zPermissions();

	DataBase db;

	static files file;

	public void setUesrRole(role role) {
		this.role = role;
	}

	//	public void setFile(files file) {
	//		this.file = file;
	//	}

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

			//set label
			headerLabel.setText("Files That "+role.getRoleDescription()+" Role Can Update:");

			resourcesArray = new ArrayList<files>();
			//get files that this persons role can see
			ArrayList<Integer> rolePermissions = role.getAllPermissions();//get permissions of that role
			System.out.println("ROLES OF THAT PERMISSION ARE----------------:");
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
			for (files f : resourcesArray) {
				System.out.println(f);
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
			resourcesArray = nonNullFiles;
			
			//displaying in table //(int iD, String fileName, String fileDescription)
			ObservableList<files> filesList = FXCollections.observableArrayList(resourcesArray);

			fileIdCol.setCellValueFactory(new PropertyValueFactory<files, Integer>("iD"));
			fileNameCol.setCellValueFactory(new PropertyValueFactory<files, String>("fileName"));
			fileConetentCol.setCellValueFactory(new PropertyValueFactory<files, String>("fileDescription"));

			resourcesTable.setItems(filesList);

			//when i press on one of the files another page should open and set the file object
			//logic:
			//open page and pass (files object)
			//(1) display files object description in text area with initialize
			//(2) after editing press update + load all fileslist or set them
			//(3) save its content to fileslist -> resources.delete(file.id) then resources.insert(new file(file.id, file.descriptoin, file.name)
			//or: file.setFiledescription(). save() -> check if its updated
			//return button

			// Add event handler for mouse clicks
			resourcesTable.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
				if (event.getButton().equals(javafx.scene.input.MouseButton.PRIMARY)) {
					int index = resourcesTable.getSelectionModel().getSelectedIndex();
					files file = resourcesTable.getItems().get(index);

					System.out.println(file);

					if(file != null) {
						//open another page showing 
						Stage stage = (Stage) backButton.getScene().getWindow();

						try {
							// Load the FXML file using the same stage
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FileContentsView.fxml"));
							Parent root = (Parent) fxmlLoader.load(); 

							//get the controller instance + pass info to the other page
							FileContentController controller = fxmlLoader.getController();
							controller.setFileObj(file);
							controller.setRole(role);

							//set the new scene
							Scene scene = new Scene(root);
							stage.setScene(scene);
							stage.setTitle("File Contents Page");
							stage.show();

						} catch (IOException e) {
							e.printStackTrace();
						}
					}



				}
			});

		});
	}

}
