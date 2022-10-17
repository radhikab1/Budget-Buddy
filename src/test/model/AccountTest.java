package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Represents tests for Account
public class AccountTest {
    private Account testAccount;

    @BeforeEach
    public void runBefore() {
        testAccount = new Account("John Smith", 200.00);
    }

    @Test
    public void testConstructor() {
        assertTrue(testAccount.getAccountId() > 0);
        assertEquals("John Smith", testAccount.getAccountName());
        assertEquals(200.00, testAccount.getAccountBalance());
    }

    @Test
    public void testMakeCredit() {
        testAccount.makeCredit(10.00);
        assertEquals(210.00, testAccount.getAccountBalance());
    }

    @Test
    public void testMakeCreditMultipleTimes() {
        testAccount.makeCredit(1.70);
        assertEquals(201.70, testAccount.getAccountBalance());
        testAccount.makeCredit(20.00);
        assertEquals(221.70, testAccount.getAccountBalance());
    }

    @Test
    public void testMakeDebt() {
        testAccount.makeDebt(10.00);
        assertEquals(190.00, testAccount.getAccountBalance());
    }

    @Test
    public void testMakeDebtMultipleTimes() {
        testAccount.makeDebt(15.30);
        assertEquals(184.70, testAccount.getAccountBalance());
        testAccount.makeDebt(14.00);
        assertEquals(170.70, testAccount.getAccountBalance());
    }

    @Test
    public void testMakeDebtToGetZeroAccountBalance() {
        testAccount.makeDebt(170.00);
        assertEquals(30.00, testAccount.getAccountBalance());
        testAccount.makeDebt(30.00);
        assertEquals(0.0, testAccount.getAccountBalance());
    }
}