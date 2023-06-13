package ch.bbw.encryptm114;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;

import java.util.Random;

public class Controller {

    public TextField input;
    public CheckBox salt;
    public CheckBox pepper;
    public CheckBox hash;
    public CheckBox xor;
    @FXML
    private Label outText;

    @FXML
    protected void handleEncrypt() { //behandelt sachä wenn de button druckt wird
        String message = input.getText();
        if (message.equals("")) {
            return;
        }
        if (xor.isSelected()) {
            message = XOREncrypt(message);
        }
        if (salt.isSelected()) {
            message = getSalt() + message;
        }
        if (pepper.isSelected()) {
            message = message + getSalt(); //das salt isch jz eifach churz än pepper
        }
        if (hash.isSelected()) {
            message = hashMessage(message);
        }
        outText.setText(message); //setzt de text uf äm label mit id outText uf t message
    }

    //XD
    @FXML
    protected void handleDecrypt() {
        String message = input.getText();
        outText.setText(message);
    }

    String XOREncrypt(String text) { //fuärt xor verschlüsslig us
        String binText = toBinaryString(text);
        String binKey = generateKey(binText.length());
        String encryptedText = XORString(binText, binKey, binText.length());
        String out = binaryToText(encryptedText);
        return out;
    }

    String hashMessage(String text) { // hashted än string
        String binText1 = toBinaryString(text);
        String binText2 = flipString(binText1);
        String XORBinText = XORString(binText1, binText2, binText2.length());
        String out = binaryToText(XORBinText);
        return out;
    }

    String getSalt() { //erstellt ä random 3 zeichen langä string
        String binSalt = generateRandomBin(3*8);
        String out = binaryToText(binSalt);
        return out;
    }

    String toBinaryString(String textIn) { //vo text zu binärä string
        StringBuilder sb = new StringBuilder();
        char[] charText = textIn.toCharArray();
        for (char character : charText) {
            String binary = Integer.toBinaryString(character);
            String formatted = String.format("%8s", binary);
            String out = formatted.replaceAll(" ", "0");
            sb.append(out);
        }
        return sb.toString();
    }

    String flipString(String text) { //trült än string um
        String out = "";
        char[] charText = text.toCharArray();
        for(char character : charText) {
            out = character + out;
        }
        return out;
    }

    String XORString(String text1, String text2 , int length ) { //macht xor operation uf zwei binäri strings
        String out = "";
        for (int i = 0 ; i < length ; i++) {
            if(text1.charAt(i) == text2.charAt(i)) {
                out += "1";
            }
            else {
                out += "0";
            }
        }
        return out;
    }


    String binaryToText(String binText) { //vo binär zu text
        String out = "";
        String[] byteArr = splitInPairsOf(binText, 8);
        for (String byteText: byteArr ) {
            int charCode = Integer.parseInt(byteText, 2);
            String str = new Character((char)charCode).toString();
            out += str;
        }
        return out;
    }

    String[] splitInPairsOf(String text, int n) { // splität in paar vo n
        String[] results = text.split("(?<=\\G.{" + n + "})");
        return results;
    }

    String generateKey(int length) {
        return generateRandomBin(length);
    } // generiert än key für xor verslüsslig

    String generateRandomBin(int length) { //generiert än random binärä zeichächeti
        String out = "";
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            int randInt = rand.nextInt(2);
            out += randInt;
        }
        return out;
    }

    @FXML
    private ToggleButton darkModeToggle;

    @FXML
    private VBox root;

    @FXML
    private void toggleDarkMode() {
        boolean darkModeEnabled = darkModeToggle.isSelected();

        if (darkModeEnabled) {
            root.setStyle("-fx-background-color: #202020;");
            xor.setStyle("-fx-text-fill: white;");
            hash.setStyle("-fx-text-fill: white;");
            salt.setStyle("-fx-text-fill: white;");
            pepper.setStyle("-fx-text-fill: white;");
            input.setStyle("-fx-background-color: #a9a9a9;");
            outText.setStyle("-fx-background-color: #5c5c5c; -fx-text-fill: white; -fx-padding: 15;");
            darkModeToggle.setText("Light Mode");

        } else {
            root.setStyle("");
            xor.setStyle("");
            hash.setStyle("");
            salt.setStyle("");
            pepper.setStyle("");
            input.setStyle("");
            outText.setStyle("-fx-background-color: #CCCCCCCC; -fx-padding: 15;");
            darkModeToggle.setText("Dark Mode");
        }
    }

}