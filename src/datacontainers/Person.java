
package datacontainers;

//import stuff here 
import java.util.ArrayList;
import datacontainers.Address;


public class Person {
	
	private String personCode;
	private String firstName;
	private String lastName;
	private Address address;
	private ArrayList<String> email;
	
	public Person(String personCode, String firstName, String lastName, Address address) {
		super();
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		//this won't have a set email adress yet, that will be checked if the read in person has emai lro not, they it willb e set later
		//leeave email up to the driver/converter classes 
	}

	public String getPersonCode() {
		return this.personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public ArrayList<String> getEmail() {
		return this.email;
	}

	public void setEmail(ArrayList<String> email) {
		this.email = email;
	}
	
	public String getName() {
		return this.firstName + " " + this.lastName;
	}
	
	
}
