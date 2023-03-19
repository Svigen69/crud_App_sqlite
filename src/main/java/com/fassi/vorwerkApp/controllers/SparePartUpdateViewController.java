package com.fassi.vorwerkApp.controllers;


import com.fassi.vorwerkApp.core.DialogResult;
import com.fassi.vorwerkApp.models.SparePart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

public class SparePartUpdateViewController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private SparePart sparePart;
    private DialogResult<SparePart> dialogResult;
    @FXML
    private ComboBox sparePartType;
    @FXML
    private TextField sparePartName;
    @FXML
    private TextField sparePartPrice;
    @FXML
    private TextField sparePartAvailability;

    public SparePartUpdateViewController(DialogResult<SparePart> dialogResult, SparePart sparePart) {
        this.sparePart = sparePart;
        this.dialogResult = dialogResult;
    }

    @FXML
    void onAccept(ActionEvent event) {
        SparePart sparePart= new SparePart((sparePartName.getText()),
                Double.parseDouble(sparePartPrice.getText()),
                Integer.parseInt(sparePartAvailability.getText()),
                (sparePartType.getSelectionModel().getSelectedItem().toString()));

        this.dialogResult.onAccept(sparePart);
    }

    @FXML
    void onCancel(ActionEvent event) {
        this.dialogResult.onCancel();
    }

}
