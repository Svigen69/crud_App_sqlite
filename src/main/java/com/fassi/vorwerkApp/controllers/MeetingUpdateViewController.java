package com.fassi.vorwerkApp.controllers;

import com.fassi.vorwerkApp.core.DialogResult;
import com.fassi.vorwerkApp.enumerations.EMeetingType;
import com.fassi.vorwerkApp.models.Meeting;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

public class MeetingUpdateViewController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.clientFullName.setText(this.meeting.getClient().getFullName());

    }

    private Meeting meeting;
    private DialogResult<Meeting> dialogResult;
    @FXML
    private ComboBox productType;
    @FXML
    private TextField clientFullName;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField productAvailability;

    public MeetingUpdateViewController(DialogResult<Meeting> dialogResult, Meeting meeting) {
        this.meeting = meeting;
        this.dialogResult = dialogResult;
    }

    /*@FXML
    void onAccept(ActionEvent event) {
        Meeting meeting = new Meeting(
                productType.getSelectionModel().getSelectedIndex()>0? EMeetingType.VOR_ORT:EMeetingType.TELEFONISCH);

        this.dialogResult.onAccept(meeting);
    }
*/
    @FXML
    void onCancel(ActionEvent event) {
        this.dialogResult.onCancel();
    }

}
