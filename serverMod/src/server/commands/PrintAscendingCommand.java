package server.commands;

import server.armory.DataExchange;
import server.receiver.UserManager;
import server.receiver.collection.ICollectionManager;

/**
 * Класс-команда print_ascending со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
//ConcreteCommand
public class PrintAscendingCommand implements Command {
    /**
     * Поле имя команды
     */
    private final String name = "print_ascending";
    /**
     * Поле описание команды
     */
    private final String description = "- вывести элементы коллекции в порядке возрастания";

    /**
     * Метод, передающий выполнение команды приемнику
     */

    @Override
    public void execute(DataExchange dataExchange, ICollectionManager icm, String arg) {
        dataExchange.send(icm.printAscending());
    }


    /**
     * Метод получения значения поля (@link PrintAscendingCommand#description)
     *
     * @return description возвращает описание команды
     */

    @Override
    public String getDescription() {
        return description;
    }


    /**
     * Метод получения значения поля (@link PrintAscendingCommand#name)
     *
     * @return name возвращает имя команды
     */

    @Override
    public String getName() {
        return name;
    }
}