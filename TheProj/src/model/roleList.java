package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class roleList implements Serializable {
	private ChainingHashTable<role>  theDateTable;   
	private int size;//new
	private ArrayList<Integer> roleIdList;//new
	
	public int getSize() {//new
		return size;
	}
	public ArrayList<Integer> getRoleIds(){//new
		return this.roleIdList;
	}
	public void setRolesIdsList(ArrayList<Integer> newRolesIds) {//new
		this.roleIdList = newRolesIds;
	}

	public roleList() {  
		theDateTable = new ChainingHashTable<role> (100) ; 
		this.size = 0;
		this.roleIdList = new ArrayList<Integer>();
	}  

	public void insertSt2(role thepatients) { 
		int id= thepatients.getID(); 
		if (theDateTable.find(id)!= null ) {
			System.out.println("This role is already on table");   
		}
		else {
			theDateTable.insert( id , thepatients); 
			size++;//new
			roleIdList.add(id);//new
		}

	}

	// search
	public role searchSt2 (int k ) {  
		role thepatient = theDateTable.find(k)  ; 
		if (thepatient!= null) return thepatient ; 
		else {
			Scanner intScanner = new Scanner (System.in);   
			System.out.println(" Role Identfier not valid,  enter the ID again ");  
			int k2 = intScanner.nextInt(); 
			searchSt2 (k2) ; 
		}
		return null;  
	}

	//delete
	public boolean deleteSt2(int m ) {  
		if (theDateTable.find(m)!=null) {
			theDateTable.delete(m);  return true;
		}
		return false  ; 
	} 
	//display 
	public void display() { 
		theDateTable.displayTable();
	}



}
