package spreadsheet;
import java.util.*;

public class User{
	//Properties
	public String name;
	public double balance; //owed-debt
	public boolean isWinner;
	public ArrayList<Transfer> transactions; //for now, in input order, not chronological order
	public int[] date; //date since last transaction recorded
	
	public User(String name){
		this.name = name;
		this.isWinner = false;
		this.transactions = null;
		this.date = new int[3];
	}
	
	public void addPayment(User payee, Transfer t){ //"this" is paying "t" to "payee"
		//add transfer to transactions history
		this.transactions.add(t);
		payee.transactions.add(t);
		
		this.balance = this.balance - t.amount; //decrease this' balance
		payee.balance = payee.balance + t.amount; //increase payee's balance
		
		//somehow adjust to chronological order? ORDER BY DATE
		//records latest transaction inputed date of "this" and "payee"
			// if can order chronological, retrieve last transaction & input that date
		this.date = t.date;
		payee.date = t.date;
	}
	
	public void cancelPayment(int index){
		//identify users and transfer involved
		User tPayee = this.transactions.get(index).payee;
		User tPayer = this.transactions.get(index).payer;
		
		//payee loses that payment amt
		tPayee.balance = tPayee.balance - this.transactions.get(index).amount;
		//payer gets that payment amt back
		tPayer.balance = tPayer.balance + this.transactions.get(index).amount;
		
		//both remove this transfer from the transaction history
		tPayee.transactions.remove(this.transactions.get(index));
		tPayer.transactions.remove(this.transactions.get(index));
		
		//reorder transactions by date somehow? should already be chronological order if was added in in order from add payment
		
		//both adjust date of last transaction
		tPayee.date = tPayee.transactions.get(transactions.size()-1).date;
		tPayer.date = tPayer.transactions.get(transactions.size()-1).date;
		}
	
//	public void editPayment(int index, Transfer tNew){ //"edits" by replacing transfer at "index" with tNew that has updated info
//		//identify users involved
//		Transfer old = this.transations.get(index);
//		User tPayee = this.transactions.get(index).payee;
//		User tPayer = this.transactions.get(index).payer;
//		
//		//replacing with updated transfer tNew
//		if(tPayee.name.equals(this.name)){
//			tPayer.transactions.set(tPayer.indexOf(old), tNew);
//			this.transactions.set(index, tNew);
//		}
//		else{
//			tPayee.transactions.set(tPayee.indexOf(old), tNew);
//			this.transactions.set(index, tNew);
//		}
//		//reoder transactions by date for tPayee and tPayer somehow
//	}
	
	
	
}
