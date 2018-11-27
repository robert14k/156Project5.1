package dbInteractions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import datacontainers.Address;
import datacontainers.Customer;
import datacontainers.General;
import datacontainers.Invoice;
import datacontainers.InvoiceList;
import datacontainers.MovieTicket;
import datacontainers.ParkingPass;
import datacontainers.Person;
import datacontainers.Product;
import datacontainers.Refreshment;
import datacontainers.SeasonPass;
import datacontainers.Student;
import dbInteractions.dbConnection;
import fileReader.FlatFileReader;

public class DBReader {
	
	
	public ArrayList<Person> getPersons() {
		//connect to database, make a statement, line by line, results, and grab stuff and assign them
		
		Connection conn = dbConnection.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		String personQuery = "SELECT * FROM Person;";
		ArrayList<Person> people= new ArrayList<Person>();
		Person p;
		String code, first, last;
		int addressCode;
		Address a;
		try
		{
			ps = conn.prepareStatement(personQuery);
			rs = ps.executeQuery();
			while(rs.next()){
				
				code = rs.getString("PersonCode");
				first = rs.getString("PersonFirstName");
				last = rs.getString("PersonLastName");
				addressCode = rs.getInt("AddressID");
				a = getAddress(addressCode);
				p = new Person(code, first, last, a);
				people.add(p);
			}
			rs.close();
			ps.close();
			conn.close();
	
		}
		catch (SQLException e)
		{
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return people;
	}
	
	
	public Address getAddress(int code) {
		Address a = null;
		Connection conn = dbConnection.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		String addressQuery = "SELECT a.Address, a.City, a.State, z.ZipCode, c.Country FROM Address as a JOIN Zip as z on a.ZipID=z.ZipID JOIN Country as c on a.CountryID=c.CountryID WHERE a.AddressID=?";
		String street, city, zip, country, state;
		
		try
		{
			ps = conn.prepareStatement(addressQuery);
			ps.setInt(1, code);
			rs = ps.executeQuery();
			rs.next();
			
			street = rs.getString("Address");
			city = rs.getString("City");
			country = rs.getString("Country");
			state = rs.getString("State");
			zip = rs.getString("ZipCode");
			a = new Address(street, city, state, zip, country);
			
			rs.close();
			ps.close();
			conn.close();
	
		}
		catch (SQLException e)
		{
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return a;
	}
	
	public ArrayList<Customer> getCusotmers(List<Person> people) {
	
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		Connection conn = dbConnection.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		String customerQuery = "SELECT * FROM Customer";
		Customer c;
		Address a;
		Person contact;
		String customerCode;
		String name;
		
		try
		{
			ps = conn.prepareStatement(customerQuery);
			rs = ps.executeQuery();
				
		
			while(rs.next()) {
				customerCode = rs.getString("CustomerCode");
				name = rs.getString("CompanyName");
				a = getAddress(rs.getInt("AddressID"));
				contact = getPerson(rs.getInt("PersonID"));

				if(rs.getString("CustomerType").equals("Student")){
					c = new Student(customerCode, contact, name, a);
				}else {
					c = new General(customerCode, contact, name, a);
				}
				customerList.add(c);
			}
			rs.close();
			ps.close();
			conn.close();
		}
		catch (SQLException e)
		{
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return customerList;
	}
	
	
	
	public Person getPerson(int code) {
		Address a = null;
		Connection conn = dbConnection.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		String personQuery = "SELECT * FROM Person WHERE PersonID=?";
		Person p = null;
		String id, first, last;
		int addressCode;
		try
		{
			ps = conn.prepareStatement(personQuery);
			ps.setInt(1, code);
			rs = ps.executeQuery();
			rs.next();
			
			id = rs.getString("PersonID");
			first = rs.getString("PersonFirstName");
			last = rs.getString("PersonLastName");
			addressCode = rs.getInt("AddressID");
			a = getAddress(addressCode);
			p = new Person(id, first, last, a);
			
			rs.close();
			ps.close();
			conn.close();
	
		}
		catch (SQLException e)
		{
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return p;
	}
	
	
	public ArrayList<Product> getProducts() {
		ArrayList<Product> productList = new ArrayList<Product>();
		Connection conn = dbConnection.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		String productQuery = "SELECT * FROM Products";
		Address a = null;
		String productCode;
		
		try
		{
			ps = conn.prepareStatement(productQuery);
			rs = ps.executeQuery();
		
			while(rs.next()) {
				productCode = rs.getString("ProductCode");
				if(rs.getString("ProductType").equals("M")) {
					DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
					DateTime movieTime = formatter.parseDateTime(rs.getString("TimeMovie"));
					String name = rs.getString("ProductName");
					
					a = getAddress(rs.getInt("AddressID"));
					
					
					String screenNo = rs.getString("SeatNumber");
					double price = rs.getDouble("ProductPrice");
					
					MovieTicket ticket = new MovieTicket(productCode, movieTime, name, a, screenNo, price);
					
					productList.add(ticket);
					
					
				}else if(rs.getString("ProductType").equals("R")) {
					String name = rs.getString("ProductName");
					double price = rs.getDouble("ProductPrice");
					
					Refreshment refreshment = new Refreshment(productCode, name, price);
					
					productList.add(refreshment);
					
				}else if(rs.getString("ProductType").equals("S")) {
					DateTimeFormatter formatterS = DateTimeFormat.forPattern("yyyy-mm-dd");
					DateTime startTime = formatterS.parseDateTime(rs.getString("EventStart"));
					DateTime endTime = formatterS.parseDateTime(rs.getString("EventEnd"));
					String name = rs.getString("ProductName");
					double price = rs.getDouble("ProductPrice");
					
					SeasonPass seasonPass= new SeasonPass(productCode, name, startTime, endTime, price);
					productList.add(seasonPass);
					
				}else{
					double price = rs.getDouble("ProductPrice");
					ParkingPass parkingPass = new ParkingPass(productCode, price);
					productList.add(parkingPass);
				}	
			}
			rs.close();
			ps.close();
			conn.close();
		}
		catch (SQLException e)
		{
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	return productList;
	}
	
	
	
	public InvoiceList getInvoice(List<Person> people, List<Product> products, List<Customer> customers){
	
		InvoiceList invoiceList = new InvoiceList();
		ArrayList<Product> invoiceProducts = null;
		//this will need to be ahcne to the linked list once tim is done with it!***********************^^^^^^^^^^^^^^^^^
		Connection conn = dbConnection.getConnection();
		PreparedStatement ps;
		ResultSet rs, rs2, rs3;
		String productQuery = "SELECT * FROM Invoice";
		Invoice invoice = null;
		String invoiceCode, customerCode;
		DateTime invoiceTime;
		Customer customer = null;
		Person sp = null;
		int invoiceID, customerID, salesPersonID;
		try
		{
			ps = conn.prepareStatement(productQuery);
			rs = ps.executeQuery();
			while(rs.next()) {
				invoiceID = rs.getInt("InvoiceID");
				invoiceCode = rs.getString("InvoiceCode");
				customerID = rs.getInt("CustomerID");
				salesPersonID = rs.getInt("SalesPerson");
				invoiceProducts = getInvoiceProducts(invoiceID, products);
				DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-mm-dd");
				invoiceTime = formatter.parseDateTime(rs.getString("InvoiceDate"));
				ps = conn.prepareStatement("SELECT CustomerCode FROM Customer WHERE CustomerID = ?");
				ps.setInt(1, customerID);
				rs2 = ps.executeQuery();
				rs2.next();
				customerCode = rs2.getString("CustomerCode");
				customer = FlatFileReader.codeGetCustomer(customerCode, customers);
				ps = conn.prepareStatement("SELECT PersonCode FROM Person WHERE PersonID = ?");
				ps.setInt(1, salesPersonID);
				rs3 = ps.executeQuery();
				rs3.next();
				sp = FlatFileReader.codeGetPerson(rs3.getString("PersonCode"), people);
				
				//still need to find person and cusomter, then this will be done excpet ofr the fany stuff in the getinvoiceproducts that deasl with the weird buisness rules and the withmovie or not
				invoice = new Invoice(invoiceCode, invoiceTime, customer, sp, invoiceProducts);
				invoiceList.add(invoice);
				rs2.close();
				rs3.close();
			}
			rs.close();
			ps.close();
			conn.close();
		}
		catch (SQLException e)
		{
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		
		return invoiceList;
	}
	
	public ArrayList<Product> getInvoiceProducts(int invoiceID, List<Product> products) {
		ArrayList<Product> productList = new ArrayList<Product>();
		Connection conn = dbConnection.getConnection();
		PreparedStatement ps;
		ResultSet rs, rs2, rs3, rs4;
		String productQuery = "SELECT ProductID FROM InvoiceProduct WHERE InvoiceID = ?";
		Address a = null;
		String productCode;
		
		try
		{
			ps = conn.prepareStatement(productQuery);
			ps.setInt(1, invoiceID);
			rs = ps.executeQuery();
		 
			while(rs.next()) {
				ps = conn.prepareStatement("SELECT * FROM Products WHERE ProductID = ?");
				ps.setInt(1, rs.getInt("ProductID"));
				rs2 = ps.executeQuery();
				rs2.next();
				productCode = rs2.getString("ProductCode");
				ps = conn.prepareStatement("SELECT * FROM InvoiceProduct WHERE ProductID = ?");
				ps.setInt(1, rs.getInt("ProductID"));
				rs3 = ps.executeQuery();
				rs3.next();
				String subCode;
				
				if(rs2.getString("ProductType").equals("M")) {
					DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
					DateTime movieTime = formatter.parseDateTime(rs2.getString("TimeMovie"));
					String name = rs2.getString("ProductName");
					
					a = getAddress(rs2.getInt("AddressID"));
					
					
					String screenNo = rs2.getString("SeatNumber");
					double price = rs2.getDouble("ProductPrice");
					
					MovieTicket ticket = new MovieTicket(productCode, movieTime, name, a, screenNo, price);
					ticket.setAmount(rs3.getInt("QuantityOne"));
					productList.add(ticket);
					
					
				}else if(rs2.getString("ProductType").equals("R")) {
					String name = rs2.getString("ProductName");
					double price = rs2.getDouble("ProductPrice");
					
					Refreshment refreshment = new Refreshment(productCode, name, price);
					refreshment.setAmount(rs3.getInt("QuantityOne"));
					if(rs3.getString("SubProduct") != null) {
						refreshment.isWithTicket();
					}
					productList.add(refreshment);
					
				}else if(rs2.getString("ProductType").equals("S")) {
					DateTimeFormatter formatterS = DateTimeFormat.forPattern("yyyy-mm-dd");
					DateTime startTime = formatterS.parseDateTime(rs2.getString("EventStart"));
					DateTime endTime = formatterS.parseDateTime(rs2.getString("EventEnd"));
					String name = rs2.getString("ProductName");
					double price = rs2.getDouble("ProductPrice");
					
					SeasonPass seasonPass= new SeasonPass(productCode, name, startTime, endTime, price);
					seasonPass.setAmount(rs3.getInt("QuantityOne"));
					productList.add(seasonPass);
					
				}else{
					double price = rs2.getDouble("ProductPrice");
					ParkingPass parkingPass = new ParkingPass(productCode, price);
					parkingPass.setAmount(rs3.getInt("QuantityOne"));
					if((subCode = rs3.getString("SubProduct")) != null) {
						parkingPass.isWithTicket();
						//parkingPass.setAmount(FlatFileReader.codeGetProduct(subCode, products).getAmount());
						ps = conn.prepareStatement("SELECT QuantityOne FROM InvoiceProduct AS ip JOIN Products AS p ON ip.ProductID=p.ProductID WHERE p.ProductCode = ?");
						ps.setString(1, subCode);
						rs4 = ps.executeQuery();
						rs4.next();
						parkingPass.setAmount(rs4.getInt("QuantityOne"));
						rs4.close();
						
					}
					productList.add(parkingPass);
				}
				rs2.close();
				rs3.close();
			}
			rs.close();
			ps.close();
			conn.close();
		}
		catch (SQLException e)
		{
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	return productList;
	}
	
}

