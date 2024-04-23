package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DataBase implements Serializable{
	private MemberList allMembers ; 
	private roleList allRoles ;  
	private filesList allPermissions ;  
	//private filesList allPermissions;
	//	private String Filename1 = "allMembers.dat" ; 
	//	private String Filename2 = "allRoles.dat" ; 

	private String Filename1 = "allMemberss.json" ; 
	private String Filename2 = "allRoless.json" ;
	private String Filename3 = "resourcesFiless.json" ; 
	//private String Filename3 = "C:\\Users\\YoMrN\\Desktop\\here\\resourcesFiles.txt";


	 
	public DataBase(MemberList allMembers, roleList allRoles, filesList allPermissions) {
		super();
		this.allMembers = allMembers;
		this.allRoles = allRoles;
		this.allPermissions = allPermissions;
	}
	public DataBase(MemberList allMembers, roleList allRoles ) {
		super();
		this.allMembers = allMembers;
		this.allRoles = allRoles; 
	}

	public void save(){ 
		try {  
			FileOutputStream toTheFile1 = new FileOutputStream(Filename1);
			ObjectOutputStream theObject1 = new ObjectOutputStream(toTheFile1);
			theObject1.writeObject(allMembers);
			theObject1.close();
			toTheFile1.close();
			
			FileOutputStream toTheFile2 = new FileOutputStream(Filename2);
			ObjectOutputStream theObject2 = new ObjectOutputStream(toTheFile2);
			theObject2.writeObject(allRoles);
			theObject2.close();
			toTheFile2.close();
			
			FileOutputStream toTheFile3 = new FileOutputStream(Filename3);
			ObjectOutputStream theObject3 = new ObjectOutputStream(toTheFile3);
			theObject3.writeObject(allPermissions);
			theObject3.close();
			toTheFile3.close();
			
			System.out.println("the data been saved Sucssufully");} 
		catch (IOException e) {  e.printStackTrace();
		}
	}
 
	public void load() throws IOException{  
		File myFile1 = new File (Filename1) ;  
		File myFile2 = new File (Filename2) ;  
		File myFile3 = new File (Filename3) ;  
		
		if (myFile1.exists()==false) myFile1.createNewFile() ;
		if (myFile2.exists()==false) myFile2.createNewFile() ;
		if (myFile3.exists()==false) myFile3.createNewFile() ;
		
		try { 
			FileInputStream fromTheFile1 = new FileInputStream(myFile1);
			ObjectInputStream theObject1 = new ObjectInputStream(fromTheFile1);
			allMembers = (MemberList) theObject1.readObject();
			theObject1.close();
			fromTheFile1.close();  
				
			FileInputStream fromTheFile2 = new FileInputStream(myFile2);
			ObjectInputStream theObject2 = new ObjectInputStream(fromTheFile2);
			allRoles = (roleList) theObject2.readObject();
			theObject2.close();
			fromTheFile2.close();  	
			
			FileInputStream fromTheFile3 = new FileInputStream(myFile3);
			ObjectInputStream theObject3 = new ObjectInputStream(fromTheFile3); 
			allPermissions = (filesList) theObject3.readObject();
			theObject3.close();
			fromTheFile3.close();   
		} catch (IOException i){ i.printStackTrace(); } 	catch (ClassNotFoundException e) { 	e.printStackTrace(); } }
	
 

	public MemberList getAllMembers() {
		//System.out.println("Counter is "+ allMembers.getCounter());
		return allMembers;
		
	}

	public roleList getAllRoles() {
		return allRoles;
	}

	public filesList getAllResources() {
		return allPermissions;
	}

	public void setANewResourcesList(filesList allPermissions) {
		this.allPermissions = allPermissions;
		
		
	}


}
