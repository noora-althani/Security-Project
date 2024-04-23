package model;
import java.io.Serializable;
import java.util.ArrayList;

public class files implements Serializable{ 
	private int ID ; 
	private String fileName ;   
	private String fileDescription ;
	public files(int iD, String fileName, String fileDescription) {
		super();
		ID = iD;
		this.fileName = fileName;
		this.fileDescription = fileDescription;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileDescription() {
		return fileDescription;
	}
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
	@Override
	public String toString() {
		return "files [ID=" + ID + ", fileName=" + fileName + ", fileDescription=" + fileDescription + "]";
	}   
	
	
	
}
