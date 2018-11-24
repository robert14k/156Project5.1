package dbInteractions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import datacontainers.Address;
import datacontainers.Person;
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
		//need in order to return 
//		street = null;
//		city = null;
//		country = null;
//		state = null;
//		zip = null;
//		a = new Address(street, city, state, zip, country);
		
		try
		{
			ps = conn.prepareStatement(addressQuery);
			ps.setInt(1, code);
			rs = ps.executeQuery();
			while(rs.next()){

				street = rs.getString("Address");
				city = rs.getString("City");
				country = rs.getString("Country");
				state = rs.getString("State");
				zip = Integer.toString(rs.getInt("ZipCode"));
				a = new Address(street, city, state, zip, country);
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
		
		return a;
	}
	
}
