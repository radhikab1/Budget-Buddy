package persistence;

import model.Account;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkAccount(Integer id, String name, Double balance, Account account) {
        assertEquals(id, account.getAccountId());
        assertEquals(name, account.getAccountName());
        assertEquals(balance, account.getAccountBalance());
    }
}
