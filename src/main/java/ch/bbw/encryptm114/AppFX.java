package ch.bbw.encryptm114;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AppFX extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("gui.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 800);
        stage.setTitle("Encrypt your message");
        stage.setScene(scene);
        URL imagesUrl = getClass().getResource("images/icon.png");
        Image icon = new Image(imagesUrl.toString());
        stage.getIcons().add(icon);
        stage.show();
    }

    public void start() {
        launch();
    }
}
