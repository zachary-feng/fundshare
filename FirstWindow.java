package edu.utexas.se.swing.sample;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.util.*;

public class FirstWindow extends JFrame  
{
	//Create the new cardlayout and main panel for the overall structure
	private JPanel mainPanel = new JPanel(); 
	CardLayout cl = new CardLayout();

	//Create the first subPane, to be showed when the program starts
	private JPanel firstWindowPane = new JPanel();
	
	//Initialize the options in the combo box
	private String storeUser, storePass;
	private JLabel newPass = new JLabel("Select a new password!");
	private JTextField newPassEnterHere = new JTextField ();
	private JComboBox<String> nameList = new JComboBox<String>();
	//In Main Menu: Username and Password
	private JPanel passwordPanel = new JPanel();
	private JLabel username = new JLabel ("Username: ");
	private JLabel password = new JLabel ("Password: ");
	private JTextField userInput = new JTextField();
	private JTextField passInput = new JTextField();
	private String testUser, testPass;
	
	//Logout button
	private JButton logout = new JButton("Log Out");
	
	//Create option to create a new user
	private JButton addNewUser = new JButton ("Add New User");
	private String newUserName;

	//Set up option for when you are creating a new user
	private JPanel newUserPane = new JPanel();
	private JTextField name = new JTextField();
	private JButton doneEntering = new JButton ("Return");
	private JLabel enterName = new JLabel("Please enter your name here: ");
	private JLabel instructions = new JLabel ("Press enter after you type your name.");

	 //Create JPanel for the UserGUI main panel
	private JPanel contentPane = new JPanel();
	private JLabel thisUser = new JLabel(); 
	
	private JPanel paymentPane = new JPanel();
	private JLabel payee = new JLabel ("Select who you owe money to");
	private JComboBox<String> payeeList = new JComboBox<String>();
	private JLabel amountText = new JLabel ("How much do you owe them?");
	private JTextField amount = new JTextField();
	private String chosenPayee;
	private double amountRead;
	
	private JPanel balancePane = new JPanel();
	private JButton doneBalance = new JButton ("Return");
	private JLabel balanceText = new JLabel ("Your balance in dollars is: ");
	private JLabel balance = new JLabel();
	
	private JPanel transactionHistoryPane = new JPanel();
	private JButton doneHistory = new JButton ("Return");
	private String[] columns = new String[]{"payer", "payee", "amount"};
	private String[][] data;
	private JTable table;
	
	private JButton makePayment = new JButton ("Make Payment");	
	private JButton checkBalance = new JButton ("Check Balance");
	private JButton transactionHistory = new JButton ("Transaction History");
	 
