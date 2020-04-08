package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.reflect.TypeToken;
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
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
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
    private Selector selector;
    private Selector sel;
    private String commandname;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Gson routeGson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(ZonedDateTime.class, new GsonZonedDateTimeConverter()).create();


    /**
     * Устанавливает активное соединение с сервером.
     */
    public void clientWork ( ) {
        try (Scanner scanner = new Scanner(System.in)) {
            outcoming = new InetSocketAddress("localhost", 8443);
            while (true) {
                try (SocketChannel outcomingchanell = SocketChannel.open(outcoming)) {
                    dataExchangeWithServer = new DataExchangeWithServer(outcomingchanell);
                    selector = Selector.open( );
                    sel = Selector.open( );
                    outcomingchanell.configureBlocking(false);
                    outcomingchanell.register(selector, SelectionKey.OP_READ);
                    outcomingchanell.register(sel, SelectionKey.OP_WRITE);
                    Type mapType = new TypeToken<HashMap<String, String>>(){}.getType();
                    selector.select( );
                    userManager = new UserManager(scanner, new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8)), true, gson.fromJson(dataExchangeWithServer.getFromServer( ), mapType));
                    clientLaunch( );
                    userManager.write("Завершение программы.");
                    System.exit(0);
                } catch (IOException e) {
                    if (!commandname.equals("exit")) {
                        userManager.writeln("Нет связи с сервером. Подключиться ещё раз (введите {да} или {нет})?");
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
                    } else userManager.writeln("Завершение работы");
                }
            }
        }
    }


        public void clientLaunch () throws IOException {
//            selector.select( );
//            userManager.writeln(dataExchangeWithServer.getFromServer( ));

            String line = "check";
            while (!line.equals("exit")) {
                userManager.write("Введите команду: ");
                line = userManager.read( );
                line = line.trim( );
                commandname = line;
                String arg = null;
                if (line.indexOf(" ") != -1) {
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
                } else {
                    command = new CommandDescription(commandname, arg, null);
                }

                sel.select( );
                dataExchangeWithServer.sendToServer(routeGson.toJson(command));

                selector.select( );
                userManager.writeln(dataExchangeWithServer.getFromServer( ));

            }
        }
    }