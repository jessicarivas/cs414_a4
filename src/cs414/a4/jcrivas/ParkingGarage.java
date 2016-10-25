package cs414.a4.jcrivas;

import java.util.HashSet;
import java.util.Set;

public class ParkingGarage {
	private String _garage;
	private ParkingGarageView _view;
	private int _totalSpots;
	private int _availableSpots;
	private Boolean _gateOpen;
	private int _ticketTracker;
	Set<Driver> _drivers;
	Set<Administrator> _admins;
	
	public ParkingGarage() {
		_garage = "garage";
		_totalSpots = 12;
		_availableSpots = 12;
		_gateOpen = false;
		_ticketTracker = 0;
		_drivers = new HashSet<Driver>();
		_admins = new HashSet<Administrator>();
		addAdministrator("admin", "password");
	}
	
	public void addAdministrator(String name, String password) {
		if (!_admins.contains(name)) {
			Administrator admin = new Administrator(name, password);			
		}
	}

	public void enterItem(String id, String qty) {
		// TODO Auto-generated method stub
		System.out.println(id);
	}

	public int getTotalOccupancy() {
		return _totalSpots;		
	}

	public int getAvailability() {
		return _availableSpots;		
	}
	
	public void addView(ParkingGarageView view) {
		  {_view = view;}		
	}

	public void openGate() {
		_gateOpen = true;
	}

	public void closeGate() {
		_gateOpen = false;
	}
	
	public String getGatePosition() {
		if (_gateOpen)
			return "open";
		else 
			return "closed";
	}
	
	public void printTicket() {
		_ticketTracker++;
		Driver driver = new Driver(_ticketTracker);
		_drivers.add(driver);
		_view.update();
	}

	public void updateOccupancy() {
		_availableSpots--;
	}

	public boolean containsTicket(String text) {
		int ticket = 0;
		try {
		    ticket = Integer.parseInt(text);
		} catch (NumberFormatException e) {
		    return false;
		}
		for (Driver driver: _drivers) {
			int id = driver.getTicketNumber();
			if ((id == ticket)) {
				return true;
			}
		}
		return true;
	}
}