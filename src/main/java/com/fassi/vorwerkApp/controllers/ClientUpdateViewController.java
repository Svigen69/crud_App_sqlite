package com.fassi.vorwerkApp.controllers;

import com.fassi.vorwerkApp.core.DialogResult;
import com.fassi.vorwerkApp.models.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientUpdateViewController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.clientFirstName.setText(this.client.getFirstName());
        this.clientLastName.setText(this.client.getLastName());
        this.clientAddress.setText(this.client.getAddress());
        this.clientFirstName.setText(this.client.getFirstName());
        this.clientPhoneNumber.setText(this.client.getPhoneNumber());
        this.clientEmail.setText(this.client.getEmail());
    }

    private Client client;
    private DialogResult<Client> dialogResult;

    @FXML
    private TextField clientFirstName;
    @FXML
    private TextField clientLastName;
    @FXML
    private TextField clientAddress;
    @FXML
    private TextField clientPhoneNumber;
    @FXML
    private TextField clientEmail;

    public ClientUpdateViewController(DialogResult<Client> dialogResult, Client client) {
        this.client = client;
        this.dialogResult = dialogResult;
    }
    @FXML
    void onAccept(ActionEvent event) {
        Client client = new Client((clientFirstName.getText()),
                (clientLastName.getText()),
                (clientAddress.getText()),
                (clientPhoneNumber.getText()),
                clientEmail.getText());
        this.dialogResult.onAccept(client);
    }
    @FXML
    void onCancel(ActionEvent event) {
        this.dialogResult.onCancel();
    }
}
