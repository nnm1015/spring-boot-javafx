package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.example.service.FileService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteController {

    @FXML
    private ListView<String> noteList;

    @FXML
    private TextArea textArea;

    @FXML
    private ComboBox<String> fontComboBox;

    @FXML
    private ComboBox<String> fontSizeComboBox;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnDelete;

    private FileService fileService;

    public NoteController(FileService fileService) {
        this.fileService = fileService;
    }

    @FXML
    public void initialize() {
        fontComboBox.getItems().addAll("Arial", "Courier New", "Verdana", "Times New Roman");
        fontSizeComboBox.getItems().addAll("12", "14", "16", "18", "24", "32", "48", "64");
        updateNoteList();
    }

    @FXML
    private void changeFont() {
        String selectedFont = fontComboBox.getValue();
        textArea.setStyle("-fx-font-family: '" + selectedFont + "';");
        String selectedSize = fontSizeComboBox.getValue();
        textArea.setStyle("-fx-font-size: " + selectedSize + "px;");
    }

    @FXML
    private void changeFontSize() {
        String selectedFont = fontComboBox.getValue();
        textArea.setStyle("-fx-font-family: '" + selectedFont + "';");
        String selectedSize = fontSizeComboBox.getValue();
        textArea.setStyle("-fx-font-size: " + selectedSize + "px;");
    }

    private void updateNoteList() {
        List<String> notes = fileService.getAllNotes();
        noteList.getItems().clear();
        noteList.getItems().addAll(notes);
    }

    @FXML
    public void createNote() {
        fileService.createNote("new_note.txt");
        updateNoteList();
    }

    @FXML
    public void deleteNote() {
        String selectedNote = noteList.getSelectionModel().getSelectedItem();
        if (selectedNote != null) {
            fileService.deleteNote(selectedNote);
            updateNoteList();
        }
    }
}

