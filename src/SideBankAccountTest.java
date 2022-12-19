import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SideBankAccountTest {

    private BankAccount bankAccount;

    @Before
    public void setUp() {
        bankAccount = new BankAccount();
    }

    @Test
    public void testCardinality() {
        float result = bankAccount.getBalance();
        assertEquals("Balance is not correct", 0, result, 0.001f);
    }

    @Test(timeout = 1000)
    public void testTimeFrame() throws Exception {
        for(int i =0 ; i < 1000; i++) {
            bankAccount.addTransaction(100f);
        }
        bankAccount.getBalance();
    }
}