package com.fassi.vorwerkApp.controllers;


import com.fassi.vorwerkApp.core.DialogResult;
import com.fassi.vorwerkApp.models.Product;
import com.fassi.vorwerkApp.models.SparePart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SparePartAddViewController {
    private DialogResult<SparePart> dialogResult;

    public SparePartAddViewController(DialogResult<SparePart> dialogResult) {
        this.dialogResult = dialogResult;
    }
    @FXML
    private TextField sparePartName;
    @FXML
    private TextField sparePartPrice;
    @FXML
    private TextField sparePartAvailability;
    @FXML
    private TextField sparePartType;

    @FXML
    void onAccept(ActionEvent event) {
        SparePart sparePart = new SparePart((sparePartName.getText()),
                (Double.parseDouble(sparePartPrice.getText())),
                (Integer.parseInt(sparePartAvailability.getText())),
                (sparePartType.getText()));
        this.dialogResult.onAccept(sparePart);
    }
    @FXML
    void onCancel(ActionEvent event){
        this.dialogResult.onCancel();
    }


}