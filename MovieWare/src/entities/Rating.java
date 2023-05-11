package entities;

import java.io.Serializable;

public class Rating implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6970919658421001111L;
	String comment;
	int value;
	
	public Rating(int value) {
		this.value = value;
	}
	
	public Rating(int value, String comment) {
		this.value = value;
		this.comment = comment;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String getComment() {
		return this.comment;
	}
}
