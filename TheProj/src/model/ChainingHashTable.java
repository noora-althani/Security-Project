package model;

import java.io.Serializable;
import java.util.ArrayList;

public class ChainingHashTable<E> implements Serializable {
	private ASortedList[] hashArray;

	public ChainingHashTable(int size){
		hashArray = new ASortedList[size];  
		for(int j=0; j<hashArray.length; j++)          
			hashArray[j] = new ASortedList();} 

	public int hashFunc(int key)  { return key % hashArray.length; } 

	public void insert(int k, E d)   {
		int hashVal = hashFunc(k);   
		hashArray[hashVal].insert(k,d);  } 

	public void delete(int k)  {
		int hashVal = hashFunc(k);   
		hashArray[hashVal].delete(k);  }  

	public E find(int k) {   
		int hashVal = hashFunc(k);  
		E d = (E) hashArray[hashVal].search(k);   
		return d  ; }  

	public void displayTable(){ 
		for(int j=0; j<hashArray.length; j++)  
			hashArray[j].displayList(); 
	} 
	
	
}