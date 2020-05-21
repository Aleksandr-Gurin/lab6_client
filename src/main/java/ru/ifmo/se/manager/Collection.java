package ru.ifmo.se.manager;

import ru.ifmo.se.musicians.MusicBand;

import java.io.Serializable;
import java.util.*;

/**
 * Класс, управляющий коллекцией
 */
public class Collection implements Serializable {
    private LinkedHashSet<MusicBand> musicBands;

    private Date initDate = new Date();

    /**
     * Constructor collection
     *
     * @param musicBands Коллекция, которой управляет класс
     */
    public Collection(LinkedHashSet<MusicBand> musicBands) {
        this.musicBands = musicBands;
    }

    /**
     * Добавляет новый элемент в коллекцию
     *
     * @param musicBand добавляемый элемент
     */
    public String add(MusicBand musicBand) {
        musicBands.add(musicBand);
        return "Объект добавлен в коллекцию";
    }

    /**
     * Обновляет значения элемента в коллекции
     *
     * @param id id, обновляемого элемента
     * @param mb Объект, который содержит значения, кторые должен принять обновляемый элемент
     */
    public void update(int id, MusicBand mb) {
        musicBands.stream().filter((o) -> o.getId() == id).forEachOrdered((o) -> {
            o.setNumberOfParticipants(mb.getNumberOfParticipants());
            o.setName(mb.getName());
            o.setGenre(mb.getGenre());
            o.setFrontMan(mb.getFrontMan());
            o.setEstablishmentDate(mb.getEstablishmentDate());
            o.setCoordinates(mb.getCoordinates());
        });
    }

    /**
     * Удаляет элемент коллекции
     *
     * @param id id, удаляемого элемента
     */
    public String remove(int id) {
        MusicBand musicBand;
        musicBand = musicBands.stream().filter((e) -> e.getId() == id).findFirst().orElse(null);
        if (musicBand != null) {
            musicBands.remove(musicBand);
            return "Объект удален";
        } else {
            return "id не найден, повторите команду";
        }
    }

    /**
     * Удаляет элементы коллекции, которые больше данного
     *
     * @param musicBand Объект, с которым сравниваются элементы коллекции
     */
    public String removeGreater(MusicBand musicBand) {
        musicBands.removeIf((e) -> e.compareTo(musicBand) > 0);
        return ("Объекты удалены");
    }


    /**
     * Удаляет элементы коллекции, которые меньше данного
     *
     * @param musicBand Объект, с которым сравниваются элементы коллекции
     */
    public String removeLower(MusicBand musicBand) {
        musicBands.removeIf((e) -> e.compareTo(musicBand) < 0);
        return ("Объекты удалены");
    }

    /**
     * Выводит в стандартный поток вывода все элементы коллекции в строковом представлении
     */
    public String show() {
        StringBuilder result = new StringBuilder();
        musicBands.stream().sorted(Comparator.comparing(x -> x.getFrontMan().getNationality())).forEach((o) -> result.append(o.toString()).append("\n"));
        return result.toString();
    }

    /**
     * Возвращает дату инициализации коллекции
     *
     * @return Date
     */
    public Date getInitDate() {
        return initDate;
    }

    /**
     * Выводит элементы в обратном порядке
     */
    public String printDescending() {
        StringBuilder result = new StringBuilder();
        musicBands.stream().sorted().forEachOrdered((o) -> result.append(o.toString()).append("\n"));
        return (result.toString());
    }

    /**
     * Возвращает коллекцию
     *
     * @return LinkedHashSet
     */
    public LinkedHashSet<MusicBand> getCollection() {
        return musicBands;
    }

    /**
     * Очищает коллекцию
     */
    public String clear() {
        musicBands.clear();
        return ("Коллекция очищена");
    }

    /**
     * Возвращает элементы, значение поля numberOfParticipants которых меньше заданного
     *
     * @param nop numberOfParticipants
     */
    public List<MusicBand> filterLessThanNumberOfParticipants(int nop) {
        ArrayList<MusicBand> result = new ArrayList<>();
        musicBands.stream().filter((o) -> o.getNumberOfParticipants() < nop).forEach(result::add);
        return result;
    }

    /**
     * Выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
     */
    public String info() {
        return ("Тип: " + musicBands.getClass() + "\nДата инициализации: " + getInitDate() + "\nКоличество элементов: " + musicBands.size());
    }

    /**
     * Выводит любой объект из коллекции, значение поля genre которого является максимальным
     */
    public Object maxByGenre() {
        MusicBand mb = musicBands.stream().filter((o) -> o.getGenre() != null).max(Comparator.comparing(MusicBand::getGenre)).orElse(null);
        if (mb != null) {
            return (mb);
        } else return "Элемент не найден";
    }
}
