package cs414.a4.jcrivas;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class Ticket {

	private int _ticketNumber;
	private long _startTime;
	
	public Ticket(int id) {
		_ticketNumber = id;
		_startTime = new java.util.Date().getTime();
	}

	public int getNumber() {
		return _ticketNumber;
	}
	
	public long getStartTime() {
		return _startTime;
	}

	public int calculateCost(int cost) {
		long endTime = new java.util.Date().getTime();
		long difference = endTime - _startTime;
		long hours = TimeUnit.MILLISECONDS.toSeconds(difference);
		int total = (int) (hours * cost);
		return total;
	}
}
