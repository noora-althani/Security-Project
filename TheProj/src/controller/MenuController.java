package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.DataBase;
import model.Member;
import model.MemberList;
import model.files;
import model.filesList;
import model.role;
import model.roleList;
import model.test;
import model.zPermissions;

public class MenuController { 

	private static Member user;//must be static so that when we go to a page then return back the value does not become null
	private static role role;

	//we have two options (1) call new instance in each handling which will load the file [must save in the end of the handel in the next page]
	//(2) do everything with one static test then save (don't know if this will work)
	private test test;

	public roleList roles;
	public MemberList members;
	public filesList files;

	zPermissions p = new zPermissions();


	//use it when using the passing method in LoginController class
	public void setUser(Member user){
		this.user = user;
	}

	public void setUserRole(role currentRole) {
		this.role = currentRole;
	}




	@FXML
	private Button exitButton;


	@FXML
	void handleAddRole(ActionEvent event) throws IOException {
		test = new test();

		System.out.println("User is: ---------------> "+ user.getID());
		System.out.println("Role: -------------------> "+ role.getID());

		members = test.getMembersList();
		roles = test.getRoleList();


		//check permissions [got it from addRole function]
		if(roles.searchSt2(role.getID()).getAllPermissions().contains(1)) {
			showAlert("Your Role Has Permission To Access, "+user.getName()+" Is AUTHORIZED to Access");

			//check timing [got it from addRole function]
			if(p.checkTiming(role.getID(), roles) == true) {
				//AUTHORIZATION SUCCESS -> open another page 

				//get the stage
				Stage stage = (Stage) exitButton.getScene().getWindow();

				//close current page
				stage.hide();

				try {
					//load the FXML file using the same stage
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddRoleView.fxml"));
					Parent root = (Parent) fxmlLoader.load(); 

					//get the controller instance + pass info to other page
					AddRoleController controller = fxmlLoader.getController();
					controller.setUser(user);
					//controller.setRolesList(roles);
					controller.setUserRole(role);
					//controller.setMembersList(members);

					//set the new scene
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.setTitle("Add Role Page");
					stage.show();

				} catch (IOException e) {
					e.printStackTrace();
				}

				//(do the logic in new screen)
				//load
				//(1) enter role description
				//(2) enter role id
				//(3) choose permissions [multi select list]
				//(4) timing?
				//(5) create role object + insert in roles
				//save



			}else {
				showAlert("Sorry, your role cant be occupied at this time");
			}

		}else {
			showAlert("Your Role Does Not Have Permission To Access");
		}
	}

	@FXML
	void handleRemoveRole(ActionEvent event) throws IOException {

		test = new test();
		members = test.getMembersList();
		roles = test.getRoleList();

		if (roles.searchSt2(role.getID()).getAllPermissions().contains(2))  {

			if(p.checkTiming(role.getID(), roles) == true) {
				showAlert("Your Role Has Permission To Access, "+user.getName()+" Is AUTHORIZED to Access");
				//open new page
				Stage stage = (Stage) exitButton.getScene().getWindow();

				//close current page
				stage.hide();

				try {
					//load the FXML file using the same stage
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/RemoveRoleView.fxml"));
					Parent root = (Parent) fxmlLoader.load(); 

					//get the controller instance + pass info to other page
					RemoveRoleController controller = fxmlLoader.getController();

					//set the new scene
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.setTitle("Remove Role Page");
					stage.show();

				} catch (IOException e) {
					e.printStackTrace();
				}


			}else {
				showAlert("Sorry, your role cant be occupied at this time");
			}


		}else {
			showAlert("Your Role Does Not Have Permission To Access");
		}
	}

	@FXML
	void handleViewRolesList(ActionEvent event) throws IOException {
		//load roles list
		test = new test();
		roles = test.getRoleList();

		if (roles.searchSt2(role.getID()).getAllPermissions().contains(3))  {
			showAlert("Your Role Has Permission To Access, "+user.getName()+" Is AUTHORIZED to Access");

			if(p.checkTiming(role.getID(), roles) == true) {
				//open new page
				Stage stage = (Stage) exitButton.getScene().getWindow();

				//close current page
				stage.hide();

				try {
					//load the FXML file using the same stage
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DisplayRolesView.fxml"));
					Parent root = (Parent) fxmlLoader.load(); 

					//get the controller instance + pass info to other page
					DisplayRolesController controller = fxmlLoader.getController();

					//set the new scene
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.setTitle("Display Role Page");
					stage.show();

				} catch (IOException e) {
					e.printStackTrace();
				}


			}else {
				showAlert("Sorry, your role cant be occupied at this time");
			}


		}else {
			showAlert("Your Role Does Not Have Permission To Access");
		}
	}

