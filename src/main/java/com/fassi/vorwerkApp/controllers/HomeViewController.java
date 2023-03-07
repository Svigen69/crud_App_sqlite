package com.fassi.vorwerkApp.controllers;

import com.fassi.vorwerkApp.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        Parent root = loader.load(Application.class.getResource("view/Client_view.fxml"));
        Scene scene = new Scene(root, 810, 430);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setTitle("HomeView");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToProductSpace(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(Application.class.getResource("view/Product_view.fxml"));
        Scene scene = new Scene(root, 610, 440);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setTitle("HomeView");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToSpareParts(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(Application.class.getResource("view/SparePart_view.fxml"));
        Scene scene = new Scene(root, 645, 450);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setTitle("HomeView");
        stage.setScene(scene);
        stage.show();
    }

}

