package spreadsheet;
import java.util.*;

public class User{
	//Properties
	public String name;
	public double balance; //owed-debt
	public boolean isWinner;
	public ArrayList<Transfer> transactions;
	
	public User(String name){
		this.name = name;
		this.balance = 0;
		this.isWinner = false;
		this.transactions = new ArrayList<Transfer>();
	}
	
	public void printInfo(){
		System.out.println("Name: " + this.name);
		System.out.println("Balance: " + this.balance);
		System.out.println("Transactions: ");
		for (int i = 0; i<this.transactions.size(); i++){
			System.out.println(this.transactions.get(i).toString());
		}
	}
	
	public void addPayment(Transfer t){ //"this" is paying "t" to "payee"
		if(this.name.equals(t.payer.name)){
			this.balance = this.balance - t.amount;
			t.payee.balance = t.payee.balance + t.amount;
		}
		else{ //this is the payee
			this.balance = this.balance+t.amount;
			t.payer.balance = t.payer.balance - t.amount;
		}
		
		//add transfer to transactions history
		this.transactions.add(t);
		t.payee.transactions.add(t);
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
		if (this.name.equals(tPayee.name)){
			tPayer.transactions.remove(tPayer.indexOf(this.transactions.get(index)));
			tPayee.transactions.remove(this.transactions.get(index));
		}
		else{//this.name is tPayer
			tPayee.transactions.remove(tPayee.indexOf(this.transactions.get(index)));
			tPayer.transactions.remove(this.transactions.get(index));
		}
	}
	
	public void editPayment(int index, Transfer tNew){ //"edits" by replacing transfer at "index" with tNew that has updated info
		//identify users involved
		Transfer old = this.transactions.get(index);
		User tOPayee = this.transactions.get(index).payee;
		User tNPayee = tNew.payee;
		
		if(tOPayee.name.equals(tNPayee.name)){ //same user receives the updated transfer
			this.addPayment(tNew);
			tOPayee.cancelPayment(tOPayee.indexOf(old));
		}
		else{ //different user receives the updated transfer
			tOPayee.cancelPayment(tOPayee.indexOf(old));
			this.addPayment(tNew);
		}
	}

	private int indexOf(Transfer old){
		int loc=-1;
		int numTrans = this.transactions.size();
		for (int i = 0; i<numTrans; i++){
			Transfer test = this.transactions.get(i);
			if(test.equals(old)){
				loc = i;
				break;
			}
		}
		return loc;
	}
	
	private boolean equals(User u){
		if (this.name.equals(u.name)){
			return true;
		}
		else{
			return false;
		}
	}
}
