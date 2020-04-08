package server.armory;

import server.receiver.collection.Navigator;
import server.receiver.collection.RouteBook;

import java.io.IOException;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.util.Scanner;

public class ServerApp {


    /**
     * Точка входа в программу. Управляет подключением к клиентам и созданием потоков для каждого из них.
     *
     * @param args массив по умолчанию в основном методе. Не используется здесь.
     */
    public static void main (String[] args) throws IOException, InterruptedException {
        Scanner in = new Scanner(System.in);
        RouteBook routeBook = new RouteBook( );
        Navigator navigator = new Navigator(routeBook);
        SocketAddress address = new InetSocketAddress("localhost", 8443);
        try (ServerSocketChannel ss = ServerSocketChannel.open( )) {
            ss.bind(address);
            System.out.print("Сервер начал слушать клиента " + "\nПорт " + ss.getLocalAddress( ) +
                        " / Адрес " + InetAddress.getLocalHost( ) + ".\nОжидаем подключения клиента\n ");
            String path = "serverMod/routes.json";
            ss.socket().setSoTimeout(60000);
            while (true) {
                System.out.println(1);
                Socket incoming = ss.socket( ).accept( );
                System.out.println(incoming + " подключился к серверу.");
                ServerConnection sc = new ServerConnection(navigator, incoming, path);
                sc.serverWork( );
            }

            } catch (UnknownHostException e) {
                e.printStackTrace( );
            } catch (SocketTimeoutException e) {
            System.out.println("Никто не хочет подключаться, ну что ж, сохраню коллекцию и завершу работу...");
            ServerConnection.theEnd();
            } catch (IOException e) {
                e.printStackTrace( );
            }

    }
}