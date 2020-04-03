package server.commands;


import server.armory.DataExchange;
import server.receiver.UserManager;
import server.receiver.collection.ICollectionManager;

/**
 * Класс-команда filter_less_than_distance со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
//ConcreteCommand
public class FilterLessThanDistanceCommand implements Command {
    /** Поле имя команды */
    private final String name = "filter_less_than_distance";
    /** Поле описание команды */
    private final String description = "- вывести элементы, значение поля distance которых меньше заданного";

    /**
     * Метод, передающий выполнение команды приемнику
     */

    @Override
    public void execute(UserManager userManager, ICollectionManager icm, String s) {
        Float distance = userManager.checkFloatInput(s)? Float.parseFloat(s) : userManager.parseFloatInput("Введите корректный distance: ");
        icm.filterLessThanDistance(distance).forEach(x->userManager.writeln(x.toString()));
    }

    @Override
    public void execute(DataExchange dataExchange, ICollectionManager icm, String arg) {
        Float distance = userManager.checkFloatInput(arg)? Float.parseFloat(s) : userManager.parseFloatInput("Введите корректный distance: ");
        icm.filterLessThanDistance(distance).forEach(x->userManager.writeln(x.toString()));
    }


    /**
     * Метод получения значения поля (@link FilterLessThanDistanceCommand#description)
     * @return description возвращает описание команды
     */

    @Override
    public String getDescription() {
        return description;
    }


    /**
     * Метод получения значения поля (@link FilterLessThanDistanceCommand#name)
     * @return name возвращает имя команды
     */

    @Override
    public String getName() {
        return name;
    }
}