package com.fassi.vorwerkApp.controllers;

import com.fassi.vorwerkApp.core.DialogResult;
import com.fassi.vorwerkApp.models.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ClientAddViewController {
    private DialogResult<Client> dialogResult;
    public ClientAddViewController(DialogResult<Client> dialogResult){
        this.dialogResult = dialogResult;
    }
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
    void onCancel(ActionEvent event){
        this.dialogResult.onCancel();
    }

}
