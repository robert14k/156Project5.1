package datacontainers;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MovieTicket extends Ticket{
	
	private DateTime movieTime;
	private String movieName;
	private Address address;
	private String movieCode;
	private double price;
	
	public MovieTicket(String productCode, DateTime movieTime, String movieName, Address address, String movieCode,
			double price) {
		super(productCode);
		this.movieTime = movieTime;
		this.movieName = movieName;
		this.address = address;
		this.movieCode = movieCode;
		this.price = price;
		if((this.movieTime.getDayOfWeek() == 2) || (this.movieTime.getDayOfWeek() == 4)) {
			this.discount = price * 0.07;
		}else {
			this.discount = 0;
		}
		this.tax = (this.price - this.discount) * 0.06;
	}
	
	public MovieTicket(MovieTicket old) {
		super(old.productCode);
		this.movieTime = old.movieTime;
		this.movieName = old.movieName;
		this.address = old.address;
		this.movieCode = old.movieCode;
		this.price = old.price;
		this.discount = old.discount;
		this.tax = old.tax;
	}

	public DateTime getMovieTime() {
		return this.movieTime;
	}

	public void setMovieTime(DateTime movieTime) {
		this.movieTime = movieTime;
	}

	public String getMovieName() {
		return this.movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getMovieCode() {
		return this.movieCode;
	}

	public void setMovieCode(String movieCode) {
		this.movieCode = movieCode;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	
	public void setTotalCost() {
		this.totalCost = this.tax + this.price;
	}

	
	public double getCost() {
		return this.price;
	}
	
	public String getType() {
		return "MovieTicket";
	}
	
	public void Print(Double productCost) {
		double sub, tax, total;
		sub = this.amount * productCost;
		tax = this.tax * this.amount;
		total = sub + tax;
		DateTimeFormatter f = DateTimeFormat.forPattern("MMM d,yyyy HH:mm");
		String date = f.print(this.movieTime);
		String product = String.format("%s - '%s' @ %s", "MovieTicket", this.movieName, this.address.getStreet());
		String productAmount = String.format("%s (%d/units @ %4.2f - Tue/Thu 7%% off)", date, this.amount, productCost);
		System.out.println(String.format("%-4s      %-60s   $%-9.2f $%-9.2f $%-9.2f", this.productCode , product, sub, tax, total));
		System.out.println(String.format("%-4s      %-60s    %-9s  %-9s  %-9s", "" , productAmount, "", "", ""));
	}
	
	
}
