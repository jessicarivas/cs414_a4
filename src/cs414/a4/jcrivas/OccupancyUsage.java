package cs414.a4.jcrivas;

import java.util.Set;
import java.util.concurrent.TimeUnit;

class OccupancyUsage extends GarageUsage {
	
	public String getUsage(String timeFrame) {
		long time = 0;
		switch(timeFrame) {
			case "Hour": time = HOUR_TO_MILLISECONDS;
			case "Day": time = DAY_TO_MILLISECONDS;
			case "Week": time = WEEK_TO_MILLISECONDS;
			case "Month": time = MONTH_TO_MILLISECONDS;
		}
		long now  = new java.util.Date().getTime();
		String usage = "Total occupancy usage for the last " + timeFrame + " is " + findTickets(now, now - time);
		return usage;
	}
	
	private String findTickets(long now, long time) {
		int total = 0;
		for (Ticket ticket : _tickets) {
			long startTime = ticket.getStartTime();
			long endTime = ticket.getEndTime();
			if (endTime != 0) {
				if (endTime > time){ 
					total ++;
				}
			} else if (startTime > time) {
				total ++;	
			}
		}
		String totalString = total + " cars.";
		return totalString;
	}
	

}
