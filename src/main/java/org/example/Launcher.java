package org.example;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Launcher {
    public static void main(String[] args) {
        Application.launch(JavafxNoteApplication.class, args);
    }
}
