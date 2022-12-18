package ro.ase.acs.tests;

import org.junit.Before;
import org.junit.Test;
import ro.ase.acs.exceptions.InsufficentFunds;
import ro.ase.acs.models.BankAccount;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BankAccountTest {

    private BankAccount bankAccount;

    @Before
    public void setUp() {
        bankAccount = new BankAccount();
    }

    @Test
    public void testConformance() throws InsufficentFunds {
        BankAccount bankAccount = new BankAccount();
        bankAccount.addTransaction(300.331f);
        bankAccount.addTransaction(100.667f);
        bankAccount.addTransaction(0.01f);

        float result = bankAccount.getBalance();
        assertEquals("The balance is not right", 401f, result, 0.001f);
    }

    @Test
    public void testOrdering1() throws InsufficentFunds {
        bankAccount.addTransaction(200f);
        bankAccount.addTransaction(200f);
        bankAccount.addTransaction(-100f);

        float result = bankAccount.getBalance();
        assertEquals("The balance is not right", 300, result, 0.001f);
    }

    @Test
    public void testOrdering2() {
        try {
            bankAccount.addTransaction(-100f);
        } catch (InsufficentFunds insufficentFunds) {

        }
        try {
            bankAccount.addTransaction(200f);
        } catch (InsufficentFunds insufficentFunds) {

        }
        try {
            bankAccount.addTransaction(200f);
        } catch (InsufficentFunds insufficentFunds) {

        }

        float result = bankAccount.getBalance();
        assertEquals("The balance is not right", 400, result, 0.001f);
    }

    @Test
    public void testRange() throws InsufficentFunds {
        bankAccount.addTransaction(1900f);
        bankAccount.addTransaction(-1800f);
        bankAccount.addTransaction(3000f);
        bankAccount.addTransaction(2000f);
        bankAccount.addTransaction(-2050f);
        bankAccount.addTransaction(0f);

        List<Float> transactions = bankAccount.getTransactions();
        assertTrue("Invalid transaction accepted", transactions.contains(1900f));
        assertTrue("Invalid transaction accepted", transactions.contains(-1800f));
        assertTrue("Invalid transaction accepted", transactions.contains(2000f));
        assertFalse("Invalid transaction accepted", transactions.contains(3000f));
        assertFalse("Invalid transaction accepted",transactions.contains(-2050f));
        assertFalse("Invalid transaction accepted",transactions.contains(0f));
    }

    @Test
    public void testReference(){
        List<Float> transactions = new ArrayList<>();
        transactions.add(300f);
        transactions.add(-150f);
        transactions.add(400f);


        bankAccount.restoreTransactions(transactions);
        transactions.add(200f);
        float result = bankAccount.getBalance();
        assertEquals("Invalid transaction", 550f,result,0.001f);
    }

    @Test
    public void testCardinality1() {
        float result = bankAccount.getBalance();
        assertEquals("Balance is not correct", 0, result, 0.001f);
    }

    @Test
    public void testCardinality2() throws InsufficentFunds {
        bankAccount.addTransaction(100f);
        float result = bankAccount.getBalance();
        assertEquals("Balance is not correct", 100, result, 0.001f);
    }

    @Test
    public void testCardinality3() throws InsufficentFunds {
        for(int i=0; i < 500; i++) {
            bankAccount.addTransaction(100f);
        }
        for(int i = 0 ; i < 500; i++) {
            bankAccount.addTransaction(-100f);
        }
        float result = bankAccount.getBalance();
        assertEquals("Balance is not correct", 0 , result, 0.001f);
    }

    @Test(timeout = 1000)
    public void testTimeFrame() throws InsufficentFunds {
        for(int i =0 ; i < 1000; i++) {
            bankAccount.addTransaction(100f);
        }
        bankAccount.getBalance();
    }

    @Test
    public void testRight() throws InsufficentFunds {
        bankAccount.addTransaction(100.55f);
        bankAccount.addTransaction(50.33f);
        bankAccount.addTransaction(-79.82f);
        bankAccount.addTransaction(-45.43f);

        float result = bankAccount.getBalance();
        assertEquals("The balance is incorrect", 25.63,result,0.001f);
    }

    @Test
    public void testBoundary1() throws InsufficentFunds {
        bankAccount.addTransaction(2000f);
        bankAccount.addTransaction(-2000f);

        float result = bankAccount.getBalance();
        assertEquals("Incorrect bounds", 0, result, 0.001f );
    }

    @Test
    public void testBoundary2() throws InsufficentFunds {
        bankAccount.addTransaction(2000f);
        bankAccount.addTransaction(2000f);
        bankAccount.addTransaction(-2001f);
        bankAccount.addTransaction(2001f);


        float result = bankAccount.getBalance();
        assertEquals("Incorrect bounds", 4000, result, 0.001f);
    }

    @Test
    public void testInverse() throws InsufficentFunds{
        bankAccount.addTransaction(100.55f);
        bankAccount.addTransaction(50.33f);
        bankAccount.addTransaction(-79.82f);
        bankAccount.addTransaction(-45.43f);

        float result = bankAccount.getBalance();
        List<Float> transactions = bankAccount.getTransactions();

        for(int i = transactions.size()-1; i >= 0; i--) {
            result -= transactions.get(i);
        }

        assertEquals("Incorrect", 0, result, 0.001f);

    }

    @Test
    public void testCrossCheck() throws InsufficentFunds {
        bankAccount.addTransaction(100.55f);
        bankAccount.addTransaction(50.33f);
        bankAccount.addTransaction(-79.82f);
        bankAccount.addTransaction(-45.43f);

        float result = bankAccount.getBalance();
        List<Float> transactions = bankAccount.getTransactions();

        float expected = (float) transactions.stream().mapToDouble(x->(double)x).sum();

        assertEquals("Balance is incorrect", expected, result, 0.001f);
    }

    @Test(expected = InsufficentFunds.class)
    public void testErrorCondition() throws InsufficentFunds {
        bankAccount.addTransaction(300f);
        bankAccount.addTransaction(-350f);

    }

    @Test(timeout = 1)
    public void testPerformance() throws InsufficentFunds {
        bankAccount.addTransaction(150f);


    }


}