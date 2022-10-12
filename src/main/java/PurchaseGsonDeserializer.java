import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PurchaseGsonDeserializer implements JsonDeserializer<Purchase> {
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    public Purchase deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String title = jsonObject.get("title").getAsString();
        int sum = jsonObject.get("sum").getAsInt();
        Date date;

        try {
            date = simpleDateFormat.parse(jsonObject.get("date").getAsString());
        } catch (ParseException e) {
            throw new JsonParseException(e.getMessage());
        }

        return new Purchase(title, date, sum);
    }
}
