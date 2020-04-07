package server.armory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.command.CommandDescription;
import common.converters.GsonZonedDateTimeConverter;
import server.receiver.collection.Navigator;

import java.io.*;
import java.net.Socket;
import java.time.ZonedDateTime;

public class ServerConnection<T> {

    private Navigator navigator;
    private Socket incoming;
    private Driver driver;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Gson routeGson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(ZonedDateTime.class, new GsonZonedDateTimeConverter()).create();

    public ServerConnection (Navigator navigator, Socket incoming) {
        driver = new Driver( );
        this.navigator = navigator;
        this.incoming = incoming;
    }


    public void serverWork ( ) throws  IOException {
        try (DataOutputStream sendToClient = new DataOutputStream(incoming.getOutputStream( ));
             DataInputStream getFromClient = new DataInputStream(incoming.getInputStream( ))) {

            DataExchangeWithClient dataExchangeWithClient = new DataExchangeWithClient(getFromClient,sendToClient);
            Driver driver = new Driver();
            dataExchangeWithClient.sendToClient(gson.toJson(driver.getAvailable()));
            driver.load(dataExchangeWithClient, navigator, "serverMod/routes.json");
            dataExchangeWithClient.sendToClient("Соединение установлено.\nВы можете начать ввод команд");
            do {
                CommandDescription command = routeGson.fromJson(dataExchangeWithClient.getFromClient( ), CommandDescription.class);
                Driver.getLive( ).execute(dataExchangeWithClient, navigator, command.getName( ), command.getArg( ), command.getRoute( ));
            } while (!incoming.isClosed());
            System.out.println("пока-пока");

        }

    }

}
