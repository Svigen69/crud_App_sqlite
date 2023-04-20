package com.fassi.vorwerkApp.controllers.clients;

import com.fassi.vorwerkApp.core.DialogResult;
import com.fassi.vorwerkApp.models.Client;
import com.fassi.vorwerkApp.utils.validators.utils.ValidatorsUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.text.Text;

public class AddClientController {
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

    public AddClientController(DialogResult<Client> dialogResult) {
        this.dialogResult = dialogResult;
    }

    private boolean validate() {
        boolean isValid = true;
        if (!ValidatorsUtil.isFirstName(this.clientFirstName.getText().trim())) {
            isValid = false;
            this.clientFirstNameValidation.setText("Vorname Ungültig");
        } else this.clientFirstNameValidation.setText("");

        if (!ValidatorsUtil.isLastName(this.clientLastName.getText().trim())) {
            isValid = false;
            this.clientLastNameValidation.setText("Name Ungüultig");
        } else this.clientLastNameValidation.setText("");

        if (!ValidatorsUtil.isAddress(this.clientAddress.getText().trim())) {
            isValid = false;
            this.clientAddressValidation.setText("Adresse Ungültig");
        } else this.clientAddressValidation.setText("");

        if (!ValidatorsUtil.isPhoneNumber(this.clientPhoneNumber.getText().trim())) {
            isValid = false;
            this.clientPhoneNumberValidation.setText("Telefonnummer Ungültig");
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
            this.dialogResult.onAccept(new Client(this.clientFirstName.getText(), this.clientLastName.getText(), this.clientAddress.getText(), this.clientPhoneNumber.getText(), this.clientEmail.getText()));
    }

    @FXML
    void onCancel(ActionEvent event) {
        this.dialogResult.onCancel();
    }

}
