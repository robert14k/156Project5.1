package datacontainers;

public class ParkingPass extends Service{

	private double parkingFee;
	private boolean withTicket;
	private int numTicket;
	
	public ParkingPass(String productCode, double parkingFee) {
		super(productCode);
		this.parkingFee = parkingFee;
		this.tax = parkingFee * 0.04;
		this.withTicket = false;
		this.numTicket = 0;
	}
	
	public ParkingPass(ParkingPass old) {
		super(old.productCode);
		this.parkingFee = old.parkingFee;
		this.tax = old.tax;
		this.withTicket = false;
		this.numTicket = 0;
	}
	

	public void setnumTicket(int ticket) {
		this.numTicket = ticket;
	}
	
	public int getnumTicket(int ticket) {
		return this.numTicket;
	}
	
	public double getParkingFee() {
		return this.parkingFee;
	}

	public void setParkingFee(double parkingFee) {
		this.parkingFee = parkingFee;
	}
	
	public void setTotalCost() {
		this.totalCost = this.tax + this.parkingFee;
	}

	public boolean isWithTicket() {
		return withTicket;
	}

	public void setWithTicket(boolean withTicket) {
		this.withTicket = withTicket;
	}
	
	public double getCost() {
		
		return this.getParkingFee();
	}
	
	public double getTax() {
		return this.tax;
	}
	
	public String getType() {
		return "ParkingPass";
	}
	
	public int getParkingAmount() {
		int count;
		if(this.numTicket > this.amount) {
			count = 0;
		}else {
			count = (this.amount - this.numTicket);
		}
		return count;
	}
	
	public void Print(Double productCost) {
		double sub, tax, total;
		int free = 0;
		int count;
		if(this.numTicket > this.amount) {
			free = this.amount;
			count = 0;
		}else {
			count = (this.amount - this.numTicket);
			free = count;
		}
		if(this.numTicket >= this.amount) {
			count = this.amount;
		}else {
			count = (this.amount - this.numTicket);
		}
		free = count;
		sub = count * this.parkingFee;
		tax = this.tax * count;
		total = sub + tax;
		String product = String.format("%s (%d units @ %.2f with %d free)", "ParkingPass", this.amount, this.parkingFee, free);
		System.out.println(String.format("%-4s      %-60s   $%-9.2f $%-9.2f $%-9.2f", this.productCode , product, sub, tax, total));
	}
	
	
	
}
