package com.model;

public class User {
	private String first = "";
	private String last = "";
	private String email = "";
	public User(String first, String last, String email) {
		setFirst(first);
		setLast(last);
		setEmail(email);
	}	
	
	public void setFirst(String first) {
		this.first = first;
	}
	public String getFirst() {
		return this.first;
	}
	public void setLast(String last) {
		this.last = last;
	}
	public String getLast() {
		return this.last;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return this.email;
	}
}
