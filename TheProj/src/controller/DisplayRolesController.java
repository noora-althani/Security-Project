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
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.DataBase;
import model.MemberList;
import model.role;
import model.roleList;

public class DisplayRolesController implements Initializable{
	public MemberList members;
	public roleList roles;
	public DataBase files;
	
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

			ArrayList<Integer> rolesIds = this.roles.getRoleIds();
			ArrayList<role> arrayRolesList = new ArrayList<role>();
			
			for (Integer id : rolesIds) {//a way of traversing in roles list (inorder to getting arraylist of type roles)
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
			
		});
	}

}
