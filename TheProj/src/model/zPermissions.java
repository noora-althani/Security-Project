package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

public class zPermissions implements Serializable { 
	ArrayList<String>  thePermissions = new  ArrayList<String > ();  

	public ArrayList<String> getPermissions(){//new
		return thePermissions;
	}
	//    Permission table 
	//1, Add role 
	//2, remove role
	//3, view roles list
	//4, Add member
	//5, remove member
	//6 view members list

	public  void addRole(int USerRoleID , roleList theRoles, filesList thefiles) {//p1 /// -----------------------------Changed [added  filesList thefiles]
		Permissiontable() ;

		if (theRoles.searchSt2(USerRoleID).getAllPermissions().contains(1))  { 
			System.out.println("You have the Permission");

			if ( checkTiming( USerRoleID ,  theRoles) == true) {

				Scanner intScanner = new Scanner (System.in);   
				Scanner stringScanner = new Scanner (System.in);   
				System.out.println("You are on time");


				String theRoleDescription;  			
				int theID;  
				ArrayList<Integer > theallPermissions = new  ArrayList<Integer >  () ;
				int thestartingHour;
				int theendHour; 
				int permissionsss=0;

				System.out.println("Enter The role Description"); theRoleDescription= stringScanner.nextLine(); 
				System.out.println("Enter The role Identifier"); theID = intScanner.nextInt(); 
				System.out.println("what permission you want to add? press -1 to stop adding");  
				int index= 0; 
				for(String s : thePermissions)  System.out.println((index++)+": "+s);
				thefiles.display();/// -----------------------------Changed

				while (permissionsss!=-1 ) { 
					permissionsss = intScanner.nextInt(); 
					if (permissionsss==-1 ) System.out.println("Permissions added Succsufully");  
					else theallPermissions.add(permissionsss);  }   

				System.out.println("Notice that Hours starting from 0 (12 AM ) to 23 (11 PM) "
						+ "Enter The starting hour, 0 if didnt want to check"); thestartingHour = intScanner.nextInt(); 
						System.out.println("Enter The end hour , 0 if didnt want to check"); theendHour = intScanner.nextInt(); 

						theRoles.insertSt2(new role( theID, theRoleDescription, theallPermissions, thestartingHour, theendHour )); 
			}
			else System.out.println("Sorry, your role cant be occupied at this time");
		}
		else System.out.println("Permission not valid");  
	}

	public  void removeRole(int USerRoleID , roleList theRoles) { //p2

		if (theRoles.searchSt2(USerRoleID).getAllPermissions().contains(2))  { 
			System.out.println("You have the Permission");
			if ( checkTiming( USerRoleID ,  theRoles) == true) { 
				Scanner intScanner = new Scanner (System.in); 
				theRoles.display();
				int theRoleID; 
				System.out.println("Enter ID of role you want to remove");
				theRoleID= intScanner.nextInt() ; 
				theRoles.deleteSt2(theRoleID); 

				ArrayList<Integer> rolesIDArrayList = theRoles.getRoleIds();//new
				for (Integer id : rolesIDArrayList) {
					if(id == USerRoleID) {
						//remove it from list
						rolesIDArrayList.remove(id);
					}
				}theRoles.setRolesIdsList(rolesIDArrayList);

			}
			else System.out.println("Sorry, your role cant be occupied at this time");
		}
		else System.out.println("Permission not valid");  
	}

	public  void viewRolesList(int USerRoleID , roleList theRoles) { //p3
		if (theRoles.searchSt2(USerRoleID).getAllPermissions().contains(3))  {  

			System.out.println("You have the Permission");

			if ( checkTiming( USerRoleID ,  theRoles) == true) {////////////// 

				System.out.println("You are on time");

				theRoles.display();

			}else System.out.println("Sorry, your role cant be occupied at this time");
		}else System.out.println("Permission not valid");  
	}

	public  void addMember(int USerRoleID ,MemberList theMembers,  roleList theRoles) {//p4
		if (theRoles.searchSt2(USerRoleID).getAllPermissions().contains(4))  { 
			System.out.println("You have the Permission");
			if ( checkTiming( USerRoleID ,  theRoles) == true) {
				Scanner intScanner = new Scanner (System.in);   
				Scanner stringScanner = new Scanner (System.in);   
				System.out.println("You are on time");

				ArrayList<Integer>  theRolesID = new ArrayList<Integer> (); 
				int theID ; 
				String theName ;   
				String thePass ; 
				int rolesToAdd= 0 ;
				System.out.println("Enter The member name"); theName= stringScanner.nextLine(); 
				System.out.println("Enter password"); thePass= stringScanner.nextLine(); 
				System.out.println("Enter member ID"); theID = intScanner.nextInt(); 
				System.out.println("what roles you want to add? press -1 to stop adding");  
				theRoles.display(); 
				while (rolesToAdd!=-1 ) { 
					rolesToAdd = intScanner.nextInt(); 
					if (rolesToAdd==-1 ) System.out.println("Roles added Succsufully");  
					else theRolesID.add(rolesToAdd);  
				}    
				theMembers.insertSt2(new Member(theRolesID, theID, theName, thePass)); 
			}
			else System.out.println("Sorry, your role cant be occupied at this time");
		}
		else System.out.println("Permission not valid");  
	}

