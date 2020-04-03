package server.commands;

import server.armory.DataExchange;
import server.receiver.UserManager;
import server.receiver.collection.ICollectionManager;

/**
 * Класс-команда remove_by_id со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
//ConcreteCommand
public class RemoveByIdCommand implements Command {
    /** Поле имя команды */
    private final String name = "remove_by_id";
    /** Поле описание команды */
    private final String description = "- удалить элемент из коллекции по его id";

    /**
     * Метод, передающий выполнение команды приемнику
     */

    @Override
    public void execute(DataExchange dataExchange, ICollectionManager icm, String arg) {
        Long id = userManager.checkLongInput(s)? Long.parseLong(s) : userManager.parseLongInput("Введите корректный id: ");
        userManager.writeln(icm.removeById(id)? "Элемент с id : " + id + " удален!": "Элемент не найден");
    }

    /**
     * Метод получения значения поля (@link RemoveByIdCommand#description)
     * @return description возвращает описание команды
     */

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Метод получения значения поля (@link RemoveByIdCommand#name)
     * @return name возвращает имя команды
     */
    @Override
    public String getName() {
        return name;
    }
}