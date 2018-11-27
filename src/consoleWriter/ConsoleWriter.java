package consoleWriter;

import java.util.List;
import datacontainers.*;

public class ConsoleWriter {
	
	//this will have a method that takes invoice arraylist and writes report directly to console
	//this will also call all the compute methods when we print out the invoices 
	public void InvoiceConsolePrinter(InvoiceList invoices) {
		//this will print to console and call the compute statement on all the invoices 
		System.out.println("=========================");
		System.out.println("Executive Summary Report");
		System.out.println("=========================");
		System.out.println(String.format("%-10s %-25s          %-20s %-8s     %-8s  %-8s%-8s   %-8s", "Invoice", "Customer", "Saleserson", "Subtotal", "Fees", "Taxes", "Discount", "Total"));
		//print out total reports
		
		System.out.println("=====================================================================================================================================================");
		double subtemp, subtotal=0, feetemp, fee=0, taxtemp, tax=0, discount=0, discounttemp, totaltemp, total=0;
		for(int i = 1; i<invoices.size; i++) {
			//this has to first print out all the invoices with the subtotal, fees, taxes, discount, and total computes...
			discounttemp = 0;
			subtemp = invoices.getInvoice(i).getProductsSubtotal();
			subtotal += subtemp;
			feetemp = invoices.getInvoice(i).getFee();
			fee += feetemp;
			taxtemp = invoices.getInvoice(i).getTax();
			tax += taxtemp;
			if(invoices.getInvoice(i).getCustomer().isStudent()) {
				discounttemp = invoices.getInvoice(i).getProuductsDiscount(subtemp);
				discounttemp += taxtemp;
				discount += discounttemp;
				totaltemp = subtemp + feetemp - discounttemp;
			}else {
				totaltemp = subtemp + feetemp + taxtemp - discounttemp;
			}
				total += totaltemp;
			
			System.out.println(String.format("%-10s %-25s %-7s %-20s $%8.2f $%8.2f $%8.2f $(%7.2f) $%8.2f", invoices.getInvoice(i).getInvoiceCode(), invoices.getInvoice(i).getCustomer().getName(), invoices.getInvoice(i).getCustomer().getType(), invoices.getInvoice(i).getSalesperson().getName(), subtemp, feetemp, taxtemp, discounttemp, totaltemp));	
			// print invoice.getCode, invoice.getcustomer.getname also make studnet/general method and call here , invoice.getslalespeperson
			//still on same line, invoice.getSubtotal(will return double with all the products's price*amount added up)
			// invoice.getCutomer.getfee(this wll return 0 for general, 6.xyz for student)
			
			//invoice.gettaxes(this will be a method in invoice that iterates over the list of products and calls product.gettax, and adds them up)
			//invoice.getdiscount^similar to that except do something diffrent with student 
			//total = math from the last 4 things ^
			//then it will iterate again but make sure you sumTotla+= all the columns so you can print the TOTALS at the end
			
		}
		System.out.println("=====================================================================================================================================================");
		System.out.println(String.format("%-10s %-25s %-7s %-20s $%8.2f $%8.2f $%8.2f $(%7.2f) $%8.2f", "TOTALS", "", "", "", subtotal, fee, tax, discount, total));	
		//print TOTLAS Line here 
		
		System.out.println("");
		System.out.println("Individual Invoice Detail Reports");
		System.out.println("==============================================");
		
		for(int i = 1; i<invoices.size; i++) {
			//more detailed report 	
			//rpint invoice name,
			//print customer info
			//then another for loop to iterate over arraylist of products and print ou there name, subttoal (price * amount)
			// the product.getTax() * amount 
			// the producct.gettotal() * amount
			// sub totals, add up all the columbs above for taxes, total, and subtotal columns 
			
			//print 'thanks 4 ur pucrchase 
			System.out.println("Invoice " + invoices.getInvoice(i).getInvoiceCode());
			System.out.println("=============================");
			
			System.out.println("Salesperson: "+ invoices.getInvoice(i).getSalesperson().getName());
			System.out.println("Customer Info:");
			invoices.getInvoice(i).getCustomer().print();
			System.out.println("------------------------------------------");
			System.out.println(String.format("%-4s      %-60s  %-10s %-10s %-10s", "Code", "Item", "SubTotal", "Taxes", "Total"));
			
			double subs=0, taxs=0, totals=0;
			for(Product b : invoices.getInvoice(i).getProductList()) {
				int amount = b.getAmount();
				double cost  = b.getCost();
				if(b.getType().equals("SeasonPass")) {
					cost = b.computeCost(invoices.getInvoice(i).getInvoiceDate());
				}else if(b.getType().equals("MovieTicket")) {
					cost = ((b.getCost() - b.getDiscount()));
				}else if(b.getType().equals("ParkingPass")) {
					amount = b.getParkingAmount();
				}
				b.Print(cost);
				//System.out.println(String.format("%-4s      %-20s  %-10s %-10s %-10s", b.getProductCode() , b.getType(), "SubTotal", "Taxes", "Total"));
				subs += (cost * (amount) - b.getnumTicket());
				taxs += b.getTax() * (amount) - b.getnumTicket();
				totals += ((cost * (amount) - b.getnumTicket()) - (b.getTax() * b.getAmount()));
				
			}
			System.out.println("                                                                         ==========================  ");
			System.out.println(String.format("%-60s      %-4s   $%-8.2f $%-9.2f $%-8.2f", "SUB-TOTALS", "", subs, taxs, totals));
			
			if(invoices.getInvoice(i).getCustomer().isStudent()) {
				double studentDiscount = (subs * .08) + taxs; 
				double studentTotal = totals - studentDiscount + 6.75;
				System.out.println(String.format("%-60s      %-4s  %-10s %-9s ($%-8.2f)", "DISCOUNT (8% Studnet & No Tax)", "", "", "", studentDiscount));
				System.out.println(String.format("%-60s      %-4s  %-10s %-10s $%-8.2f", "ADDITIONAL FEE (Student)", "", "", "", 6.75));
				System.out.println(String.format("%-60s      %-4s  %-10s %-10s $%-8.2f", "TOTAL", "", "", "", studentTotal));
				
			}else {
				System.out.println(String.format("%-60s      %-4s  %-10s %-10s $%-8.2f", "TOTAL", "", "", "", totals));
			}
			
		}
	} 
}
