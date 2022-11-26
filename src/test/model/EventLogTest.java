package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Represents Unit Tests for the EventLog class
public class EventLogTest {
    private Event e1;
    private Event e2;
    private Event e3;
    private Event e4;
    private EventLog el;

    @BeforeEach
    public void runBefore() {
        e1 = new Event("Account Added: Account Id: 1, Account Name: Radhika Bajaj, Account Balance: 1000.0");
        e2 = new Event("Account Added: Account Id: 2, Account Name: John Smith, Account Balance: 100.0");
        e3 = new Event("Account Added: Account Id: 3, Account Name: Emily Wood, Account Balance: 3.0");
        e4 = new Event("Account Removed: Account Id: 2, Account Name: John Smith, Account Balance: 100.0");

        el = EventLog.getInstance();
        el.clear();

        el.logEvent(e1);
        el.logEvent(e2);
        el.logEvent(e3);
        el.logEvent(e4);
    }

    @Test
    public void testLogEvent() {
        List<Event> l = new ArrayList<>();

        for (Event next : el) {
            l.add(next);
        }

        assertTrue(l.contains(e1));
        assertTrue(l.contains(e2));
        assertTrue(l.contains(e3));
        assertTrue(l.contains(e4));

        assertEquals(5, l.size());
        assertEquals("Event log cleared.",
                l.get(0).getDescription());
        assertEquals("Account Added: Account Id: 1, Account Name: Radhika Bajaj, Account Balance: 1000.0",
                l.get(1).getDescription());
        assertEquals("Account Added: Account Id: 2, Account Name: John Smith, Account Balance: 100.0",
                l.get(2).getDescription());
        assertEquals("Account Added: Account Id: 3, Account Name: Emily Wood, Account Balance: 3.0",
                l.get(3).getDescription());
        assertEquals("Account Removed: Account Id: 2, Account Name: John Smith, Account Balance: 100.0",
                l.get(4).getDescription());
    }

}
