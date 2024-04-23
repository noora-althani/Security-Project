package model;

import java.io.Serializable;

public class ANode<E>  implements Serializable{
	int key;
	E data;
	ANode<E> leftChild;
	ANode<E> rightChild;

	public ANode(int k,E e) {
		key=k;
		data=e;
		leftChild=null;
		rightChild=null; 
	}

	public void display() {
		System.out.print(key+":");
		System.out.println(data);

	}
}