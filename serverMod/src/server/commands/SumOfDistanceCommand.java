package server.commands;

import server.armory.DataExchange;
import server.receiver.UserManager;
import server.receiver.collection.ICollectionManager;

/**
 * Класс-команда sum_of_distance со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
//ConcreteCommand
public class SumOfDistanceCommand implements Command {
    /**
     * Поле имя команды
     */
    private final String name = "sum_of_distance";
    /**
     * Поле описание команды
     */
    private final String description = "- ввывести сумму значений поля distance для всех элементов коллекции";

    /**
     * Метод, передающий выполнение команды приемнику
     */

    @Override
    public void execute(DataExchange dataExchange, ICollectionManager icm, String arg) {
        if (icm.size() == 0) {
            dataExchange.send("Коллекция пуста");
        } else {
            dataExchange.send("Сумма всех маршрутов : " + icm.sumOfDistance());
        }
    }

    /**
     * Метод получения значения поля (@link SumOfDistanceCommand#description)
     *
     * @return description возвращает описание команды
     */
    @Override
    public String getDescription() {
        return description;
    }


    /**
     * Метод получения значения поля (@link SumOfDistanceCommand#name)
     *
     * @return name возвращает имя команды
     */
    @Override
    public String getName() {
        return name;
    }
}