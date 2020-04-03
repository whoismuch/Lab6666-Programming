package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import common.command.CommandDescription;
import common.converters.CommandDescriptionConverter;
import common.converters.GsonZonedDateTimeConverter;
import common.generatedClasses.Route;

import java.io.*;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.ZonedDateTime;
import java.util.Scanner;


public class ClientProviding {

    private static Scanner fromKeyboard;
    private static ObjectOutputStream toServer;
    private static ObjectInputStream fromServer;
   // private SocketAddress outcoming;
    private SocketChannel outcomingchanell;
    private ByteBuffer f;
    private UserManager userManager;

    /**
     * Устанавливает активное соединение с сервером.
     */
    public void clientWork (UserManager userManager) throws IOException {
        this.userManager = userManager;
        try (Scanner scanner = new Scanner(System.in)) {
            userManager.setScanner(scanner);
            while (true) {
                try (Socket outcoming = new Socket(InetAddress.getLocalHost( ), 8800)) {
                    try (ObjectOutputStream outputStream = new ObjectOutputStream(outcoming.getOutputStream( ));
                         ObjectInputStream inputStream = new ObjectInputStream(outcoming.getInputStream( ))) {
                        toServer = outputStream;
                        fromServer = inputStream;
                        clientLaunch();
                        userManager.write("Завершение программы.");
                    }
                } catch (IOException e) {
                    userManager.write("Нет связи с сервером. Подключться ещё раз (введите {да} или {нет})?");
                    String answer;
                    while (!(answer = userManager.read()).equals("да")) {
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
                    userManager.write("Подключение ...");
                    continue;
                }
            }
        }
    }


    public void clientLaunch () throws IOException {
        try {
            userManager.write((String) fromServer.readObject( ));
            String line;
            while (!(line = userManager.read()).equals("exit")) {
                line = line.trim();
                String commandname = line;
                String arg = null;
                if (line.indexOf(" ")!=1) {
                    commandname = line.substring(0, line.indexOf(" "));
                    arg = (line.substring(line.indexOf(" "))).trim();
                }
                CommandDescription command = new CommandDescription(commandname, arg );

                Gson commandGson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(CommandDescription.class, new CommandDescriptionConverter()).create();
                //System.out.println(gson.fromJson(gson.toJson(command),CommandDescription.class));

                toServer.writeObject(commandGson.toJson(command));
                String flag = (String) fromServer.readObject();
                System.out.println(flag);
                if (flag.equals("Вы можете начать ввод объекта")){
                    Route route = userManager.readRoute();
                    Gson routeGson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new GsonZonedDateTimeConverter()).setPrettyPrinting().create();
                    toServer.writeObject(routeGson.toJson(route));
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Класс не найден: " + e.getMessage( ));
        }
    }



}