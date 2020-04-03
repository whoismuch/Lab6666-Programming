package server.armory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import common.command.CommandDescription;
import server.commands.Command;
import common.converters.CommandDescriptionConverter;
import server.receiver.collection.Navigator;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class ServerConnection {

    private Navigator navigator;
    private Socket incoming;
    private HashMap<String, Command> commands;
    private Driver driver;

    public ServerConnection (Navigator navigator, Socket incoming) {
        driver = new Driver( );
        this.navigator = navigator;
        this.incoming = incoming;
    }

    public void serverWork ( ) {
        DataExchange dataExchange = new DataExchange(incoming);
        dataExchange.send("Соединение установлено.\n Вы можете начать ввод команд");
        Gson gson = new GsonBuilder( ).setPrettyPrinting( ).registerTypeAdapter(CommandDescription.class, new CommandDescriptionConverter( )).create( );
        CommandDescription command = gson.fromJson((JsonElement) dataExchange.get( ), CommandDescription.class);
        Driver.getLive( ).execute(dataExchange, navigator, command.getName( ), command.getArg( ));
    }

}
