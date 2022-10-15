package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    private Account account;

    @BeforeEach
    public void runBefore() {
        account = new Account("John Smith", 200.00);
    }

    @Test
    public void testConstructor() {
        assertTrue(account.getAccountId() > 0);
        assertEquals("John Smith", account.getAccountName());
        assertEquals(200.00, account.getAccountBalance());
    }

    @Test
    public void testMakeCredit() {
        account.makeCredit(10.00);
        assertEquals(210.00, account.getAccountBalance());
    }

    @Test
    public void testMakeCreditMultipleTimes() {
        account.makeCredit(1.70);
        assertEquals(201.70, account.getAccountBalance());
        account.makeCredit(20.00);
        assertEquals(221.70, account.getAccountBalance());
    }

    @Test
    public void testMakeDebt() {
        account.makeDebt(10.00);
        assertEquals(190.00, account.getAccountBalance());
    }

    @Test
    public void testMakeDebtMultipleTimes() {
        account.makeDebt(15.30);
        assertEquals(184.70, account.getAccountBalance());
        account.makeDebt(14.00);
        assertEquals(170.70, account.getAccountBalance());
    }

    @Test
    public void testMakeDebtToZeroAccountBalance() {
        account.makeDebt(170.00);
        assertEquals(30.00, account.getAccountBalance());
        account.makeDebt(30.00);
        assertEquals(0.0, account.getAccountBalance());
    }
}