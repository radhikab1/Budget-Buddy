package persistence;

import org.json.JSONObject;

// Represents a writable conversion of this as JSON object
public interface Writable {
    // EFFECTS: returns this as JSON object

    JSONObject toJson();
}
