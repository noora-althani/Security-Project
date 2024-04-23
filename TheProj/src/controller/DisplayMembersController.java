package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.glass.events.MouseEvent;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class DisplayMembersController implements Initializable{
	public MemberList members;
	public roleList roles;
	public DataBase files;

	@FXML
	private TableView<Member> membersTable;

	@FXML
	private TableColumn<Member, Integer> memberIdCol;

	@FXML
	private TableColumn<Member, String> nameCol;

	@FXML
	private TableColumn<Member, String> passwordCol;

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

			//get method to get list of roles object		//QUESTION: HOW TO TRAVERSE CHAININGTABLE
			//get objects of chaining thing list to an array list for easy use -> so that we can deal with objects	
			//[Did it]
			//(1) create array in rolesList class + get method
			//(2) whenever inserting add id no. in list
			//(3) retrive list here
			//(4) for each this array -> inside ( role roleObj = roles.searchstr(id) )
			ArrayList<Integer> membersIds = new ArrayList<Integer>();
			ArrayList<Member> arrayMembersList = new ArrayList<Member>();
			membersIds = members.getMembersIdsList();
			for (Integer id : membersIds) {//traversing roles list (getting arraylist of type roles)
				Member memObj = members.searchSt2(id);
				arrayMembersList.add(memObj);
			}


			//displaying in table
			ObservableList<Member> membersList = FXCollections.observableArrayList(arrayMembersList);
			//(ArrayList<Integer> rolesID, int iD, String name, String pass)
			memberIdCol.setCellValueFactory(new PropertyValueFactory<Member, Integer>("iD"));
			nameCol.setCellValueFactory(new PropertyValueFactory<Member, String>("name"));
			passwordCol.setCellValueFactory(new PropertyValueFactory<Member, String>("pass"));

			membersTable.setItems(membersList);
			
			 //add event handler for mouse clicks
	        membersTable.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
	            if (event.getButton().equals(javafx.scene.input.MouseButton.PRIMARY)) {
	                int index = membersTable.getSelectionModel().getSelectedIndex();
	                Member member = membersTable.getItems().get(index);
	               
	                System.out.println(member);
	                
	                //open another page showing 
	                Stage stage = (Stage) backButton.getScene().getWindow();

	                try {
	                    // Load the FXML file using the same stage
	                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DisplayMemberRolesView.fxml"));
	                    Parent root = (Parent) fxmlLoader.load(); 

	                    // Get the controller instance + pass info to the other page
	                    DisplayMemberRolesController controller = fxmlLoader.getController();
	                    controller.setMember(member);
	                    
	                    // Set the new scene
	                    Scene scene = new Scene(root);
	                    stage.setScene(scene);
	                    stage.setTitle("Display Role Page");
	                    stage.show();

	                } catch (IOException e) {
	                    e.printStackTrace();
	                }

	            }
	        });
			

		});
	}

}
