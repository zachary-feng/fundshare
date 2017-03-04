package spreadsheet;
import java.util.*;

public class User{
	//Properties
	public String name;
	public double balance; //owed-debt
	public boolean isWinner;
	public ArrayList<Transfer> transactions;
	public int[] date; //date since last transaction recorded
	
	public User(String name){
		this.name = name;
		this.balance = 0;
		this.isWinner = false;
		this.transactions = null;
		this.date = new int[3];
	}
	
	public void addPayment(Transfer t){ //"this" is paying "t" to "payee"
		//add transfer to transactions history
		this.transactions.add(t);
		t.payee.transactions.add(t);
		
		this.balance = this.balance - t.amount; //decrease this' balance
		t.payee.balance = t.payee.balance + t.amount; //increase payee's balance
		
		//order transactions by date
		this.sortTransactions(this.transactions);
		t.payee.sortTransactions(t.payee.transactions);
		
		//returns latest date
		this.date = this.transactions.get(transactions.size()-1).date;
		t.payee.date = t.payee.transactions.get(transactions.size()-1).date;
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
		
		//both reorder transactions by date
		tPayee.sortTransactions(tPayee.transactions);
		tPayer.sortTransactions(tPayer.transactions);
		
		//both adjust date of last transaction
		tPayee.date = tPayee.transactions.get(transactions.size()-1).date;
		tPayer.date = tPayer.transactions.get(transactions.size()-1).date;
		}
	
	public void editPayment(int index, Transfer tNew){ //"edits" by replacing transfer at "index" with tNew that has updated info
		//identify users involved
		Transfer old = this.transactions.get(index);
		User tOPayee = this.transactions.get(index).payee;
		User tNPayee = tNew.payee;
		
		tOPayee.cancelPayment(tOPayee.transactions.indexOf(old)); //cancel transfer to old payee
		//replacing with updated transfer tNew
		if(tOPayee.name.equals(tNPayee.name)){ //same user receives the updated transfer
			tOPayee.addPayment(tNew);
			tOPayee.sortTransactions(tOPayee.transactions); 
			tOPayee.date = tOPayee.transactions.get(transactions.size()-1).date;
		}
		else{ //different user receives the updated transfer
			tNPayee.addPayment(tNew);
			tNPayee.sortTransactions(tNPayee.transactions); //reordering transactions
			tNPayee.date = tNPayee.transactions.get(transactions.size()-1).date;
		}
		this.cancelPayment(index); //cancel old transfer to "this"
		this.addPayment(tNew); //add new transfer to "this"
		this.sortTransactions(this.transactions); 
		this.date = this.transactions.get(transactions.size()-1).date;
	}
	
	
	//SORTING TRANSACTIONS
	private void sortTransactions(ArrayList<Transfer> transList){
		int numTrans = transList.size();
		//Sorting by year
		quickSort(transList, 0, numTrans-1, 0);
		//Sorting by month for same year
		for (int i=0; i<numTrans; i++){
			int counter=0;
			for (int j = 1; counter<numTrans-j; j++){
				if (!(transList.get(i).date[1]==transList.get(i+j).date[1])){
					break;
				}
				counter++;
			}
			if (counter>0){
				quickSort(transList, i, i+counter, 1);
				i = i+counter;
			}
		}
		//Sorting by day for same month
		for (int i=0; i<numTrans; i++){
			int counter=0;
			for (int j = 1; counter<numTrans-j; j++){
				if (!(transList.get(i).date[2]==transList.get(i+j).date[2])){
					break;
				}
				counter++;
			}
			if (counter>0){
				quickSort(transList, i, i+counter, 2);
				i = i+counter;
			}
		}
	}
	
	private void quickSort(ArrayList<Transfer> transList, int start, int stop, int digit){
		if (start<stop){
			int pivot = partition(transList, start, stop, digit);
			quickSort(transList, start, pivot-1, digit);
			quickSort(transList, pivot+1, stop, digit);
		}
	}
	
	private int partition(ArrayList<Transfer> transList, int start, int stop, int digit){
		int pivot = transList.get(stop).date[digit];
		int left = start;
		int right = stop-1;
		while (left<=right){
			while (left<=right && transList.get(left).date[digit]<pivot){
				left = left+1;
			}
			while (left<=right && transList.get(right).date[digit]>=pivot){
				right = right+1;
			}
			if (left<=right){
				Transfer tempA = transList.get(right);
				transList.set(right,transList.get(left));
				transList.set(left,tempA);
			}
		}
		Transfer tempB = transList.get(stop);
		transList.set(stop,transList.get(right));
		transList.set(right,tempB);
		return left;
	}
	
	
	
}
