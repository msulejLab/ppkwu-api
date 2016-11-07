package pl.ppkwu.lab.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MainController implements Initializable {

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

    private StringProperty currentFileProperty = new SimpleStringProperty();

    private FileOperations fileOperations;

    private static final Logger log = Logger.getLogger(MainController.class.getSimpleName());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileOperations = mockedFileOperations();
        initializeButtonsEvents();
        initializeTextField();

        log.info("Controller has been initialized");

    }

    private void initializeTextField() {
        fileNameField.textProperty().bind(currentFileProperty);
    }

    private void initializeButtonsEvents() {
        writeButton.setOnAction((event) -> {
            onWriteButtonClicked(event);
        });

        readButton.setOnAction((event) -> {
            onReadButtonClicked(event);
        });

        openButton.setOnAction((event) -> {
            onOpenFileButtonClicked(event);
        });
    }

    private void onOpenFileButtonClicked(ActionEvent event) {
        log.info("Open button clicked");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            currentFileProperty.setValue(selectedFile.getAbsolutePath());
        }
    }

    private void onReadButtonClicked(ActionEvent event) {
        log.info("Load button clicked");

        fileOperations.readFile(currentFileProperty.getValue(), new ReadCallback() {

            @Override
            public void onSuccess(String content) {
                log.info("Read file operation succeed");
                log.info("Content: \n" + content);
                statusLabel.setText("Content of file has been loaded");
            }

            @Override
            public void onFailure(String errorMessage) {
                log.warning("Read file operation failed: " + errorMessage);
                statusLabel.setText(errorMessage);
            }
        });
    }

    private void onWriteButtonClicked(ActionEvent event) {
        log.info("Write button clicked");

        String content = fileContentArea.getText();
        fileOperations.writeFile(currentFileProperty.getValue(), content, new WriteCallback() {

            @Override
            public void onSuccess() {
                log.info("Write file operation succeed");
                fileContentArea.setText(content);
                statusLabel.setText("Content has been written to file");
            }

            @Override
            public void onFailure(String errorMessage) {
                log.info("Read file operation failed: " + errorMessage);
                statusLabel.setText(errorMessage);
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private FileOperations mockedFileOperations() {
        return new FileOperations() {

            @Override
            public void readFile(String fileName, ReadCallback callback) {
                callback.onSuccess("Example content");
            }

            @Override
            public void writeFile(String fileName, String content, WriteCallback callback) {
                callback.onFailure("File not found");
            }
        };
    }
}
