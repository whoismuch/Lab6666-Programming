package server.commands;

import server.armory.DataExchange;
import server.armory.Driver;
import server.receiver.UserManager;
import server.receiver.collection.ICollectionManager;

/**
 * Класс-команда history со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
//ConcreteCommand
public class HistoryCommand implements Command {
    /** Поле имя команды */
    private final String name = "history";
    /** Поле описание команды */
    private final String description = "- вывести последние 7 команд (без их аргументов)";
    /**
     * Метод, передающий выполнение команды приемнику
     */

    @Override
    public void execute(DataExchange dataExchange, ICollectionManager icm, String arg) {
        if (Driver.getLive().getHistory().size() == 0) {
            dataExchange.send("Вы еще ничего не вводили.");
        } else {
            Driver.getLive().getHistory().forEach(x -> dataExchange.send(x));
        }
    }


    /**
     * Метод получения значения поля (@link HistoryCommand#description)
     * @return description возвращает описание команды
     */

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Метод получения значения поля (@link HistoryCommand#name)
     * @return name возвращает имя команды
     */
    @Override
    public String getName() {
        return name;
    }
}