	@FXML
	void handleAddMember(ActionEvent event) throws IOException {

		test = new test();

		roles = test.getRoleList();
		members = test.getMembersList();

		if (roles.searchSt2(role.getID()).getAllPermissions().contains(4))  {

			if(p.checkTiming(role.getID(), roles) == true) {
				showAlert("Your Role Has Permission To Access, "+user.getName()+" Is AUTHORIZED to Access");
				//authorized access
				//open new page
				Stage stage = (Stage) exitButton.getScene().getWindow();

				//close current page
				stage.hide();

				try {
					//load the FXML file using the same stage
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddMemberView.fxml"));
					Parent root = (Parent) fxmlLoader.load(); 

					//get the controller instance + pass info to other page
					AddMemberController controller = fxmlLoader.getController();
					//controller.setUser(user);
					//controller.setUserRole(role);

					//set the new scene
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.setTitle("Add Member Page");
					stage.show();

					//(add member page logic)
					//load in initialization -> files = new DataBase + files.load + load members and roles
					//(1) enter name [text feild]
					//(2) enter password [text field]
					//(3) enter id number -> make it auto generated [show in uneditable text field] -> getCounter()
					//(4) choose roles (from list of roles) [list view + set of input]
					//(5) create member object
					//save in handle add button -> files.save



				} catch (IOException e) {
					e.printStackTrace();
				}


			}else {
				showAlert("Sorry, your role cant be occupied at this time");
			}


		}else {
			showAlert("Your Role Does Not Have Permission To Access");
		}

	}

	@FXML
	void handleRemoveMember(ActionEvent event) throws IOException {

		test = new test();
		members = test.getMembersList();
		roles = test.getRoleList();

		if (roles.searchSt2(role.getID()).getAllPermissions().contains(5))  {

			if(p.checkTiming(role.getID(), roles) == true) {
				showAlert("Your Role Has Permission To Access, "+user.getName()+" Is AUTHORIZED to Access");
				//open new page
				Stage stage = (Stage) exitButton.getScene().getWindow();

				//close current page
				stage.hide();

				try {
					//load the FXML file using the same stage
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/RemoveMemberView.fxml"));
					Parent root = (Parent) fxmlLoader.load(); 

					//get the controller instance + pass info to other page
					RemoveMemberController controller = fxmlLoader.getController();

					//set the new scene
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.setTitle("Remove Member Page");
					stage.show();

				} catch (IOException e) {
					e.printStackTrace();
				}


			}else {
				showAlert("Sorry, your role cant be occupied at this time");
			}


		}else {
			showAlert("Your Role Does Not Have Permission To Access");
		}

	}

	@FXML
	void handleViewMembersList(ActionEvent event) throws IOException {
		test = new test();

		roles = test.getRoleList();
		members = test.getMembersList();

		if (roles.searchSt2(role.getID()).getAllPermissions().contains(6))  {

			if(p.checkTiming(role.getID(), roles) == true) {
				showAlert("Your Role Has Permission To Access, "+user.getName()+" Is AUTHORIZED to Access");
				//authorized access
				//open new page
				Stage stage = (Stage) exitButton.getScene().getWindow();

				//close current page
				stage.hide();

				try {
					//load the FXML file using the same stage
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DisplayMembersView.fxml"));
					Parent root = (Parent) fxmlLoader.load(); 

					//get the controller instance + pass info to other page
					DisplayMembersController controller = fxmlLoader.getController();

					//set the new scene
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.setTitle("Display Members Page");
					stage.show();

				} catch (IOException e) {
					e.printStackTrace();
				}


			}else {
				showAlert("Sorry, your role cant be occupied at this time");
			}


		}else {
			showAlert("Your Role Does Not Have Permission To Access");
		}
	}	



	@FXML
	void handleAddAResource(ActionEvent event) throws IOException {
		test = new test();

		roles = test.getRoleList();
		members = test.getMembersList();
		files = test.getFiles();

		if (roles.searchSt2(role.getID()).getAllPermissions().contains(7))  {   
			if ( p.checkTiming( role.getID() ,  roles) == true) {
				showAlert("Your Role Has Permission To Access, "+user.getName()+" Is AUTHORIZED to Access");

				//open page (add resource page)
				Stage stage = (Stage) exitButton.getScene().getWindow();

				//close current page
				stage.hide();

				try {
					//load the FXML file using the same stage
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CreateFileView.fxml"));
					Parent root = (Parent) fxmlLoader.load(); 

					//get the controller instance + pass info to other page
					CreateFileController controller = fxmlLoader.getController();
					//controller.setUser(user);
					//controller.setUserRole(role);

					//set the new scene
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.setTitle("Add Member Page");
					stage.show();



				} catch (IOException e) {
					e.printStackTrace();
				}


				//add logic
				//load
				//(1) create file object
				//(2) add to files list
				//save


			}else {
				showAlert("Sorry, your role cant be occupied at this time");
			}
		}else {
			showAlert("Your Role Does Not Have Permission To Access");
		}
	}

