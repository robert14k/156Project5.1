package driver;

import datacontainers.*;
import fileReader.FlatFileReader;
import fileWriter.*;
import java.util.List;
//import java.util.ArrayList;

public class DataConverter {

	public static void dataConverter() {
		//quick test to make sure the reader and witrer and person class work 
		// this wil look diffrent once were actuallly doen 
		
		FlatFileReader fr = new FlatFileReader();
		 
		List<Person> personList = fr.readPersons();
		
		JsonWriter jWriter = new JsonWriter();
		jWriter.jsonPersonConverter(personList);
		
		System.out.println("Person json converted sucessfully");
		
		//XMLWriter xmlWriter = new XMLWriter();
	    //xmlWriter.xmlPersonConverter(personList);
	    
	    System.out.println("Person XML converted sucessfully");
		
		//give the person list to the customer reader
		
		List<Customer> customerList = fr.readCusotmers(personList);
		
		jWriter.jsonCusotmerConverter(customerList);
		
		System.out.println("customer json converted sucessfully");
		
		List<Product> productList = fr.readProducts();
		jWriter.jsonProductConverter(productList);
		
		System.out.println("products json converted sucessfully");
		
		
	}

}



//This program is irrelevant
