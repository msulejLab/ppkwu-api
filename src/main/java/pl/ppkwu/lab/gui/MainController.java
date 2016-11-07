package pl.ppkwu.lab.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MainController implements Initializable {

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

    private Logger log = Logger.getLogger(MainController.class.getSimpleName());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusLabel.setText("Hello from controller");

        initializeButtonsEvents();

        log.info("Controller has been initialized");
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
    }

    private void onReadButtonClicked(ActionEvent event) {
        log.info("Load button clicked");
    }

    private void onWriteButtonClicked(ActionEvent event) {
        log.info("Write button clicked");
    }
}
