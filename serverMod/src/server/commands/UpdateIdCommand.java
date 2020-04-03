package server.commands;

import common.generatedClasses.Route;
import server.receiver.UserManager;
import server.receiver.collection.ICollectionManager;

/**
 * Класс-команда update_id со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
public class UpdateIdCommand implements Command {
    /**
     * Поле имя команды
     */
    private final String name = "update_id";
    /**
     * Поле описание команды
     */
    private final String description = " - обновить значение элемента коллекции, id которого равен заданному";

    /**
     * Метод, передающий выполнение команды приемнику
     */

    @Override
    public void execute(UserManager userManager, ICollectionManager icm, String s) {
        Long id = userManager.checkLongInput(s)? Long.parseLong(s) : userManager.parseLongInput("Введите корректный id: ");
        Route route = userManager.readRoute();
        userManager.writeln(icm.updateId(id,route)? "Объект с id: " + id + " обновлен" : "Элемент не найден");
    }

    /**
     * Метод получения значения поля (@link UpdateIdCommand#description)
     *
     * @return description возвращает описание команды
     */
    public String getDescription() {
        return description;
    }

    /**
     * Метод получения значения поля (@link UpdateIdCommand#name)
     *
     * @return name возвращает имя команды
     */
    public String getName() {
        return name;
    }
}