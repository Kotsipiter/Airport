package airlineDB;

import java.sql.*;

import java.util.*;

public class AirlineDBConnector {

	public static void main(String[] args) throws Exception {
		int x;

		Scanner input = new Scanner(System.in);

		do {
			System.out.println("[1] Insert flights, clients or reservations\r\n" + "[2] Show available flights\r\n"
					+ "[3] Show full flights\r\n" + "[4]Show available flights between Toronto and New York\r\n"
					+ "[5] Make a reservation for a specific airline-flight\r\n" + "[6] Pay deposit\r\n"
					+ "[7] Show cancelled reservations\r\n" + "[0] Exit\r\n" + "");
			x = input.nextInt();

			switch (x) {
			case 1:
				insertAirlineData();
				break;
			case 2:
				showAvailableFlights();
				break;
			case 3:
				showFullFlights();
				break;
			case 4:
				showFlightsBetweenTorontoNewYork();
				break;
			case 5:
				//asking for which airline the client would like to make a reservation for
				input.nextLine();
				System.out.println("Please insert airline code");
				String ac = input.nextLine();
				insertReservation(ac);
				break;
			case 6:
				payDeposit();
				break;
			case 7:
				showCancelledReservations();
				break;
			}
		} while (x != 0);

	}

	public static Connection getConnection() {

		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "airlinedb";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";

		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}

