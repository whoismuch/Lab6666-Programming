package server.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import common.converters.GsonZonedDateTimeConverter;
import common.generatedClasses.Route;
import server.armory.DataExchangeWithClient;
import server.receiver.collection.ICollectionManager;

import java.time.ZonedDateTime;

/**
 * Класс-команда remove_lower со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */

public class RemoveLowerCommand implements Command {
    /**
     * Поле имя команды
     */
    private final String name = "remove_lower";
    /**
     * Поле описание команды
     */
    private final String description = "- удалить из коллекции все элементы, меньшие, чем заданный (требует ввод объекта коллекции)";

    /**
     * Метод, передающий выполнение команды приемнику
     */
    private String arg = "e";

    @Override
    public String toString ( ) {
        return name + " " + description;
    }

    @Override
    public void execute(DataExchangeWithClient dataExchangeWithClient, ICollectionManager icm, String arg, Route route) {
        icm.removeLower(route);
        dataExchangeWithClient.sendToClient("Элементы, меньшие заданного, успешно удалены");
    }

    /**
     * Метод получения значения поля (@link RemoveLowerCommand#description)
     *
     * @return description возвращает описание команды
     */

    @Override
    public String getDescription() {
        return description;
    }


    /**
     * Метод получения значения поля (@link RemoveLowerCommand#name)
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