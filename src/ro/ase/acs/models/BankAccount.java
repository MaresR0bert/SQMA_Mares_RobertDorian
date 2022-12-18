package ro.ase.acs.models;

import ro.ase.acs.exceptions.InsufficentFunds;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String owner;
    private String IBAN;
    private List<Float> transactions = new ArrayList<>();

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public List<Float> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Float> transactions) {
        this.transactions = transactions;
    }

    public float getBalance(){
        float sold = 0;
        for(float s : transactions){
            sold+=s;
        }
        return sold;
    }

    public void addTransaction(Float f) throws InsufficentFunds {
        if(-f > getBalance()){
            throw new InsufficentFunds();
        }
        if(f <= 2000f && f >= -2000f && f != 0){
            this.transactions.add((float) (Math.floor(f * 100) / 100));
        }
    }

    public void restoreTransactions(List<Float> transactions){
        this.transactions.clear();
        for(Float trans : transactions){
            this.transactions.add(trans);
        }
    }
}
