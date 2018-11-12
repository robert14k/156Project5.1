package fileWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import datacontainers.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.joda.time.DateTime;
import java.lang.reflect.Type;


public class JsonWriter {

	public void jsonPersonConverter(List<Person> persons) {
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		File jsonOutput = new File("data/Persons.json");
		
		PrintWriter jsonPrintWriter = null;
		
		try {
			jsonPrintWriter = new PrintWriter(jsonOutput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for(Person a : persons) {
			String personOutput = gson.toJson(a);
			jsonPrintWriter.write(personOutput + "\n");
		}
		
		
		jsonPrintWriter.close();
		
	}
	
	public void jsonCusotmerConverter(List<Customer> customers) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		File jsonOutput = new File("data/Customers.json");
		
		PrintWriter jsonPrintWriter = null;
		
		try {
			jsonPrintWriter = new PrintWriter(jsonOutput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for(Customer a : customers) {
			String customerOutput = gson.toJson(a);
			jsonPrintWriter.write(customerOutput + "\n");
		}
		
		jsonPrintWriter.close();
	}
	
	public void jsonProductConverter(List<Product> products) {
		
		
		
		Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, serializer).setPrettyPrinting().create();
		File jsonOutput = new File("data/Products.json");
		
		PrintWriter jsonPrintWriter = null;
		
		try {
			jsonPrintWriter = new PrintWriter(jsonOutput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for(Product a : products) {
			String customerOutput = gson.toJson(a);
			jsonPrintWriter.write(customerOutput + "\n");
		}
		
		jsonPrintWriter.close();
	}
	
	private static JsonSerializer<DateTime> serializer = new JsonSerializer<DateTime>() {
		  public JsonElement serialize(DateTime dateTime, Type typeOfSrc, JsonSerializationContext 
			             context) {
	     
			  String dtString = dateTime.toString("yyyy-MM-dd HH:mm");
		      return new JsonPrimitive(dtString);
			  
		  }
		};

}
