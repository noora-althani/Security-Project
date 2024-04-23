package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
public class filesList implements Serializable{
	private ChainingHashTable< files>  theDateTable ;   
	private int counter = 0;//new
	private ArrayList<Integer> filesIdsList;//new

	public filesList() { 
		theDateTable = new ChainingHashTable<files> (100) ; 
		filesIdsList = new ArrayList<Integer>();
	} 

	public int getCounter() {//new
		return counter;
	}
	public ArrayList<Integer> getFilesIds() {//new
		// TODO Auto-generated method stub
		return this.filesIdsList;
	} 

	public void setFilesIdsList(ArrayList<Integer> filesIdsList) {//new
		this.filesIdsList = filesIdsList;
	}

	public void insertSt2(files thepatients) { 
		int id= (thepatients).getID(); 
		if (theDateTable.find(id)!= null ) {
			System.out.println("This file is already on table");   
		}else {
			theDateTable.insert( id , thepatients);  
			counter++;//new
			filesIdsList.add(id);//new
		}
	}
	// search
	public files searchSt2 (int k )  {   
		files thepatient = theDateTable.find(k)  ; 
		if (thepatient!= null) return thepatient ;  
		return  null ;  
	}
	//delete
	public boolean deleteSt2(int m ) {  
		if (theDateTable.find(m)!=null) { theDateTable.delete(m);  return true ; }
		return false  ; } 
	//display 
	public void display() { theDateTable.displayTable(); }


}
