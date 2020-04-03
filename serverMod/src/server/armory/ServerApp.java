package server.armory;

import server.receiver.collection.Navigator;
import server.receiver.collection.RouteBook;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerApp {



    /**
     * Точка входа в программу. Управляет подключением к клиентам и созданием потоков для каждого из них.
     *
     * @param args массив по умолчанию в основном методе. Не используется здесь.
     */
    public static void main (String[] args) {
        RouteBook routeBook = new RouteBook();
        Navigator navigator = new Navigator(routeBook);
        try (ServerSocket server = new ServerSocket(8800)) {
            System.out.print("Сервер начал слушать клиента " + "\nПорт " + server.getLocalPort( ) +
                    " / Адрес " + InetAddress.getLocalHost( ) + ".\nОжидаем подключения клиента ");
            Socket incoming = server.accept( );
            System.out.println(incoming + " подключился к серверу.");
            ServerConnection sc = new ServerConnection(navigator, incoming);
            sc.serverWork();
        } catch (UnknownHostException e) {
            e.printStackTrace( );
        } catch (IOException e) {
            e.printStackTrace( );
        }
    }
}