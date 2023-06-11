package ch.bbw.encryptm114;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    public TextField input;
    public CheckBox salt;
    public CheckBox pepper;
    public CheckBox hash;
    public CheckBox xor;
    @FXML
    private Label outText;

    @FXML
    protected void handleEncrypt() {
        String message = input.getText();
        if (message.equals("")) {
            return;
        }
        if (xor.isSelected()) {
            outText.setText("xor");
        }
        if (salt.isSelected()) {
            outText.setText("salt");
        }
        if (pepper.isSelected()) {
            outText.setText("pepper");
        }
        if (hash.isSelected()) {
            message = hashMessage(message);
        }
        outText.setText(message);
    }

    String hashMessage(String text) {
        String binText1 = toBinaryString(text);
        String binText2 = flipString(binText1);
        String XORBinText = XORString(binText1, binText2, binText2.length());
        String out = binaryToText(XORBinText);
        return out;
    }

    String toBinaryString(String textIn) {
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

    String flipString(String text) {
        String out = "";
        char[] charText = text.toCharArray();
        for(char character : charText) {
            out = character + out;
        }
        return out;
    }

    String XORString(String text1, String text2 , int length ) {
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


    String binaryToText(String binText) {
        String out = "";
        String[] byteArr = splitInPairsOf(binText, 8);
        for (String byteText: byteArr ) {
            int charCode = Integer.parseInt(byteText, 2);
            String str = new Character((char)charCode).toString();
            out += str;
        }
        return out;
    }

    String[] splitInPairsOf(String text, int n) {
        String[] results = text.split("(?<=\\G.{" + n + "})");
        return results;
    }
}