	@FXML
	void handleDeleteResource(ActionEvent event) throws IOException {
		test = new test();

		System.out.println("Here ");
		roles = test.getRoleList();
		members = test.getMembersList();
		files = test.getFiles();

		if (roles.searchSt2(role.getID()).getAllPermissions().contains(8))  {   
			if ( p.checkTiming( role.getID() ,  roles) == true) {
				showAlert("Your Role Has Permission To Access, "+user.getName()+" Is AUTHORIZED to Access");

				//open page (add resource page)
				Stage stage = (Stage) exitButton.getScene().getWindow();

				//close current page
				stage.hide();

				try {
					//load the FXML file using the same stage
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DeleteFileView.fxml"));
					Parent root = (Parent) fxmlLoader.load(); 

					//get the controller instance + pass info to other page
					DeleteFileController controller = fxmlLoader.getController();
					controller.setUesrRole(role);

					//set the new scene
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.setTitle("Delete File Page");
					stage.show();



				} catch (IOException e) {
					e.printStackTrace();
				}


				//add logic
				//load
				//(1) create file object
				//(2) add to files list
				//save


			}else {
				showAlert("Sorry, your role cant be occupied at this time");
			}
		}else {
			showAlert("Your Role Does Not Have Permission To Access");
		}
	}

	@FXML
	void handleViewResources(ActionEvent event) throws IOException{
		test = new test();

		roles = test.getRoleList();
		members = test.getMembersList();
		files = test.getFiles();

		ArrayList<Integer> rolePermissions = role.getAllPermissions();

		if (rolePermissions.contains(9))  { //can view
			System.out.println("you have permission of reading, let me check for the file you want to use");
			if ( p.checkTiming( role.getID() ,  roles) == true) {
				showAlert("Your Role Has Permission To Access, "+user.getName()+" Is AUTHORIZED to Access");

				//open other page.
				Stage stage = (Stage) exitButton.getScene().getWindow();

				//close current page
				stage.hide();

				try {
					//load the FXML file using the same stage
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DisplayFilesView.fxml"));
					Parent root = (Parent) fxmlLoader.load(); 

					//get the controller instance + pass info to other page
					DisplayFilesController controller = fxmlLoader.getController();
					controller.setUesrRole(role);

					//set the new scene
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.setTitle("View Permissible Resources Page");
					stage.show();



				} catch (IOException e) {
					e.printStackTrace();
				}

				//view files logic:
				//load 
				//(1) check files that role can view
				//(2) display them in a table
				//back button

			}else {
				showAlert("Sorry, your role cant be occupied at this time");
			}
		}else {
			showAlert("Your Role Does Not Have Permission To Access");
		}

	}


	@FXML
	void handleUpdateResource(ActionEvent event) throws IOException {
		test = new test();

		roles = test.getRoleList();
		members = test.getMembersList();
		files = test.getFiles();

		//		ArrayList<files> resourcesArray = new ArrayList<files>();
		//		ArrayList<Integer> filesIds = files.getFilesIds();
		//		
		//		for (Integer id : filesIds) {
		//			files fileObj = files.searchSt2(id);
		//			resourcesArray.add(fileObj);
		//		}
		//		HashSet<files> uniqueResourcesSet = new HashSet<>(resourcesArray);
		//		ArrayList<files> uniqueResources = new ArrayList<>(uniqueResourcesSet); 
		//


		ArrayList<Integer> rolePermissions = role.getAllPermissions();


		if (rolePermissions.contains(10))  { //can view
			System.out.println("you have permission of reading, let me check for the file you want to use");
			if ( p.checkTiming( role.getID() ,  roles) == true) {
				showAlert("Your Role Has Permission To Access, "+user.getName()+" Is AUTHORIZED to Access");

				//open other page.
				Stage stage = (Stage) exitButton.getScene().getWindow();

				//close current page
				stage.hide();

				try {
					//load the FXML file using the same stage
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UpdateFileView.fxml"));
					Parent root = (Parent) fxmlLoader.load(); 

					//get the controller instance + pass info to other page
					UpdateFileController controller = fxmlLoader.getController();
					controller.setUesrRole(role);

					//set the new scene
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.setTitle("Update Resource Page");
					stage.show();



				} catch (IOException e) {
					e.printStackTrace();
				}

				//view files logic:
				//load 
				//(1) check files that role can view
				//(2) display them in a table
				//back button

			}else {
				showAlert("Sorry, your role cant be occupied at this time");
			}
		}else {
			showAlert("Your Role Does Not Have Permission To Access");
		}
	}


	@FXML
	public void handleExit(ActionEvent event) throws IOException {
		test = new test();
		
		roles = test.getRoleList();
		members = test.getMembersList();
		files = test.getFiles();
		
		System.out.println("FILES ARE ---------------");
		files.display();
		
				//close current page
				((Window) exitButton.getScene().getWindow()).hide();
		
				try {
					//display login page
					Stage stage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/view/LoginView.fxml"));
					stage.setScene(new Scene(root));
					stage.setTitle("Login Page");
					stage.show();
		
					//logout alert
					showAlert("You Are Logged Out Successfully");
		
				} catch (IOException e) {
					e.printStackTrace(); 
				}
	}







	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Menu Action");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}



