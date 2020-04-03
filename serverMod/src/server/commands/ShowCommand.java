package server.commands;

import server.armory.DataExchange;
import server.receiver.UserManager;
import server.receiver.collection.ICollectionManager;

/**
 * Класс-команда show со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
//ConcreteCommand
public class ShowCommand implements Command {
    /**
     * Поле имя команды
     */
    private final String name = "show";
    /**
     * Поле описание команды
     */
    private final String description = " - вывести в стандартный поток вывода все элементы коллекции в строковом представлении";

    /**
     * Метод, передающий выполнение команды приемнику
     */

    @Override
    public void execute(DataExchange dataExchange, ICollectionManager icm, String arg) {
        dataExchange.send(icm.show());
    }

    /**
     * Метод получения значения поля (@link ShowCommand#description)
     *
     * @return description возвращает описание команды
     */

    public String getDescription() {
        return description;
    }

    /**
     * Метод получения значения поля (@link ShowCommand#name)
     *
     * @return name возвращает имя команды
     */

    public String getName() {
        return name;
    }

}



