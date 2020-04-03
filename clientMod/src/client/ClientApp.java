package client;

import java.io.IOException;

public class ClientApp {

    private static UserManager userManager;
    /**
     * @param args массив по умолчанию в основном методе. Не используется здесь.
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Запуск клиентского модуля.\nПодключение к серверу ...");
        ClientProviding provide = new ClientProviding();
        provide.clientWork(userManager);
    }
}

