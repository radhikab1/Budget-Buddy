package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Represents Unit Tests for the EventLog class
public class EventLogTest {
    private AccountsList accounts;
    private Account account1;
    private Account account2;
    private Account account3;
    private Event e1;
    private Event e2;
    private Event e3;
    private Event e4;
    private EventLog el;

    @BeforeEach
    public void runBefore() {
        accounts = new AccountsList("Radhika's AccountsList");
        account1 = new Account(1, "Radhika Bajaj", 1000.0);
        account2 = new Account(2, "John Smith", 100.0);
        account3 = new Account(3, "Emily Wood", 3.0);
        e1 = new Event("Added Account: Account Id: 4, Account Name: Jack, Account Balance: 1.0");
        e2 = new Event("Added Account: Account Id: 5, Account Name: Jill, Account Balance: 0.0");
        e3 = new Event("Added Account: Account Id: 6, Account Name: Emma, Account Balance: 80.0");
        e4 = new Event("Removed Account: Account Id: 5, Account Name: Jill, Account Balance: 0.0");

        el = EventLog.getInstance();
        el.clear();

        el.logEvent(e1);
        el.logEvent(e2);
        el.logEvent(e3);
        el.logEvent(e4);
    }

    @Test
    public void testLogEvent() {
        accounts.addAccount(account1);
        accounts.addAccount(account2);
        accounts.addAccount(account3);
        accounts.removeAccount(account2);
        List<Event> l = new ArrayList<>();

        for (Event next : el) {
            l.add(next);
        }

        assertTrue(l.contains(e1));
        assertTrue(l.contains(e2));
        assertTrue(l.contains(e3));
        assertTrue(l.contains(e4));

        assertEquals(9, l.size());
        assertEquals("Event log cleared.",
                l.get(0).getDescription());
        assertEquals("Added Account: Account Id: 4, Account Name: Jack, Account Balance: 1.0",
                l.get(1).getDescription());
        assertEquals("Added Account: Account Id: 5, Account Name: Jill, Account Balance: 0.0",
                l.get(2).getDescription());
        assertEquals("Added Account: Account Id: 6, Account Name: Emma, Account Balance: 80.0",
                l.get(3).getDescription());
        assertEquals("Removed Account: Account Id: 5, Account Name: Jill, Account Balance: 0.0",
                l.get(4).getDescription());
        assertEquals("Added Account: Account Id: 1, Account Name: Radhika Bajaj, Account Balance: 1000.0",
                l.get(5).getDescription());
        assertEquals("Added Account: Account Id: 2, Account Name: John Smith, Account Balance: 100.0",
                l.get(6).getDescription());
        assertEquals("Added Account: Account Id: 3, Account Name: Emily Wood, Account Balance: 3.0",
                l.get(7).getDescription());
        assertEquals("Removed Account: Account Id: 2, Account Name: John Smith, Account Balance: 100.0",
                l.get(8).getDescription());
    }

    @Test
    public void testClear() {
        el.clear();
        Iterator<Event> itr = el.iterator();
        assertTrue(itr.hasNext());
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

}
