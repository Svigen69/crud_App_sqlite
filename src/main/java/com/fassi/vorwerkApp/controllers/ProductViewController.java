package com.fassi.vorwerkApp.controllers;

import com.fassi.vorwerkApp.Application;
import com.fassi.vorwerkApp.models.Client;
import com.fassi.vorwerkApp.models.Product;
import com.fassi.vorwerkApp.repositories.ClientRepository;
import com.fassi.vorwerkApp.repositories.ProductRepository;
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

public class ProductViewController implements Initializable {

    @FXML
    private TableView<Product> tableView;
    @FXML
    private ComboBox productType;

    //Columns
    @FXML
    private TableColumn<Product, String> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TableColumn<Product, Integer> productAvailabilityColumn;
    @FXML
    private TableColumn<Product, String> productTypeColumn;

    //Text input
    @FXML
    private TextField productName;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField productAvailability;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("productPrice"));
        productAvailabilityColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productAvailability"));
        productTypeColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productType"));
        productNameColumn.setText("Name");
        productPriceColumn.setText("Preis");
        productAvailabilityColumn.setText("verfügbarkeit");
        productTypeColumn.setText("Produktkategorie");
        try {
            ObservableList<Product> products = tableView.getItems();
            products.addAll(new ProductRepository().getAll());
            tableView.setItems(products);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addProduct(ActionEvent event) {
        Product product = new Product((productName.getText()),
                Double.parseDouble(productPrice.getText()),
                Integer.parseInt(productAvailability.getText()),
                productType.getValue().toString());

        ObservableList<Product> products = tableView.getItems();
        products.add(product);
        ProductRepository productRepository = new ProductRepository();
        try {
            productRepository.addOne(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        tableView.setItems(products);
    }
    @FXML
    void removeProduct(ActionEvent event) {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();
        Product product = this.tableView.getSelectionModel().getSelectedItem();
        //if (selectedID >= 0)
        ProductRepository productRepository= new ProductRepository();

        try {
            productRepository.deleteOne(product.getProductId());
            tableView.getItems().remove(selectedID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void updateProduct(ActionEvent event){

    }
    @FXML
    void goBack (ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(Application.class.getResource("view/Home_view.fxml"));
        Scene scene = new Scene(root, 600, 450);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();;
        stage.setResizable(false);
        stage.setTitle("HandelsvertreterApp");
        stage.setScene(scene);
        stage.show();
    }

}