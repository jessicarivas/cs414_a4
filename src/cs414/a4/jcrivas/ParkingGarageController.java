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

  
  //Build/Return a Color button: it calls the changeColor method in model
  JButton get(final String color, final int amount)
  {
	  JButton b = new JButton();
	  
	  b.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e)
		  {
		     System.out.println("Debug-Controller: " + "Color +10/-10 button pressed (" +
		                        color+","+amount+")");
         garage.getAvailability();}
	  });
	  
	  return b;
  }
    
     
  //Build/Return a JTextField: for entering Numeric Values: it calls the changeColor
  //  method in model
  JTextField getColorField(final String color)
  {
	  final String     errorMessage = "Enter [0,255]";
	  final JTextField tf           = new JTextField(errorMessage,5);
	  
	  tf.addActionListener(new ActionListener()
	  {
	    public void actionPerformed(ActionEvent action)
	    {
		    System.out.println("Debug-Controller: " + "Color text field activated (" +
		                        color+",\""+tf.getText()+"\")");
//	      garage.selectAction(color,tf.getText());
	    }
	  });
	  
	  tf.addFocusListener(new FocusAdapter()
	  {
	    public void focusLost(FocusEvent event)
	    {
		    System.out.println("Debug-Controller: " + "Color text field loses focus (" +
		                        color+",\""+tf.getText()+"\")");
//	      garage.changeColorViaTextField(color,tf.getText());
	    }
	  });

	  return tf;
  }

public int printTicket() {
	garage.openGate();
	int ticket = garage.printTicket();
	// TODO Auto-generated method stub
	return ticket;
}

public void payTicket() {
	// TODO Auto-generated method stub
	
}

public void logInAdmin() {
	// TODO Auto-generated method stub
	
}

public void closeGate() {
	garage.closeGate();
	garage.updateOccupancy();
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


