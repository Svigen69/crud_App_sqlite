package com.fassi.vorwerkApp.controllers.products;

import com.fassi.vorwerkApp.Application;
import com.fassi.vorwerkApp.core.DialogResult;
import com.fassi.vorwerkApp.enumerations.EProductCategory;
import com.fassi.vorwerkApp.enumerations.EProductType;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {

    @FXML
    private TableView<Product> tableView;

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
    private TableColumn<Product, EProductCategory> productCategorieColumn;
    @FXML
    private TableColumn<Product, EProductType> productTypeColumn;
    @FXML
    private TextField searchTextField;

    //Text input

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
        this.loadProducts();

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> search(newValue));
    }

    private void loadProducts() {
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
    void onRefresh(ActionEvent event) {
        this.loadProducts();
    }

    @FXML
    void addRequest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("view/products/user_add_product_view.fxml"));
        Stage stage = new Stage();
        loader.setController(new AddProductController(new DialogResult<Product>() {
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
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Vowerk");
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("assets/icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void updateRequest(ActionEvent event) throws IOException {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Product product = this.tableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("view/products/user_update_product_view.fxml"));
            Stage stage = new Stage();
            loader.setController(
                    new UpdateProductController(
                            new DialogResult<Product>() {
                                @Override
                                public void onAccept(Product result) {
                                    ProductRepository productRepository = new ProductRepository();

                                    try {
                                        productRepository.updateOne(product.getId(), result);
                                        ObservableList<Product> products = tableView.getItems();
                                        List<Product> updateProducts = products.stream().map(e -> e.getId() == product.getId() ? result : e).toList();
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
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Vorwerk");
            stage.getIcons().add(new Image(Application.class.getResourceAsStream("assets/icon.png")));
            stage.setScene(scene);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fehler");
            alert.setHeaderText("kein Produkt ausgewählt!");
            alert.setContentText("Produkt auswählen bitte ☺");
            alert.showAndWait();
        }

    }

    @FXML
    void removeRequest(ActionEvent event) {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            if (this.confirm("Product delete", "Vorwerk", "Are you sure ?")) {
                Product product = this.tableView.getSelectionModel().getSelectedItem();
                ProductRepository productRepository = new ProductRepository();
                try {
                    productRepository.deleteOne(product.getId());
                    tableView.getItems().remove(selectedID);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fehler");
            alert.setHeaderText("kein Produkt ausgewählt!");
            alert.setContentText("Produkt auswählen bitte ☺");
            alert.showAndWait();
        }
    }

    private void search(String pattern) {
        if (pattern.length() == 0)
            this.loadProducts();
        else try {
            ObservableList<Product> clients = tableView.getItems();
            clients.clear();
            clients.addAll(new ProductRepository().search(pattern));
            tableView.setItems(clients);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean confirm(String header, String title, String question) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(question);
        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);
    }


    @FXML
    void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(Application.class.getResource("view/app_home_view.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setTitle("Vorwerk");
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("assets/icon.png")));
        stage.setScene(scene);
        stage.show();
    }
}





