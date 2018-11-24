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
import datacontainers.MovieTicket;
import datacontainers.ParkingPass;
import datacontainers.Person;
import datacontainers.Product;
import datacontainers.Refreshment;
import datacontainers.SeasonPass;
import datacontainers.Student;
import dbInteractions.dbConnection;

public class DBReader {
	
	
	public ArrayList<Person> getPersons() {
		//connect to database, make a statement, line by line, results, and grab stuff and assign them
		
		Connection conn = dbConnection.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		String personQuery = "SELECT * FROM Person;";
		ArrayList<Person> people= new ArrayList<Person>();
		Person p;
		String id, first, last;
		int addressCode;
		Address a;
		try
		{
			ps = conn.prepareStatement(personQuery);
			rs = ps.executeQuery();
			while(rs.next()){
				
				id = rs.getString("PersonID");
				first = rs.getString("PersonFirstName");
				last = rs.getString("PersonLastName");
				addressCode = rs.getInt("AddressID");
				a = getAddress(addressCode);
				p = new Person(id, first, last, a);
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
			zip = Integer.toString(rs.getInt("ZipCode"));
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
				customerCode = rs.getString("CustomerID");
				name = rs.getString("CompanyName");
				a = getAddress(rs.getInt("AddressID"));
				contact = getPerson(rs.getInt("PersonID"));

				if(rs.getString("CustomerType").equals("S")){
					c = new Student(customerCode, contact, name, a);
				}else {
					c = new General(customerCode, contact, name, a);
				}
				customerList.add(c);
			}
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
				}	
			}
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
