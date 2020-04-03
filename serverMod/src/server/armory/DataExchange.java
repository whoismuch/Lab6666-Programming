package server.armory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class DataExchange {

    private Socket incoming;
    public DataExchange (Socket incoming) {
        this.incoming = incoming;
    }


    public void send(String message) {
        try(ObjectOutputStream sendToClient = new ObjectOutputStream(incoming.getOutputStream())) {
            sendToClient.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace( );
        }
    }

    public Object get(){
        try(ObjectInputStream getFromClient = new ObjectInputStream(incoming.getInputStream())) {
            return getFromClient.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace( );
            return  "Ой-Ой-Ой";
        }
    }

}
