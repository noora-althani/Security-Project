package model;

 
// ID = 1 admin 2 recp 3 doc  4 patient
	import java.io.Serializable;
import java.util.ArrayList;

	public class Member implements Serializable {
		private ArrayList<Integer>  rolesID ; 
		private int ID ; 
		private String name ;   
		private String pass ;
		public Member(ArrayList<Integer> rolesID, int iD, String name, String pass) {
			super();
			this.rolesID = rolesID;
			ID = iD;
			this.name = name;
			this.pass = pass;
		}
		public ArrayList<Integer> getRolesID() {
			return rolesID;
		}
		public void setRolesID(ArrayList<Integer> rolesID) {
			this.rolesID = rolesID;
		}
		public int getID() {
			return ID;
		}
		public void setID(int iD) {
			ID = iD;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPass() {
			return pass;
		}
		public void setPass(String pass) {
			this.pass = pass;
		}
		@Override
		public String toString() {
			return "Member [rolesID=" + rolesID + ", ID=" + ID + ", name=" + name + "]";
		}
		
	 
 
		
	}	 