package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

public class test implements Serializable  
{ 
	private MemberList Members ; 
	private roleList roles ; 
	private filesList  filess ;   /// -----------------------------Changed 
	public DataBase fileSystem ;   
	private ArrayList<Integer>  allPermissions ; 
	
	public MemberList getMembersList() {//new
		return Members;
	} 
	public roleList getRoleList() {//new
		return roles;
	}
	public filesList getFiles() {//NEW
		return filess;
	}
	public DataBase getDatabase() {//new
		return fileSystem;
	}
	

	public test () throws IOException
	{       	 
			if (Members==null) Members = new MemberList  () ;  
			if (roles==null) roles = new roleList  () ;   
			if (filess==null) filess = new filesList  () ;   /// -----------------------------Changed 
	 
			fileSystem = new DataBase(Members, roles, filess );  /// -----------------------------Changed [added filess]
			fileSystem.load() ; 
			Members = fileSystem.getAllMembers(); 
			roles = fileSystem.getAllRoles() ;    
			filess = fileSystem.getAllResources(); /// -----------------------------Changed
	 
			ArrayList<Integer >  PermissionsOfAdmin = new ArrayList < Integer>() ; 
			PermissionsOfAdmin.add(1); 
			PermissionsOfAdmin.add(2); 
			PermissionsOfAdmin.add(3); 
			PermissionsOfAdmin.add(4); 
			PermissionsOfAdmin.add(5); 
			PermissionsOfAdmin.add(6); 
			PermissionsOfAdmin.add(7); 
			PermissionsOfAdmin.add(8); 
			PermissionsOfAdmin.add(9); 
			PermissionsOfAdmin.add(10); 
			role Adminrole = new role ( 1 , "Admin", PermissionsOfAdmin ,0,0 ) ;  
			roles.insertSt2(Adminrole); 
			ArrayList<Integer >  TheRoles=   new ArrayList < Integer>(); 
			TheRoles.add(1 ) ; 
			Member Ahmed= new Member(TheRoles , 1, "Ahmed" , "a" ) ; 
			Members.insertSt2(Ahmed);
			Members.display(); roles.display(); 
			//
			FileOutputStream toTheFile1 = new FileOutputStream("allMemberss.json");
			ObjectOutputStream theObject1 = new ObjectOutputStream(toTheFile1);
			theObject1.writeObject( Members);
			theObject1.close();
			toTheFile1.close();
			//
			FileOutputStream toTheFile2 = new FileOutputStream("allRoless.json"); 
			ObjectOutputStream theObject2 = new ObjectOutputStream(toTheFile2);
			theObject2.writeObject(roles);
			theObject2.close();
			toTheFile2.close(); 
			roles.display(); Members.display(); filess.display();
	}  
	public void logIn( int id , String password , int role) throws IOException 
	{ 
				Scanner userChoice = new Scanner (System.in);      
				System.out.println(" " // -----------------------------Changed [the menu options ]
						+ "1, Add role \r\n"
						+ "2, remove role\r\n"
						+ "3, view roles list\r\n"
						+ "4, Add member\r\n"
						+ "5, remove member\r\n"
						+ "6, view members list\r\n"
						+ "7, Add a new resource\r\n"
						+ "8, delete a resource \r\n"		
						+ "9, to view a resource \r\n"	
						+ "10, to update a resource \r\n"	
						+ "11, To exit");
				int choice =  userChoice.nextInt() ; 
				zPermissions sf = new zPermissions();   
				if ( choice ==1) {   sf.addRole(role, roles, filess ); fileSystem.save();  	}/// -----------------------------Changed [added filess]
				else if ( choice ==2) {  sf.removeRole( role,  roles  ); fileSystem.save();   } 
				else if ( choice ==3) {  sf.viewRolesList(role, roles); } 
				else if ( choice ==4) {   sf.addMember(role, Members, roles); fileSystem.save();  	}
				else if ( choice ==5) {  sf.removeMember(role, Members, roles); fileSystem.save();   } 
				else if ( choice ==6) { 	sf.viewMembersList(role, Members, roles); } 
				else if ( choice ==7) { 	sf.addResource(role , roles, filess);  fileSystem.save();}  
				else if ( choice ==8) { 	sf.deleteResource(role , roles, filess) ;  } /// -----------------------------Changed
	     		else if ( choice ==9) { 	sf.viewResource(role, roles, filess); } /// -----------------------------Changed
				else if ( choice ==10) { 	sf.writeToResource(role , roles, filess); fileSystem.save(); }/// -----------------------------Changed
				else if ( choice ==11) { 
					fileSystem.save();  try { menu() ;} catch (IOException e) {  e.printStackTrace(); }
									  }  
				logIn ( id , password , role); 
	}


