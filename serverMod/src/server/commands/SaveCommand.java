package server.commands;

import com.google.gson.JsonSyntaxException;
import server.exceptions.NoPermissionsException;
import server.receiver.UserManager;
import server.receiver.collection.ICollectionManager;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Класс-команда save со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
//ConcreteCommand
public class SaveCommand implements Command {
    /**
     * Поле имя команды
     */
    private final String name = "save";
    /**
     * Поле описание команды
     */
    private final String description = "- сохранить коллекцию в файл";
    private final String path = "/Users/aleksandr/АСМ/ITMO/Laba6/routes.json";


    /**
     * Метод, передающий выполнение команды приемнику
     */

    @Override
    public void execute(UserManager userManager, ICollectionManager icm, String arg) {
        try {
            icm.save(path);
            userManager.writeln("Коллекция сохранена");
        } catch (NoPermissionsException e) {
            userManager.writeln(e.getMessage());
        } catch (JsonSyntaxException e) {
            userManager.writeln("Ошибка парсера");
        } catch (NullPointerException e) {
            userManager.writeln("Файл пуст!");
        } catch (FileNotFoundException e) {
            userManager.writeln("Файла по указанному пути не существует!");
        } catch (IOException e) {
            userManager.writeln("Ошибка при рабтое с файлом");
        }
    }


    /**
     * Метод получения значения поля (@link SaveCommand#description)
     *
     * @return description возвращает описание команды
     */

    @Override
    public String getDescription() {
        return description;
    }


    /**
     * Метод получения значения поля (@link SaveCommand#name)
     *
     * @return name возвращает имя команды
     */
    @Override
    public String getName() {
        return name;
    }
}