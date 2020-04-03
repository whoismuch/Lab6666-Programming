package server.receiver.collection;


import com.google.gson.JsonSyntaxException;
import server.comparators.*;
import server.exceptions.NoPermissionsException;
import common.generatedClasses.Route;
import server.json.JsonDeserialization;
import server.json.JsonSerialization;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для работы с коллекцией
 */
public class Navigator implements ICollectionManager {
    private static ICollection<Route> routeBook;

    public Navigator(ICollection<Route> routeBook) {
        this.routeBook = routeBook;
    }

    /**
     * Метод выводит информацию о коллекции
     *
     * @return строка с информацией о коллекции
     */
    @Override
    public String info() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Время инициализации коллекции: ").append(routeBook.getInitializationTime().toString()).append('\n');
        stringBuilder.append("Количество элементов в коллекции: ").append(routeBook.size()).append('\n');
        stringBuilder.append("Тип коллекции: ").append(routeBook.getCollectionClass().getName());
        return stringBuilder.toString();
    }

    /**
     * Добавляет маршрут в коллекцию.
     *
     * @param route маршрут, которая будет добавлена.
     */
    @Override
    public void add(Route route) {
        routeBook.add(route);
    }

    /**
     * Очищает коллекцию.
     */
    @Override
    public void clear() {
        routeBook.clear();
    }

    /**
     * Метод удаляет элемент коллекции, id которого равен заданному
     * @param id элемента который удаляем
     * @return true - если элемент существовал и false - в ином случае
     */
    @Override
    public boolean removeById(long id) {
        List<Route> routes = routeBook.toList().stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        if(!routes.isEmpty()){
            routes.forEach(routeBook::remove);
            return true;
        }
        return false;
    }
    /**
     * Возвращает все элементы коллекции.
     * @return Строку со всеми элементами коллекции.
     */
    @Override
    public String show() {
        StringBuilder stringBuilder = new StringBuilder("Элементов в коллекции: " + routeBook.size()).append("\n");
        routeBook.toList().forEach(x -> stringBuilder.append(x.toString()).append('\n'));
        return stringBuilder.toString();
    }
    /**
     * Генерирует новый id
     * @param id   идентификатор объекта
     * @param route
     * @return true - если объект был найден и обновлён, false -  в ином случае
     */
    @Override
    public boolean updateId(long id, Route route) {
        if (!removeById(id)) return false;
        routeBook.add(id,route);
        return true;
    }
    /**
     * Метод выводит сумму значений поля distance для всех элементов коллекции
     */
    @Override
    public Float sumOfDistance() {
        return routeBook.toList().stream().reduce(0f,(x,y) -> x + y.getDistance(),(x,y)-> x + y);
    }

    /**
     * Возвращает размер коллекции.
     *
     * @return кол-во элементов в коллекции.
     */
    @Override
    public int size() {
        return routeBook.size();
    }

    @Override
    public void save(String path) throws JsonSyntaxException, NullPointerException, FileNotFoundException, NoPermissionsException, IOException {
        JsonSerialization jsonSerialization = new JsonSerialization();
        jsonSerialization.saveCollectionToFile(routeBook, path);
    }

    @Override
    public void load(String path) throws JsonSyntaxException, NullPointerException, FileNotFoundException, NoPermissionsException, IOException {
        JsonDeserialization jsonDeserialization = new JsonDeserialization();
        jsonDeserialization.loadCollectionFromFile(routeBook, path);
    }

    /**
     * Метод сортирующий коллекцию обьъектов класса Route(маршрутов)
     *
     * @param routes коллекция объектов класса Route(маршрутов)
     * @return routes2 возвращает отсортированную коллекцию объектов класса Route(маршрутов)
     */
    @Override
    public List<Route> sort(List<Route> routes) {
        Collections.sort(routes, new NameComparator().thenComparing(new DistanceComparator()).thenComparing(new XxFromComparator()).thenComparing(new YyFromComparator()).thenComparing(new XFromXToComparator()).thenComparing(new YFromYToComparator()));
        return routes;
    }

    /**
     * Метод выводит элементы, значение поля distance которых меньше заданного
     *
     * @param distance значение для сравнения
     */
    @Override
    public List<Route> filterLessThanDistance(Float distance) {
        return routeBook.toList().stream().filter(x -> x.getDistance() < distance).collect(Collectors.toList());
    }

    /**
     * Метод удаляет из коллекции все элементы, превышающие заданный
     *
     * @param route эемент для сравнения
     */
    @Override
    public long removeGreater(Route route) {
        return routeBook.toList().stream().filter(x -> x.compareTo(route) < 0).count();

    }

    /**
     * Метод удаляет из коллекции все элементы, меньше, чем заданный
     *
     * @param route эемент для сравнения
     */
    @Override
    public long removeLower(Route route) {
        return routeBook.toList().stream().filter(x -> x.compareTo(route) > 0).count();
    }

    /**
     * Метод выводит элементы коллекции в порядке возрастания
     */
    @Override
    public String printAscending() {
        if (routeBook.size() == 0) {
            return "Коллекция пуста";
        } else {
            StringBuilder stringBuilder = new StringBuilder("Элементов в коллекции: " + routeBook.size()).append("\n");
            sort(routeBook.toList()).forEach(x -> stringBuilder.append(x.toString()).append('\n'));
            return stringBuilder.append("Коллекция успешно отсортирована!").toString();
        }
    }

    /**
     * Метод удаляет элемент коллекции, id которого равен заданному
     */
}
