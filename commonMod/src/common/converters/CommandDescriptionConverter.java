package common.converters;
import common.command.CommandDescription;
import com.google.gson.*;

import java.lang.reflect.Type;

public class CommandDescriptionConverter implements JsonSerializer<CommandDescription> , JsonDeserializer<CommandDescription> {

    @Override
    public CommandDescription deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new CommandDescription(jsonElement.getAsJsonObject().get("name").getAsString(), jsonElement.getAsJsonObject().get("arg").getAsString());
    }

    @Override
    public JsonElement serialize(CommandDescription comandDescription, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty("name",comandDescription.getName());
        result.addProperty("arg",comandDescription.getArg());
        return result;
    }
}
