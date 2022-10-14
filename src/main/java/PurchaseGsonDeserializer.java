import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class PurchaseGsonDeserializer implements JsonDeserializer<Purchase> {
    @Override
    public Purchase deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String title = jsonObject.get("title").getAsString();
        int sum = jsonObject.get("sum").getAsInt();
        LocalDate date = null;

        try {
            date = LocalDate.parse(jsonObject.get("date").getAsString().replace(".", "-"));
        } catch (DateTimeParseException e) {
            throw new JsonParseException(e.getMessage());
        }

        return new Purchase(title, date, sum);
    }
}
