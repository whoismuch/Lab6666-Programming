package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import common.command.CommandDescription;
import common.converters.GsonZonedDateTimeConverter;
import common.generatedClasses.Route;

import java.io.*;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class ClientProviding {

    private DataExchangeWithServer dataExchangeWithServer;
    private SocketAddress outcoming;
    private UserManager userManager;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Gson routeGson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(ZonedDateTime.class, new GsonZonedDateTimeConverter()).create();


    /**
     * Устанавливает активное соединение с сервером.
     */
    public void clientWork ( ) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            outcoming = new InetSocketAddress("localhost", 8800 );
            while (true) {
                try (SocketChannel outcomingchanell = SocketChannel.open(outcoming)) {
                    dataExchangeWithServer = new DataExchangeWithServer(outcomingchanell);
                    userManager = new UserManager(scanner, new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8)), true, gson.fromJson(dataExchangeWithServer.getFromServer(), HashMap.class));
                    clientLaunch( );
                    userManager.write("Завершение программы.");
                } catch (IOException e) {
                    userManager.writeln("Нет связи с сервером. Подключться ещё раз (введите {да} или {нет})?");
                    String answer;
                    while (!(answer = userManager.read( )).equals("да")) {
                        switch (answer) {
                            case "":
                                break;
                            case "нет":
                                System.exit(0);
                                break;
                            default:
                                userManager.write("Введите корректный ответ.");
                        }
                    }
                    userManager.writeln("Подключение ...");
                    continue;
                }
            }
        }
    }


    public void clientLaunch ( ) throws IOException {
        userManager.writeln(dataExchangeWithServer.getFromServer());
        userManager.writeln(dataExchangeWithServer.getFromServer());

        String line = "check";
        while (!line.equals("exit")) {
            userManager.write("Введите команду: ");
            line = userManager.read();
            line = line.trim( );
            String commandname = line;
            String arg = null;
            if (line.indexOf(" ") !=-1) {
                    commandname = line.substring(0, line.indexOf(" "));
                    arg = (line.substring(line.indexOf(" "))).trim( );
            }

            if (!userManager.checkCommandName(commandname)) {
                continue;
            }
            if (!userManager.checkArg(commandname, arg)) {
                continue;
            }

            CommandDescription command;
            if (userManager.checkElement(commandname)) {
                Route route = userManager.readRoute( );
                command = new CommandDescription(commandname, arg, route);
            }
            else {command = new CommandDescription(commandname, arg, null);}

            dataExchangeWithServer.sendToServer(routeGson.toJson(command));

            userManager.writeln(dataExchangeWithServer.getFromServer());

        }
    }
}