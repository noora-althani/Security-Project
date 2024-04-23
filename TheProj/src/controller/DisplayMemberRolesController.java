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
import model.role;
import model.roleList;

public class DisplayMemberRolesController implements Initializable{
	public MemberList members;
	public roleList roles;
	public DataBase files;

	private Member member;

	public void setMember(Member member) {
		this.member = member;
	}

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
	private Button closeButton;

	@FXML
	private Label memberInfoLabel;

	@FXML
	void handleBackButton(ActionEvent event) {
		//go back to menu page
		try {
			//close current page
			((Window) closeButton.getScene().getWindow()).hide();

			//redirect to main page 
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DisplayMembersView.fxml"));     
			Parent root = (Parent)fxmlLoader.load(); 
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Display Members Page");
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

			//set member info label
			memberInfoLabel.setText("Roles Of Member: "+member.getName()+" (ID: "+member.getID()+")");
			
			//get roleIds from member
			ArrayList<Integer> roleIds = member.getRolesID();//of member
			ArrayList<role> rolesOfMember = new ArrayList<role>();

			for (Integer roleId : roleIds) {
				rolesOfMember.add(roles.searchSt2(roleId));//get role object of these ids
			}


			//now we have list of roles to display
			//displaying in table
			ObservableList<role> rolesList = FXCollections.observableArrayList(rolesOfMember);

			roleIdCol.setCellValueFactory(new PropertyValueFactory<role, Integer>("iD"));
			descriptionCol.setCellValueFactory(new PropertyValueFactory<role, String>("roleDescription"));
			permissionsCol.setCellValueFactory(new PropertyValueFactory<role, ArrayList<Integer>>("allPermissions"));
			startHourCol.setCellValueFactory(new PropertyValueFactory<role, Integer>("startingHour"));
			endHourCol.setCellValueFactory(new PropertyValueFactory<role, Integer>("endHour"));

			rolesTable.setItems(rolesList);

		});
	}
}
