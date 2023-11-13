package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalList {

    private final Map<String, List<String>> animals;

    public AnimalList() {
        animals = new HashMap<>();
    }

    public void readFromFile() {
        String userDir = FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .getParent()
                .toString();
        File file = new File(userDir+"/webapps/laba_16/src/main/resources/animals.txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parsedLine = line.split(":");
                List<String> parsedAnimals = List.of(parsedLine[1].split(","));
                animals.put(parsedLine[0], parsedAnimals);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, List<String>> getAnimals() {
        return animals;
    }
}