    public FirstWindow()
    {	
 
        super("Roommate Money List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        makePayment.setFont(new Font("Times New Roman", Font.BOLD, 30));
        addNewUser.setFont(new Font("Times New Roman", Font.BOLD, 30));
        
        //make sure mainPanel has the card layout
        mainPanel.setLayout(cl);
        
        initializeFirstWindow();
		
		//Must initialize the nameList elements before showing
		nameList.setFont(new Font ("Times New Roman", Font.BOLD, 40));
		
		//Add the components to the first panel
		firstWindowPane.add(nameList);
		firstWindowPane.add(addNewUser);
		
		initializePasswordPanel();

		passwordPanel.add(username);
		passwordPanel.add(userInput);
		passwordPanel.add(password);
		passwordPanel.add(passInput);

		
		
		
		userInput.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				setTestUser(userInput.getText());
			}
		});
		
		passInput.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				if (SQLMain.verify(testUser, passInput.getText()))
					cl.show(mainPanel, "2a");
			}
		});
		

			
		initializeNewUser();
		
		//Adding components to the user pane
		newUserPane.add(enterName);	
		newUserPane.add(name);
		newUserPane.add(newPass);
		newUserPane.add(newPassEnterHere);
		newUserPane.add(instructions);
		newUserPane.add(doneEntering);

		
		initializeMainFrame();
		
		//Add the components to the main panel
		mainPanel.add(firstWindowPane, "1");	 //Creating the main login window
		mainPanel.add(contentPane, "2a");
		mainPanel.add(newUserPane, "2b");	//Creating a new user
		mainPanel.add(passwordPanel, "1b");	//Checking logins
		
	
		
		//If you select any user's name, go to the "2a" main panel
		name.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				String tempX = name.getText();
					setNameSelected(tempX);
					System.out.println("user is " + tempX );
				}
		});
		
		newPassEnterHere.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String tempX = newPassEnterHere.getText();
				setPassword(tempX);
				System.out.println(" pass is " + storePass);
				SQLMain.registerUser(storeUser, storePass);
				
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
				newUserName = name.getText();
				nameList.addItem(newUserName);
			}

		}); 
		
		doneEntering.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				cl.show(mainPanel, "1");
				nameList.addActionListener(new ActionListener(){
					public void actionPerformed (ActionEvent e){
						nameList.getSelectedItem();
						cl.show(mainPanel, "1b");
						userInput.setText((String) nameList.getSelectedItem());
					}
				});
			}
		});
		
		//Initialize Buttons
		initializeCheckBalance();
		initializeMakePayment();
		initializeTransactionLabel();
		
		//GridBagLayout for Main Panel
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.fill = GridBagConstraints.VERTICAL;

		c.gridx = 0;
		c.gridy = 0;
		
		thisUser.setText((String) nameList.getSelectedItem());
		thisUser.setFont(new Font ("Times New Roman", Font.BOLD, 30));


		contentPane.add(thisUser, c);
		
		c.gridy = 1;
		contentPane.add(makePayment, c);
		c.gridy = 2;
		contentPane.add(checkBalance, c);
		c.gridy = 3;
		c.gridheight = 3;
		c.fill = GridBagConstraints.VERTICAL;
		contentPane.add(transactionHistory, c);
		contentPane.add(logout);

		logout.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				cl.show(mainPanel, "1");
			}
		});
		
		transactionHistory.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				
				data = SQLMain.pastPayment(userInput.getText());
				table = new JTable(data, columns);
				table.setRowHeight(50);
				
				initializeTransactionHistory();
				table.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 30));
				
				transactionHistoryPane.setLayout(new BorderLayout());
				transactionHistoryPane.add(table.getTableHeader(), BorderLayout.NORTH);
				transactionHistoryPane.add(table, BorderLayout.CENTER);
				transactionHistoryPane.add(doneHistory, BorderLayout.SOUTH);

				doneHistory.addActionListener(new ActionListener(){
					public void actionPerformed (ActionEvent e){
						cl.show(mainPanel, "2a");
						transactionHistoryPane = new JPanel();
					}
				});
			
				mainPanel.add(transactionHistoryPane, "hist");
				cl.show(mainPanel, "hist");
				table = new JTable();
			}
		});
		
		makePayment.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				initializeMakePayment();
									
				paymentPane.add(payee);
				paymentPane.add(payeeList);
				paymentPane.add(amountText);
				paymentPane.add(amount);
				
				amount.addActionListener(new ActionListener() {
					public void actionPerformed (ActionEvent acd){
//						while(!isDouble(amount.getText())){
//						}
						amountRead = Double.parseDouble(amount.getText());
						chosenPayee = ((String) payeeList.getSelectedItem());
						SQLMain.makePayment(userInput.getText(), chosenPayee, amountRead);
						cl.show(mainPanel, "2a");
						amount = new JTextField();
						amountRead = 0;
						paymentPane = new JPanel();
					}
				});
				
				mainPanel.add(paymentPane, "pay");
				cl.show(mainPanel, "pay");
			}
		});
		
		
		checkBalance.addActionListener(new ActionListener(){
			public void actionPerformed (ActionEvent e){
				balance.setText(Double.toString(SQLMain.getBalance(userInput.getText())));
				initializeBalanceCheck();
				balancePane.add(balanceText);
				balancePane.add(balance);
				balancePane.add(doneBalance);
				doneBalance.addActionListener(new ActionListener(){
					public void actionPerformed (ActionEvent e){
						cl.show(mainPanel, "2a");
						balancePane = new JPanel();
					}
				});
				amount.addActionListener(new ActionListener() {
					public void actionPerformed (ActionEvent e){
						cl.show(mainPanel, "2a");
						balancePane = new JPanel();
						balance = new JLabel();
					}});
				mainPanel.add(balancePane, "bal");
				cl.show(mainPanel, "bal");
				balancePane = new JPanel();
				balance = new JLabel();
			}
		});

		
        add(mainPanel);
        pack();
        setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    
    public void initializeTransactionHistory(){
    	transactionHistoryPane.setLayout(new GridLayout(2, 5));
    	transactionHistoryPane.setSize(1000,1000);
    	
    	transactionHistoryPane.setVisible(true);
    	doneHistory.setFont(new Font("Times New Roman", Font.BOLD, 30));
    	table.setFont(new Font("Times New Roman", Font.BOLD, 30));
    }
 
    public void initializeMakePayment(){
    	amount = new JTextField();
    	amountRead = 0;
    	payeeList = new JComboBox();
    	paymentPane.setLayout(new GridLayout(2, 2));
    	paymentPane.setVisible(true);
    	
    	for (int i = 0; i < nameList.getItemCount(); i++){
			if (!nameList.getItemAt(i).equals(userInput.getText()))
				payeeList.addItem(nameList.getItemAt(i)); 
    	}
    	payee.setFont(new Font("Times New Roman", Font.BOLD, 30));
    	payeeList.setFont(new Font("Times New Roman", Font.BOLD, 30));
    	amountText.setFont(new Font("Times New Roman", Font.BOLD, 30));
    	amount.setFont(new Font("Times New Roman", Font.BOLD, 30));
    }
    
    public void initializeBalanceCheck(){
    	balancePane.setLayout(new GridLayout(2, 2));
    	balancePane.setVisible(true);
    	
    	balance.setFont(new Font("Times New Roman", Font.BOLD, 30));
    	balanceText.setFont(new Font("Times New Roman", Font.BOLD, 30));
    	doneBalance.setFont(new Font("Times New Roman", Font.BOLD, 30));
    	
    }
    
    public void setTestUser (String user){
    	testUser = user;
    }
    
    public void setTestPass (String pass){
    	testPass = pass;
    }
    
    public void initializePasswordPanel(){
    	passwordPanel.setLayout(new GridLayout(2,3));
		passwordPanel.setSize(2000, 1000);
		passwordPanel.setVisible(true);
				
		username.setFont(new Font ("Times New Roman", Font.BOLD, 30));
		password.setFont(new Font ("Times New Roman", Font.BOLD, 30));
		userInput.setFont(new Font ("Times New Roman", Font.BOLD, 30));
		passInput.setFont(new Font ("Times New Roman", Font.BOLD, 30));
    }
    
    public void setNameSelected(String tempX){
    	storeUser = tempX;    	
    }
    
    public void setPassword(String tempX){
    	storePass = tempX;
    }
    

    public void initializeFirstWindow(){
		//Initialize the information for your First Window Pane
		firstWindowPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		firstWindowPane.setLayout(new GridLayout(2, 1));
		firstWindowPane.setSize(2000, 2000);
		setBounds(500, 100, 1500, 1500);		//x y position, x y length		
		firstWindowPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				
		//Initialize the values for the First Window
		firstWindowPane.setBounds(500, 100, 800, 150);		//x y position, x y length	
	}
    
    public void initializeNewUser(){
    	newUserPane.setSize(2000, 2000);
    	//New User Pane initialization
		newUserPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		newUserPane.setLayout(new GridLayout(3, 2));
		
		//Formatting font
		enterName.setFont(new Font("Times New Roman", Font.BOLD, 30));
		name.setFont(new Font("Times New Roman", Font.BOLD, 30));
		doneEntering.setFont(new Font("Times New Roman", Font.BOLD, 30));
		newPassEnterHere.setFont(new Font("Times New Roman", Font.BOLD, 30));
		newPass.setFont(new Font ("Times New Roman", Font.BOLD, 30));
    }
    
    public void initializeMainFrame(){
    	//Setting conditions for the main user GUI
    	contentPane.setBounds(500, 100, 3000, 3000);
    	contentPane.setSize(2000, 2000);
    	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    	contentPane.setLayout(new GridBagLayout());
    }
    
    public void initializeCheckBalance(){
    	checkBalance.setFont(new Font("Times New Roman", Font.BOLD, 30));
    }
    
    public void initializeTransactionLabel(){
    	transactionHistory.setFont(new Font("Times New Roman", Font.BOLD, 30));
    }
    
    private static boolean isDouble(String s){
    	try{
    		Double.parseDouble(s);
    		return true;
    	}
    	catch (Exception e){
    		return false;
    	}
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
