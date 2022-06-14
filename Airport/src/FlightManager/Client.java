package FlightManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
	static ArrayList<Airplane> airplaneList = new ArrayList<Airplane>();
	static ArrayList<Flight> flightList = new ArrayList<Flight>();
	static ArrayList<Menu> menuList = new ArrayList<Menu>();
	static ArrayList<Ticket> ticketList = new ArrayList<Ticket>();

	public static void main(String[] args) {
		int x;

		Scanner input = new Scanner(System.in);

		do {
			System.out.println("[1] Insert Airplane\r\n" + "[2] Insert Menu\r\n" + "[3] Insert Flight\r\n"
					+ "[4] Cancel Flight\r\n" + "[5] Book Ticket\r\n" + "[6] Cancel Ticket\r\n"
					+ "[7] Order Menu Items (Available only in Business Seats)\r\n"
					+ "[8] Seats Capacity for a certain Flight\r\n" + "[0] Exit\r\n" + "");
			x = input.nextInt();

			switch (x) {
			case 1:
				insertAirplane();
				break;
			case 2:
				insertMenu();
				break;
			case 3:
				insertFlight();
				break;
			case 4:
				cancelFlight();
				break;
			case 5:
				bookticket();
				break;
			case 6:
				cancelticket();
				break;
			case 7:
				orderMenuItems();
				break;
			case 8:
				seatCapacityForFlight();
				break;

			}
		} while (x != 0);
	}

	private static void seatCapacityForFlight() {
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the code of the flight you wish to check the capacity of");
		String flightCode = input.nextLine();

		for (Flight tempFlight : flightList) {
			if (tempFlight.getFlightCode().equals(flightCode)) {
				System.out.println("Flight Found");
				int numberOfBusinessRows = tempFlight.getAirplane().getBusinessRows();
				for (int n = 0; n < numberOfBusinessRows; n++) {
					for (int m = 0; m < tempFlight.getAirplane().getColumns(); m++) {
						if (tempFlight.getSeats()[n][m].getTicket() != null) {
							System.out.printf("[[X]] ");
						} else {
							System.out.printf("[[ ]] ");
						}
					}
					System.out.printf("%n");
				}
				for (int n = numberOfBusinessRows; n < tempFlight.getAirplane().getRows(); n++) {
					for (int m = 0; m < tempFlight.getAirplane().getColumns(); m++) {
						if (tempFlight.getSeats()[n][m].getTicket() != null) {
							System.out.printf(" [X] ");
						} else {
							System.out.printf(" [ ] ");
						}
					}
					System.out.printf("%n");
				}
			}
		}
	}

	private static void orderMenuItems() {
		Scanner input = new Scanner(System.in);

		System.out.println("Please input ticket code");
		String ticketCode = input.nextLine();
		String customerDessert;
		String customerMainDish;
		String customerDrink;

		Ticket ticket = foundTicket(ticketCode);
		if (ticket != null) {
			System.out.println("Ticket found.");
			for (Flight tempFlight : flightList) {
				if (tempFlight.getFlightCode().equals(ticket.getFlightCode())) {
					System.out.println("Flight found.");
					// go through seats array in flight
					for (int n = 0; n < tempFlight.getAirplane().getRows(); n++) {
						for (int m = 0; m < tempFlight.getAirplane().getColumns(); m++) {
							if (tempFlight.getSeats()[n][m].getTicket() != null) {
								if (tempFlight.getSeats()[n][m].getTicket().getTicketCode().equals(ticketCode)) {
									System.out.println("Seat found.");
									if (tempFlight.getSeats()[n][m] instanceof BusinessSeat) {
										for (Menu tempMenu : menuList) {
											if (tempMenu.getMenuCode().equals(tempFlight.getMenuCode())) {
												System.out.println("Here is the menu");
												System.out.println(tempMenu.toString());
												System.out.println("Please input one Main Dish");
												customerMainDish = input.nextLine();
												System.out.println("Please input one Dessert");
												customerDessert = input.nextLine();
												System.out.println("Please input one Drink");
												customerDrink = input.nextLine();

												ticket.setCustomerDessert(customerDessert);
												ticket.setCustomerMainDish(customerMainDish);
												ticket.setCustomerDrink(customerDrink);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} else
			System.out.println("Ticket not found");

	}

	private static Ticket foundTicket(String ticketCode) {
		for (Ticket tempTicket : ticketList) {
			if (tempTicket.getTicketCode().equals(ticketCode)) {
				return tempTicket;
			}
		}
		return null;
	}

	public static void insertAirplane() {
		Scanner input = new Scanner(System.in);

		String airplaneCode;
		String description;
		int rows;
		int columns;
		int businessRows;
		boolean codeFound = false;

		System.out.println("Please input airplane code.");
		airplaneCode = input.nextLine();
		System.out.println("Please input airplane description");
		description = input.nextLine();
		System.out.println("Please input airplane rows");
		rows = input.nextInt();
		System.out.println("Please input airplane columns");
		columns = input.nextInt();
		System.out.println("Please input airplane business rows");
		businessRows = input.nextInt();

		for (Airplane tempAirplane : airplaneList) {
			if (tempAirplane.getAirplaneCode().equals(airplaneCode)) {
				System.out.println("This Airplane code already exists in the database exiting");
				codeFound = true;
				break;
			}
		}
		if (codeFound == false) {
			Airplane airplane = new Airplane(airplaneCode, description, rows, columns, businessRows);
			airplaneList.add(airplane);
			System.out.println("New airplane added!");
		}
		System.out.println();

	}

	public static void insertMenu() {

		Scanner input = new Scanner(System.in);

		String menuCode;
		ArrayList<String> mainDishes = new ArrayList<String>();
		ArrayList<String> desserts = new ArrayList<String>();
		ArrayList<String> drinks = new ArrayList<String>();
		boolean codeFound = false;
		String mainDish;
		String dessert;
		String drink;

		System.out.println("Please input Menu code.");
		menuCode = input.nextLine();
		do {
			System.out.println("Please input Main Dish or 0 to continue to desserts ");
			mainDish = input.nextLine();
			if (!mainDish.contentEquals("0")) {
				mainDishes.add(mainDish);
			}
		} while (!mainDish.contentEquals("0"));
		do {
			System.out.println("Please input Dessert or 0 to continue to Drinks ");
			dessert = input.nextLine();
			if (!dessert.contentEquals("0")) {
				desserts.add(dessert);
			}
		} while (!dessert.contentEquals("0"));
		do {
			System.out.println("Please input Drink or 0 to continue");
			drink = input.nextLine();
			if (!drink.contentEquals("0")) {
				drinks.add(drink);
			}
		} while (!drink.contentEquals("0"));

		for (Menu tempMenu : menuList) {
			if (tempMenu.getMenuCode().equals(menuCode)) {
				System.out.println("This Menu code already exists in the database");
				codeFound = true;
				break;
			}
		}
		if (codeFound == false) {
			Menu menu = new Menu(menuCode, mainDishes, desserts, drinks);
			menuList.add(menu);
			System.out.println("New menu added!");
		}

		System.out.println("");

	}

	public static void insertFlight() {
		Scanner input = new Scanner(System.in);
		Flight flight = new Flight();
		System.out.println("Please input FlightCode");
		String flightCode = input.nextLine();

		System.out.println("Please input  Flight Year");
		int year = input.nextInt();
		System.out.println("Please input Flight month (1-12)");
		int month = input.nextInt();
		System.out.println("Please input Flight day (1-31)");
		int day = input.nextInt();
		LocalDate localDate = LocalDate.of(year, month, day);

		System.out.println("Please input Flight Hour");
		int hour = input.nextInt();
		System.out.println("Please input Flight Minute");
		int minute = input.nextInt();
		System.out.println("Please input Flight Second");
		int second = input.nextInt();
		LocalTime localTime = LocalTime.of(hour, minute, second);

		System.out.println("Please input place of departure");
		String departure = input.nextLine();
		input.nextLine();
		System.out.println("Please input destination");
		String destination = input.nextLine();

		System.out.println("Please input Airplane code ");
		String airplaneCode = input.nextLine();

		Airplane airplane = foundAirplane(airplaneCode);
		if (airplane != null) {
			System.out.println("Airplane found.");
			int i = airplane.getRows();
			int j = airplane.getColumns();
			Seat[][] seats = new Seat[i][j];

			int numberOfBusinessRows = airplane.getBusinessRows();
			for (int n = 0; n < numberOfBusinessRows; n++) {
				for (int m = 0; m < j; m++) {
					System.out.println("Please input business seat code of row: " + n + " and column:" + m);
					String tempSeatCode = input.nextLine();

					Seat businessSeat = new BusinessSeat(tempSeatCode, n, m, null, null);
					seats[n][m] = businessSeat;

				}
			}
			for (int n = numberOfBusinessRows; n < i; n++) {
				for (int m = 0; m < j; m++) {
					System.out.println("Please input economy seat code of row: " + n + " and column:" + m);
					String tempSeatCode = input.nextLine();
					int tempRowNumber = n;
					int tempColumnNumber = m;

					Seat economySeat = new EconomySeat(tempSeatCode, tempRowNumber, tempColumnNumber, null);
					seats[n][m] = economySeat;
				}
			}

			System.out.println("Please input menuCode");
			String menuCode = input.nextLine();
			boolean foundAirplaneInFlightList = foundAirplaneInFlightList(airplaneCode);
			int allSeats = airplane.getRows() * airplane.getColumns();
			// set
			flight.setAirplane(airplane);
			flight.setAllSeats(allSeats);
			flight.setDate(localDate);
			flight.setDeparture(departure);
			flight.setDestination(destination);
			flight.setFlightCode(flightCode);
			flight.setMenuCode(menuCode);
			flight.setSeats(seats);
			flight.setTakenSeats(0);
			flight.setTime(localTime);

			boolean timeFound = foundTime(localDate);
			if (timeFound == false && foundAirplaneInFlightList == false) {
				flightList.add(flight);
				System.out.println("Flight added");
			} else {
				System.out.println("This flight already exists");
			}
		} else if (airplane == null) {
			System.out.println("Airplane not found. Exit\n");
		}

	}

	private static boolean foundAirplaneInFlightList(String airplaneCode) {
		for (Flight tempFlight : flightList) {
			Airplane tempAirplane = tempFlight.getAirplane();
			if (tempAirplane.getAirplaneCode() == airplaneCode) {
				return true;
			}
		}

		return false;
	}

	private static boolean foundTime(LocalDate time) {
		for (Flight tempFlight : flightList) {
			if (tempFlight.getDate() == time) {
				return true;
			}
		}
		return false;
	}

	private static Airplane foundAirplane(String airplaneCode) {
		for (Airplane tempAirplane : airplaneList) {
			if (tempAirplane.getAirplaneCode().equals(airplaneCode)) {
				return tempAirplane;
			}
		}

		return null;
	}

	private static void cancelFlight() {
		Scanner input = new Scanner(System.in);

		System.out.println("Please input the code of the flight you would like to delete");
		String flightToDelete = input.nextLine();
		int counter = -1;
		boolean flightFound = false;

		for (Flight tempFlight : flightList) {
			counter = counter + 1;
			if (flightToDelete == tempFlight.getFlightCode()) {
				flightFound = true;
				flightList.remove(counter);
			}
		}
		if (flightFound == true) {
			counter = -1;
			for (Ticket tempTicket : ticketList) {
				counter = counter + 1;
				if (flightToDelete == tempTicket.getFlightCode()) {
					System.out.println(tempTicket.getPrice() + " Must be returned to client: "
							+ tempTicket.getCustomerFirstName() + tempTicket.getCustomerLastName());
					ticketList.remove(counter);
				}
			}
			System.out.println("Flight and tickets succesfully canceled");
		} else if (flightFound == false) {
			System.out.println("No flight with such code found. Exiting");
		}

	}

	private static void bookticket() {
		Scanner input = new Scanner(System.in);

		System.out.println("Please input ticketCode");
		String ticketCode = input.nextLine();
		System.out.println("Please input flightcode");
		String flightCode = input.nextLine();
		System.out.println("Please enter ticket type ");
		String ticketType = input.nextLine();
		System.out.println("Please input Year");

		int year = input.nextInt();
		System.out.println("Please input month (1-12)");
		int month = input.nextInt();
		System.out.println("Please input day (1-31)");
		int day = input.nextInt();
		LocalDate date = LocalDate.of(year, month, day);
		input.nextLine();
		System.out.println("Please input seat Type (Business or Economy)");
		String seatType = input.nextLine();
		System.out.println("Please input ticket price");
		int price = input.nextInt();
		input.nextLine();

		System.out.println("Please input customer First name");
		String customerFirstName = input.nextLine();

		System.out.println("Please input customer Last name");
		String customerLastName = input.nextLine();

		String customerDessert = null;
		String customerMainDish = null;
		String customerDrink = null;

		Flight flight = foundFlight(flightCode);
		// if flight is found add ticket
		if (flight != null) {
			if (seatType.equals("Business")) {
				boolean businessSeatIsAvailable = foundAvailableBusinessSeat(flight);
				if (businessSeatIsAvailable == true) {
					Ticket ticket = new Ticket(ticketCode, flightCode, ticketType, date, seatType, price,
							customerFirstName, customerLastName, customerDessert, customerMainDish, customerDrink);

					// add ticket to seat array in the correct flight
					for (Flight tempFlight : flightList) {
						boolean seatFound = false;
						if (tempFlight.getFlightCode().equals(flightCode)) {
							for (int n = 0; n < flight.getAirplane().getBusinessRows(); n++) {
								for (int m = 0; m < flight.getAirplane().getColumns(); m++) {
									if (flight.getSeats()[n][m].getTicket() == null) {
										flight.getSeats()[n][m].setTicket(ticket);
										seatFound = false;
										break;
									}
								}
								if (seatFound == true) {
									break;
								}
							}
						}
					}
					ticketList.add(ticket);
					System.out.println("Ticket added successfully");
				} else if (businessSeatIsAvailable == false) {
					System.out.println("Business Seat not available");
				}

			} else if (seatType.equals("Economy")) {
				boolean availableEconomySeat = foundEconomySeat(flight);
				if (availableEconomySeat == true) {
					Ticket ticket = new Ticket(ticketCode, flightCode, ticketType, date, seatType, price,
							customerFirstName, customerLastName, customerDessert, customerMainDish, customerDrink);
					for (Flight tempFlight : flightList) {
						if (tempFlight.getFlightCode().equals(flightCode)) {
							boolean seatFound = false;
							for (int n = flight.getAirplane().getBusinessRows(); n < flight.getAirplane()
									.getRows(); n++) {
								for (int m = 0; m < flight.getAirplane().getColumns(); m++) {
									if (flight.getSeats()[n][m].getTicket() == null) {
										flight.getSeats()[n][m].setTicket(ticket);
										seatFound = true;
										break;
									}
								}
								if (seatFound == true) {
									break;
								}
							}
						}
					}
					ticketList.add(ticket);
					System.out.println("Ticket added successfully");
				} else if (availableEconomySeat == false) {
					System.out.println("Economy Seat not available");
				}
			}
		} else if (flight == null) {
			System.out.println("There is not flight with such code. Returning to main menu");
		}
	}

	private static Flight foundFlight(String flightCode) {
		for (Flight tempFlight : flightList) {
			if (tempFlight.getFlightCode().equals(flightCode)) {
				return tempFlight;
			}
		}

		return null;
	}

	private static boolean foundAvailableBusinessSeat(Flight flight) {
		int numberOfBusinessSeats = flight.getAirplane().getColumns() * flight.getAirplane().getBusinessRows();
		int counterOfTakenBusinessSeats = 0;
		for (Ticket tempTicket : ticketList) {
			if (tempTicket.getFlightCode().equals(flight.getFlightCode())
					&& tempTicket.getSeatType().equals("Business")) {
				counterOfTakenBusinessSeats = counterOfTakenBusinessSeats + 1;
			}
		}
		if (numberOfBusinessSeats == counterOfTakenBusinessSeats) {
			return false;
		} else {
			return true;
		}
	}

	private static boolean foundEconomySeat(Flight flight) {
		int numberOfEconomySeats = (flight.getAllSeats() / flight.getAirplane().getRows())
				* (flight.getAirplane().getBusinessRows() - flight.getAirplane().getBusinessRows());
		int counterOfTakenEconomySeats = 0;
		for (Ticket tempTicket : ticketList) {
			if (tempTicket.getFlightCode().equals(flight.getFlightCode())
					&& tempTicket.getSeatType().equals("Economy")) {
				counterOfTakenEconomySeats = counterOfTakenEconomySeats + 1;
			}
		}
		if (numberOfEconomySeats == counterOfTakenEconomySeats) {
			return false;
		} else {
			return true;
		}

	}

	private static void cancelticket() {
		Scanner input = new Scanner(System.in);
		System.out.println("Please input the code of the ticket you would like to cancel ");
		String ticketCode = input.nextLine();
		int counter = -1;
		for (Ticket tempTicket : ticketList) {
			counter = counter + 1;
			if (tempTicket.getTicketCode().equals(ticketCode)) {
				ticketList.remove(counter);
				System.out.println("Ticket succefully deleted");
				tempTicket = null;
			}
		}
	}
}
