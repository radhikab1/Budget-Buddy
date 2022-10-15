package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BankTest {
    private List<Account> accounts;
    private Account account1;
    private Account account2;

    @BeforeEach
    public void runBefore() {
        accounts = new ArrayList<>();
        account1 = new Account("John Smith", 200.00);
        account2 = new Account("Emily Wood", 0.00);
    }

    @Test
    public void testConstructor() {
        assertNotNull(accounts.getAccounts());
        assertEquals(0, accounts.getAccounts().size());
    }

    @Test
    public void testAddAccountOnce() {
        accounts.addAccount(account1);
        assertEquals(1, accounts.size());
        assertEquals(account1, accounts.get(1));
    }

    @Test
    public void testAddAccountMultipleTimes() {
        accounts.addAccount(account1);
        accounts.addAccount(account2);
        assertEquals(2, accounts.size());
        assertEquals(account1, accounts.get(1));
        assertEquals(account2, accounts.get(2));
    }

    @Test
    public void testRemoveAccountOnce() {
        accounts.add(account1);
        accounts.add(account2);
        accounts.removeAccount(account2);
        assertEquals(1, accounts.size());
        assertEquals(account1, accounts.get(1));
    }

    @Test
    public void testRemoveAccountMultipleTimes(){
        accounts.add(account1);
        accounts.add(account2);
        accounts.removeAccount(account2);
        assertEquals(1, accounts.size());
        assertEquals(account1, accounts.get(1));
        accounts.removeAccount(account1);
        assertEquals(0, accounts.size());
    }
}

