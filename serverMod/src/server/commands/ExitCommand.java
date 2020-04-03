package server.commands;

import server.armory.DataExchange;
import server.receiver.collection.ICollectionManager;

/**
 * Класс-команда exit со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
//ConcreteCommand
public class ExitCommand implements Command {
    /** Поле имя команды */
    private final String name = "exit";
    /** Поле описание команды */
    private final String description = " - завершить программу(без сохранения файла)";

    /**
     * Конструктор - привязывает команду к приемнику
     * @param navigator объект класса-receiver Navigator
     */



    /**
     * Метод, передающий выполнение команды приемнику
     */

    @Override
    public void execute(DataExchange dataExchange, ICollectionManager icm, String arg) {
        System.out.println("Завершаю работу...");
    }


    /**
     * Метод получения значения поля (@link ExitCommand#description)
     * @return description возвращает описание команды
     */

    public String getDescription() {
        return description;
    }

    /**
     * Метод получения значения поля (@link ExitCommand#name)
     * @return name возвращает имя команды
     */

    public String getName() {
        return name;
    }
}


