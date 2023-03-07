package com.fassi.vorwerkApp.controllers;

import com.fassi.vorwerkApp.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private Label isConnected;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void Login(ActionEvent event) throws Exception {
    try {
        if (username.getText().equals("Fassi") && password.getText().equals("123456")){
            isConnected.setText("username und passwort sind richtig");
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(Application.class.getResource("view/Home_view.fxml"));
            Scene scene = new Scene(root, 500, 450);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();;
            stage.setResizable(false);
            stage.setTitle("HandelsvertreterApp");
            stage.setScene(scene);
            stage.show();
        } else{
            isConnected.setText("username und passwort sind falsch");
        }
    } catch (IOException e) {
        isConnected.setText("username und passwort sind falsch");
        e.printStackTrace();
    }
}
}



