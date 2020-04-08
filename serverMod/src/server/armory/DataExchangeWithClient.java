package server.armory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;

public class DataExchangeWithClient {

    private DataInputStream getFromClient;
    private DataOutputStream sendToClient;
    private byte[] local;
    private Charset charset;


    public DataExchangeWithClient (DataInputStream getFromClient, DataOutputStream sendToClient) {
        this.getFromClient = getFromClient;
        this.sendToClient = sendToClient;
        charset = Charset.forName("UTF-8");
    }




    public void sendToClient (String message) {
        try {
            local = message.getBytes(charset);
            System.out.println(message );
            sendToClient.write(local);
        } catch (IOException e) {
            e.printStackTrace( );
        }
    }

    public String getFromClient (){
        try {
            local = new byte[5000];
            getFromClient.read(local);
            String message = new String(local, charset);
            return message.trim();
        } catch (IOException e) {
            e.printStackTrace( );
            return  "Ой-Ой-Ой";
        }
    }



}
