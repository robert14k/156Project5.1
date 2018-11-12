package datacontainers;

public class Student extends Customer{
	
	public Student(String customerCode, Person contact, String name, Address address) {
		super(customerCode, contact, name, address);
		// TODO Auto-generated constructor stub
	}
	

	public boolean isStudent() {
		return true;
	}
	
	public String getType() {
		return "Student";
	}
	
	public void print() {
		System.out.println(this.name + " " + this.customerCode);
		System.out.println("Student");
		System.out.println(this.contact.getName());
		System.out.println(this.address.getStreet());
		System.out.println(this.address.getCity() + " " + this.address.getState() + " " + this.address.getZip() + " " + this.address.getCountry());
	}

}
