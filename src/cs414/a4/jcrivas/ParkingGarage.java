package cs414.a4.jcrivas;

import java.util.HashSet;
import java.util.Set;

public class ParkingGarage {
	private String _garage;
	private ParkingGarageView _view;
	private int _totalSpots;
	private int _totalDrivers;
	private Boolean _gateOpen;
	private int _ticketTracker;
	private int _lostTicketFee;
	private int _hourlyCost;
	private Set<Driver> _drivers;
	private Set<Administrator> _admins;
	private FinanceUsage fUsage;
	private OccupancyUsage oUsage;
	
	public ParkingGarage() {
		_garage = "garage";
		_totalSpots = 0;
		_totalDrivers = 0;
		_lostTicketFee = 500;
		_hourlyCost = 1;
		_gateOpen = false;
		_ticketTracker = 0;
		_drivers = new HashSet<Driver>();
		_admins = new HashSet<Administrator>();
		addAdministrator("admin", "password");
		fUsage = new FinanceUsage();
		oUsage = new OccupancyUsage();
	}
	
	public void addAdministrator(String name, String password) {
		if (!_admins.contains(name)) {
			Administrator admin = new Administrator(name, password);	
			_admins.add(admin);
		}
	}

	public int getTotalOccupancy() {
		return _totalSpots;		
	}

	public int getAvailability() {
		return (_totalSpots - _totalDrivers);		
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
	
	public int printTicket() {
		_ticketTracker++;
		Driver driver = new Driver(_ticketTracker, _hourlyCost);
		_drivers.add(driver);
		fUsage.addTransaction(driver.getTicket());
		oUsage.addTransaction(driver.getTicket());
		return _ticketTracker;
	}

	public void addDriver() {
		if(getAvailability() > 0)
			_totalDrivers++;
	}
	
	public void removeDriver() {
		if (_totalDrivers > 0)
			_totalDrivers--;
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
		return false;
	}

	public int getLostTicketFee() {
		return _lostTicketFee;
	}
	
	private Driver getDriver(int ticket) {
		for (Driver driver: _drivers) {
			int id = driver.getTicketNumber();
			if ((id == ticket)) {
				return driver;
			}
		}
		return null;
	}

	public int getTicketCost(int ticket) {
		Driver driver = getDriver(ticket);
		int cost = driver.getTicketCost();
		return cost;
	}

	public Boolean logInAdmin(String username, String password) {
		System.out.println(_admins);
		for (Administrator admin: _admins) {
			String name = admin.getUsername();
			String pwd = admin.getPassword();
			System.out.println(name.equals(username));
			System.out.println(password.equals(pwd));
			if ((name.equals(username)) && (password.equals(pwd))) {
				return true;
			}
		}
		return false;
	}

	public void setTotalOccupancy(int number) {
		if (number >= _totalDrivers && number > 0) {
			_totalSpots = number;
		}
	}

	public void setTotalDrivers(int number) {
		if (_totalSpots >= number && number > 0) {
			_totalDrivers = number;
		} 
	}

	public String getUsageString(String typeArray, String timeArray) {
		String usage = "";
		if (typeArray == "Finance") {
			usage = fUsage.getUsage(timeArray);
		} else if (typeArray == "Occupancy") {
			usage = oUsage.getUsage(timeArray);
		}
		return usage;
		
	}
}