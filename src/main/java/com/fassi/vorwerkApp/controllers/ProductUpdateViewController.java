package com.fassi.vorwerkApp.controllers;

import com.fassi.vorwerkApp.core.DialogResult;
import com.fassi.vorwerkApp.models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

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
                (productType.getSelectionModel().getSelectedItem().toString()));

        this.dialogResult.onAccept(product);
    }

    @FXML
    void onCancel(ActionEvent event) {
        this.dialogResult.onCancel();
    }

}
