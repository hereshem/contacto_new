package com.alvin.contacto;

public class Contacts {

	private int id;
	private String username, fname, lname, mobile, home, office, timestamp;
	
	public Contacts(int id, String username, String fname, String lname,
			String mobile, String home, String office, String timestamp) {
		super();
		this.id = id;
		this.username = username;
		this.fname = fname;
		this.lname = lname;
		this.mobile = mobile;
		this.home = home;
		this.office = office;
		this.timestamp = timestamp;
	}
	
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getFname() {
		return fname;
	}
	public String getLname() {
		return lname;
	}
	public String getMobile() {
		return mobile;
	}
	public String getHome() {
		return home;
	}
	public String getOffice() {
		return office;
	}
	public String getTimestamp() {
		return timestamp;
	}
	
	
}
