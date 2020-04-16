package server.armory;

import common.command.CommandDescription;
import server.receiver.collection.Navigator;

import java.net.Socket;

public class ServerConnection {

    private Socket incoming;

    private static Driver driver;
    private static Navigator navigator;
    private static String path;

    public ServerConnection (Navigator navigator,Socket incoming, String path) {
        driver = new Driver( );
        this.navigator = navigator;
        this.path = path;
        this.incoming = incoming;

    }


    public void serverWork ()  {

        GetFromClient getFromClient = new GetFromClient(incoming);
        SendToClient sendToClient = new SendToClient(incoming);

        if (getFromClient.get().equals("I'm ready to get available commands and base collection ")) {
            sendToClient.send(driver.getAvailable( ));
            driver.load(sendToClient, navigator, path);
        }

        CommandDescription command = (CommandDescription) getFromClient.get();
        driver.execute(sendToClient, navigator, command.getName( ), command.getArg( ), command.getRoute( ), driver);
    }


    public static void theEnd() {
        driver.save(null, navigator, path );
    }
}
