package FlightManager;

public class BusinessSeat extends Seat {
		
	 

		public BusinessSeat(String seatCode, int rowNum, int columnNum, Ticket ticket, Menu menu) {
		super(seatCode, rowNum, columnNum, ticket);
		this.menu = menu;
	}

		private Menu menu;

		public Menu getMenu() {
			return menu;
		}

		public void setMenu(Menu menu) {
			this.menu = menu;
		}
}
