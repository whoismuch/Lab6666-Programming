package server.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import common.converters.GsonZonedDateTimeConverter;
import common.generatedClasses.Route;
import server.armory.DataExchange;
import server.receiver.UserManager;
import server.receiver.collection.ICollectionManager;

import java.time.ZonedDateTime;

/**
 * Класс-команда remove_greater со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
//ConcreteCommand
public class RemoveGreaterCommand implements Command {
    /**
     * Поле имя команды
     */
    private final String name = "remove_greater";
    /**
     * Поле описание команды
     */
    private final String description = "- удалить из коллекции все элементы, превышающие заданный";

    /**
     * Метод, передающий выполнение команды приемнику
     */

    @Override
    public void execute(DataExchange dataExchange, ICollectionManager icm, String arg) {
        dataExchange.send("Вы можете начать ввод объекта");
        Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new GsonZonedDateTimeConverter()).setPrettyPrinting().create();
        Route route = gson.fromJson((JsonElement) dataExchange.get(), Route.class);
        dataExchange.send(icm.size()-icm.removeGreater(route) + " элементов больше заданного успешно удалено");

    }


    /**
     * Метод получения значения поля (@link RemoveGreaterCommand#description)
     *
     * @return description возвращает описание команды
     */

    @Override
    public String getDescription() {
        return description;
    }


    /**
     * Метод получения значения поля (@link RemoveGreaterCommand#name)
     *
     * @return name возвращает имя команды
     */

    @Override
    public String getName() {
        return name;
    }
}