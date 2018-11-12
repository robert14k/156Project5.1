package fileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import datacontainers.*;
import java.io.File;
import java.io.FileNotFoundException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class FlatFileReader {
	
	public ArrayList<Person> readPersons() {
		Scanner sc = null;
		
		try {
			sc = new Scanner(new File("data/Persons.dat"));
			sc.nextLine();
			
			ArrayList<Person> personList = new ArrayList<Person>();
			
			while(sc.hasNext()) {
				String line = sc.nextLine();
				String data[] = line.split(";"); //tokenize and store in array os string
				
				String personCode = data[0];
				
				String name[] = data[1].split(","); //tokenize name on commma 
				String lastName = name[0];
				String firstName = name[1];
				
				String location[] = data[2].split(",");
				String street = location[0];
				String city = location[1];
				String state = location[2];
				String zip = location[3]; 
				String country = location[4];
				
				Address address = new Address(street, city, state, zip, country);
				Person person = new Person(personCode, firstName, lastName, address);
				
				//check for email adress 
				if(data.length == 4) {
					ArrayList<String> email = new ArrayList<String>(); 
					String emails[] = data[3].split(",");
					for(String  a : emails) {
						email.add(a);
					}
				}
				
				personList.add(person);
				
			}
			sc.close();
			return personList;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public ArrayList<Customer> readCusotmers(List<Person> people) {
		Scanner sc = null;
		
		try {
			sc = new Scanner(new File("data/Customers.dat"));
			sc.nextLine();
			
			ArrayList<Customer> customerList = new ArrayList<Customer>();
			
			while(sc.hasNext()) {
				String line = sc.nextLine();
				String data[] = line.split(";");
				
				String customerCode = data[0];
				String type = data[1];
				Person contact = codeGetPerson(data[2], people);
				String name = data[3];
				//get the adress and break it up
				String location[] = data[4].split(",");
				String street = location[0];
				String city = location[1];
				String state = location[2];
				String zip = location[3]; 
				String country = location[4];
				
				Address address = new Address(street, city, state, zip, country);
				Customer customer;
				//this might break.....
				if(type.equals("S")){
					customer = new Student(customerCode, contact, name, address);
				}else {
					customer = new General(customerCode, contact, name, address);
				}
				customerList.add(customer);
			}
			sc.close();
			return customerList;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Product> readProducts() {
		Scanner sc = null;
		
		try {
			sc = new Scanner(new File("data/Products.dat"));
			sc.nextLine();
			
			ArrayList<Product> productList = new ArrayList<Product>();
			
			while(sc.hasNext()) {
				String line = sc.nextLine();
				String data[] = line.split(";");
				
				String code = data[0];
				String type = data[1];
				if(type.equals("M")) {
					DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
					DateTime movieTime = formatter.parseDateTime(data[2]);
					String name = data[3];
					
					
					String location[] = data[4].split(",");
					String street = location[0];
					String city = location[1];
					String state = location[2];
					String zip = location[3]; 
					String country = location[4];
					
					Address address = new Address(street, city, state, zip, country);
					
					String screenNo = data[5];
					double price = Double.parseDouble(data[6]);
					
					MovieTicket ticket = new MovieTicket(code, movieTime, name, address, screenNo, price);
					
					productList.add(ticket);
					
				}else if(type.equals("S")) {
					DateTimeFormatter formatterS = DateTimeFormat.forPattern("yyyy-mm-dd");
					DateTime startTime = formatterS.parseDateTime(data[3]);
					DateTime endTime = formatterS.parseDateTime(data[4]);
					String name = data[2];
					double cost = Double.parseDouble(data[5]);
					
					SeasonPass seasonPass= new SeasonPass(code, name, startTime, endTime, cost);
					productList.add(seasonPass);
					
				}else if(type.equals("P")) {
					double fee = Double.parseDouble(data[2]);
					ParkingPass parkingPass = new ParkingPass(code, fee);
					
					productList.add(parkingPass);
				}else if(type.equals("R")){
					String name = data[2];
					double cost = Double.parseDouble(data[3]);
					
					Refreshment refreshment = new Refreshment(code, name, cost);
					
					productList.add(refreshment);
				}
			}
			sc.close();
			return productList;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Person codeGetPerson(String code, List<Person> people) {
		for(Person a: people) {
			if(code.equals(a.getPersonCode())) {
				return a;
			}

		}
		Person blank = null;
		return blank;
		
	}
	
	public ArrayList<Invoice> readInvoice(List<Person> people, List<Product> products, List<Customer> customers){
		Scanner sc = null;
		
		try {
			sc = new Scanner(new File("data/Invoices.dat"));
			sc.nextLine();
			
			ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();
			
			while(sc.hasNext()) {
				String line = sc.nextLine();
				String data[] = line.split(";");
				
				String invoiceCode = data[0];
				String customerCode = data[1];
				String spCode = data[2];
				DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-mm-dd");
				DateTime invoiceTime = formatter.parseDateTime(data[3]);
				
				String productArray[] = data[4].split(",");
				ArrayList<Product> productList = new ArrayList<Product>();
				String productInfo[];
				Product currentProduct = null;
				Product originalProduct = null;
				//String arrayProduct[];
				for(int i=0; i<productArray.length; i++) {
					productInfo = productArray[i].split(":");
					originalProduct = codeGetProduct(productInfo[0], products);
					if(originalProduct.getType().equals("MovieTicket")) {
						currentProduct = new MovieTicket((MovieTicket) originalProduct);
					}else if(originalProduct.getType().equals("Refreshment")) {
						currentProduct = new Refreshment((Refreshment) originalProduct);
					}else if(originalProduct.getType().equals("ParkingPass")) {
						currentProduct = new ParkingPass((ParkingPass) originalProduct);
					}else if(originalProduct.getType().equals("SeasonPass")) {
						currentProduct = new SeasonPass((SeasonPass) originalProduct);
					}
					
					//this is to test and assign the amount of untis they bought 
					if(productInfo.length>1) {
						currentProduct.setAmount(Integer.parseInt(productInfo[1].trim()));
					}
					//this sees if there is a 3rd thing which would be a movie attached to a prkign pass
					if(productInfo.length>2) {
						currentProduct.setWithTicket(true);
						if(codeGetProduct(productInfo[2], productList) != null) {
							currentProduct.setnumTicket(codeGetProduct(productInfo[2], productList).getAmount());
						}else {
							currentProduct.setnumTicket(codeGetProduct(productInfo[2], products).getAmount());
						}
					}
					productList.add(currentProduct);
					currentProduct = null;
					
				}
				
				Customer customer = codeGetCustomer(customerCode, customers);
				Person sp = codeGetPerson(spCode, people);
				
				Invoice invoice = new Invoice(invoiceCode, invoiceTime, customer, sp, productList);
				if(invoice.hasBoth()) {
					invoice.editRefreshment();
				}
				invoiceList.add(invoice);
			}
			sc.close();
			return invoiceList;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Customer codeGetCustomer(String code, List<Customer> people) {
		for(Customer a: people) {
			if(code.equals(a.getCustomerCode())) {
				return a;
			}

		}
		Customer blank = null;
		return blank;
		
	}
	public Product codeGetProduct(String code, List<Product> products) {
		for(Product a: products) {
			if(code.equals(a.getProductCode())) {
				return a;
			}

		}
		Product blank = null;
		return blank;
		
	}
}


