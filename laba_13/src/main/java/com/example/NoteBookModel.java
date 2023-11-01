package com.example;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NoteBookModel {

    private final Map<String, ArrayList<String>> notes = new HashMap<>();

    public NoteBookModel() {
    }

    public synchronized void add(String name, String phone) {
        if (!name.isEmpty() && !phone.isEmpty()) {
            if (notes.containsKey(name)) {
                notes.get(name).add(phone);
            } else {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(phone);
                notes.put(name, arrayList);
            }
        }
    }

    public synchronized void reset() {
        notes.clear();
    }


    public synchronized void readFromFile() {
        try {
            String userDirectory = FileSystems.getDefault()
                    .getPath("")
                    .toAbsolutePath()
                    .getParent().toString();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(userDirectory + "/webapps/laba_13/src/main/resources/Notes"));
            String name = bufferedReader.readLine();
            String phone = bufferedReader.readLine();
            while (name != null || phone != null) {
                if (name != null && phone != null) {
                    this.add(name.split("name ")[1], phone.split("phone ")[1]);
                }
                name = bufferedReader.readLine();
                phone = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void saveFile() {
        String userDirectory = FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .getParent().toString();
        File file = new File(userDirectory + "/webapps/laba_13/src/main/resources/Notes");

        try (FileWriter fw = new FileWriter(file);
             BufferedWriter bf = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bf)) {
            for (var map : notes.entrySet()) {
                out.println("name " + map.getKey());
                out.println("phone " + map.getValue().toString().substring(1, map.getValue().toString().length() - 1));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, ArrayList<String>> getNotes() {
        return notes;
    }

}
