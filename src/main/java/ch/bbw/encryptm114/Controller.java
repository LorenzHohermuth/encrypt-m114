package ch.bbw.encryptm114;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class Controller {

    String xorKey = "";

    int caesarKey = 0;
    public TextField input;

    public Button decryptBox;
    public CheckBox salt;
    public CheckBox pepper;
    public CheckBox hash;
    public CheckBox xor;
    public CheckBox caesar;
    @FXML
    private Label outText;

    boolean canDecrypte = false;

    @FXML
    protected void handleEncrypt() { //behandelt sachä wenn de button druckt wird
        String message = input.getText();
        if (message.equals("")) {
            return;
        }
        if (salt.isSelected()) {
            message = getSalt() + message;
        }
        if (pepper.isSelected()) {
            message = message + getSalt(); //das salt isch jz eifach churz än pepper
        }
        if (caesar.isSelected()) {
            message = caesar(message, 3);
            canDecrypte = true;
        }
        if (xor.isSelected()) {
            message = XOREncrypt(message);
            canDecrypte = true;
        }
        if (hash.isSelected()) {
            message = hashMessage(message);
        }
        outText.setText(message); //setzt de text uf äm label mit id outText uf t message
        setStateDecrypt();
    }
    public void start(Stage stage) {
        StackPane root = new StackPane();
        // set icon
        stage.getIcons().add(new Image("@images/chatting.png"));
        stage.setTitle("Wow!! Stackoverflow Icon");
        stage.setScene(new Scene(root, 300, 250));
        stage.show();
    }

    @FXML
    protected void handleDecrypt() {
        if(canDecrypte && xor.isSelected() && outText.getText() != "") {
            String encryptedMessageBin = toBinaryString(outText.getText());
            String decryptedMessageBin = XORString(encryptedMessageBin, xorKey, encryptedMessageBin.length());
            String decryptedMessage = binaryToText(decryptedMessageBin);
            outText.setText(decryptedMessage);
        }
        if(canDecrypte && caesar.isSelected() && outText.getText() != "") {
            String encryptedText = outText.getText();
            String decryptedText = caesar(encryptedText, caesarKey * -1);
            outText.setText(decryptedText);
        }
    }

    String XOREncrypt(String text) { //fuärt xor verschlüsslig us
        String binText = toBinaryString(text);
        String binKey = generateKey(binText.length());
        xorKey = binKey;
        String encryptedText = XORString(binText, binKey, binText.length());
        String out = binaryToText(encryptedText);
        return out;
    }

    String caesar(String text, int amountRotation) {
        caesarKey = amountRotation;
        String[] arrText = text.split("");
        String caesarEncrypted = "";
        for (int i = 0; i < arrText.length; i++) {
            String g = arrText[i];
            char character = g.charAt(0);
            character += amountRotation;
            String out = Character.toString(character);
            caesarEncrypted += out;
        }
        return caesarEncrypted;
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
        String[] arrText1 = text1.split("");
        String[] arrText2 = text2.split("");
        for (int i = 0 ; i < length ; i++) {
            if(arrText1[i].equals(arrText2[i])) {
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
            caesar.setStyle("-fx-text-fill: white;");
            input.setStyle("-fx-background-color: #a9a9a9;");
            outText.setStyle("-fx-background-color: #5c5c5c; -fx-text-fill: white; -fx-padding: 15;");
            darkModeToggle.setText("Light Mode");

        } else {
            root.setStyle("");
            xor.setStyle("");
            hash.setStyle("");
            salt.setStyle("");
            pepper.setStyle("");
            caesar.setStyle("");
            input.setStyle("");
            caesar.setStyle("");
            outText.setStyle("-fx-background-color: #CCCCCCCC; -fx-padding: 15;");
            darkModeToggle.setText("Dark Mode");
        }
    }
    void setStateDecrypt() {
        if (!canDecrypte){
            decryptButtonGray();
        }
        else {
            decryptButtonWhite();
        }
    }

    void decryptButtonGray() {
        decryptBox.setStyle("-fx-background-color: #bbb");
    }

    void decryptButtonWhite() {
        decryptBox.setStyle("-fx-background-color: #ddd");
    }

    @FXML
    void handleCheckBox() {
        canDecrypte = false;
        setStateDecrypt();
    }

}