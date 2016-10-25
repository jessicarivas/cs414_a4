package cs414.a4.jcrivas;

public class Driver {
	
	private Ticket _ticket;
	
	public Driver(int id) {
		_ticket = new Ticket(id);
	}

	public int getTicketNumber() {
		return _ticket.getNumber();
	}

	public int getTicketCost(int cost) {
		return _ticket.calculateCost(cost);	
	}

}
