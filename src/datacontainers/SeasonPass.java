package datacontainers;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class SeasonPass extends Ticket{
	
	private String name;
	private DateTime startDate;
	private DateTime endDate;
	private Double cost;
	
	public SeasonPass(String productCode, String name, DateTime startDate, DateTime endDate, Double cost) {
		super(productCode);
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.cost = cost;
		this.tax = this.cost * 0.06;
	}
	
	public SeasonPass(SeasonPass old) {
		super(old.productCode);
		this.name = old.name;
		this.startDate = old.startDate;
		this.endDate = old.endDate;
		this.cost = old.cost;
		this.tax = old.tax;
		this.cost = cost + 8;
		this.tax = this.cost * 0.06;
	}

	public String getName() {
		return this.name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public DateTime getStartDate() {
		return this.startDate;
	}


	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}


	public DateTime getEndDate() {
		return this.endDate;
	}


	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}

	public double getCost() {
		return (this.cost + 8);
	}
	public double computeCost(DateTime invoiceDate) {
		double daysLeft;
		double totalDays;
		double ratio;
		if(invoiceDate.isAfter(getStartDate())) {
			daysLeft = Days.daysBetween(invoiceDate.toLocalDate(), this.endDate.toLocalDate()).getDays();
			totalDays = Days.daysBetween(this.startDate.toLocalDate(), this.endDate.toLocalDate()).getDays();
			ratio = daysLeft / totalDays;
			this.tax = ((ratio * cost) + 8) * 0.06;
			return ((ratio * cost) + 8);
		}
		return this.cost + 8;
	}

	public void setCost(Double cost) {
		this.cost = cost + 8;
	}
	
	public String getType() {
		return "SeasonPass";
	}
	
	public void Print(Double productCost) {
		double sub, tax, total;
		sub = this.amount * productCost;
		tax = this.tax * this.amount;
		total = sub + tax;
		String product = String.format("%s - %s", "SeasonPass", this.name);
		String productAmount = String.format("(%d/units @ %.2f + $8 fee/unit)", this.amount, (productCost - 8));
		System.out.println(String.format("%-4s      %-60s   $%-9.2f $%-9.2f $%-9.2f", this.productCode , product, sub, tax, total));
		System.out.println(String.format("%-4s      %-60s    %-9s  %-9s  %-9s", "" , productAmount, "", "", ""));
	}
	
}
