package com.fassi.vorwerkApp;

import com.fassi.vorwerkApp.services.SqliteServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        //mapping id view
        Parent root = loader.load(getClass().getResource("view/Login_view.fxml"));
        Scene scene = new Scene(root, 600, 450);
        stage.setResizable(false);
        stage.setTitle("Vorwerk");
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("assets/icon.png")));
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        System.out.println(args);
        try {
            SqliteServices.init("VorwerkDb.Db");
            launch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}