package model;

import java.io.Serializable;
import java.util.ArrayList;

public class ASortedList <E> implements Serializable{
	private ANode<E> root; 

	public E search(int k) {
		if (root!= null) { 
			ANode<E> current=root;

			while(current.key!=k)  {

				if (k<current.key)  {
					current=current.leftChild;
				}
				else {
					current=current.rightChild;
				}
				if(current == null) {
					return null; 
				}
			}
			return current.data;
		}  
		return null ;
	}

	public void insert(int k, E e){
		ANode<E> newNode = new ANode(k,e); 
		if(root==null) root = newNode;
		else   {
			ANode current = root; 
			ANode parent;
			while(true)   {
				parent = current;
				if(k < current.key)  { 
					current = current.leftChild;
					if(current == null)   { 
						parent.leftChild = newNode;
						return;  } } 
				else  {
					current = current.rightChild;
					if(current == null)   { 
						parent.rightChild = newNode;
						return;  }  }   }  }  } 

	public boolean delete(int k) {
		ANode current = root;
		ANode parent = root;
		boolean isLeftChild = true;

		while(current.key!= k)   { 
			parent = current;
			if(k < current.key)   { isLeftChild = true; current = current.leftChild;  }
			else   {  isLeftChild = false; current = current.rightChild;  }
			if(current == null) return false;   }

		if(current.leftChild==null &&current.rightChild==null) {
			if(current == root)  root = null; 
			else if(isLeftChild) parent.leftChild = null; 
			else  parent.rightChild = null;  } 

		else if(current.rightChild==null) 
			if(current == root)  root = current.leftChild;
			else  if(isLeftChild)  parent.leftChild = current.leftChild;
			else   parent.rightChild = current.leftChild;  
		else if(current.leftChild==null) if(current == root)  root = current.rightChild;
		else if(isLeftChild) parent.leftChild = current.rightChild;
		else parent.rightChild = current.rightChild;
		else   { 
			ANode successor = getSuccessor(current);
			if(current == root)   root = successor;
			else if(isLeftChild)  parent.leftChild = successor;
			else parent.rightChild = successor; successor.leftChild = current.leftChild; } 
		return true; } 

	private ANode<E> getSuccessor(ANode<E> delNode){
		ANode<E> successorParent = delNode;
		ANode<E> successor = delNode;
		ANode<E> current = delNode.rightChild;  
		while(current != null) {
			successorParent = successor;
			successor = current;
			current = current.leftChild; }
		if(successor != delNode.rightChild){
			successorParent.leftChild = successor.rightChild;
			successor.rightChild = delNode.rightChild;  }
		return successor;} 

	public void inorder(ANode <E> n){ 

		if(n == null)  {
			return;
		}else{ 

			inorder(n.leftChild);  
			n.display();  
			inorder(n.rightChild);

		}   
	}
	
	public void displayList( ){
		inorder(root);   
	} 	 
}