	public  void removeMember(int USerRoleID ,MemberList theMembers, roleList theRoles) { //p5
		if (theRoles.searchSt2(USerRoleID).getAllPermissions().contains(5))  { 
			System.out.println("You have the Permission");
			if ( checkTiming( USerRoleID ,  theRoles) == true) { 
				Scanner intScanner = new Scanner (System.in); 
				int theMembereID; 
				System.out.println("Enter ID of Member you want to remove");
				theMembereID= intScanner.nextInt() ; 
				theMembers.deleteSt2(theMembereID); 

				ArrayList<Integer> membersIDArrayList = theMembers.getMembersIdsList();//new
				for (Integer id : membersIDArrayList) {
					if(theMembereID == id) {
						membersIDArrayList.remove(id);
					}
				}theMembers.setMembersIdsList(membersIDArrayList);

			}
			else System.out.println("Sorry, your role cant be occupied at this time");
		}
		else System.out.println("Permission not valid");  
	}

	public  void viewMembersList(int USerRoleID ,MemberList theMembers, roleList theRoles) { //p6
		if (theRoles.searchSt2(USerRoleID).getAllPermissions().contains(6))  {  ///
			System.out.println("You have the Permission");
			if ( checkTiming( USerRoleID ,  theRoles) == true) {////////////// 
				System.out.println("You are on time");
				theMembers.display();
			}
			else System.out.println("Sorry, your role cant be occupied at this time");
		}
		else System.out.println("Permission not valid");  
	}


	public  boolean checkTiming(int USerRoleID , roleList theRoles) {//pr1  
		//Check time for user 
		int theStart= theRoles.searchSt2(USerRoleID).getStartingHour(); 
		int theEnd= theRoles.searchSt2(USerRoleID).getEndHour() ;

		if (theStart ==0 && theEnd==0  ) return true ; 
		else {
			SimpleDateFormat TheHour = new SimpleDateFormat ("HH");
			Date date = new Date() ;  
			String hourTime = TheHour.format(date );   
			int UserCurrentTime = Integer.parseInt(hourTime) ; 
			if (theStart <= UserCurrentTime && UserCurrentTime < theEnd ) return true; }
		return false ; 
	}

	//-----------------------------------------------------------------------
	public  void addResource(int USerRoleID , roleList theRoles, filesList theResources)  { //p7  /// -----------------------------Changed [new function]
		if (theRoles.searchSt2(USerRoleID).getAllPermissions().contains(7))  {   

			System.out.println("You have the Permission");

			if ( checkTiming( USerRoleID ,  theRoles) == true) {////////////// 

				System.out.println("You are on time");

				//TODO
				Scanner intScanner = new Scanner (System.in);   
				Scanner stringScanner = new Scanner (System.in); 

				int theID; 
				String theNewRecource;  
				String theDescription;	

				///files(int iD, String fileName, String fileDescription)///// 

				System.out.println("Enter ID for the Recourfce: ");
				theID= intScanner.nextInt(); 
				System.out.println("Enter file name (dont add space and Upper case senstive) ");
				theNewRecource= stringScanner.nextLine() + ".json" ; 
				System.out.println("describe the Recource: ");
				theDescription= stringScanner.nextLine(); 

				theResources.insertSt2(new files( theID ,theNewRecource ,theDescription  ));  
			}

			else System.out.println("Sorry, your role cant be occupied at this time");
		}
		else System.out.println("Permission not valid");  
	}

	public  void deleteResource(int USerRoleID , roleList theRoles, filesList theResources) { //8/// -----------------------------Changed [new function]
		if (theRoles.searchSt2(USerRoleID).getAllPermissions().contains(8))  {  ///change
			System.out.println("You have the Permission");
			if ( checkTiming( USerRoleID ,  theRoles) == true) {////////////// 
				System.out.println("You are on time");
				//TODO
				Scanner intScanner = new Scanner (System.in);     int choice ; 
				theResources.display();
				System.out.println("Wrtite the ID for resource you want to delete");
				choice = intScanner.nextInt();   	 
				File x = new File (theResources.searchSt2(choice).getFileName() ) ; 
				x.delete();  
				theResources.deleteSt2(choice); 	
			}
			else System.out.println("Sorry, your role cant be occupied at this time");
		}
		else System.out.println("Permission not valid");  
	}




