package server.commands;

import server.armory.DataExchange;
import server.receiver.collection.ICollectionManager;



/**
 * Класс-команда clear со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
//ConcreteCommand
public class ClearCommand implements Command {
    /** Поле имя команды */
    private final String name = "clear";
    /** Поле описание команды */
    private final String description = " - очистить коллекцию";

    /**
     * Метод, передающий выполнение команды приемнику
     */

    @Override
    public void execute(DataExchange dataExchange, ICollectionManager icm, String arg) {
        icm.clear();
        dataExchange.send("Коллекция очищена!");
    }

    /**
     * Метод получения значения поля (@link ClearCommand#description)
     * @return description возвращает описание команды
     */

    public String getDescription() {
        return description;
    }

    /**
     * Метод получения значения поля (@link ClearCommand#name)
     * @return name возвращает имя команды
     */

    public String getName() {
        return name;
    }
}


