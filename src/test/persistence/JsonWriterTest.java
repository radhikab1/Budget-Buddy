package persistence;

import model.Account;
import model.AccountsList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            AccountsList al = new AccountsList("My accounts list");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccountsList() {
        try {
            AccountsList al = new AccountsList("My accounts list");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccountsList.json");
            writer.open();
            writer.write(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccountsList.json");
            al = reader.read();
            assertEquals("My accounts list", al.getName());
            assertEquals(0, al.numAccounts());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccountsList() {
        try {
            AccountsList al = new AccountsList("My accounts list");
            al.addAccount(new Account(1, "John Smith", 200.00));
            al.addAccount(new Account(2, "Emily Wood", 0.00));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccountsList.json");
            writer.open();
            writer.write(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccountsList.json");
            al = reader.read();
            assertEquals("My accounts list", al.getName());
            List<Account> accounts = al.getAccounts();
            checkAccount(1, "John Smith", 200.00, accounts.get(0));
            checkAccount(2, "Emily Wood", 0.00, accounts.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
