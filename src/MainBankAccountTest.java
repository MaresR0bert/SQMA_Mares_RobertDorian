import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainBankAccountTest {

    private BankAccount bankAccount;

    @Before
    public void setUp() {
        bankAccount = new BankAccount();
    }

    @Test
    public void testConformance() throws Exception {
        BankAccount bankAccount = new BankAccount();
        bankAccount.addTransaction(300.331f);
        bankAccount.addTransaction(100.667f);
        bankAccount.addTransaction(0.01f);

        float result = bankAccount.getBalance();
        assertEquals("Wrong balance", 401f, result, 0.001f);
    }

    @Test
    public void testOrdering() throws Exception {
        bankAccount.addTransaction(500f);
        bankAccount.addTransaction(-100f);

        float result = bankAccount.getBalance();
        assertEquals("Wrong balance", 400, result, 0.001f);
    }
}