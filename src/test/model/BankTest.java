package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BankTest {
    private Bank bank;
    private Account account1;
    private Account account2;

    @BeforeEach
    public void runBefore() {
        bank = new Bank();
        account1 = new Account("John Smith", 200.00);
        account2 = new Account("Emily Wood", 0.00);
    }

    @Test
    public void testConstructor() {
        assertNotNull(bank.getAccounts());
        assertEquals(0, bank.getAccounts().size());
    }

    @Test
    public void testAddAccountOnce() {
        bank.addAccount(account1);
        assertEquals(1, bank.getAccounts().size());
        assertEquals(account1, bank.getAccounts().get(0));
    }

    @Test
    public void testAddAccountMultipleTimes() {
        bank.addAccount(account1);
        bank.addAccount(account2);
        assertEquals(2, bank.getAccounts().size());
        assertEquals(account1, bank.getAccounts().get(0));
        assertEquals(account2, bank.getAccounts().get(1));
    }

    @Test
    public void testRemoveAccountOnce() {
        bank.getAccounts().add(account1);
        bank.getAccounts().add(account2);
        bank.removeAccount(account2);
        assertEquals(1, bank.getAccounts().size());
        assertEquals(account1, bank.getAccounts().get(0));
    }

    @Test
    public void testRemoveAccountMultipleTimes(){
        bank.getAccounts().add(account1);
        bank.getAccounts().add(account2);
        bank.removeAccount(account2);
        assertEquals(1, bank.getAccounts().size());
        assertEquals(account1, bank.getAccounts().get(0));
        bank.removeAccount(account1);
        assertEquals(0, bank.getAccounts().size());
    }
}

