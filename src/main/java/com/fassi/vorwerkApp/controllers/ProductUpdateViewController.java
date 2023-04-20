package com.fassi.vorwerkApp.controllers;

import com.fassi.vorwerkApp.core.DialogResult;
import com.fassi.vorwerkApp.enumerations.EProductCategorie;
import com.fassi.vorwerkApp.enumerations.EProductType;
import com.fassi.vorwerkApp.models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductUpdateViewController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.productName.setText(this.product.getProductName());
        this.productPrice.setText(String.valueOf(this.product.getProductPrice()));
        this.productAvailability.setText(String.valueOf(this.product.getProductAvailability()));
    }

    private Product product;
    private DialogResult<Product> dialogResult;
    @FXML
    private ComboBox productType;
    @FXML
    private ComboBox productCategorie;
    @FXML
    private TextField productName;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField productAvailability;

    public ProductUpdateViewController(DialogResult<Product> dialogResult, Product product) {
        this.product = product;
        this.dialogResult = dialogResult;
    }

    @FXML
    void onAccept(ActionEvent event) {
        Product product = new Product((productName.getText()),
                Double.parseDouble(productPrice.getText()),
                Integer.parseInt(productAvailability.getText()),
                this.productType.getSelectionModel().getSelectedIndex() > 0 ? EProductType.ErsatzTeil : EProductType.Produkt,
                this.productCategorie.getSelectionModel().getSelectedIndex() > 0 ? EProductCategorie.STAUBSAUGER : EProductCategorie.MIXER);

        this.dialogResult.onAccept(product);

    }

    @FXML
    void onCancel(ActionEvent event) {
        this.dialogResult.onCancel();
    }

}
