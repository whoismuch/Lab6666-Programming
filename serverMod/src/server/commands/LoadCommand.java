package server.commands;

import com.google.gson.JsonSyntaxException;
import common.generatedClasses.Route;
import server.armory.DataExchangeWithClient;
import server.exceptions.NoPermissionsException;
import server.receiver.UserManager;
import server.receiver.collection.ICollectionManager;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Класс-команда load со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
public class LoadCommand implements Command {
    /**
     * Поле имя команды
     */
    private final String name = "load";
    /**
     * Поле описание команды
     */
    private final String description = "- загрузка колекции из файла";

    private String arg = "path";

    /**
     * Метод, передающий выполнение команды приемнику
     */

    @Override
    public void execute(DataExchangeWithClient dataExchangeWithClient, ICollectionManager icm, String arg, Route route) {
        try {
            int before = icm.size();
            icm.load(arg);
            //dataExchangeWithClient.sendToClient(("Добавлено " + (icm.size() - before) + " элементов в коллекцию \n"));
        } catch (NoPermissionsException e) {
            dataExchangeWithClient.sendToClient(e.getMessage());
        } catch (JsonSyntaxException e) {
            dataExchangeWithClient.sendToClient("Ошибка парсера, сохраненная на сервере коллекция не доступна");
        } catch (NullPointerException e) {
            dataExchangeWithClient.sendToClient("Файл пуст! Сохраненная на сервере коллекция не доступна");
        } catch (FileNotFoundException e) {
            dataExchangeWithClient.sendToClient("Упс... у нас неполадки. Сохраненная на сервере коллекция не доступна");
        } catch (IOException e) {
            dataExchangeWithClient.sendToClient("Ошибка при рабтое с файлом. Сохраненная на сервере коллекция не достуна");
        }
    }

    /**
     * Метод получения значения поля (@link LoadCommand#description)
     *
     * @return description возвращает описание команды
     */

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Метод получения значения поля (@link LoadCommand#name)
     *
     * @return name возвращает имя команды
     */

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getArg() {
        return arg;
    }
}