	public void permissions(int id , String password , int roleId, int choice) { //new
		Scanner userChoice = new Scanner(System.in);      
		System.out.println(" "
				+ "1, Add role \r\n"
				+ "2, remove role\r\n"
				+ "3, view roles list\r\n"
				+ "4, Add member\r\n"
				+ "5, remove member\r\n"
				+ "6 view members list\r\n"
				+ "7 To exit");
		//int choice =  userChoice.nextInt(); 

		zPermissions sf = new zPermissions();    
		if ( choice == 1) {
			//sf.addRole(roleId, roles);
			fileSystem.save();  	
		}
		else if ( choice ==2) {  sf.removeRole( roleId,  roles  ); fileSystem.save();   } 
		else if ( choice ==3) {  sf.viewRolesList(roleId, roles); } 
		else if ( choice ==4) {   sf.addMember(roleId, Members, roles); fileSystem.save();  	}
		else if ( choice ==5) {  sf.removeMember(roleId, Members, roles); fileSystem.save();   } 
		else if ( choice ==6) { 	sf.viewMembersList(roleId, Members, roles); } 
		else if ( choice ==7) { 
			fileSystem.save();  try { menu() ;} catch (IOException e) {  e.printStackTrace(); }
		}  
		//logIn ( id , password , roleId); 
	}

	public Member Authenticate(int Id, String password) {//new
		Member member = Members.searchSt2(Id);
		
		if(Members.searchSt2(Id) == null) {
			return null;
		}
		
		if (member.getPass().equals(password)) {
			System.out.println("Authentication Success");
			return member;
		}else {
			System.out.println("Authentication Failed");
			return null;
		}
	}

	public ArrayList<role> getRolesByUserId(int id) {//new 
		ArrayList<Integer> rolesIDs = Members.searchSt2(id).getRolesID(); 
		ArrayList<role> roles = new ArrayList<role>(); 

		for (Integer roleId : rolesIDs) {
			role roleObject = this.roles.searchSt2(roleId);
			if (roleObject != null) {
				roles.add(roleObject);
			}
		}
		return roles;
	}


	public void menu() throws IOException {   
		// To intialize Here
		Scanner thuserChoice = new Scanner (System.in);    
		Scanner strScanner1 = new Scanner (System.in); 
		System.out.println("To login Press 1 "
				+ "To Exit 2"); 
		int choicee = thuserChoice.nextInt() ;    
		if ( choicee ==1) 
		{ 			 
			System.out.println("Enter Your ID");
			int id ; id = thuserChoice.nextInt() ; 

			Member theMember = Members.searchSt2(id);   

			while ( theMember== null) { 
				System.out.println("ID is not valid, enter a valid ID");
				id = thuserChoice.nextInt() ;

				theMember = Members.searchSt2(id);  

			}

			System.out.println("Enter your password ");
			String Pass= strScanner1.nextLine();   

			if (theMember.getPass().equals(Pass) ) 
			{   
				System.out.println("Your roles are: ");
				ArrayList<Integer> rolesIDs  =  Members.searchSt2(id).getRolesID(); 
				int size = rolesIDs.size();  
				int roleID; 


				while(size!=0) {  
					roleID = rolesIDs.get((--size));  
					System.out.println(roles.searchSt2(roleID).toString());	 
				} 
				System.out.println("What role you want to access "); 

				int theRole; theRole=  thuserChoice.nextInt() ;   
				if (Members.searchSt2(id).getRolesID().contains(theRole) == false)  	{
					System.out.println( "Role not found ");   
					while ( Members.searchSt2(id).getRolesID().contains(theRole) == false)  {
						System.out.println(" Enter a role Identifier from your roles");  
						theRole = thuserChoice.nextInt();   
					}

				}
				logIn (choicee , Pass , theRole );   
			} 
			else System.out.println(" Password didn't match ID"); 
		} 

		else if ( choicee ==2) 
		{ 
			fileSystem.save();    
			System.out.println( "EXIT"); 
			return;
		}
		menu() ;   
	}

	public static void main(String[] args) throws IOException  
	{   
		test myTest = new test ();
		myTest.menu();   
	}
}


//////////// ///////////////////////////////////////// Intizlize at first run only
//ArrayList<Integer >  PermissionsOfAdmin = new ArrayList < Integer>() ; 
//PermissionsOfAdmin.add(1); 
//PermissionsOfAdmin.add(2); 
//PermissionsOfAdmin.add(3); 
//PermissionsOfAdmin.add(4); 
//PermissionsOfAdmin.add(5); 
//PermissionsOfAdmin.add(6); 
//role Adminrole = new role ( 1 , "Admin", PermissionsOfAdmin ,0,0 ) ;  
//roles.insertSt2(Adminrole); 
//ArrayList<Integer >  TheRoles=   new ArrayList < Integer>(); 
//TheRoles.add(1 ) ; 
//Member Ahmed= new Member(TheRoles , 1, "The Admin Ahmed" , "a" ) ; 
//Members.insertSt2(Ahmed);
//Members.display(); roles.display(); 
////
//FileOutputStream toTheFile1 = new FileOutputStream("allMemberss.json");
//ObjectOutputStream theObject1 = new ObjectOutputStream(toTheFile1);
//theObject1.writeObject( Members);
//theObject1.close();
//toTheFile1.close();
////
//FileOutputStream toTheFile2 = new FileOutputStream("allRoless.json"); 
//ObjectOutputStream theObject2 = new ObjectOutputStream(toTheFile2);
//theObject2.writeObject(roles);
//theObject2.close();
//toTheFile2.close(); 
///////////// ///////////////////////////////////////////////////////////////////////////////
