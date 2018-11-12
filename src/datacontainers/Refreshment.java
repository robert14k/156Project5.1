package datacontainers;

public class Refreshment extends Service{

	private String name;
	private double cost;
	private boolean withTicket;
	
	public Refreshment(String productCode, String name, double cost) {
		super(productCode);
		this.name = name;
		this.cost = cost;
		this.tax = cost * 0.04;
		this.withTicket = false;
	}
	
	public Refreshment(Refreshment old) {
		super(old.productCode);
		this.name = old.name;
		this.cost = old.cost;
		this.tax = old.tax;
		this.withTicket = false;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isWithTicket() {
		return withTicket;
	}

	public void setWithTicket(boolean withTicket) {
		this.withTicket = withTicket;
	}
	
	public double getCost() {
		return this.cost;
	}
	
	public double getTax() {
		if(this.withTicket) {
			return (0.04 * (this.cost - (this.cost * 0.04)));
		}else {
			return (0.04 * this.cost);
		}
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public boolean isRefreshment() {
		return true;
	}
	
	public String getType() {
		return "Refreshment";
	}
	
	public void Print(Double productCost) {
		double sub, tax, total;
		
		sub = this.amount * productCost;
		tax = this.getTax()  * this.amount;
		total = sub + tax;

		String product = String.format("%s (%d units @ %.2f  5%% off if in advance)", this.name, this.amount, productCost);
		System.out.println(String.format("%-4s      %-60s   $%-9.2f $%-9.2f $%-9.2f", this.productCode, product, sub, tax, total));
	}
	
}
