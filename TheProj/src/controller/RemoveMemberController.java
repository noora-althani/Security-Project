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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.DataBase;
import model.Member;
import model.MemberList;
import model.role;
import model.roleList;

public class RemoveMemberController implements Initializable{
	public MemberList members;
	public roleList roles;
	public DataBase files;

	ArrayList<Integer> membersIds;

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

			//get members as array list -> to traverse
			membersIds = members.getMembersIdsList();
			ArrayList<Member> arrayMembersList = new ArrayList<Member>();

			for (Integer id : membersIds) {//traversing roles list (getting arraylist of type roles)
				Member memberObj = members.searchSt2(id);
				arrayMembersList.add(memberObj);
			}


			//displaying in table
			ObservableList<Member> membersList = FXCollections.observableArrayList(arrayMembersList);

			memberIdCol.setCellValueFactory(new PropertyValueFactory<Member, Integer>("iD"));
			nameCol.setCellValueFactory(new PropertyValueFactory<Member, String>("name"));
			passwordCol.setCellValueFactory(new PropertyValueFactory<Member, String>("pass"));

			membersTable.setItems(membersList);

			//add event handler for mouse clicks
			membersTable.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
				if (event.getButton().equals(javafx.scene.input.MouseButton.PRIMARY)) {
					int index = membersTable.getSelectionModel().getSelectedIndex();
					Member m = membersTable.getItems().get(index);

					System.out.println(m.getID());

					//show confirmation (true->remove role)
					if(showConfirmationAlert("Are you sure you want to remove this Member permenantly: "+m.getName())) {
						removeMember(m);
						files.save();
						//remove from observable list
						membersList.remove(m);
					}else {
						showAlert("Process Canceled");
					}



				}
			});

		});

	}

	public void removeMember(Member m) {
		//remove from roles chaining by tree
		members.deleteSt2(m.getID());
		//remove from rolesId array list
		membersIds.removeIf(id -> id == m.getID());
		members.setMembersIdsList(membersIds);
		//confirmations
		System.out.println("Member Deleted Successfully");
		showAlert("Member ["+ m.getName()+", ID: "+m.getID()+"] Has been Deleted Succsesfully");
	}

	//CONFIRMATION ALERT FUNCTIONS
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
