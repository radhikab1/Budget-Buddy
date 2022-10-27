package persistence;

import model.Account;
import model.AccountsList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads accounts list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads accounts list from file and returns it;
    //          throws IOException if an error occurs while reading data from file
    public AccountsList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccountsList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses accounts list from JSON object and returns it
    private AccountsList parseAccountsList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        AccountsList al = new AccountsList(name);
        addAccounts(al, jsonObject);
        return al;
    }

    // MODIFIES: al
    // EFFECTS: parses accounts from JSON object and adds them to accounts list
    private  void addAccounts(AccountsList al, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("accounts");
        for (Object json: jsonArray) {
            JSONObject nextAccount = (JSONObject) json;
            addAccount(al, nextAccount);
        }
    }

    // MODIFIES: al
    // EFFECTS: parses account from JSON object and adds it to accounts list
    private void addAccount(AccountsList al, JSONObject jsonObject) {
        Integer id = Integer.valueOf(jsonObject.getInt("id"));
        String name = jsonObject.getString("name");
        Double balance = Double.valueOf(jsonObject.getDouble("balance"));
        Account account = new Account(id, name, balance);
        al.addAccount(account);
    }
}
