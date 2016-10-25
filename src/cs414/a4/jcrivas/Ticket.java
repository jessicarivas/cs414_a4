package cs414.a4.jcrivas;

public class Ticket {

	private int _ticketNumber;
	private int _timeStamp;
	
	public Ticket(int id) {
		_ticketNumber = id;
	}

	public int getNumber() {
		return _ticketNumber;
	}
}
