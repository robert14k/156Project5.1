package datacontainers;

public abstract class Service extends Product {

	public Service(String productCode) {
		super(productCode);
	}
	
	public abstract double getCost();
}
