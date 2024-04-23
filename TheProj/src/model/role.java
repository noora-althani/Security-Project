package model;

import java.io.Serializable;
import java.util.ArrayList;

public class role  implements Serializable{
	private int ID;  
	private String RoleDescription;  
	private ArrayList<Integer >  allPermissions ;
	private int startingHour;
	private int endHour;

	
	public role(int iD, String roleDescription,
			ArrayList<Integer> allPermissions, 
			int startingHour, int endHour)
	
	{
		super();
		ID = iD; 
		RoleDescription = roleDescription;
		this.allPermissions = allPermissions;
		this.startingHour = startingHour;
		this.endHour = endHour;
	}
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getRoleDescription() {
		return RoleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		RoleDescription = roleDescription;
	}
	public ArrayList<Integer> getAllPermissions() {
		return allPermissions;
	}
	public void setAllPermissions(ArrayList<Integer> allPermissions) {
		this.allPermissions = allPermissions;
	}
	public int getStartingHour() {
		return startingHour;
	}
	public void setStartingHour(int startingHour) {
		this.startingHour = startingHour;
	}
	public int getEndHour() {
		return endHour;
	}
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	@Override
	public String toString() {
		return "role [ID=" + ID + ", RoleDescription=" + RoleDescription + ", allPermissions=" + allPermissions
				+ ", startingHour=" + startingHour + ", endHour=" + endHour + "]";
	}	
	
}
