package ch.bbw.encryptm114;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    public TextField input;
    @FXML
    public CheckBox salt;
    @FXML
    public CheckBox pepper;
    @FXML
    private Label outText;

    @FXML
    protected void onHelloButtonClick() {
        outText.setText(input.getText());
    }
}