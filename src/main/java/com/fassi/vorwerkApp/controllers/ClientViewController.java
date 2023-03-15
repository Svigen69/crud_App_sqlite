package com.fassi.vorwerkApp.controllers;

import com.fassi.vorwerkApp.Application;
import com.fassi.vorwerkApp.core.DialogResult;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientViewController implements Initializable {

    private Client updtingClient = null;

    @FXML
    private TableView<Client> tableView;

    @FXML
    private TableColumn<Client, String> clientIdColumn;
    @FXML
    private TableColumn<Client, String> clientFirstNameColumn;
    @FXML
    private TableColumn<Client, String> clientLastNameColumn;
    @FXML
    private TableColumn<Client, String> clientAddressColumn;
    @FXML
    private TableColumn<Client, String> clientPhoneNumberColumn;
    @FXML
    private TableColumn<Client, String> clientEmailColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        clientFirstNameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("firstName"));
        clientLastNameColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("lastName"));
        clientAddressColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("address"));
        clientPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("phoneNumber"));
        clientEmailColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));

        clientFirstNameColumn.setText("Vorname");
        clientLastNameColumn.setText("Name");
        clientAddressColumn.setText("Adresse");
        clientPhoneNumberColumn.setText("Telefonnummer");
        clientEmailColumn.setText("Email");
        try {
            ObservableList<Client> clients = tableView.getItems();
            clients.addAll(new ClientRepository().getAll());
            tableView.setItems(clients);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void addRequest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("view/ClientAdd_view.fxml"));
        Stage stage = new Stage();
        loader.setController(new ClientAddViewController(new DialogResult<Client>() {
            @Override
            public void onAccept(Client result) {
                ClientRepository clientRepository = new ClientRepository();
                try {
                    clientRepository.addOne(result);
                    ObservableList<Client> clients = tableView.getItems();
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
        stage.setTitle("Kunde Hinzufügen");

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void updateRequest(ActionEvent event) throws IOException {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Client client = this.tableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("view/ClientUpdate_view.fxml"));
            Stage stage = new Stage();
            loader.setController(
                    new ClientUpdateViewController(
                            new DialogResult<Client>() {
                                @Override
                                public void onAccept(Client result) {
                                    ClientRepository clientRepository = new ClientRepository();
                                    try {
                                        clientRepository.updateOne(client.getClienId(), result);
                                        ObservableList<Client> clients = tableView.getItems();
                                        List<Client> updateClients = clients.stream().map(e -> e.getClienId() == client.getClienId() ? result : e).toList();
                                        clients.clear();
                                        clients.addAll(updateClients);
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
                            }, client)
            );
            Parent root = loader.load();
            Scene scene = new Scene(root, 410, 400);
            stage.setResizable(false);
            stage.setTitle("Kunde Hinzufügen");
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
            Client client = this.tableView.getSelectionModel().getSelectedItem();
            ClientRepository clientRepository = new ClientRepository();
            try {
                clientRepository.deleteOne(client.getClienId());
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
        Scene scene = new Scene(root, 400, 450);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.setTitle("HandelsvertreterApp");
        stage.setScene(scene);
        stage.show();
    }
}

