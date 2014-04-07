package models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SAPI_User {

	public String userName;
	public int age;
	public Date created;
	public Date updated;
	public String email;
	public String address;
	public int id;
	public String objectType;
	public int success;
	public int error;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public SAPI_User(int id, String name, String created, String email, int success, int error){
		this.id = id;
		this.userName = name;
		try {
			this.created = df.parse(created);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.success = success;
		this.error = error;		
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreated() {
		return created.toString();
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}
	
}
