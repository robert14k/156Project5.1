package datacontainers;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class Invoice {

	private String invoiceCode;
	private DateTime invoiceDate;
	private Customer customer;
	private Person salesperson;
	private ArrayList<Product> productList;
	
	public Invoice(String invoiceCode, DateTime invoiceDate, Customer customer, Person salesperson,
			ArrayList<Product> productList) {
		super();
		this.invoiceCode = invoiceCode;
		this.invoiceDate = invoiceDate;
		this.customer = customer;
		this.salesperson = salesperson;
		this.productList = productList;
	}
	
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	public DateTime getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(DateTime invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Person getSalesperson() {
		return salesperson;
	}
	public void setSalesperson(Person salesperson) {
		this.salesperson = salesperson;
	}
	public ArrayList<Product> getProductList() {
		return productList;
	}
	public void setProductList(ArrayList<Product> productList) {
		this.productList = productList;
	}

	public double getProductsSubtotal() {
		double sum = 0;
		int amount;
		for(Product a : this.productList) {
			amount = a.getAmount();
			if(a.getType().equals("SeasonPass")) {
				sum += (a.computeCost(this.invoiceDate) * amount);
			}else if(a.getType().equals("MovieTicket")){
				sum += ((a.getCost() - a.getDiscount()) * amount);
			}else if(a.getType().equals("ParkingPass")) {
				amount = a.getParkingAmount();
			}else{
				sum += a.getCost() * amount;
			}
			
//				if(this.invoiceDate.isAfter(a.getStartDate())) {
//					sum += (((((Days.daysBetween(this.invoiceDate.toLocalDate(), a.getEndDate().toLocalDate()).getDays()) / Days.daysBetween(a.getStartDate().toLocalDate(), a.getEndDate().toLocalDate()).getDays())) * a.getCost()) + 8.0) * a.getAmount();
//				}
			
			
		}
		return sum;
	}
	
	public double getFee() {
		if(this.getCustomer().isStudent()) {
			return 6.75;
		}else {
			return 0;
		}
	}
	
	public double getTax() {
		double sum = 0;
		for(Product a : this.productList) {
			sum += a.getTax() * a.getAmount();
		}
		return sum;
	}
	
	public double getProuductsDiscount(double amount) {
		if(this.getCustomer().isStudent()) {
			return amount * 0.08;
		}else {
			return 0;
		}
	}
	
	public boolean hasBoth() {
		boolean ticket = false;
		boolean food = false;
		for(Product a : this.productList) {
			if(a.isTicket()) {
				ticket = true;
			}else if(a.isRefreshment()){
				food = true;
			}
		}
		if(ticket && food) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public void editRefreshment() {
		for(Product a : this.productList) {
			if(a.isRefreshment()) {
				a.setWithTicket(true);
			}
		}
	}
		
}
