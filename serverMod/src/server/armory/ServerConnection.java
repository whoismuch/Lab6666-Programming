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

    private Socket incoming;
    private static Driver driver;
    private static Navigator navigator;
    private static String path;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Gson routeGson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(ZonedDateTime.class, new GsonZonedDateTimeConverter()).create();

    public ServerConnection (Navigator navigator, Socket incoming, String path) {
        driver = new Driver( );
        this.navigator = navigator;
        this.incoming = incoming;
        this.path = path;
    }


    public void serverWork () throws  IOException {
        try (DataOutputStream sendToClient = new DataOutputStream(incoming.getOutputStream( ));
             DataInputStream getFromClient = new DataInputStream(incoming.getInputStream( ))) {


            DataExchangeWithClient dataExchangeWithClient = new DataExchangeWithClient(getFromClient, sendToClient);
            dataExchangeWithClient.sendToClient(gson.toJson(driver.getAvailable( )));
            driver.load(dataExchangeWithClient, navigator, path);
            while (true) {
                boolean flag = false;
                CommandDescription command = routeGson.fromJson(dataExchangeWithClient.getFromClient( ), CommandDescription.class);
                if (command.getName( ).equals("exit")) flag = true;
                Driver.getLive( ).execute(dataExchangeWithClient, navigator, command.getName( ), command.getArg( ), command.getRoute( ));
                System.out.println(2);
                if (flag) break;
                System.out.println(5);
            }

        }
    }


    public static void theEnd() {
        driver.save(null, navigator, path );
    }
}
