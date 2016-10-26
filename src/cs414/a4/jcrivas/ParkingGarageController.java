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
	garage.addDriver();
	int ticket = garage.printTicket();
	// TODO Auto-generated method stub
	return ticket;
}

public void payTicket() {
	garage.openGate();
	garage.removeDriver();
	
	// TODO Auto-generated method stub
	
}

public void logInAdmin() {
	
}

public void closeGate() {
	garage.closeGate();
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
	} catch (NumberFormatException e) {
	}
	return garage.getTicketCost(ticket);
}

public Boolean logInAdmin(String username, String password) {
	return (garage.logInAdmin(username, password));
	// TODO Auto-generated method stub
	
}

public boolean isNumber(String text) {
	int number = 0;
	try {
	    number = Integer.parseInt(text);
	} catch (NumberFormatException e) {
		return false;
	}
	return true;
}

public void changeGarageOccupancy(String text) {
	int number = 0;
	try {
	    number = Integer.parseInt(text);
	} catch (NumberFormatException e) {
	}
	garage.setTotalOccupancy(number);
	
}

public void changeDriverTotal(String text) {
	int number = 0;
	try {
	    number = Integer.parseInt(text);
	} catch (NumberFormatException e) {
	}
	garage.setTotalDrivers(number);	
}

public String getUsageString(int usageType, int timeFrame) {
	String[] typeArray = {"Finance", "Occupancy"};
	String[] timeArray = {"Hour", "Day", "Week", "Month"};
	String usage = garage.getUsageString(typeArray[usageType], timeArray[timeFrame]);
	return usage;
	
}

}