	public  void viewResource(int USerRoleID , roleList theRoles, filesList theResources) throws IOException { //9//// -----------------------------Changed [new function]
		ArrayList<Integer> m =  theRoles.searchSt2(USerRoleID).getAllPermissions(); //in menu

		if (m.contains(9))  { //menu
			System.out.println("you have permission of reading, let me check for the file you want to use");
			if (checkTiming( USerRoleID ,  theRoles) == true) {//menu

				System.out.println(" You in time");
				Scanner intScanner = new Scanner (System.in);

				int theID ;  
				int theIndex; 

				for (int x : m) { //in view resources page [files you have permission to view]

					if (x>10) {
						System.out.println( theResources.searchSt2(x ) ); 
					} 
				}

				System.out.println("ID of file you want to read");//here lets make a table that contains the data of the file

				theID = intScanner.nextInt() ;  

				if (m.contains(theID) ) {

					System.out.println("You can read this file");

					Scanner stringScanner = new Scanner (System.in);  
					String fileName;  
					fileName = theResources.searchSt2(theID).getFileName(); 

					ArrayList<String >  theList  = new 	 ArrayList<String > () ; 

					theList = loadResource(  theList,   fileName) ; 

					theIndex = 1; 

					for (String x  : theList) {
						System.out.println( theIndex + " " + x); 
						theIndex++;
					}
				}
			}
			else System.out.println("Sorry, your role cant be occupied at this time");
		}
		else System.out.println("Permission not valid");  
	}	

	public  void writeToResource(int USerRoleID , roleList theRoles, filesList theResources) throws IOException { //p10 /// -----------------------------Changed [new function]
		ArrayList<Integer> m =  theRoles.searchSt2(USerRoleID).getAllPermissions(); 
		if (m.contains(10) )  { 
			System.out.println("you have permission of reading, let me check for the file you want to use");
			if ( checkTiming( USerRoleID ,  theRoles) == true) {
				System.out.println(" You in time");
				Scanner intScanner = new Scanner (System.in);   int theID ;   
				for (int x : m) { if (x>10)  System.out.println( theResources.searchSt2(x ) ); } 
				System.out.println("ID of file you want to modify");
				theID = intScanner.nextInt() ;  
				if (m.contains( theID) ) {
					System.out.println("You have access to this file");
					Scanner stringScanner = new Scanner (System.in); String thetxt; String fileName; 
					System.out.println("what you want to add?");
					thetxt =  stringScanner.nextLine();
					fileName = theResources.searchSt2(theID).getFileName(); 
					ArrayList<String >  theList  = new 	 ArrayList<String > () ; 
					theList= loadResource(  theList,   fileName) ; 
					theList.add(thetxt);  
					saveResource(  theList,   fileName) ; 
				}}
			else System.out.println("Sorry, your role cant be occupied at this time");
		}
		else System.out.println("Permission not valid");  
	}	

	//Other functions
	public  ArrayList<String>  loadResource( ArrayList<String> theList, String fileName) throws IOException { /// -----------------------------Changed [new function]
		File myFile1 = new File (fileName) ;   
		if (myFile1.exists()==false)  myFile1.createNewFile() ; 
		try { 
			FileInputStream fromTheFile1 = new FileInputStream(myFile1);
			ObjectInputStream theObject1 = new ObjectInputStream(fromTheFile1);
			theList = (ArrayList <String>) theObject1.readObject();
			theObject1.close();
			fromTheFile1.close();  
		} catch (IOException i){ i.printStackTrace(); } 	catch (ClassNotFoundException e) { 	e.printStackTrace(); }
		return theList;  }
	//	
	public  void saveResource( ArrayList<String> theList, String fileName) throws IOException { /// -----------------------------Changed [new function]
		try {  
			File myFile1 = new File (fileName) ;   
			FileOutputStream toTheFile1 = new FileOutputStream(myFile1 );
			ObjectOutputStream theObject1 = new ObjectOutputStream(toTheFile1);
			theObject1.writeObject(theList);
			theObject1.close();
			toTheFile1.close();}		catch (IOException e) {  e.printStackTrace(); }}
	//  	//-----------------------------------------------------------------------

	public zPermissions() {//new
		thePermissions.add("Add new role [write]");
		thePermissions.add("remove role [write]");
		thePermissions.add("view roles list [read]");
		thePermissions.add("Add new member [write]");
		thePermissions.add("remove member [write]");
		thePermissions.add("view members list [read]"); 
		thePermissions.add( "add new resource");
		thePermissions.add( "remove resource");
		thePermissions.add(  "view resource"); 
		thePermissions.add(  "update resource"); 
	}
	//i want it to add dynamically the created files.
	//create file, and it will be placed with allmembers.json and allroles.json as Filename.json.
	//this File object contains (fileid, List<String> content, filename?)
	//here in permissions: add method create files that places it in the right place. 
	//add method delete file. 
	//add method view files -> that only views files that was given permission to. 
	//first we need to get all created files, we will put them in a list<File> files, but to retrieve them is a challenge. 
	//as a solution: create an external file when we first run called (files names) that contains the names of the files.json so then we retrieve them one by one.



	//no need for that, we can just create a constructor to initialize things.

	void Permissiontable()   {
		//Intialize table
		thePermissions.add("Add new role [write]");
		thePermissions.add("remove role [write]");
		thePermissions.add("view roles list [read]");
		thePermissions.add("Add new member [write]");
		thePermissions.add("remove member [write]");
		thePermissions.add("view members list [read]");   

	}
} 



