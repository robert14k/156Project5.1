package com.ceg.ext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public static void removeAllPersons() {
		Connection conn = dbConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT PersonID from Person");
			ResultSet rs = ps.executeQuery();
			int id;
			while (rs.next()) {
				id = rs.getInt("PersonID");
				ps = conn.prepareStatement("DELETE FROM Customer WHERE PersonID = ?");
				ps.setInt(1, id);
				ps.executeUpdate();
				ps = conn.prepareStatement("DELETE FROM Email WHERE PersonID = ?");
				ps.setInt(1, id);
				ps.executeUpdate();
				ps = conn.prepareStatement("DELETE FROM Invoice WHERE SalesPerson = ?");
				ps.setInt(1, id);
				ps.executeUpdate();
			}
			
			ps = conn.prepareStatement("DELETE FROM Person");
			ps.executeUpdate();
	    	rs.close();
	    	ps.close();
		}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		Connection conn = dbConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT ZipID FROM Zip WHERE ZipCode = ?");
			ps.setString(1, zip);
			ResultSet rs = ps.executeQuery();
			int zipID, countryID;
			if(rs.next()) {
				zipID = rs.getInt("ZipID");
			} else {
				ps = conn.prepareStatement("INSERT INTO Zip (ZipCode) VALUES (?)");
				ps.setString(1, zip);
				ps.executeUpdate();

				ps = conn.prepareStatement("SELECT ZipID FROM Zip WHERE ZipCode = ?");
				ps.setString(1, zip);
				rs = ps.executeQuery();
				rs.next();
				zipID = rs.getInt("ZipID");
			}
			ps = conn.prepareStatement("SELECT CountryID FROM Country WHERE Country = ?");
			ps.setString(1, country);
			rs = ps.executeQuery();
			if(rs.next()) {
				countryID = rs.getInt("CountryID");
			} else {
				ps = conn.prepareStatement("INSERT INTO Country (Country) VALUES (?)");
				ps.setString(1, country);
				ps.executeUpdate();
				ps = conn.prepareStatement("SELECT CountryID FROM Country WHERE Country = ?");
				ps.setString(1, country);
				rs = ps.executeQuery();
				rs.next();
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
			ps = conn.prepareStatement("SELECT AddressID FROM Address WHERE Address = ?");
			ps.setString(1, street);
			rs = ps.executeQuery();
			rs.next();
			int addressID = rs.getInt("AddressID");

			ps = conn.prepareStatement("INSERT INTO `Person` (PersonLastName, PersonFirstName, AddressID, PersonCode ) VALUES (?, ?, ?, ?)");
			ps.setString(1, lastName);
			ps.setString(2, firstName);
			ps.setInt(3, addressID);
			ps.setString(4, personCode);
			ps.executeUpdate();

    		rs.close();
    		ps.close();
		}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	public static void addEmail(String personCode, String email) {
		Connection conn = dbConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT PersonID FROM Person WHERE PersonCode = ?");
			ps.setString(1, personCode);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int personID = rs.getInt("PersonID");
			ps = conn.prepareStatement("INSERT INTO Email (PersonID, EmailAddress) VALUES (?, ?)");
			ps.setInt(1, personID);
			ps.setString(2, email);
			ps.executeUpdate();
    		rs.close();
    		ps.close();
		}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	/**
	 * 4. Method that removes every customer record from the database
	 */
	// DELETE FROM CUSTOMERS WHERE CUSTOMERID <> NULL;
	public static void removeAllCustomers() {
		Connection conn = dbConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT CustomerID from Customer");
			ResultSet rs = ps.executeQuery();
			int id;
			while (rs.next()) {
				id = rs.getInt("CustomerID");
				ps = conn.prepareStatement("DELETE FROM Invoice WHERE CustomerID = ?");
				ps.setInt(1, id);
				ps.executeUpdate();
				
			}
			
			ps = conn.prepareStatement("DELETE FROM Customer");
			ps.executeUpdate();
	    	rs.close();
	    	ps.close();
		}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	
	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode,String name, String street, String city, String state, String zip, String country) {
		Connection conn = dbConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT PersonID from Person WHERE PersonCode = ?");
			ps.setString(1, primaryContactPersonCode);
			ResultSet rs = ps.executeQuery();
			int personID, addressID;
			rs.next();
			personID = rs.getInt("PersonID");
			
			ps = conn.prepareStatement("SELECT AddressID from Address WHERE Address = ?");
			ps.setString(1, street);
			ps.executeQuery();
			if(rs.next()) {
				addressID = rs.getInt("AddressID");
			}else {
				addressID = addAddress(street, city, state, zip, country);
			}
			ps = conn.prepareStatement("INSERT INTO Customer (CustomerType, PersonID, CompanyName, AddressID, CustomerCode) Values(?, ?, ?, ?, ?)");
			ps.setString(1, customerType);
			ps.setInt(2, personID);
			ps.setString(3, name);
			ps.setInt(4, addressID);
			ps.setString(5, customerCode);
			ps.executeUpdate();
			
			rs.close();
    		ps.close();
		}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	/**
	 * 5. Removes all product records from the database
	 */
	//DEELTE FROM PRODUCTS WHERE PRODUCTID <> NULL;
	public static void removeAllProducts() {
		Connection conn = dbConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT ProductID from Products");
			ResultSet rs = ps.executeQuery();
			int id;
			while (rs.next()) {
				id = rs.getInt("ProductID");
				ps = conn.prepareStatement("DELETE FROM InvoiceProduct WHERE ProductID = ?");
				ps.setInt(1, id);
				ps.executeUpdate();
			
			}
			
			ps = conn.prepareStatement("DELETE FROM Products");
			ps.executeUpdate();
	    	rs.close();
	    	ps.close();
		}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	/**
	 * 6. Adds an movieTicket record to the database with the provided data.
	 */
	//INSERT INTO `Products` (`ProductID`, `ProductCode`, `ProductType`, `ProductName`, `TimeMovie`, `AddressID`, `SeatNumber`, `EventStart`, `EventEnd`, `ProductPrice`) VALUES (013, 'fp12', 'M', 'Movie', '2018-10-5 18:00', 027, '3A', NULL, NULL, 13.50);
	public static void addMovieTicket(String productCode, String dateTime, String movieName, String street, String city,String state, String zip, String country, String screenNo, double pricePerUnit) {
		Connection conn = dbConnection.getConnection();
		try {
				
				PreparedStatement ps = conn.prepareStatement("SELECT AddressID from Address WHERE Address = ?");
				int addressID;
				ps.setString(1, street);
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					addressID = rs.getInt("AddressID");
				}else {
					addressID = addAddress(street, city, state, zip, country);
				}

				ps = conn.prepareStatement("INSERT INTO Products (ProductCode, ProductType, ProductName, TimeMovie, AddressID, SeatNumber, ProductPrice) VALUES (?, ?, ?, ?, ?, ?, ?)");
				ps.setString(1, productCode);
				ps.setString(2, "M");
				ps.setString(3, movieName);
				ps.setString(4, dateTime);
				ps.setInt(5, addressID);
				ps.setString(6, screenNo);
				ps.setDouble(7, pricePerUnit);
				ps.executeUpdate();

	    		rs.close();
	    		ps.close();
		}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		}
	/**
	 * 7. Adds a seasonPass record to the database with the provided data.
	 */
	//INSERT INTO `Products` (`ProductID`, `ProductCode`, `ProductType`, `ProductName`, `TimeMovie`, `AddressID`, `SeatNumber`, `EventStart`, `EventEnd`, `ProductPrice`) VALUES (014, 'xer4', 'S', 'Season Pass', NULL, NULL, NULL, '2018-10-12', '2018-10-16', 100.00);
	public static void addSeasonPass(String productCode, String name, String seasonStartDate, String seasonEndDate, double cost) {
		Connection conn = dbConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Products (ProductCode, ProductType, ProductName, EventStart, EventEnd, ProductPrice) VALUES (?, ?, ?, ?, ?, ?)");
			ps.setString(1, productCode);
			ps.setString(2, "S");
			ps.setString(3, name);
			ps.setString(4, seasonStartDate);
			ps.setString(5, seasonEndDate);
			ps.setDouble(6, cost);
			ps.executeUpdate();

    		ps.close();
		}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	/**
	 * 8. Adds a ParkingPass record to the database with the provided data.
	 */
	//INSERT INTO `Products` (`ProductID`, `ProductCode`, `ProductType`, `ProductName`, `TimeMovie`, `AddressID`, `SeatNumber`, `EventStart`, `EventEnd`, `ProductPrice`) VALUES (015, '90fa', 'P', NULL, NULL, NULL, NULL, NULL, NULL, 12.00);
	public static void addParkingPass(String productCode, double parkingFee) {
		Connection conn = dbConnection.getConnection();
		try {
			
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Products (ProductCode, ProductType, ProductPrice) VALUES (?, ?, ?)");
			ps.setString(1, productCode);
			ps.setString(2, "P");
			ps.setDouble(3, parkingFee);
			ps.executeUpdate();

    		ps.close();
		}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	/**
	 * 9. Adds a refreshment record to the database with the provided data.
	 */
	//INSERT INTO `Products` (`ProductID`, `ProductCode`, `ProductType`, `ProductName`, `TimeMovie`, `AddressID`, `SeatNumber`, `EventStart`, `EventEnd`, `ProductPrice`) VALUES (015, '32f4', 'R', 'Refreshment', NULL, NULL, NULL, NULL, NULL, 1.50);
	public static void addRefreshment(String productCode, String name, double cost) {
		Connection conn = dbConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO Products (ProductCode, ProductType, ProductName, ProductPrice) VALUES (?, ?, ?, ?)");
			ps.setString(1, productCode);
			ps.setString(2, "R");
			ps.setString(3, name);
			ps.setDouble(4, cost);
			ps.executeUpdate();

    		ps.close();
		}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	/**
	 * 10. Removes all invoice records from the database
	 */
	// REMOVE FROM INVOICE WHERE INVOICEID <> NULL;
	public static void removeAllInvoices() {
		Connection conn = dbConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT InvoiceID from Invoice");
			ResultSet rs = ps.executeQuery();
			int id;
			while (rs.next()) {
				id = rs.getInt("InvoiceID");
				ps = conn.prepareStatement("DELETE FROM InvoiceProduct WHERE InvoiceID = ?");
				ps.setInt(1, id);
				ps.executeUpdate();
				
			}
			
			ps = conn.prepareStatement("DELETE FROM Invoice");
			ps.executeUpdate();
	    	rs.close();
	    	ps.close();
	    	
		}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	/**
	 * 11. Adds an invoice record to the database with the given data.
	 */
	//INSERT INTO `Invoice` (`InvoiceID`, `CustomerID`, `InvoiceDate`) VALUES (005, 001, '2018-11-21');
	public static void addInvoice(String invoiceCode, String customerCode, String salesPersonCode, String invoiceDate) {
		Connection conn = dbConnection.getConnection();
		try {
			
			PreparedStatement ps = conn.prepareStatement("SELECT CustomerId from Customer WHERE CustomerCode = ?");
			ps.setString(1, customerCode);
			ResultSet rs = ps.executeQuery();
			int customerID, personID;
			rs.next();
			customerID = rs.getInt("CustomerID");
			
			ps = conn.prepareStatement("SELECT PersonID from Person WHERE PersonCode = ?");
			ps.setString(1, salesPersonCode);
			rs = ps.executeQuery();
			rs.next();
			personID = rs.getInt("PersonID");
			ps = conn.prepareStatement("INSERT INTO Invoice (CustomerID, SalesPerson, InvoiceDate, InvoiceCode) VALUES (?, ?, ?, ?)");
			ps.setInt(1, customerID);
			ps.setInt(2, personID);
			ps.setString(3, invoiceDate);
			ps.setString(4, invoiceCode);
			ps.executeUpdate();
			
		 	rs.close();
	    	ps.close();
	    	
		}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	/**
	 * 12. Adds a particular movieticket (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given number of units
	 */

	public static void addMovieTicketToInvoice(String invoiceCode, String productCode, int quantity) {
		Connection conn = dbConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT InvoiceID from Invoice WHERE InvoiceCode = ?");
			ps.setString(1, invoiceCode);
			ResultSet rs = ps.executeQuery();
			int invoiceID, productID;
			rs.next();
			invoiceID = rs.getInt("InvoiceID");
			
			ps = conn.prepareStatement("SELECT ProductID from Products WHERE ProductCode = ?");
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			rs.next();
			productID = rs.getInt("ProductID");
			
			ps = conn.prepareStatement("INSERT INTO InvoiceProduct (InvoiceID, ProductID, QuantityOne) VALUES ( ?, ?, ?)");
			ps.setInt(1, invoiceID);
			ps.setInt(2, productID);
			ps.setInt(3, quantity);
			ps.executeUpdate();
			
		 	rs.close();
	    	ps.close();
	    	
		}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	/**
	 * 13. Adds a particular seasonpass (corresponding to <code>productCode</code>
	 * to an invoice corresponding to the provided <code>invoiceCode</code> with
	 * the given begin/end dates
	 */
	public static void addSeasonPassToInvoice(String invoiceCode, String productCode, int quantity) {
		Connection conn = dbConnection.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT InvoiceID from Invoice WHERE InvoiceCode = ?");
			ps.setString(1, invoiceCode);
			ResultSet rs = ps.executeQuery();
			int invoiceID, productID;
			rs.next();
			invoiceID = rs.getInt("InvoiceID");
			
			ps = conn.prepareStatement("SELECT ProductID from Products WHERE ProductCode = ?");
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			rs.next();
			productID = rs.getInt("ProductID");
			
			ps = conn.prepareStatement("INSERT INTO InvoiceProduct (InvoiceID, ProductID, QuantityOne) VALUES ( ?, ?, ?)");
			ps.setInt(1, invoiceID);
			ps.setInt(2, productID);
			ps.setInt(3, quantity);
			ps.executeUpdate();
			
		 	rs.close();
	    	ps.close();
	    	
		}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

     /**
     * 14. Adds a particular ParkingPass (corresponding to <code>productCode</code> to an
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity.
     * NOTE: ticketCode may be null
     */
    public static void addParkingPassToInvoice(String invoiceCode, String productCode, int quantity, String ticketCode) {
    	Connection conn = dbConnection.getConnection();
    	try {
			
			PreparedStatement ps = conn.prepareStatement("SELECT InvoiceID from Invoice WHERE InvoiceCode = ?");
			ps.setString(1, invoiceCode);
			ResultSet rs = ps.executeQuery();
			int invoiceID, productID, subID;
			rs.next();
			invoiceID = rs.getInt("InvoiceID");
			
			ps = conn.prepareStatement("SELECT ProductID from Products WHERE ProductCode = ?");
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			rs.next();
			productID = rs.getInt("ProductID");
			
			ps = conn.prepareStatement("INSERT INTO InvoiceProduct (InvoiceID, ProductID, QuantityOne, SubProduct) VALUES (?, ?, ?, ?)");
			ps.setInt(1, invoiceID);
			ps.setInt(2, productID);
			ps.setInt(3, quantity);
			ps.setString(4, ticketCode);
			ps.executeUpdate();
			
		 	rs.close();
	    	ps.close();
	    	
    	}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
    }

    /**
     * 15. Adds a particular refreshment (corresponding to <code>productCode</code> to an
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity.
     */
    public static void addRefreshmentToInvoice(String invoiceCode, String productCode, int quantity) {
    	Connection conn = dbConnection.getConnection();
    	try {
			
			PreparedStatement ps = conn.prepareStatement("SELECT InvoiceID from Invoice WHERE InvoiceCode = ?");
			ps.setString(1, invoiceCode);
			ResultSet rs = ps.executeQuery();
			int invoiceID, productID;
			rs.next();
			invoiceID = rs.getInt("InvoiceID");
			
			ps = conn.prepareStatement("SELECT ProductID from Products WHERE ProductCode = ?");
			ps.setString(1, productCode);
			rs = ps.executeQuery();
			rs.next();
			productID = rs.getInt("ProductID");
			
			ps = conn.prepareStatement("INSERT INTO InvoiceProduct (InvoiceID, ProductID, QuantityOne) VALUES ( ?, ?, ?)");
			ps.setInt(1, invoiceID);
			ps.setInt(2, productID);
			ps.setInt(3, quantity);
			ps.executeUpdate();
			
		 	rs.close();
	    	ps.close();
	    	
    	}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
    }
    
    
    
    public static int addAddress(String street, String city, String state, String zip, String country) {
    	int addressID = 0;
    	Connection conn = dbConnection.getConnection();
    	try {
			
			PreparedStatement ps = conn.prepareStatement("SELECT ZipID from Zip WHERE ZipCode = ?");
			ps.setString(1, zip);
			ResultSet rs = ps.executeQuery();
			int zipID, countryID;
			if(rs.next()) {
				zipID = rs.getInt("ZipID");
			}else {
				ps = conn.prepareStatement("INSERT INTO Zip (ZipCode) Values(?)");
				ps.setString(1, zip);
				ps.executeUpdate();
				ps = conn.prepareStatement("SELECT ZipID from Zip WHERE ZipCode = ?");
				ps.setString(1, zip);
				rs = ps.executeQuery();
				rs.next();
				zipID = rs.getInt("ZipID");
			}
			
			ps = conn.prepareStatement("SELECT CountryID from Country WHERE Country = ?");
			ps.setString(1, country);
			rs = ps.executeQuery();
			if(rs.next()) {
				countryID = rs.getInt("CountryID");
			}else {
				ps = conn.prepareStatement("INSERT INTO Country (Country) Values(?)");
				ps.setString(1, country);
				ps.executeUpdate();
				ps = conn.prepareStatement("SELECT CountryID from Country WHERE Country = ?");
				ps.setString(1, country);
				rs = ps.executeQuery();
				rs.next();
				countryID= rs.getInt("CountryID");
			}
			ps = conn.prepareStatement("INSERT INTO Address (Address, City, State, ZipID, CountryID) Values(?, ?, ?, ?, ?)");
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setInt(4, zipID);
			ps.setInt(5, countryID);
			ps.executeUpdate();
			
			ps = conn.prepareStatement("SELECT AddressID from Address WHERE Address = ?");
			ps.setString(1, street);
			rs = ps.executeQuery();
			rs.next();
			addressID = rs.getInt("AddressID");
			
			rs.close();
    		ps.close();
    		
    	}
		catch (Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
    	return addressID;
	}

}
