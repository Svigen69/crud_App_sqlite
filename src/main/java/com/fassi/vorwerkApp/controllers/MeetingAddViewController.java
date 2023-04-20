package com.fassi.vorwerkApp.controllers;

import com.fassi.vorwerkApp.core.DialogResult;
import com.fassi.vorwerkApp.enumerations.EMeetingType;
import com.fassi.vorwerkApp.models.Meeting;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.Date;

public class MeetingAddViewController {
    private DialogResult<Meeting> dialogResult;

    public MeetingAddViewController(DialogResult<Meeting> dialogResult){this.dialogResult = dialogResult;}
    private String clientFullName;
    @FXML
    private Date meetingDate;
    @FXML
    private String meetingNote;

    @FXML
    private ComboBox meetingType;
    @FXML


   /* @FXML
    void onAccept(ActionEvent event) {
        Meeting meeting = new Meeting((clientFullName.,
                ((meetingDate.getDate()),
                ((meetingNote.getText())),
                ((meetingOrder.get)));
        this.dialogResult.onAccept(meeting);
    }*/
    @FXML
    void onCancel(ActionEvent event){
        this.dialogResult.onCancel();
    }


}
