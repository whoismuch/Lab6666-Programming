package server.commands;

import server.armory.DataExchange;
import server.armory.Driver;
import server.receiver.collection.ICollectionManager;

/**
 * Класс-команда help со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
//ConcreteCommand
public class HelpCommand implements Command {
    /** Поле имя команды */
    private final String name = "help";
    /** Поле описание команды */
    private final String description = "- вывести справку по доступным командам";

    /**
     * Метод, передающий выполнение команды приемнику
     */

    @Override
    public void execute(DataExchange dataExchange, ICollectionManager icm, String arg) {
        Driver.getLive().getAllCommands().forEach(x -> dataExchange.send(x.getName() + x.getDescription()));
    }


    /**
     * Метод получения значения поля (@link HelpCommand#description)
     * @return description возвращает описание команды
     */

    public String getDescription() {
        return description;
    }

    /**
     * Метод получения значения поля (@link HelpCommand#name)
     * @return name возвращает имя команды
     */

    public String getName() {
        return name;
    }


}
