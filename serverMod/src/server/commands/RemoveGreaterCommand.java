package server.commands;

import common.generatedClasses.Route;
import server.armory.DataExchangeWithClient;
import server.receiver.collection.ICollectionManager;


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
    private final String description = "- удалить из коллекции все элементы, превышающие заданный (требует ввод объекта коллекции)";

    private String arg = "e";

    @Override
    public String toString ( ) {
        return name + " " + description;
    }

    /**
     * Метод, передающий выполнение команды приемнику
     */

    @Override
    public void execute(DataExchangeWithClient dataExchangeWithClient, ICollectionManager icm, String arg, Route route) {
        icm.removeGreater(route);
        dataExchangeWithClient.sendToClient("Элементы, большие заданного, успешно удалены");
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

    @Override
    public String getArg() {
        return arg;
    }
}