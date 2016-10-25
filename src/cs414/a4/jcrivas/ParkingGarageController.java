package cs414.a4.jcrivas;


import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;



public class ParkingGarageController
{

	private ParkingGarage garage;
	

	//Trivial constructor (could be automatically supplied by Java)
	public ParkingGarageController()
	{}

	//Refer to the model (used in all the button methods, to call
	//  methods in the model) 
	public void addModel(ParkingGarage g) {
		garage = g;
	}

public int printTicket() {
	garage.openGate();
	garage.updateOccupancy();
	int ticket = garage.printTicket();
	// TODO Auto-generated method stub
	return ticket;
}

public void payTicket() {
	garage.openGate();
	garage.addAvailableSpot();
	
	// TODO Auto-generated method stub
	
}

public void logInAdmin() {
	// TODO Auto-generated method stub
	
}

public void closeGate() {
	garage.closeGate();
	// TODO Auto-generated method stub
	
}

public boolean getTicket(String text) {
	if (garage.containsTicket(text)) {
		return true;
	}  
	return false;
}

public int getLostTicketFee() {
	return garage.getLostTicketFee();
}

public int getTicketCost(String id) {
	int ticket = 0;
	try {
	    ticket = Integer.parseInt(id);
	    System.out.println(ticket);
	} catch (NumberFormatException e) {
	}
	return garage.getTicketCost(ticket);
}

}


