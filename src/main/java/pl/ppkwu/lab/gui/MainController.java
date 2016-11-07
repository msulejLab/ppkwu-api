package pl.ppkwu.lab.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.ppkwu.lab.api.FileOperations;
import pl.ppkwu.lab.api.ReadCallback;
import pl.ppkwu.lab.api.WriteCallback;
import pl.ppkwu.lab.impl.FileOperationsImpl;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MainController implements Initializable {

    private static final Logger log = Logger.getLogger(MainController.class.getSimpleName());

    @FXML
    private BorderPane root;

    @FXML
    private Button readButton;

    @FXML
    private Button writeButton;

    @FXML
    private Button openButton;

    @FXML
    private TextField fileNameField;

    @FXML
    private TextArea fileContentArea;

    @FXML
    private Label statusLabel;

    private Stage stage;

    private String currentFile;

    private FileOperations fileOperations;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileOperations = new FileOperationsImpl();

        writeButton.setOnAction((event) -> {
            onWriteButtonClicked(event);
        });

        readButton.setOnAction((event) -> {
            onReadButtonClicked(event);
        });

        openButton.setOnAction((event) -> {
            onOpenFileButtonClicked(event);
        });

        log.info("Controller has been initialized");
    }

    private void onOpenFileButtonClicked(ActionEvent event) {
        log.info("Open button clicked");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            fileNameField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void onReadButtonClicked(ActionEvent event) {
        log.info("Load button clicked");

        String fileName = fileNameField.getText();
        statusLabel.setText("Processing...");
        fileOperations.readFile(fileName, new ReadCallback() {

            @Override
            public void onSuccess(String content) {
                log.info("Read file operation succeed");
                log.info("Content: \n" + content);
                Platform.runLater(() -> {
                    statusLabel.setText("Content of file has been loaded");
                    fileContentArea.setText(content);
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                log.warning("Read file operation failed: " + errorMessage);
                Platform.runLater(() -> {
                    statusLabel.setText(errorMessage);
                });
            }
        });
    }

    private void onWriteButtonClicked(ActionEvent event) {
        log.info("Write button clicked");

        String fileName = fileNameField.getText();
        String content = fileContentArea.getText();
        statusLabel.setText("Processing...");
        fileOperations.writeFile(fileName, content, new WriteCallback() {

            @Override
            public void onSuccess() {
                log.info("Write file operation succeed");
                Platform.runLater(() -> {
                    fileContentArea.setText(content);
                    statusLabel.setText("Content has been written to file");
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                log.info("Read file operation failed: " + errorMessage);
                Platform.runLater(() -> {
                    statusLabel.setText(errorMessage);
                });
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