	private static void showCancelledReservations() {
		try {
			
		Scanner input = new Scanner(System.in);
		Connection con = getConnection();
		//asking for the flight id needed
		System.out.println("Please insert the flight id of which you would like to delete the canceled reservations ");
		String flightId = input.nextLine();
		
		//creating the command to get all the cancelled reservations' id of the given flight and storing them in "rs"
		String query = "SELECT reservation_id FROM reservation WHERE flight_flight_id = '"+flightId+"' AND reservation_condition = 2";
		PreparedStatement update = con.prepareStatement(query);
		ResultSet rs = update.executeQuery();
		
		//since each of the reservation has a respective total price we need to go through each one and delete them all
		while (rs.next()) {
			//deleting the total_price  data corresponding to the canceled reservation
			String totalPriceQuery = "DELETE FROM total_price WHERE reservation_reservation_id = '"+rs.getString("reservation_id")+"'";
			update = con.prepareStatement(totalPriceQuery);
			update.executeUpdate();
		}
		//deleting the cancelled reservations of the flight given
		query = "DELETE FROM reservation WHERE flight_flight_id = '"+flightId+"' AND reservation_condition = 2";
		update = con.prepareStatement(query);
		update.executeUpdate();
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
	}

	private static void payDeposit() {
		Scanner input = new Scanner(System.in);
		Connection con = getConnection();
		//Asking for which reservation you would like to make a payment for
		
		System.out.println("Please enter the reservation id you would like to pay for ");
		String reservationId = input.nextLine();
		try {
		// updating the corresponding table in the database
		String query = "UPDATE reservation SET reservation_condition = 1 WHERE reservation_id = "+reservationId;
		PreparedStatement posted = con.prepareStatement(query);
		
		//Getting the price of the flight to then update the amount of money the client has paid 
		posted.execute();
		//The price of the flight is located in  "flight_local_price" , where as the amount of money the client has paid is stored in "deposit" ,since the reservation condition is 
		//set to [1]reserved-paid the client has paid the full price therefore  "deposit" is updated to match that amount.
		
		//creating the command to get the appropriate price of the flight
		query = "SELECT flight_local_price FROM reservation WHERE reservation_id = "+reservationId;
		posted = con.prepareStatement(query);
		ResultSet rs = posted.executeQuery();
		rs.next();
		float deposit = rs.getFloat("flight_local_price");
		
		//creating the command to update the database with the new deposit 
		query = "UPDATE `reservation` SET `deposit` = "+deposit+" WHERE reservation_id ='"+reservationId+"'";
		posted = con.prepareStatement(query);
		posted.execute();
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private static void showFlightsBetweenTorontoNewYork() {
		// TODO Auto-generated method stub

	}

	private static void showFullFlights() {
		// TODO Auto-generated method stub

	}

	private static void showAvailableFlights() {
		Connection con = getConnection();
		try {
			//Creating the query for the command needed to get all the airline codes and storing them in the result set "ars"
			String airlineQuery = "SELECT airline_code FROM airline ";
			PreparedStatement command = con.prepareStatement(airlineQuery);
			ResultSet ars = command.executeQuery();
			
			while (ars.next()) {
				//getting all the flights for the speciic airline given and storing them in the result set "frs" for all existing airlines
				System.out.println("The airline with the code " + ars.getString(1) + " has the flights:");
				String flightQuery = "SELECT flight_id,departure_time,arrival_time,departure_city,arrival_city FROM flight WHERE airline_airline_code = '"
						+ ars.getString(1) + "'";
				command = con.prepareStatement(flightQuery);
				ResultSet frs = command.executeQuery();
				
				
				//Getting and printing all the information for each flight 
				while (frs.next()) {
					//Getting all the information related to seats(economy,business,smoker) and storing them in "avrs" so they can be printed
					String flightId = frs.getString("flight_id");
					String availabilityQuery = "SELECT * FROM availability WHERE flight_flight_id = " + flightId + "\n";
					command = con.prepareStatement(availabilityQuery);
					ResultSet avrs = command.executeQuery();
					avrs.next();
					//computing the seats that are available by subtracting the reserved seats from the total seats the flight has 
					int availableBusinessSeats = avrs.getInt("total_business_seats")
							- avrs.getInt("taken_business_seats");
					int availableEconomySeats = avrs.getInt("total_economy_seats") - avrs.getInt("taken_economy_seats");
					int availableSmokerSeats = avrs.getInt("total_Smoker_seats") - avrs.getInt("taken_Smoker_seats");
					
					//storing the flight information in variables to then print them
					String departureCity = frs.getString("departure_City");
					String arrivalCity = frs.getString("arrival_City");
					String departureTime = frs.getDate("departure_time").toString();
					String arrivalTime = frs.getDate("arrival_time").toString();
					//printing the flight information
					System.out.println("    Flight id : " + flightId + "\n    From : " + departureCity + "\n    To : "
							+ arrivalCity + "\n    Scheduled for :" + departureTime + "\n    Will arrive at :"
							+ arrivalTime + "\n    It has:\n      " + availableBusinessSeats
							+ " business seats available\n      " + availableEconomySeats
							+ " economy seats available\n      " + availableSmokerSeats + " smoker seats available");
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private static void insertAirlineData() {
		int x;
		String y = null;
		Scanner input = new Scanner(System.in);
		do {
			try {
				System.out.println("Insert airline code or 0 to exit to main menu");
				y = input.nextLine();
				Connection con = getConnection();
				String query = "INSERT INTO `airline`(`airline_code`) VALUES ('" + y + "')";
				PreparedStatement posted = con.prepareStatement(query);
				posted.execute();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			do {
				System.out.println("[1] Insert flight\r\n" + "[2] Insert customer\r\n" + "[3] Insert reservation\r\n"
						+ "[0] Next airline.");
				x = input.nextInt();
				switch (x) {
				case 1:
					insertFlight(y);
					break;
				case 2:
					insertCustomer();
					break;
				case 3:
					insertReservation(y);
					break;
				}
			} while (x != 0);
		} while (y.contentEquals("0"));
	}

	private static void insertReservation(String ac) {
		Scanner input = new Scanner(System.in);
		try {
			Connection con = getConnection();
			//asking the client for the information needed to make a reservation and storing in in the database
			System.out.println("Please insert the reservation's flight id.");
			String reservationFlightID = input.nextLine();

			System.out.println("Please insert the client's id.");
			String clientID = input.nextLine();

			System.out.println("Please insert the reservation's id.");
			String reservationID = input.nextLine();

			System.out.println("Please insert the departure city.");
			String depCity = input.nextLine();

			System.out.println("Please insert the reservation date(YYYY/MM/DD).");
			String reservationDate = input.nextLine();

			System.out.println("Please insert the local departure date(YYYY/MM/DD).");
			String localDepDate = input.nextLine();

			System.out.println("Please insert the local departure time(HH:MM:SS).");
			String localDepTime = input.nextLine();
			localDepTime = localDepDate + " " + localDepTime;

			System.out.println("Please insert the local arrival date(YYYY/MM/DD).");
			String localArrDate = input.nextLine();

			System.out.println("Please insert the local arrival time(HH:MM:SS).");
			String localArrTime = input.nextLine();
			localArrTime = localArrDate + " " + localArrTime;

			System.out.println("Please insert the seat's class(Business, Economy or Smoker).");
			String classSeat = input.nextLine();

			System.out.println("Please insert the flight's cost, at the customers local currency.");
			String localPrice = input.nextLine();

			System.out.println(
					"Please insert\n[1] Reserved-Paid \n[2] Cancelled" + "\n[3] Reserved-but not fully-paid yet\n");
			int reservationCondition = input.nextInt();
			input.nextLine();

			String deposit;
			if (reservationCondition == 3) {
				System.out.println("Please insert the amount of money the client has deposited.");
				deposit = input.nextLine();
			} else {
				deposit = localPrice;
			}
			//creating the command required to insert the information in the database 
			String query = "INSERT INTO reservation(`reservation_id`, `departure_city`,"
					+ " `reservation_date`, `local_departure_date`, `local_departure_time`, "
					+ "`local_arrival_date`, `local_arrival_time`, `class_seat`, `flight_local_price`,"
					+ " `reservation_condition`, `deposit`, `flight_flight_id`, `customer_client_id`, `airline_airline_code`) "
					+ "VALUES ('" + reservationID + "', '" + depCity + "','" + reservationDate + "'" + ",'"
					+ localDepDate + "','" + localDepTime + "','" + localArrDate + "'," + "'" + localArrTime + "','"
					+ classSeat + "','" + Float.parseFloat(localPrice) + "','" + reservationCondition + "'," + "'"
					+ Float.parseFloat(deposit) + "','" + reservationFlightID + "','" + clientID + "','" + ac + "')";

			PreparedStatement posted = con.prepareStatement(query);
			posted.execute();
			//calling the insertTotalPrice method to ask the client information related to the price for the reservation
			insertTotalPrice(reservationID);

		} catch (Exception e) {
			System.out.println("Error in insertReservation");
			System.err.print(e.getMessage());
		}
	}

	private static void insertCustomer() {
		Scanner input = new Scanner(System.in);
		try {
			Connection con = getConnection();

			System.out.println("Please insert the client's id.");
			String clientID = input.nextLine();

			System.out.println("Please insert the client's name.");
			String name = input.nextLine();

			System.out.println("Please insert the client's surname.");
			String surname = input.nextLine();

			System.out.println("Please insert the client's mail or 0 if non-existent.");
			String email = input.nextLine();
			if (email.contentEquals("0")) {
				email = null;
			}
			String query = "INSERT INTO customer " + " (Client_ID, Name, Surname, Email)" + " values ('" + clientID
					+ "', '" + name + "','" + surname + "','" + email + "')";

			PreparedStatement posted = con.prepareStatement(query);
			posted.execute();

			int a;
			do {
				System.out.println("Please insert [1] to add a phone number, else [0].");
				a = input.nextInt();
				if (a == 0)
					break;
				insertTel();
			} while (a == 1);

			do {
				System.out.println("Please insert [1] to add an address, else [0].");
				a = input.nextInt();
				if (a == 0)
					break;
				insertAddress();
			} while (a == 1);

			do {
				System.out.println("Please insert [1] to add a fax, else [0].");
				a = input.nextInt();
				if (a == 0)
					break;
				insertFax();
			} while (a == 1);

		} catch (Exception e) {
			System.out.println("Error in insertCustomer");
			System.err.print(e.getMessage());
		}

	}

	private static void insertTel() {
		Scanner input = new Scanner(System.in);
		try {
			Connection con = getConnection();

			System.out.println("Please insert the client's phone number id.");
			String telID = input.nextLine();

			System.out.println("Please insert the phone's country code.");
			int countryCode = input.nextInt();

			System.out.println("Please insert the phone's city code.");
			int cityCode = input.nextInt();

			System.out.println("Please insert the phone's local number.");
			int localNumber = input.nextInt();

			String query = "INSERT INTO tel (`tel_id`, `country_code`, `city_code`, `local_number`) " + "VALUES ('"
					+ telID + "','" + countryCode + "','" + cityCode + "','" + localNumber + "')";

			PreparedStatement posted = con.prepareStatement(query);
			posted.execute();

		} catch (Exception e) {
			System.out.println("Error in insertTel");
			System.err.print(e.getMessage());
		}
	}

	private static void insertFax() {
		Scanner input = new Scanner(System.in);
		try {
			Connection con = getConnection();

			System.out.println("Please insert the client's fax id.");
			String faxID = input.nextLine();

			System.out.println("Please insert the fax's country code.");
			int countryCode = input.nextInt();

			System.out.println("Please insert the fax's city code.");
			int cityCode = input.nextInt();

			System.out.println("Please insert the fax's local number.");
			int localNumber = input.nextInt();

			String query = "INSERT INTO tel (`tel_id`, `country_code`, `city_code`, `local_number`) " + "VALUES ('"
					+ faxID + "','" + countryCode + "','" + cityCode + "','" + localNumber + "')";

			PreparedStatement posted = con.prepareStatement(query);
			posted.execute();

		} catch (Exception e) {
			System.out.println("Error in insertFax");
			System.err.print(e.getMessage());
		}
	}

	private static void insertAddress() {
		Scanner input = new Scanner(System.in);
		try {
			Connection con = getConnection();

			System.out.println("Please insert the client's address id.");
			String addressID = input.nextLine();

			System.out.println("Please insert the street.");
			String street = input.nextLine();

			System.out.println("Please insert the city.");
			String city = input.nextLine();

			System.out.println("Please insert the state.");
			String state = input.nextLine();

			System.out.println("Please insert the postal code.");
			String postalCode = input.nextLine();

			System.out.println("Please insert the country.");
			String country = input.nextLine();

			String query = "INSERT INTO tel (`tel_id`, `country_code`, `city_code`, `local_number`) " + "VALUES ('"
					+ addressID + "','" + street + "','" + city + "'," + "'" + state + "','" + postalCode + "','"
					+ country + "')";

			PreparedStatement posted = con.prepareStatement(query);
			posted.execute();

		} catch (Exception e) {
			System.out.println("Error in insertAddress");
			System.err.print(e.getMessage());
		}
	}

	private static void insertTotalPrice(String reservationID) {
		Scanner input = new Scanner(System.in);
		try {
			Connection con = getConnection();

			System.out.println("Please insert price id");
			String priceID = input.nextLine();

			System.out.println("Please insert departure tax");
			String deptTax = input.nextLine();

			System.out.println("Please insert the type of currency at the departure country");
			String deptCurrency = input.nextLine();

			System.out.println("Please insert arrival tax");
			String arrivalTax = input.nextLine();

			System.out.println("Please insert the type of currency at the arrival country");
			String arrivalCurrency = input.nextLine();

			System.out.println("Please insert flight local price");
			String localPrice = input.nextLine();

			System.out.println("Please insert the exchange rate");
			String exchangeRate = input.nextLine();

			String query = "INSERT INTO total_price (`total_price_id`, `departure_tax`, "
					+ "`dept_currency`, `arrival_tax`, `arr_currency`, `flight_local_price`, "
					+ "`reservation_reservation_id`, `exchange_rate`) VALUES ('" + priceID + "', " + "'"
					+ Float.parseFloat(deptTax) + "', '" + deptCurrency + "', '" + Float.parseFloat(arrivalTax) + "', "
					+ "'" + arrivalCurrency + "', '" + Float.parseFloat(localPrice) + "', '" + reservationID + "', "
					+ "'" + Float.parseFloat(exchangeRate) + "')";

			PreparedStatement posted = con.prepareStatement(query);
			posted.execute();

		} catch (Exception e) {
			System.out.println("Error in insertTotalPrice");
			System.err.println(e.getMessage());
		}

	}

	private static void insertFlight(String y) {
		Scanner input = new Scanner(System.in);
		try {
			Connection con = getConnection();

			System.out.println("Please insert flight code");
			String flightCode = input.nextLine();

			System.out.println("Are there business class seats in this flight ?(0 for no 1 for yes)");
			String businessSeatsAvailability = input.nextLine();
			System.out.println("Are there economy class seats in this flight ?(0 for no 1 for yes)");
			String economySeatsAvailability = input.nextLine();
			System.out.println("Are there smoker seats in this flight ?(0 for no 1 for yes)");
			String smokerSeatsAvailability = input.nextLine();

			System.out.println("Please insert the departure date(YYYY/MM/DD)");
			String depdate = input.nextLine();

			System.out.println("Please insert the departure time(HH:MM:SS)");
			String deptime = input.nextLine();
			deptime = depdate + " " + deptime;

			System.out.println("Please insert the arrival date(YYYY/MM/DD)");
			String arrdate = input.nextLine();

			System.out.println("Please insert the arrival time(HH:MM:SS)");
			String arrtime = input.nextLine();
			arrtime = arrdate + " " + arrtime;

			System.out.println("Please insert the duration time(HH:MM:SS)");
			String durationtime = input.nextLine();
			durationtime = arrdate + " " + durationtime;

			String query = "INSERT INTO flight "
					+ " (flight_id, business_seats_availability, economy_seats_availability, smoker_seats_availability, "
					+ "departure_date, arrival_date, departure_time, arrival_time, flight_duration, airline_airline_code)"
					+ " values ('" + flightCode + "','" + businessSeatsAvailability + "','" + economySeatsAvailability
					+ "'," + "'" + smokerSeatsAvailability + "','" + depdate + "','" + arrdate + "'," + "'" + deptime
					+ "','" + arrtime + "', '" + durationtime + "', '" + y + "')";

			PreparedStatement posted = con.prepareStatement(query);
			posted.execute();

			insertAvailability(flightCode, businessSeatsAvailability, economySeatsAvailability,
					smokerSeatsAvailability);

		} catch (Exception e) {
			System.out.println("Something went wrong at insertFlight");
			System.err.print(e.getMessage());
		}

	}

	private static void insertAvailability(String flightCode, String businessSeatsAvailability,
			String economySeatsAvailability, String smokerSeatsAvailability) {
		Scanner input = new Scanner(System.in);

		try {
			int bs, es, ss;
			Connection con = getConnection();
			if (businessSeatsAvailability.contentEquals("1")) {
				System.out.println("How many business seats are there in the flight?");
				bs = input.nextInt();
			} else {
				bs = 0;
			}
			if (economySeatsAvailability.contentEquals("1")) {
				System.out.println("How many economy seats are there in the flight?");
				es = input.nextInt();
			} else {
				es = 0;
			}
			if (smokerSeatsAvailability.contentEquals("1")) {
				System.out.println("How many smoker seats are there in the flight?");
				ss = input.nextInt();
			} else {
				ss = 0;
			}
			input.nextLine();
			System.out.println("What is this flights availability code?");
			String availabilityCode = input.nextLine();
			String query = "INSERT INTO `availability`(`availability_id`, `total_business_seats`,"
					+ "`taken_business_seats`, `total_economy_seats`, `taken_economy_seats`, "
					+ "`total_smoker_seats`, `taken_smoker_seats`, `flight_flight_id`) VALUES" + " ('"
					+ availabilityCode + "','" + bs + "','0', '" + es + "', '0', '" + ss + "', '0','" + flightCode
					+ "')";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("Error in insertAvailability");
		}

	}

}