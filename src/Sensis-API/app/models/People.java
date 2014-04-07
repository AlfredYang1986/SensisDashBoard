package models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class People {

	String name;
	int age;
	Date create;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	int success;
	int error;
	
	public People(String name, int age, String created, int success, int error){
		this.name = name;
		this.age = age;
		this.success = success;
		this.error = error;
		try {
			this.create = df.parse(created);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("error format");
		}
	
	}

	public int getCreate() {
		return create.getDate();
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
