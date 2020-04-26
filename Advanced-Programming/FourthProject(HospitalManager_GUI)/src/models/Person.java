package models;

import java.io.Serializable;

public abstract class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	private String firstName, lastName, gender, birthDate;
	
	
	public Person(String firstName,String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Person(String firstName,String lastName,String gender,String birthDate){
		this(firstName, lastName);
		this.birthDate = birthDate;
		this.gender = gender;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getFullName(){
		return getFirstName() + "-" + getLastName();
	}
	public String getDescription(){
		return getFullName() + " " + getGender() + " " + getBirthDate();
	}
	public abstract void idGenerator();
	public abstract void idGenerator(long id);
	
}
