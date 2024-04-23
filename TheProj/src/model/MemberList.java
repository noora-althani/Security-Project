package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberList implements Serializable {
	private ChainingHashTable< Member>  Members ;   
	private int counter = 0;//new
	private ArrayList<Integer> memberIdsList;//new

	public MemberList() { 
		Members = new ChainingHashTable<Member> (100);
		memberIdsList = new ArrayList<Integer>();//new
	} 

	public int getCounter() {//new
		return counter;
	}
	public ArrayList<Integer> getMembersIdsList(){//new
		return this.memberIdsList;
	}
	
	public void setMembersIdsList(ArrayList<Integer> newMembersArraylist) {//new
		this.memberIdsList = newMembersArraylist;
	}

	public void insertSt2(Member thepatients) { 
		
		int id= thepatients.getID(); 
		if (Members.find(id)!= null ) {
			System.out.println("This Member is already on table");   
		}else {
			Members.insert( id , thepatients); 
			counter++;//moved here
			memberIdsList.add(id);//new
		}
	}
	// search
	public Member searchSt2 (int id)  {   
		Member m = Members.find(id)  ; 
		if (m!= null) return m ;  
		return  null ;  
	}
	//delete
	public boolean deleteSt2(int m ) {  
		if (Members.find(m)!=null) { Members.delete(m);  return true ; }
		return false  ; } 
	//display 
	public void display() {
		Members.displayTable(); 
	}


}
