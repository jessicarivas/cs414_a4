package cs414.a4.jcrivas;


import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.awt.Component;
import java.awt.Container;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



public class ParkingGarageView extends JFrame
{

  //Trivial constructor (could be automatically supplied by Java)
	public ParkingGarageView()
	{}
	
  //Refer to the model (used in the update method, to call the getDisplay
  //  method in the model) 
	public void addModel(ParkingGarage m)
	{garage = m;}

	
  //Refer to the controller (used to build the buttons the view will
  //  place in the view) 
	public void addController(ParkingGarageController c)
	{controller = c;}
	
	  //build does the heavy lifting; it builds the view, populating it
	  //  with the appropriate display and buttons
	  //GUI applications have lots of little details to specify to make
	  //  them look nice, and this method is in charge of them all
		public void build()
		{
	        final int numButtons = 3;
	        JRadioButton[] radioButtons = new JRadioButton[numButtons];
	        
	        final String enterGarageCommand = "default";
	        final String exitGarageCommand = "exit";
	        final String logInAdminCommand = "login";
	        final JLabel title = new JLabel("Welcome to the garage. Select an option.",
                    JLabel.CENTER);
	        JButton mainMenuButton = null;
	        final ButtonGroup group = new ButtonGroup();

		  //When a window close icon is pressed, exit the entire program
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				}
			});
			
			//Size the frame to a reasonable size and label it
			setSize(600, 400);
			setTitle("Parking Garage");
			

			//Create a font for all the buttons (save it in a field, so it can be
			//  referred to in methods called subsequently: buttonSetup)
			buttonFont = new Font("Monospaced", Font.BOLD, 18);

	    //Create a panel to store all the GUI components:
	    //  colors has textfields and buttons to enter the color values
	    //  show displays the color palette and the hexidecimal value
	    JPanel sideBySide = new JPanel();
	    sideBySide.setLayout(new GridLayout(1,2));
	    
	    //Create a panel to store the textfields and buttons to enter the
	    //  color values, and put it in on the left of sideBySide.
	    JPanel mainMenu = new JPanel();
	    mainMenu.setLayout(new GridLayout(5,1));
	    mainMenu.add(title);
        radioButtons[0] = new JRadioButton(
        		"<html><font color=red>Enter Garage</font></html>");
        radioButtons[0].setActionCommand(enterGarageCommand);

        radioButtons[1] = new JRadioButton(
        	"<html><font color=green>Exit Garage</font></html>");
        radioButtons[1].setActionCommand(exitGarageCommand);

        radioButtons[2] = new JRadioButton(
          "<html><font color=blue>Log In Admin</font></html>");
        radioButtons[2].setActionCommand(logInAdminCommand);

	    for (int i = 0; i < numButtons; i++) {
	    	group.add(radioButtons[i]);
	    	mainMenu.add(radioButtons[i]);
	    }
        radioButtons[0].setSelected(true);
        mainMenuButton = new JButton("Select Option");
        mainMenu.add(mainMenuButton);
        

        mainMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String command = group.getSelection().getActionCommand();

                //ok dialog
                if (command == enterGarageCommand) {
                	if (garage.getAvailability() == 0) {
                		JOptionPane.showMessageDialog(mainMenu,
                				"No garage availability. Please come back later.");
                	} else {
                		int ticketNumber = controller.printTicket();
                		update();
                		Object[] options = {"Enter garage"};
                		int n = JOptionPane.showOptionDialog(mainMenu,
                				"Thank you for using our garage. Your ticket number is: " + ticketNumber  + ". Gate is now open. Please take your ticket and enter garage.", 
                				"Dispensing ticket", 
                				JOptionPane.OK_OPTION,
                				JOptionPane.QUESTION_MESSAGE,
                				null,
                				options,
                				options[0]
                				);
                		if (n == JOptionPane.OK_OPTION) {
                			controller.closeGate();
                			update();
                		}
                	}

                } else if (command == exitGarageCommand) {
                    Object[] options = {"Submit Ticket Number", "Lost/Invalid Ticket"};

		            JPanel panel = new JPanel();
		            panel.add(new JLabel("Enter ticket number"));
		            JTextField textField = new JTextField(15);
		            panel.add(textField);
		            int cost = 0;
		
		            int result = JOptionPane.showOptionDialog(null, panel, "Exit Garage",
		                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
		                    null, options, null);
		            if (result == JOptionPane.YES_OPTION){
		            	if (!controller.getTicket(textField.getText())) {
		            		JOptionPane.showMessageDialog(mainMenu,
	                				"Incorrect ticket number. Please try again.");
		            		} else {
		            			cost = controller.getTicketCost(textField.getText());
				            	JOptionPane.showMessageDialog(mainMenu,
				                        "Total ticket fee is $" + cost + ".");
		            		}	
		            } else if (result == JOptionPane.NO_OPTION) {
		            	cost = controller.getLostTicketFee();
		            	JOptionPane.showMessageDialog(mainMenu,
		                        "Lost ticket fee is $" + cost + ".");
		            }
		            if (cost > 0) {
	                    Object[] paymentOptions = {"Cash", "Credit Card"};
	                    Object[] cashOptions = {"Sufficient Cash", "Insufficient Cash"};
	                    Object[] creditOptions = {"Valid Credit Card", "Declined Credit Card"};

			            JPanel paymentPanel = new JPanel();
			            paymentPanel.add(new JLabel("Pay Ticket"));
			            Boolean paymentApproved = false;
			            int submitPayment = 0;
		
			            int paymentType = JOptionPane.showOptionDialog(null, paymentPanel, "Select payment type",
			                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
			                    null, paymentOptions, null);
			            if (paymentType == JOptionPane.NO_OPTION) {
				            JPanel creditPanel = new JPanel();
				            creditPanel.add(new JLabel("Enter credit card number"));
				            JTextField creditNumber = new JTextField(15);
				            creditPanel.add(creditNumber);
				            
				            submitPayment = JOptionPane.showOptionDialog(null, creditPanel, "Insert credit card",
				                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
				                    null, creditOptions, null);
			            }
			            else if (paymentType == JOptionPane.YES_OPTION) {
				            submitPayment = JOptionPane.showOptionDialog(null, paymentPanel, "Insert cash",
				                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
				                    null, cashOptions, null);		            	
			            }
			            if (submitPayment == JOptionPane.YES_OPTION) {
			            	controller.payTicket();
			            	update();
	                		Object[] exitOptions = {"Exit garage"};
	                		int n = JOptionPane.showOptionDialog(mainMenu,
	                				"Thank you for using our garage. Gate is now open. Please exit garage.", 
	                				"Dispensing ticket", 
	                				JOptionPane.OK_OPTION,
	                				JOptionPane.QUESTION_MESSAGE,
	                				null,
	                				exitOptions,
	                				exitOptions[0]
	                				);
	                		if (n == JOptionPane.OK_OPTION) {
	                			controller.closeGate();
	                			update();
	                		}
			            } else {
	                		JOptionPane.showMessageDialog(mainMenu,
	                				"Invalid payment. Please contact garage administrator.");
			            }
		            }
		            

                } else if (command == logInAdminCommand) {
                    Object[] options = {"Log In"};

		            JPanel panel = new JPanel();
		            panel.add(new JLabel("Username:"));
		            JTextField userField = new JTextField(15);
		            panel.add(userField);
		            panel.add(new JLabel("Password:"));
		            JTextField passwordField = new JTextField(15);
		            panel.add(passwordField);
		            int cost = 0;
		
		            int result = JOptionPane.showOptionDialog(null, panel, "Log In Admin",
		                    JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE,
		                    null, options, null);   
		            if (result == JOptionPane.OK_OPTION) {
		            	if(controller.logInAdmin(userField.getText(), passwordField.getText())) {
		            		launchAdminPanel();
		            	} else {
	                		JOptionPane.showMessageDialog(mainMenu,
	                				"Incorrect password. Please try again.");		            		
		            	}
		            }
                }
            }

			private void launchAdminPanel() {
				 final int numButtons = 3;
			        JRadioButton[] radioButtons = new JRadioButton[numButtons];
			        
			        final String enterGarageCommand = "default";
			        final String exitGarageCommand = "exit";
			        final String logInAdminCommand = "login";
			        final JLabel title = new JLabel("Welcome to the garage. Select an option.",
		                    JLabel.CENTER);
			        JButton mainMenuButton = null;
			        final ButtonGroup group = new ButtonGroup();

				  //When a window close icon is pressed, exit the entire program
					addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e)
						{
							System.exit(0);
						}
					});
					
					//Size the frame to a reasonable size and label it
					setSize(600, 400);
					setTitle("Parking Garage");
					

					//Create a font for all the buttons (save it in a field, so it can be
					//  referred to in methods called subsequently: buttonSetup)
					buttonFont = new Font("Monospaced", Font.BOLD, 18);

			    //Create a panel to store all the GUI components:
			    //  colors has textfields and buttons to enter the color values
			    //  show displays the color palette and the hexidecimal value
			    JPanel adminPanel = new JPanel();
			    adminPanel.setLayout(new GridLayout(1,2));
			    
			    //Create a panel to store the textfields and buttons to enter the
			    //  color values, and put it in on the left of sideBySide.
			    JPanel mainMenu = new JPanel();
			    mainMenu.setLayout(new GridLayout(5,1));
			    mainMenu.add(title);
		        radioButtons[0] = new JRadioButton(
		        		"<html><font color=red>Enter Garage</font></html>");
		        radioButtons[0].setActionCommand(enterGarageCommand);

		        radioButtons[1] = new JRadioButton(
		        	"<html><font color=green>Exit Garage</font></html>");
		        radioButtons[1].setActionCommand(exitGarageCommand);

		        radioButtons[2] = new JRadioButton(
		          "<html><font color=blue>Log In Admin</font></html>");
		        radioButtons[2].setActionCommand(logInAdminCommand);

			    for (int i = 0; i < numButtons; i++) {
			    	group.add(radioButtons[i]);
			    	mainMenu.add(radioButtons[i]);
			    }
		        radioButtons[0].setSelected(true);
		        mainMenuButton = new JButton("Select Option");
		        mainMenu.add(mainMenuButton);
		        

		        mainMenuButton.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		                String command = group.getSelection().getActionCommand();

		                //ok dialog
		                if (command == enterGarageCommand) {

		                } else if (command == exitGarageCommand) {

		                } else if (command == logInAdminCommand) {
		                }
		            }
		        });
		        adminPanel.add(mainMenu);
				Container contentPane = getContentPane();
				contentPane.add(adminPanel, "Center");
				
				update();
			}
        });

	    sideBySide.add(mainMenu);
	    
	    
	    //Create a panel to show displays the color palette and the
	    //  hexidecimal value, and put it in on the right of sideBySide.
	    JPanel show = new JPanel();
	    show.setLayout(new GridLayout(0,1));
	    show.add(new JPanel(){
	      public void paintComponent(Graphics g)
	      {
	        if (colorSwatch == null)
	          return;
	        Dimension size = getSize();
	        g.setColor(colorSwatch);
	        g.fillRect(size.width/4,size.height/4,size.width/2,size.height/2);
	      }},"Center");
	    
	    sign = new JTextField();
	    sign.setEditable(false);
	    sign.setBackground(Color.gray);
	    sign.setFont(buttonFont);
	    show.add(sign);
	    
	    gate = new JTextField();
	    gate.setEditable(false);
	    gate.setBackground(Color.white);
	    gate.setFont(buttonFont);
	    show.add(gate);
	    
	    sideBySide.add(show);
	    
	    //Put the side by side panel at the center of the main JFrame's
	    //  content panel
			Container contentPane = getContentPane();
			contentPane.add(sideBySide, "Center");
			
			update();
		}
	 			
	  //When the model changes, it calls update, which determines how to
	  //  view the model by calling its getRed/getGreen/getBlue methods.
	  //This seems a bit circular, but it isn't (you need to know
	  //   more about the MVC pattern to understand better)
		void update() {
			int availability = garage.getAvailability();
			String gateOpen = garage.getGatePosition();

			sign.setText  ("parking spots: " + availability);
			gate.setText("gate: " +  gateOpen);
			repaint();
		}


	  //Instance Variables
	  

	  private ParkingGarageController controller;  //Controller creates button in View
	  private ParkingGarage garage;       //Model tells update what to display
		
		Font       buttonFont;          //Information shared by multiple methods
		JTextField red, green, blue, sign, gate;
		Color      colorSwatch;
	 }
