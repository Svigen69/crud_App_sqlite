package com.fassi.vorwerkApp.controllers.clients;

import com.fassi.vorwerkApp.core.DialogResult;
import com.fassi.vorwerkApp.models.Client;
import com.fassi.vorwerkApp.utils.validators.utils.ValidatorsUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateClientController implements Initializable {


    private Client client;
    private DialogResult<Client> dialogResult;

    @FXML
    private TextField clientFirstName;
    @FXML
    private Text clientFirstNameValidation;
    @FXML
    private TextField clientLastName;
    @FXML
    private Text clientLastNameValidation;
    @FXML
    private TextField clientAddress;
    @FXML
    private Text clientAddressValidation;
    @FXML
    private TextField clientPhoneNumber;
    @FXML
    private Text clientPhoneNumberValidation;
    @FXML
    private TextField clientEmail;
    @FXML
    private Text clientEmailValidation;

    public UpdateClientController(DialogResult<Client> dialogResult, Client client) {
        this.client = client;
        this.dialogResult = dialogResult;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.clientFirstName.setText(this.client.getFirstName());
        this.clientLastName.setText(this.client.getLastName());
        this.clientAddress.setText(this.client.getAddress());
        this.clientFirstName.setText(this.client.getFirstName());
        this.clientPhoneNumber.setText(this.client.getPhoneNumber());
        this.clientEmail.setText(this.client.getEmail());
    }



    private boolean validate() {
        boolean isValid = true;
        if (!ValidatorsUtil.isFirstName(this.clientFirstName.getText().trim())) {
            isValid = false;
            this.clientFirstNameValidation.setText("Invalid first name");
        } else this.clientFirstNameValidation.setText("");

        if (!ValidatorsUtil.isLastName(this.clientLastName.getText().trim())) {
            isValid = false;
            this.clientLastNameValidation.setText("Invalid last name");
        } else this.clientLastNameValidation.setText("");

        if (!ValidatorsUtil.isAddress(this.clientAddress.getText().trim())) {
            isValid = false;
            this.clientAddressValidation.setText("Invalid address");
        } else this.clientAddressValidation.setText("");

        if (!ValidatorsUtil.isPhoneNumber(this.clientPhoneNumber.getText().trim())) {
            isValid = false;
            this.clientPhoneNumberValidation.setText("Invalid Phone");
        } else this.clientPhoneNumberValidation.setText("");

        if (!ValidatorsUtil.isEmail(this.clientEmail.getText().trim())) {
            isValid = false;
            this.clientEmailValidation.setText("Invalid email");
        } else this.clientEmailValidation.setText("");

        return isValid;
    }

    @FXML
    void onAccept(ActionEvent event) {
        if (this.validate())
            this.dialogResult.onAccept(new Client(clientFirstName.getText(), clientLastName.getText(), clientAddress.getText(), clientPhoneNumber.getText(), clientEmail.getText()));
    }

    @FXML
    void onCancel(ActionEvent event) {
        this.dialogResult.onCancel();
    }
}
