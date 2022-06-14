package FlightManager;

import java.time.LocalDate;

public class Ticket {
	private String ticketCode;
	private String flightCode;
	private String ticketType;
	private LocalDate date;
	private String seatType;
	private int price;
	private String customerFirstName;
	private String customerLastName;
	private String customerDessert;
	private String customerMainDish;
	private String customerDrink;

	public Ticket(String ticketCode, String flightCode, String ticketType, LocalDate date, String seatType, int price, String customerFirstName,
			String customerLastName, String customerDessert, String customerMainDish, String customerDrink) {
		super();
		this.ticketCode = ticketCode;
		this.flightCode = flightCode;
		this.ticketType = ticketType;
		this.date = date;
		this.seatType = seatType;
		this.price = price;
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.customerDessert = customerDessert;
		this.customerMainDish = customerMainDish;
		this.customerDrink = customerDrink;
	}

	public String getTicketCode() {
		return ticketCode;
	}

	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}

	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getCustomerDessert() {
		return customerDessert;
	}

	public void setCustomerDessert(String customerDessert) {
		this.customerDessert = customerDessert;
	}

	public String getCustomerMainDish() {
		return customerMainDish;
	}

	public void setCustomerMainDish(String customerMainDish) {
		this.customerMainDish = customerMainDish;
	}

	public String getCustomerDrink() {
		return customerDrink;
	}

	public void setCustomerDrink(String customerDrink) {
		this.customerDrink = customerDrink;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
