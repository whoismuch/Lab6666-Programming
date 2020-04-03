package server.commands;


import server.armory.DataExchange;
import server.armory.Driver;
import server.receiver.UserManager;
import server.receiver.collection.ICollectionManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.NoSuchElementException;

/**
 * Класс-команда execute_script со свойствами <b>name</b>, <b>description</b>, <b>navigator</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */

public class ExecuteScriptCommand implements Command {
    /**
     * Поле имя команды
     */
    private final String name = "execute_script";
    /**
     * Поле описание команды
     */
    private final String description = "- считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь";

    /**
     * Метод, передающий выполнение команды приемнику
     */
//execute_script 1.txt
    @Override
    public void execute(UserManager userManager, ICollectionManager icm, String arg) {
        try {
            UserManager scriptInterface = new UserManager(new FileReader(s), new OutputStreamWriter(System.out), false);
            while (scriptInterface.hasNextLine()) {
                String line = scriptInterface.read();
                Driver.getLive().execute(scriptInterface, icm, line);
            }
            userManager.writeln("Скрипт выполнен");
        } catch (NoSuchElementException e) {
            userManager.writeln("Недостаточно введенных данных");
        } catch (NullPointerException e) {
            userManager.writeln("Файл пуст!");
        } catch (FileNotFoundException e) {
            userManager.writeln("Файла по указанному пути не существует!");
        } catch (IOException e) {
            userManager.writeln("Ошибка при рабтое с файлом");
        }
    }

    @Override
    public void execute(DataExchange dataExchange, ICollectionManager icm, String arg) {
        try {
            DataExchange scriptInterface = new DataExchange()
            UserManager scriptInterface = new UserManager(new FileReader(s), new OutputStreamWriter(System.out), false);
            while (scriptInterface.hasNextLine()) {
                String line = scriptInterface.read();
                Driver.getLive().execute(scriptInterface, icm, line);
            }
            userManager.writeln("Скрипт выполнен");
        } catch (NoSuchElementException e) {
            userManager.writeln("Недостаточно введенных данных");
        } catch (NullPointerException e) {
            userManager.writeln("Файл пуст!");
        } catch (FileNotFoundException e) {
            userManager.writeln("Файла по указанному пути не существует!");
        } catch (IOException e) {
            userManager.writeln("Ошибка при рабтое с файлом");
        }
    }


    /**
     * Метод получения значения поля (@link ExecuteScriptCommand#description)
     *
     * @return description возвращает описание команды
     */

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Метод получения значения поля (@link ExecuteScriptCommand#name)
     *
     * @return name возвращает имя команды
     */

    @Override
    public String getName() {
        return name;
    }
}