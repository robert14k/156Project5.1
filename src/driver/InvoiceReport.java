package driver;

import java.util.ArrayList;
import java.util.List;

import consoleWriter.ConsoleWriter;
import datacontainers.Customer;
import datacontainers.Invoice;
import datacontainers.Person;
import datacontainers.Product;
import dbInteractions.DBReader;
import fileReader.FlatFileReader;
import fileWriter.JsonWriter;

public class InvoiceReport {

	public static void main(String[] args) {
		
		FlatFileReader fr = new FlatFileReader();
		DBReader dbr = new DBReader();
		
		//List<Person> personList = fr.readPersons();
		List<Person> personList = dbr.getPersons();
			
		//List<Customer> customerList = fr.readCusotmers(personList);
		List<Customer> customerList = dbr.getCusotmers(personList);
		
		//List<Product> productList = fr.readProducts();
		List<Product> productList = dbr.getProducts();
		
		//List<Invoice> invoiceList = fr.readInvoice(personList, productList, customerList);
		List<Invoice> invoiceList = dbr.getInvoice(personList, productList, customerList);
		
		ConsoleWriter cw = new ConsoleWriter();
		
		cw.InvoiceConsolePrinter(invoiceList);
		//we will then need to create consol wrtier, it takes invoice list and argumetn and generates invoice report
		//This will probs need an invoice writer class assositated with it
	}

}
