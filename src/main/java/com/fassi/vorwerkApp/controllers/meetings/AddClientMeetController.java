package com.fassi.vorwerkApp.controllers.meetings;

import com.fassi.vorwerkApp.core.DialogResult;
import com.fassi.vorwerkApp.enumerations.EMeetingType;
import com.fassi.vorwerkApp.enumerations.EProductCategory;
import com.fassi.vorwerkApp.enumerations.EProductType;
import com.fassi.vorwerkApp.models.Client;
import com.fassi.vorwerkApp.models.Meeting;
import com.fassi.vorwerkApp.models.Product;
import com.fassi.vorwerkApp.repositories.ProductRepository;
import com.fassi.vorwerkApp.utils.validators.utils.DateUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class AddClientMeetController implements Initializable {
    private Client client;
    @FXML
    private DatePicker meetingDate;
    @FXML
    private Text meetingDateValidation;
    @FXML
    private TextArea meetingNote;
    @FXML
    private ComboBox meetingType;

    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TableColumn<Product, Integer> productAvailabilityColumn;
    @FXML
    private TableColumn<Product, EProductCategory> productCategorieColumn;
    @FXML
    private TableColumn<Product, EProductType> productTypeColumn;

    private DialogResult<Meeting> dialogResult;

    public AddClientMeetController(Client client, DialogResult<Meeting> dialogResult) {
        this.client = client;
        this.dialogResult = dialogResult;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        productAvailabilityColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        productCategorieColumn.setCellValueFactory(new PropertyValueFactory<Product, EProductCategory>("category"));
        productTypeColumn.setCellValueFactory(new PropertyValueFactory<Product, EProductType>("type"));

        productNameColumn.setText("Name");
        productPriceColumn.setText("Preis");
        productAvailabilityColumn.setText("verfügbarkeit");
        productCategorieColumn.setText("Kategorie");
        productTypeColumn.setText("Typ");
        this.meetingDate.setValue(LocalDate.now());
        this.meetingType.getSelectionModel().selectFirst();
        this.loadProducts();

    }

    private void loadProducts() {
        try {
            ObservableList<Product> products = this.productsTableView.getItems();
            products.clear();
            products.addAll(new ProductRepository().getAll());
            this.productsTableView.setItems(products);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onAccept(ActionEvent event) {
        if (this.validate())
            this.dialogResult.onAccept(new Meeting(DateUtil.fromDate(this.meetingDate.getValue()).getTime(), this.client, this.productsTableView.getSelectionModel().getSelectedItem(), this.meetingType.getSelectionModel().getSelectedIndex() == 0 ? EMeetingType.TELEFONISCH : EMeetingType.VOR_ORT, this.meetingNote.getText()));
    }



    private boolean validate() {
        boolean isValid = true;
        if (DateUtil.fromDate(this.meetingDate.getValue()).getTime() <= new Date().getTime()) {
            this.meetingDateValidation.setText("Datum Ungültig ( Datum > heute)");
            isValid = false;
        } else this.meetingDateValidation.setText("");

        if (this.productsTableView.getSelectionModel().getSelectedIndex() < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fehler");
            alert.setHeaderText("kein Kunde ausgewählt!");
            alert.setContentText("kunde auswählen bitte ☺");
            alert.showAndWait();
            isValid = false;
        }
        return isValid;
    }

    @FXML
    void onCancel(ActionEvent event) {
        this.dialogResult.onCancel();
    }


}
