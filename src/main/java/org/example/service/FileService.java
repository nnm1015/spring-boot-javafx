package org.example.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {
    private final String NOTES_DIRECTORY = "notebook";

    public FileService() {
        File dir = new File(NOTES_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public List<String> getAllNotes() {
        File dir = new File(NOTES_DIRECTORY);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files != null) {
            return Arrays.stream(files).map(File::getName).toList();
        }
        return List.of();
    }

    public void createNote(String noteName) {
        File file = new File(NOTES_DIRECTORY, noteName);
        if (!file.exists()) {
            try {
                Files.createFile(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteNote(String noteName) {
        File file = new File(NOTES_DIRECTORY, noteName);
        if (file.exists()) {
            file.delete();
        }
    }
}
