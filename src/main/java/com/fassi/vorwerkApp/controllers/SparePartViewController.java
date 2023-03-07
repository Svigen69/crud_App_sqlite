package com.fassi.vorwerkApp.controllers;

import com.fassi.vorwerkApp.Application;
import com.fassi.vorwerkApp.models.Client;
import com.fassi.vorwerkApp.models.Product;
import com.fassi.vorwerkApp.models.SparePart;
import com.fassi.vorwerkApp.repositories.ClientRepository;
import com.fassi.vorwerkApp.repositories.ProductRepository;
import com.fassi.vorwerkApp.repositories.SparePartRepository;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SparePartViewController implements Initializable {

    //Table
    @FXML
    private TableView<SparePart> tableView;

    //Columns
    @FXML
    private TableColumn<Client, String> sparePartIdColumn;

    @FXML
    private TableColumn<SparePart, String> sparePartNameColumn;

    @FXML
    private TableColumn<SparePart, Integer> sparePartPriceColumn;

    @FXML
    private TableColumn<SparePart, Integer> sparePartAvailabilityColumn;
    @FXML
    private TableColumn<SparePart, String> sparePartTypeColumn;

    //Text input
    @FXML
    private TextField sparePartName;

    @FXML
    private TextField sparePartPrice;

    @FXML
    private TextField sparePartAvailability;
    @FXML
    private ComboBox sparePartType;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sparePartNameColumn.setCellValueFactory(new PropertyValueFactory<SparePart, String>("sparePartName"));
        sparePartPriceColumn.setCellValueFactory(new PropertyValueFactory<SparePart, Integer>("sparePartPrice"));
        sparePartAvailabilityColumn.setCellValueFactory(new PropertyValueFactory<SparePart, Integer>("sparePartAvailability"));
        sparePartTypeColumn.setCellValueFactory(new PropertyValueFactory<SparePart, String>("sparePartType"));
        sparePartNameColumn.setText("Name");
        sparePartPriceColumn.setText("Preis");
        sparePartAvailabilityColumn.setText("Verfügbarkeit");
        sparePartTypeColumn.setText("Produkt");
        try {
            ObservableList<SparePart> spareParts = tableView.getItems();
            spareParts.addAll(new SparePartRepository().getAll());
            tableView.setItems(spareParts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //Submit button
    @FXML
    void addSparepart(ActionEvent event) {
        SparePart sparePart = new SparePart(sparePartName.getText(),
                Double.parseDouble(sparePartPrice.getText()),
                Integer.parseInt(sparePartAvailability.getText()),
                sparePartType.getValue().toString());

        ObservableList<SparePart> spareParts = tableView.getItems();
        spareParts.add(sparePart);
        SparePartRepository sparePartRepository = new SparePartRepository();
        try {
            sparePartRepository.addOne(sparePart);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        tableView.setItems(spareParts);

    }

    @FXML
    void removeSparePart(ActionEvent event) {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();
        SparePart sparePart = this.tableView.getSelectionModel().getSelectedItem();
        // if (selectedID >= 0)
        SparePartRepository sparePartRepository = new SparePartRepository();

        try {
            sparePartRepository.deleteOne(sparePart.getSparePartId());
            tableView.getItems().remove(selectedID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(Application.class.getResource("view/Home_view.fxml"));
        Scene scene = new Scene(root, 600, 450);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ;
        stage.setResizable(false);
        stage.setTitle("HandelsvertreterApp");
        stage.setScene(scene);
        stage.show();
    }
}