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
                		controller.printTicket();
                		update();
                		Object[] options = {"Enter garage"};
                		int n = JOptionPane.showOptionDialog(mainMenu,
                				"Thank you for using our garage. Gate is now open. Please take your ticket and enter garage.", 
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
                    Object[] options = {"Submit ticket number", "I lost my ticket"};


	            JPanel panel = new JPanel();
	            panel.add(new JLabel("Enter ticket number"));
	            JTextField textField = new JTextField(15);
	            panel.add(textField);
	
	            int result = JOptionPane.showOptionDialog(null, panel, "Exit Garage",
	                    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
	                    null, options, null);
	            if (result == JOptionPane.YES_OPTION){
	            	if (controller.getTicket(textField.getText()))
	                JOptionPane.showMessageDialog(null, textField.getText());
	            }


                //yes/no (with customized wording)
                } else if (command == logInAdminCommand) {
                    controller.logInAdmin();
                return;
                }
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


	  //Called only in createButtonPanel below, to set the attributes of
	  //  the buttons (their label, font) and add them to the GUI
	  private void buttonSetup(JPanel  panelForButtons,
	                           JButton b,
	                           String  bLabel)
	  {
			b.setText(bLabel);
			b.setFont(buttonFont);
			panelForButtons.add(b);
	  }
	  
	  
	  //Creates the entire panel of textfields and buttons (using GridLayout)
	  //It both creates the buttons (from the controller) and
	  //  places them in the grid (for the view)
	  private JPanel createColorPalette(String color,JTextField colorField)
		{
			JPanel colorChoose = new JPanel();
			colorChoose.setLayout(new GridLayout(2,2));
			
			colorChoose.add(new JLabel(color));
//			JButton colorUp = controller.printTicket();
//			buttonSetup(colorChoose,colorUp,"+10");
			colorChoose.add(colorField);
			
			return colorChoose;
	  }
			
			
	  //When the model changes, it calls update, which determines how to
	  //  view the model by calling its getRed/getGreen/getBlue methods.
	  //This seems a bit circular, but it isn't (you need to know
	  //   more about the MVC pattern to understand better)
		void update()
		{
	     int availability = garage.getAvailability();
	     String gateOpen = garage.getGatePosition();

	     sign.setText  ("parking spots: " + availability);
	     gate.setText("gate: " +  gateOpen);
			 repaint();
		}





	  //Instance Variables
	  

	  private ParkingGarageController controller;  //Controller creates button in View
		private ParkingGarage      garage;       //Model tells update what to display
		
		Font       buttonFont;          //Information shared by multiple methods
		JTextField red, green, blue, sign, gate;
		Color      colorSwatch;
	 }
