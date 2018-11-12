package datacontainers;

public class Customer {
	
	protected String customerCode;
	protected Person contact;
	protected String name;
	protected Address address;
	
	public Customer(String customerCode, Person contact, String name, Address address) {
		super();
		this.customerCode = customerCode;
		this.contact = contact;
		this.name = name;
		this.address = address;
	}

	public String getCustomerCode() {
		return this.customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Person getContact() {
		return this.contact;
	}

	public void setContact(Person contact) {
		this.contact = contact;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	public boolean isStudent() {
		return false;
	}
	
	public String getType() {
		return "General";
	}

	public void print() {
		
	}

}
