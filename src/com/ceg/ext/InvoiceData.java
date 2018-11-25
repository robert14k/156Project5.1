package com.ceg.ext;

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
		//DELETE FROM PERSON WHERE PersonID is not null
		//DELETE FROM PERSON WHERE PERSONID <> NULL;
	}

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
	//INSERT INTO `Address` (`AddressID`, `Address`, `City`, `State`, `ZipID`, `CountryID`) VALUES (021, "1111West11th Street", 'Ogallala', 'Nebraska', 001, 001);
	// INSERT INTO `Person` (`PersonID`, `PersonLastName`, `PersonFirstName`, `AddressID`) VALUES (021, 'Parker', 'Peter', 021);
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country) {}

	/**
	 * 3. Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	//INSERT INTO `Email` (`EmailID`, `PersonID`, `EmailAddress`) VALUES (031, 021, 'email@email.com');
	public static void addEmail(String personCode, String email) {}

	/**
	 * 4. Method that removes every customer record from the database
	 */
	// DELETE FROM CUSTOMERS WHERE CUSTOMERID <> NULL;
	public static void removeAllCustomers() {}

	public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode,String name, String street, String city, String state, String zip, String country) {}
	
	/**
	 * 5. Removes all product records from the database
	 */
	//DEELTE FROM PRODUCTS WHERE PRODUCTID <> NULL;
	public static void removeAllProducts() {}

	/**
	 * 6. Adds an movieTicket record to the database with the provided data.
	 */
	//INSERT INTO `Products` (`ProductID`, `ProductCode`, `ProductType`, `ProductName`, `TimeMovie`, `AddressID`, `SeatNumber`, `EventStart`, `EventEnd`, `ProductPrice`) VALUES (013, 'fp12', 'M', 'Movie', '2018-10-5 18:00', 027, '3A', NULL, NULL, 13.50);
	public static void addMovieTicket(String productCode, String dateTime, String movieName, String street, String city,String state, String zip, String country, String screenNo, double pricePerUnit) {}

	/**
	 * 7. Adds a seasonPass record to the database with the provided data.
	 */
	//INSERT INTO `Products` (`ProductID`, `ProductCode`, `ProductType`, `ProductName`, `TimeMovie`, `AddressID`, `SeatNumber`, `EventStart`, `EventEnd`, `ProductPrice`) VALUES (014, 'xer4', 'S', 'Season Pass', NULL, NULL, NULL, '2018-10-12', '2018-10-16', 100.00);
	public static void addSeasonPass(String productCode, String name, String seasonStartDate, String seasonEndDate,	double cost) {}

	/**
	 * 8. Adds a ParkingPass record to the database with the provided data.
	 */
	//INSERT INTO `Products` (`ProductID`, `ProductCode`, `ProductType`, `ProductName`, `TimeMovie`, `AddressID`, `SeatNumber`, `EventStart`, `EventEnd`, `ProductPrice`) VALUES (015, '90fa', 'P', NULL, NULL, NULL, NULL, NULL, NULL, 12.00);
	public static void addParkingPass(String productCode, double parkingFee) {}

	/**
	 * 9. Adds a refreshment record to the database with the provided data.
	 */
	//INSERT INTO `Products` (`ProductID`, `ProductCode`, `ProductType`, `ProductName`, `TimeMovie`, `AddressID`, `SeatNumber`, `EventStart`, `EventEnd`, `ProductPrice`) VALUES (015, '32f4', 'R', 'Refreshment', NULL, NULL, NULL, NULL, NULL, 1.50);
	public static void addRefreshment(String productCode, String name, double cost) {}

	/**
	 * 10. Removes all invoice records from the database
	 */
	// REMOVE FROM INVOICE WHERE INVOICEID <> NULL;
	public static void removeAllInvoices() {}

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
