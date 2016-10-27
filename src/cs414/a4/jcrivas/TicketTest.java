package cs414.a4.jcrivas;

import static org.junit.Assert.*;

import org.junit.Test;

public class TicketTest {

	@Test
	public void testTicket() {
		Ticket ticket = new Ticket(1,2);
		assertEquals(2, ticket.getHourlyCost());
		assertEquals(1, ticket.getNumber());
	}

}
