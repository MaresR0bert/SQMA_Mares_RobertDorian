package bank;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String owner;
    private String IBAN;
    private List<Float> transactions = new ArrayList<>();

    public List<Float> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Float> transactions) {
        this.transactions = transactions;
    }

    public float getBalance(){
        float balance = 0;
        for(float transaction : transactions){
            balance+=transaction;
        }
        return balance;
    }

    public void addTransaction(Float amount) throws Exception {
        if(-amount > getBalance()){
            throw new Exception("Insufficent funds");
        }
        if(amount <= 2000f && amount >= -2000f && amount != 0){
            this.transactions.add((float) (Math.floor(amount * 100) / 100));
        }
    }
}
