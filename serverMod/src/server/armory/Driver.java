package server.armory;

import common.generatedClasses.Route;
import server.commands.*;
import server.exceptions.NoExecuteScriptInScript;
import server.receiver.collection.ICollectionManager;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Класс-invoker, вызывающий команды, со свойством <b>man</b>
 *
 * @author Саня Малета и Хумай Байрамова
 * @version final
 */
public class Driver {
    private static Driver driver;
    private ArrayDeque<String> arrayDeque;

    public static Driver getLive ( ) {
        if (driver == null) driver = new Driver( );
        return driver;
    }

    /**
     * Поле словарь, где ключом является название команды, а значением - объект соответствующей команды
     */
    private HashMap<String, Command> man = new HashMap( );
    private HashMap<String, String> available = new HashMap<>();

    public Driver ( ) {
        this.arrayDeque = new ArrayDeque<>( );
        registerCommand(new AddCommand( ));
        registerCommand(new ClearCommand( ));
//        registerCommand(new ExecuteScriptCommand( ));
//        registerCommand(new ExitCommand( ));
        registerCommand(new FilterLessThanDistanceCommand( ));
        registerCommand(new HelpCommand( ));
//        registerCommand(new HistoryCommand( ));
        registerCommand(new InfoCommand( ));
//        registerCommand(new LoadCommand( ));
//        registerCommand(new PrintAscendingCommand( ));
//        registerCommand(new RemoveByIdCommand( ));
//        registerCommand(new RemoveGreaterCommand( ));
        registerCommand(new RemoveLowerCommand( ));
//        registerCommand(new SaveCommand( ));
        registerCommand(new ShowCommand( ));
        registerCommand(new SumOfDistanceCommand( ));
        registerCommand(new UpdateIdCommand( ));

    }

    /**
     * Метод, регистрирующий доступные команды в словаре команд
     *
     * @param command объект команды
     */

    private void registerCommand (Command command) {
        man.put(command.getName( ), command);
        available.put(command.getName(), command.getArg());
    }

    /**
     * Метод, используемый для будущего исполнения определенной команды
     *
     * @param icm
     * @param line
     * @throws NoExecuteScriptInScript ошибка возникает, если в скрипте будет команда вызова скрипта
     */
    public void execute (DataExchangeWithClient dataExchangeWithClient, ICollectionManager icm, String line, String arg, Route route) throws NoExecuteScriptInScript {
        Command command = man.get(line);
        if (command == null) {
            dataExchangeWithClient.sendToClient("Неверное имя команды : " + line);
        } else {
            command.execute(dataExchangeWithClient, icm, arg, route);
            addHisrory(line);
        }
    }

    /**
     * Возвращает все команды из реестра.
     *
     * @return Список всех команд.
     */
    public List<Command> getAllCommands ( ) {
        return man.keySet( ).stream( ).map(x -> (man.get(x))).collect(Collectors.toList( ));
    }

    /**
     * Метод добавляет команду в очердь после ее исполнения
     *
     * @param commandName имя команды
     */
    private void addHisrory (String commandName) {
        if (arrayDeque.size( ) >= 7) {
            arrayDeque.pollLast( );
        }
        arrayDeque.addFirst(commandName);
    }

    public ArrayDeque<String> getHistory ( ) {
        return arrayDeque;
    }

    public HashMap<String, String> getAvailable ( ) {
        return available;
    }
}
