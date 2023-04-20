package com.fassi.vorwerkApp.controllers;

import com.fassi.vorwerkApp.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class HomeViewController {
    @FXML
    private Label label;
    @FXML
    private Button kundenListe;
    @FXML
    private Button verfuegbareProdukte;
    @FXML
    private Button verfuegbareErsatzteile;


    @FXML
    private void goToClientSpace(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(Application.class.getResource("view/clients/user_clients_view.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("assets/icon.png")));
        stage.setTitle("Vowerk");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToProductSpace(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(Application.class.getResource("view/products/user_products_view.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("assets/icon.png")));
        stage.setResizable(false);
        stage.setTitle("Vorwerk");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToCalender(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(Application.class.getResource("view/meets/user_clients_meets_calendar_view.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("assets/icon.png")));
        stage.setTitle("Vorwerk");
        stage.setScene(scene);
        stage.show();
    }


}

