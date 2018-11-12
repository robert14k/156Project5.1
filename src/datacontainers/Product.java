package datacontainers;

import org.joda.time.DateTime;

public abstract class Product {

	protected String productCode;
	protected double tax;
	protected double totalCost;
	protected double discount;
	protected int amount;

	public Product(String productCode) {
		super();
		this.productCode = productCode;
		this.amount = 1;
	}
	
	public Product(Product old) {
		super();
		this.productCode = old.productCode;
		this.amount = old.amount;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
		
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void setWithTicket(boolean withTicket) {
		
	}
	
	public boolean isWithTicket() {
		return false;
	}

	public abstract double getCost();
	
	public boolean isRefreshment() {
		return false;
	}
	
	public boolean isTicket() {
		return false;
	}
	
	public void setnumTicket(int ticket) {
	}
	public int getnumTicket() {
		return 0;
	}
	
	public String getType() {
		return null;
	}
	
	public DateTime getStartDate() {
		return null;
	}
	
	public DateTime getEndDate() {
		return null;
	}

	public void Print(Double productCost) {
		
	}
	
	public double computeCost(DateTime invoiceDate) {
		return 0.0;
	}
	
	public int getParkingAmount() {
		return 0;
	}
}
