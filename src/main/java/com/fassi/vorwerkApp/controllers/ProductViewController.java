package com.fassi.vorwerkApp.controllers;

import com.fassi.vorwerkApp.Application;
import com.fassi.vorwerkApp.core.DialogResult;
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
import javafx.scene.control.*;
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
    void onRefresh(ActionEvent event) {
        try {
            ObservableList<Product> products = tableView.getItems();
            products.clear();
            products.addAll(new ProductRepository().getAll());
            tableView.setItems(products);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addRequest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("view/ProductAdd_view.fxml"));
        Stage stage = new Stage();
        loader.setController(new ProductAddViewController(new DialogResult<Product>() {
            @Override
            public void onAccept(Product result) {
                ProductRepository productRepository = new ProductRepository();
                try {
                    productRepository.addOne(result);
                    ObservableList<Product> clients = tableView.getItems();
                    clients.add(result);
                    tableView.setItems(clients);
                    stage.close();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onCancel() {
                stage.close();
            }
        }));
        Parent root = loader.load();
        Scene scene = new Scene(root, 410, 400);
        stage.setResizable(false);
        stage.setTitle("Vowerk");

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void updateRequest(ActionEvent event) throws IOException {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Product product = this.tableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("view/ProductUpdate_view.fxml"));
            Stage stage = new Stage();
            loader.setController(
                    new ProductUpdateViewController(
                            new DialogResult<Product>() {
                                @Override
                                public void onAccept(Product result) {
                                    ProductRepository productRepository = new ProductRepository();

                                    try {
                                        productRepository.updateOne(product.getProductId(), result);
                                        ObservableList<Product> products = tableView.getItems();
                                        List<Product> updateProducts = products.stream().map(e -> e.getProductId() == product.getProductId() ? result : e).toList();
                                        products.clear();
                                        products.addAll(updateProducts);
                                        tableView.setItems(products);
                                        stage.close();

                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }

                                @Override
                                public void onCancel() {
                                    stage.close();
                                }
                            }, product)
            );
            Parent root = loader.load();
            Scene scene = new Scene(root, 410, 400);
            stage.setResizable(false);
            stage.setTitle("Vorwerk");
            stage.setScene(scene);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fehler");
            alert.setHeaderText("kein Kunde ausgewählt!");
            alert.setContentText("kunde auswählen bitte ☺");
            alert.showAndWait();
        }

    }

    @FXML
    void removeRequest(ActionEvent event) {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Product product = this.tableView.getSelectionModel().getSelectedItem();
            ClientRepository clientRepository = new ClientRepository();
            try {
                clientRepository.deleteOne(product.getProductId());
                tableView.getItems().remove(selectedID);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fehler");
            alert.setHeaderText("kein Kunde ausgewählt!");
            alert.setContentText("kunde auswählen bitte ☺");
            alert.showAndWait();
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
        stage.setTitle("Vorwerk");
        stage.setScene(scene);
        stage.show();
    }









/*
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
        stage.setTitle("Vorwerk");
        stage.setScene(scene);
        stage.show();
    }
*/
}