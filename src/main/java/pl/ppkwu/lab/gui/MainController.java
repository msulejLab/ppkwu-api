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

    StringProperty currentFileProperty = new SimpleStringProperty();

    private Logger log = Logger.getLogger(MainController.class.getSimpleName());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusLabel.setText("Hello from controller");

        initializeButtonsEvents();

        log.info("Controller has been initialized");

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
    }

    private void onWriteButtonClicked(ActionEvent event) {
        log.info("Write button clicked");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
