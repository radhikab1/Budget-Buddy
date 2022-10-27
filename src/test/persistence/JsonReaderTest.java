package persistence;

import model.Account;
import model.AccountsList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Represents tests for JsonReader
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
    try {
        AccountsList al = reader.read();
        fail("IOException expected");
    } catch (IOException e) {
        // pass
    }
    }

    @Test
    void testReaderEmptyAccountsList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccountsList.json");
        try {
            AccountsList al = reader.read();
            assertEquals("My accounts list", al.getName());
            assertEquals(0, al.numAccounts());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAccountsList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccountsList.json");
        try {
            AccountsList al = reader.read();
            assertEquals("My accounts list", al.getName());
            List<Account> accounts = al.getAccounts();
            assertEquals(2, accounts.size());
            checkAccount(1, "John Smith", 200.00, accounts.get(0));
            checkAccount(2, "Emily Wood", 0.00, accounts.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
