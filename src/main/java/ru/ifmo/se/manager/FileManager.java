package ru.ifmo.se.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.ifmo.se.musicians.MusicBand;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Scanner;

/**
 * Работает с файлами
 *
 * @author Gurin Minu
 * @version 0
 * @since 0
 */
public class FileManager {

    File startFile;

    /**
     * Constructor FileManager
     */
    public FileManager() {

    }

    /**
     * Читает xml файл, и возвращает коллекцию MusicBand из этого файла
     *
     * @param file xml файл, в котором находится коллекция
     * @return Коллекция MusicBand
     */
    public LinkedHashSet<MusicBand> readFile(File file) throws FileNotFoundException {
        startFile = file;
        StringBuilder xml = new StringBuilder();
        Scanner scanner = null;
        boolean flag = true;
        LinkedHashSet<MusicBand> musicBands = new LinkedHashSet<>();
        XmlMapper mapper = new XmlMapper();
        mapper.registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        scanner = new Scanner(file, "UTF-8");
        xml = new StringBuilder();
        while (scanner.hasNextLine()) {
            xml.append(scanner.nextLine());
        }
        try {
            Collections.addAll(musicBands, mapper.readValue(xml.toString(), MusicBand[].class));
            System.out.println("Путь к файлу введен успешно");
        } catch (IOException e) {
            System.out.println("В этом файле содержится нечто неизвестное");
        }
        return musicBands;
    }

    /**
     * Сохраняет коллекцию в изначальный xml файл
     *
     * @param collection
     */
    public void saveFile(Collection collection) {
        XmlMapper mapper = new XmlMapper();
        mapper.registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        String serialized = null;
        try {
            serialized = mapper.writeValueAsString(collection.getCollection());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(startFile);
            assert serialized != null;
            fileOutputStream.write(serialized.getBytes());
            fileOutputStream.close();
            System.out.println("Файл сохранен");
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
