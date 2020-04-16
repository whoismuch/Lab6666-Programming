package client;

import common.command.CommandDescription;
import common.generatedClasses.Route;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;


public class ClientProviding {

    private DataExchangeWithServer dataExchangeWithServer;
    private UserManager userManager;
    private Selector selector;
    private String commandname;
    /**
     * Устанавливает активное соединение с сервером.
     */
    public void clientWork (boolean iBegin) {
        try (Scanner scanner = new Scanner(System.in)) {
            SocketAddress outcoming = new InetSocketAddress("localhost", 8443);
            userManager = new UserManager(scanner,
                    new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8)),
                    true);
            while (true) {
                try (SocketChannel outcomingchannel = SocketChannel.open(outcoming)) {

                    dataExchangeWithServer = new DataExchangeWithServer(outcomingchannel);

                    selector = Selector.open( );
                    outcomingchannel.configureBlocking(false);
                    outcomingchannel.register(selector, SelectionKey.OP_READ);

                    String beginMessage = "I've already got everything :(";
                    if(iBegin) beginMessage = "I'm ready to get available commands and base collection ";
                    dataExchangeWithServer.sendToServer(beginMessage);
                    if (iBegin) {
                        selector.select( );
                        userManager.setAvailable((HashMap) dataExchangeWithServer.getFromServer());
                        iBegin = false;
                    }


                    clientLaunch( );
                    exit();
                } catch (IOException e) {
                    if (!commandname.equals("exit")) {
                        userManager.writeln("Нет связи с сервером. Подключиться ещё раз (введите {да} или {нет})?");
                        String answer;
                        while (!(answer = userManager.read( )).equals("да")) {
                            switch (answer) {
                                case "":
                                    break;
                                case "нет":
                                    exit();
                                    break;
                                default:
                                    userManager.write("Введите корректный ответ.");
                            }
                        }
                        userManager.writeln("Подключение ...");
                        continue;
                    } else exit();
                }
            }
        }
    }


    public void clientLaunch () throws IOException {

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

            dataExchangeWithServer.sendToServer(command);

            selector.select( );
            userManager.writeln(dataExchangeWithServer.getFromServer( ).toString());

        }

    }
    public void exit() {
            userManager.write("Завершение программы.");
            System.exit(0);
    }
}