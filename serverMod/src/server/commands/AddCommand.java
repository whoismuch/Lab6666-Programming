package server.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import common.converters.GsonZonedDateTimeConverter;
import common.generatedClasses.Route;
import server.armory.DataExchange;
import server.receiver.collection.ICollectionManager;

import java.time.ZonedDateTime;

/**
 * Класс-команда add со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
//ConcreteCommand
public class AddCommand implements Command {
    /**
     * Поле имя команды
     */
    private final String name = "add";
    /**
     * Поле описание команды
     */
    private final String description = " - добавить новый элемент в коллекцию";

    /**
     * Метод, передающий выполнение команды приемнику
     */


    @Override
    public void execute(DataExchange dataExchange, ICollectionManager icm, String arg) {
        try {
            dataExchange.send("Вы можете начать ввод объекта");
            Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new GsonZonedDateTimeConverter()).setPrettyPrinting().create();
            Route route = gson.fromJson((JsonElement) dataExchange.get(), Route.class);
            icm.add(route);
            dataExchange.send("Объект " + route.getName() + " успешно добавлен в коллекцию!");
        }catch (ClassCastException e){
            dataExchange.send("Ошибка при создании класса");
        }
    }

    /**
     * Метод получения значения поля (@link AddCommand#description)
     *
     * @return description возвращает описание команды
     */

    public String getDescription() {
        return description;
    }

    /**
     * Метод получения значения поля (@link AddCommand#name)
     *
     * @return name возвращает имя команды
     */

    public String getName() {
        return name;
    }


}


