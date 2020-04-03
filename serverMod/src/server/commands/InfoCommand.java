package server.commands;

import server.armory.DataExchange;
import server.receiver.UserManager;
import server.receiver.collection.ICollectionManager;

/**
 * Класс-команда info со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
//ConcreteCommand
public class InfoCommand implements Command {
    /**
     * Поле имя команды
     */
    private final String name = "info";
    /**
     * Поле описание команды
     */
    private final String description = "- вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";

    /**
     * Метод, передающий выполнение команды приемнику
     */

    @Override
    public void execute(DataExchange dataExchange, ICollectionManager icm, String arg) {
        dataExchange.send(icm.info());
    }


    /**
     * Метод получения значения поля (@link InfoCommand#description)
     *
     * @return description возвращает описание команды
     */

    public String getDescription() {
        return description;
    }

    /**
     * Метод получения значения поля (@link InfoCommand#name)
     *
     * @return name возвращает имя команды
     */

    public String getName() {
        return name;
    }
}