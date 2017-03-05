package edu.utexas.se.swing.sample;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class FirstWindow extends JFrame  
{
	//Create the new cardlayout and main panel for the overall structure
	private JPanel mainPanel = new JPanel(); 
	CardLayout cl = new CardLayout();

	//Create the first subPane, to be showed when the program starts
	private JPanel firstWindowPane = new JPanel();
	
	//Initialize the options in the combo box
	private String[] names = {"Select User", "Julia", "Zach", "Deif"};
	private JComboBox nameList = new JComboBox (names);
	
	
	//Create option to create a new user
	private JButton addNewUser = new JButton ("Add New User");
	private String nameSelected = names[0];
	private int counter = 0;
	private String newUserName;
	private String[] newNameArray; 

	//Set up option for when you are creating a new user
	private JPanel newUserPane = new JPanel();
	private JTextField name = new JTextField();
	private JButton doneEntering = new JButton ("Return");
	private JLabel enterName = new JLabel("Please enter your name here: ");
	private JLabel instructions = new JLabel ("Press enter after you type your name.");

	 //Create JPanel for the UserGUI main panel
	private JPanel contentPane = new JPanel();
	private String user;
	private JLabel thisUser = new JLabel(); 
	
	private JButton makePayment = new JButton ("Make Payment");
	private JButton checkBalance = new JButton ("Check Balance");
	private JLabel transactionHistory = new JLabel ("transactionHistory");
	 
    public FirstWindow()
    {
    	
        super("Roommate Money List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
        //make sure mainPanel has the card layout
        mainPanel.setLayout(cl);
        
        initializeFirstWindow();
		
		//Must initialize the nameList elements before showing
		nameList.setFont(new Font ("Times New Roman", Font.BOLD, 40));
		
		//Add the components to the first panel
		firstWindowPane.add(nameList);
		firstWindowPane.add(addNewUser);
		
		initializeNewUser();
		
		//Adding components to the user pane
		newUserPane.add(enterName);	
		newUserPane.add(name);
		newUserPane.add(instructions);
		newUserPane.add(doneEntering);
		
		initializeMainFrame();
		
		//Add the components to the main panel
		mainPanel.add(firstWindowPane, "1");	 //Creating the main login window
		mainPanel.add(contentPane, "2a");
		mainPanel.add(newUserPane, "2b");	//Creating a new user

		//If you select any user's name, go to the "2a" main panel
		nameList.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
					String tempX = (String) nameList.getSelectedItem();
					setNameSelected(tempX);
					cl.show(mainPanel, "2a");
				}
		});
		addNewUser.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				cl.show(mainPanel, "2b");
			}
		});
	
		name.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				//Create characteristics of a new user here
				System.out.println("Pressed");
				newUserName = name.getText();
				firstWindowPane.remove(nameList);
				firstWindowPane.remove(addNewUser);
				newNameArray = new String[names.length+1];
				for (int i = 0; i < names.length; i++)
					newNameArray[i] = names [i];
				newNameArray[names.length] = newUserName;
				nameList = new JComboBox (newNameArray);
				firstWindowPane.add(nameList);
				firstWindowPane.add(addNewUser);
			}

		}); 
		
		doneEntering.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				cl.show(mainPanel, "1");
			}
		});
		
		//Initialize Buttons
		initializeCheckBalance();
		initializeMakePayment();
		initializeTransactionLabel();
		
		//GridBagLayout for Main Panel
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		
		thisUser.setText((String) nameList.getSelectedItem());
		thisUser.setFont(new Font ("Times New Roman", Font.BOLD, 30));
		thisUser.revalidate();
		thisUser.repaint();

		contentPane.add(thisUser, c);
		
		c.gridy = 1;
		contentPane.add(makePayment, c);
		c.gridy = 2;
		contentPane.add(checkBalance, c);
		c.gridy = 3;
		contentPane.add(transactionHistory, c);
		
		makePayment.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				System.out.println("Payment made");
			}
		});
		
		
		
		checkBalance.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				System.out.println("Balance Checked");
			}
		});

        add(mainPanel);
        pack();
        setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void setNameSelected(String tempX){
    	nameSelected = tempX;
    	thisUser.setText(nameSelected);
    	thisUser.repaint();
    }
    

    public void initializeFirstWindow(){
		//Initialize the information for your First Window Pane
		firstWindowPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		firstWindowPane.setLayout(new GridLayout(2, 1));
		firstWindowPane.setSize(1000, 1000);
		setBounds(500, 100, 1500, 1500);		//x y position, x y length		
		firstWindowPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				
		//Initialize the values for the First Window
		firstWindowPane.setBounds(500, 100, 800, 150);		//x y position, x y length	
	}
    
    public void initializeNewUser(){
    	//New User Pane initalization
		newUserPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		newUserPane.setLayout(new GridLayout(2, 2));
		
		//Formatting font
		enterName.setFont(new Font("Times New Roman", Font.BOLD, 30));
		name.setFont(new Font("Times New Roman", Font.BOLD, 30));
		doneEntering.setFont(new Font("Times New Roman", Font.BOLD, 30));
		
    }
    
    public void initializeMainFrame(){
    	//Setting conditions for the main user GUI
    	contentPane.setBounds(500, 100, 1500, 1500);
    	contentPane.setSize(100, 100);
    	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    	contentPane.setLayout(new GridBagLayout());
    }
    
    public void initializeCheckBalance(){
    	checkBalance.setFont(new Font("Times New Roman", Font.BOLD, 30));
    }
    
    public void initializeMakePayment(){
    	makePayment.setFont(new Font("Times New Roman", Font.BOLD, 30));
    }
    
    public void initializeTransactionLabel(){
    	transactionHistory.setFont(new Font("Times New Roman", Font.BOLD, 30));
    }
    
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable(){

            public void run(){
            	try {
            		FirstWindow frame = new FirstWindow();
            		frame.setVisible(true);
            	}
            	catch (Exception e){
            		e.printStackTrace();
            	}
            }

        });
    }

    
}
