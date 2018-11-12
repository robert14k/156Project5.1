package datacontainers;

public abstract class Ticket extends Product{

	public Ticket(String productCode) {
		super(productCode);
	}
	
	public abstract double getCost();
	
	public boolean isTicket() {
		return true;
	}
}
