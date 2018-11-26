package com.ceg.ext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import dbInteractions.dbConnection;

/*
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 * 15 methods in total, add more if required.
 * Do not change any method signatures or the package name.
 * 
 */

public class InvoiceData {

	/**
	 * 1. Method that removes every person record from the database
	 */
	public static void removeAllPersons(String PersonID) {
		try {
			Connection conn = dbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT PersonID FROM Person");
			ps.setInt(1, Integer.parseInt(PersonID));
			ResultSet rs = ps.executeQuery();
			int person;
			if(rs.next()) {
				person = rs.getInt("person");
			} else {
				ps = conn.prepareStatement("Delete from Person PersonID");
				ps.setInt(1, Integer.parseInt(PersonID));
				rs = ps.executeQuery();
				ps.executeUpdate();
				
	    		rs.close();
	    		ps.close();
	    	} 
		}
			catch (Exception e) {
	    		e.printStackTrace();
	    	}
	}
				
		//DELETE FROM PERSON WHERE PersonID is not null
		//DELETE FROM PERSON WHERE PERSONID <> NULL;

	/**
	 * 2. Method to add a person record to the database with the provided data.
	 * 
	 * 
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country) {
		
		try {
			Connection conn = dbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT ZipID FROM Zip WHERE ZipCode = ?");
			ps.setInt(1, Integer.parseInt(zip));
			ResultSet rs = ps.executeQuery();
			int zipID, countryID;
			if(rs.next()) {
				zipID = rs.getInt("ZipID");
			} else {
				ps = conn.prepareStatement("INSERT INTO Zip (ZipCode) VALUES (?)");
				ps.setInt(1, Integer.parseInt(zip));
				rs = ps.executeQuery();
				
				ps = conn.prepareStatement("SELECT ZipID FROM Zip WHERE ZipCode = ?");
				ps.setInt(1, Integer.parseInt(zip));
				rs = ps.executeQuery();
				rs.next();
				zipID = rs.getInt("ZipID");
			}
			ps = conn.prepareStatement("SELECT CountryID FROM Country WHERE Country = ?");
			ps.setInt(1, Integer.parseInt(zip));
			rs = ps.executeQuery();
			if(rs.next()) {
				countryID = rs.getInt("CountryID");
			} else {
				ps = conn.prepareStatement("INSERT INTO Country (Country) VALUES (?)");
				ps.setString(1, country);
				rs = ps.executeQuery();
				ps = conn.prepareStatement("SELECT CountryID FROM Country WHERE Country = ?");
				ps.setString(1, country);
				rs = ps.executeQuery();
				countryID = rs.getInt("CountryID");
			}
			ps = conn.prepareStatement("INSERT INTO `Address` (`Address`, `City`, `State`, `ZipID`, `CountryID`) VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setInt(4, zipID);
			ps.setInt(5, countryID);
			ps.executeUpdate();
			
			//get the address ID so I can add it to the person comit to the db
			ps = conn.prepareStatement("SELECT CountryID FROM Country WHERE Country = ?");
			ps.setString(1, state);
			rs = ps.executeQuery();
			int addressID = rs.getInt("CountryID");
			
			ps = conn.prepareStatement("INSERT INTO `Person` (PersonLastName, PersonFirstName, AddressID, PersonCode ) VALUES (?, ?, ?, ?)");
			ps.setString(1, lastName);
			ps.setString(2, firstName);
			ps.setInt(3, addressID);
			ps.setString(4, personCode);
			ps.executeUpdate();
			
    		rs.close();
    		ps.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
		
	/**
	 * 3. Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	//INSERT INTO `Email` (`EmailID`, `PersonID`, `EmailAddress`) VALUES (031, 021, 'email@email.com');
	public static void addEmail(String personCode, String email, String EmailAddress) {
		try {
			Connection conn = dbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT EmailID FROM Email WHERE PersonCode = ?");
			ps.setInt(1, Integer.parseInt(email));
			ResultSet rs = ps.executeQuery();
			int emailID, PersonID = 0;
			if(rs.next()) {
				emailID = rs.getInt("emailID");
			} else {
				ps = conn.prepareStatement("INSERT INTO Email (EmailID) VALUES (?)");
				ps.setInt(1, Integer.parseInt(email));
				rs = ps.executeQuery();
				
				ps = conn.prepareStatement("SELECT EmailID FROM Email WHERE PersonCode = ?");
				ps.setInt(1, Integer.parseInt(email));
				rs = ps.executeQuery();
				rs.next();
				emailID = rs.getInt("emailID");
			}
			ps = conn.prepareStatement("SELECT CountryID FROM Country WHERE Country = ?");
			ps.setInt(1, Integer.parseInt(email));
			rs = ps.executeQuery();

			ps = conn.prepareStatement("INSERT INTO `Email` (`EmailID`, `PersonID`, `EmailAddress`) VALUES (031, 021, 'email@email.com');");
			ps.setInt(1, emailID);
			ps.setInt(2, PersonID);
			ps.setString(3, EmailAddress);
			ps.executeUpdate();
    		rs.close();
    		ps.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}

	/**
	 * 4. Method that removes every customer record from the database
	 */
	// DELETE FROM CUSTOMERS WHERE CUSTOMERID <> NULL;
	public static void removeAllCustomers() {
		try {
			Connection conn = dbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT PersonID FROM Person");
			//ps.setInt(1, Integer.parseInt(CustomerID));
			ResultSet rs = ps.executeQuery();
			int customer;
			if(rs.next()) {
				customer = rs.getInt("customer");
			} else {
				ps = conn.prepareStatement("Delete from Customer");
				//ps.setInt(1, Integer.parseInt(CustomerID));
				rs = ps.executeQuery();
				ps.executeUpdate();
				
	    		rs.close();
	    		ps.close();
	    	} 
		}
			catch (Exception e) {
	    		e.printStackTrace();
	    	}
	}

	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode,String name, String street, String city, String state, String zip, String country) {}
	
	/**
	 * 5. Removes all product records from the database
	 */
	//DEELTE FROM PRODUCTS WHERE PRODUCTID <> NULL;
	public static void removeAllProducts() {
		try {
			Connection conn = dbConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT PersonID FROM Person");
			//ps.setInt(1, Integer.parseInt(ProductID));
			ResultSet rs = ps.executeQuery();
			int product;
			if(rs.next()) {
				product = rs.getInt("product");
			} else {
				ps = conn.prepareStatement("Delete from Product");
				//ps.setInt(1, Integer.parseInt(ProductID));
				rs = ps.executeQuery();
				ps.executeUpdate();
				
	    		rs.close();
	    		ps.close();
	    	} 
		}
			catch (Exception e) {
	    		e.printStackTrace();
	    	}
	}

	/**
	 * 6. Adds an movieTicket record to the database with the provided data.
	 */
	//INSERT INTO `Products` (`ProductID`, `ProductCode`, `ProductType`, `ProductName`, `TimeMovie`, `AddressID`, `SeatNumber`, `EventStart`, `EventEnd`, `ProductPrice`) VALUES (013, 'fp12', 'M', 'Movie', '2018-10-5 18:00', 027, '3A', NULL, NULL, 13.50);
	public static void addMovieTicket( String ProductID, String productCode, String dateTime, String movieName, String street, String city,String state, String zip, String country, String screenNo, double pricePerUnit) {
		try {
				Connection conn = dbConnection.getConnection(); 
				PreparedStatement ps = conn.prepareStatement("SELECT ProductID FROM Product WHERE ProductType = M");
				ps.setInt(1,  Integer.parseInt(ProductID));
				ResultSet rs = ps.executeQuery();
				int product;
				if(rs.next()) {
					product = rs.getInt("product");
				} else {
					ps = conn.prepareStatement("INSERT INTO Product (ProductID) VALUES (?)");
					ps.setInt(1, Integer.parseInt(ProductID));
					rs = ps.executeQuery();
					
					ps = conn.prepareStatement("SELECT ProductID FROM Product WHERE ProductCode = ?");
					ps.setInt(1, Integer.parseInt(ProductID));
					rs = ps.executeQuery();
					rs.next();
					product = rs.getInt("product");
				}
				ps = conn.prepareStatement("SELECT ProductID FROM Product WHERE ProductType = ?");
				ps.setInt(1, Integer.parseInt(ProductID));
				rs = ps.executeQuery();

				ps = conn.prepareStatement("INSERT INTO `Products` (`ProductID`, `ProductCode`, `ProductType`, `ProductName`, `TimeMovie`, `AddressID`, `SeatNumber`, `EventStart`, `EventEnd`, `ProductPrice`) VALUES (013, 'fp12', 'M', 'Movie', '2018-10-5 18:00', 027, '3A', NULL, NULL, 13.50);");
				ps.setString(1, ProductID);
				ps.setString(2, productCode);
				ps.setString(3, dateTime);
				ps.setString(4, movieName);
				ps.setString(5, street);
				ps.setString(6, city);
				ps.setString(7, state);
				ps.setString(8, zip);
				ps.setString(9, country);
				ps.setString(10, screenNo);
				ps.setDouble(11, pricePerUnit);
				ps.executeUpdate();
				ps.executeUpdate();
				
	    		rs.close();
	    		ps.close();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
		}
	/**
	 * 7. Adds a seasonPass record to the database with the provided data.
	 */
	//INSERT INTO `Products` (`ProductID`, `ProductCode`, `ProductType`, `ProductName`, `TimeMovie`, `AddressID`, `SeatNumber`, `EventStart`, `EventEnd`, `ProductPrice`) VALUES (014, 'xer4', 'S', 'Season Pass', NULL, NULL, NULL, '2018-10-12', '2018-10-16', 100.00);
	public static void addSeasonPass(String ProductID, String productCode, String name, String seasonStartDate, String seasonEndDate,	double cost) {
	try {
		Connection conn = dbConnection.getConnection(); 
		PreparedStatement ps = conn.prepareStatement("SELECT ProductID FROM Product WHERE ProductType = S");
		ps.setInt(1,  Integer.parseInt(ProductID));
		ResultSet rs = ps.executeQuery();
		int product;
		if(rs.next()) {
			product = rs.getInt("product");
		} else {
			ps = conn.prepareStatement("INSERT INTO Product (ProductID) VALUES (?)");
			ps.setInt(1, Integer.parseInt(ProductID));
			rs = ps.executeQuery();
			
			ps = conn.prepareStatement("SELECT ProductID FROM Product WHERE ProductCode = ?");
			ps.setInt(1, Integer.parseInt(ProductID));
			rs = ps.executeQuery();
			rs.next();
			product = rs.getInt("product");
		}
		ps = conn.prepareStatement("SELECT ProductID FROM Product WHERE ProductType = ?");
		ps.setInt(1, Integer.parseInt(ProductID));
		rs = ps.executeQuery();

		ps = conn.prepareStatement("INSERT INTO `Products` (`ProductID`, `ProductCode`, `ProductType`, `ProductName`, `TimeMovie`, `AddressID`, `SeatNumber`, `EventStart`, `EventEnd`, `ProductPrice`) VALUES (013, 'fp12', 'M', 'Movie', '2018-10-5 18:00', 027, '3A', NULL, NULL, 13.50);");
		ps.setString(1, ProductID);
		ps.setString(2, productCode);
		ps.setString(3, seasonStartDate);
		ps.setString(4, seasonEndDate);
		ps.setDouble(5, cost);
		ps.executeUpdate();
		ps.executeUpdate();
		
		rs.close();
		ps.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	/**
	 * 8. Adds a ParkingPass record to the database with the provided data.
	 */
	//INSERT INTO `Products` (`ProductID`, `ProductCode`, `ProductType`, `ProductName`, `TimeMovie`, `AddressID`, `SeatNumber`, `EventStart`, `EventEnd`, `ProductPrice`) VALUES (015, '90fa', 'P', NULL, NULL, NULL, NULL, NULL, NULL, 12.00);
	public static void addParkingPass(String ProductID, String productCode, double parkingFee) {
	try {
		Connection conn = dbConnection.getConnection(); 
		PreparedStatement ps = conn.prepareStatement("SELECT ProductID FROM Product WHERE ProductType = P");
		ps.setInt(1,  Integer.parseInt(ProductID));
		ResultSet rs = ps.executeQuery();
		int product;
		if(rs.next()) {
			product = rs.getInt("product");
		} else {
			ps = conn.prepareStatement("INSERT INTO Product (ProductID) VALUES (?)");
			ps.setInt(1, Integer.parseInt(ProductID));
			rs = ps.executeQuery();
			
			ps = conn.prepareStatement("SELECT ProductID FROM Product WHERE ProductCode = ?");
			ps.setInt(1, Integer.parseInt(ProductID));
			rs = ps.executeQuery();
			rs.next();
			product = rs.getInt("product");
		}
		ps = conn.prepareStatement("SELECT ProductID FROM Product WHERE ProductType = ?");
		ps.setInt(1, Integer.parseInt(ProductID));
		rs = ps.executeQuery();

		ps = conn.prepareStatement("INSERT INTO `Products` (`ProductID`, `ProductCode`, `ProductType`, `ProductName`, `TimeMovie`, `AddressID`, `SeatNumber`, `EventStart`, `EventEnd`, `ProductPrice`) VALUES (013, 'fp12', 'M', 'Movie', '2018-10-5 18:00', 027, '3A', NULL, NULL, 13.50);");
		ps.setString(1, ProductID);
		ps.setString(2, productCode);
		ps.setDouble(3, parkingFee);
		ps.executeUpdate();
		ps.executeUpdate();
		
		rs.close();
		ps.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	/**
	 * 9. Adds a refreshment record to the database with the provided data.
	 */
	//INSERT INTO `Products` (`ProductID`, `ProductCode`, `ProductType`, `ProductName`, `TimeMovie`, `AddressID`, `SeatNumber`, `EventStart`, `EventEnd`, `ProductPrice`) VALUES (015, '32f4', 'R', 'Refreshment', NULL, NULL, NULL, NULL, NULL, 1.50);
	public static void addRefreshment(String ProductID, String productCode, String name, double cost) {
	try {
		Connection conn = dbConnection.getConnection(); 
		PreparedStatement ps = conn.prepareStatement("SELECT ProductID FROM Product WHERE ProductType = R");
		ps.setInt(1,  Integer.parseInt(ProductID));
		ResultSet rs = ps.executeQuery();
		int product;
		if(rs.next()) {
			product = rs.getInt("product");
		} else {
			ps = conn.prepareStatement("INSERT INTO Product (ProductID) VALUES (?)");
			ps.setInt(1, Integer.parseInt(ProductID));
			rs = ps.executeQuery();
			
			ps = conn.prepareStatement("SELECT ProductID FROM Product WHERE ProductCode = ?");
			ps.setInt(1, Integer.parseInt(ProductID));
			rs = ps.executeQuery();
			rs.next();
			product = rs.getInt("product");
		}
		ps = conn.prepareStatement("SELECT ProductID FROM Product WHERE ProductType = ?");
		ps.setInt(1, Integer.parseInt(ProductID));
		rs = ps.executeQuery();

		ps = conn.prepareStatement("INSERT INTO `Products` (`ProductID`, `ProductCode`, `ProductType`, `ProductName`, `TimeMovie`, `AddressID`, `SeatNumber`, `EventStart`, `EventEnd`, `ProductPrice`) VALUES (013, 'fp12', 'M', 'Movie', '2018-10-5 18:00', 027, '3A', NULL, NULL, 13.50);");
		ps.setString(1, ProductID);
		ps.setString(2, productCode);
		ps.setString(3, name);
		ps.setDouble(11, cost);
		ps.executeUpdate();
		ps.executeUpdate();
		
		rs.close();
		ps.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	/**
	 * 10. Removes all invoice records from the database
	 */
	// REMOVE FROM INVOICE WHERE INVOICEID <> NULL;
	public static void removeAllInvoices() {
	try {
		Connection conn = dbConnection.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT InvoiceID FROM Invoice");
		//ps.setInt(1, Integer.parseInt(InvoiceID));
		ResultSet rs = ps.executeQuery();
		int invoice;
		if(rs.next()) {
			invoice = rs.getInt("invoice");
		} else {
			ps = conn.prepareStatement("Delete from Invoice");
			//ps.setInt(1, Integer.parseInt(InvoiceID));
			rs = ps.executeQuery();
			ps.executeUpdate();
			
    		rs.close();
    		ps.close();
    	} 
	}
		catch (Exception e) {
    		e.printStackTrace();
    	}
}

	/**
	 * 11. Adds an invoice record to the database with the given data.
	 */
	//INSERT INTO `Invoice` (`InvoiceID`, `CustomerID`, `InvoiceDate`) VALUES (005, 001, '2018-11-21');
	public static void addInvoice(String invoiceCode, String customerCode, String salesPersonCode, String invoiceDate) {}

	/**
	 * 12. Adds a particular movieticket (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given number of units
	 */

	public static void addMovieTicketToInvoice(String invoiceCode, String productCode, int quantity) {}

	/**
	 * 13. Adds a particular seasonpass (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given begin/end dates
	 */
	public static void addSeasonPassToInvoice(String invoiceCode, String productCode, int quantity) {}

     /**
     * 14. Adds a particular ParkingPass (corresponding to <code>productCode</code> to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity.
     * NOTE: ticketCode may be null
     */
    public static void addParkingPassToInvoice(String invoiceCode, String productCode, int quantity, String ticketCode) {}
	
    /**
     * 15. Adds a particular refreshment (corresponding to <code>productCode</code> to an 
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity. 
     */
    public static void addRefreshmentToInvoice(String invoiceCode, String productCode, int quantity) {}

}
