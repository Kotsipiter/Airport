package FlightManager;

public abstract class Seat {
	private String seatCode;
	private int rowNum;
	private int columnNum;
	private Ticket ticket;

	public Seat(String seatCode, int rowNum, int columnNum, Ticket ticket) {
		super();
		this.seatCode = seatCode;
		this.rowNum = rowNum;
		this.columnNum = columnNum;
		this.ticket = ticket;
	}

	public String getSeatCode() {
		return seatCode;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getColumNum() {
		return columnNum;
	}

	public void setColumNum(int columnNum) {
		this.columnNum = columnNum;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

}
