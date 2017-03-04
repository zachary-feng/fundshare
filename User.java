package spreadsheet;
import java.util.*;

public class User{
	//Properties
	public String name;
	public double balance; //owed-debt
	public boolean isWinner;
	public ArrayList<Transfer> transactions;
	public int[] date; //date since last payment  
	
	public User(String name){
		this.name = name;
		this.isWinner = false;
		this.transactions = null;
		this.date = new int[3];
		
	}
	
	public void payment(User payee, Transfer t){ //"this" is paying "t" to "payee"
		this.balance = this.balance - t.amount;
		payee.balance = payee.balance + t.amount;
		this.transactions.add(t);
		payee.transactions.add(t);
		this.date = t.date;
	}
	
}
