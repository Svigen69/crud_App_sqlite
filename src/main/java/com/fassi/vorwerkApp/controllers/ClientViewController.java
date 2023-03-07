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
    //Text input
    @FXML
    private TextField clientFirstName;
    @FXML
    private TextField clientLastName;
    @FXML
    private TextField clientAddress;
    @FXML
    private TextField clientPhoneNumber;
    @FXML
    private TextField clientEmail;

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
    void addClient(ActionEvent event) {
        Client client = new Client((clientFirstName.getText()),
                (clientLastName.getText()),
                (clientAddress.getText()),
                (clientPhoneNumber.getText()),
                clientEmail.getText());

        ClientRepository clientRepository = new ClientRepository();
        try {
            clientRepository.addOne(client);
            ObservableList<Client> clients = tableView.getItems();
            clients.add(client);
            tableView.setItems(clients);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void removeClient(ActionEvent event) {
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
    void updateClient(ActionEvent event) {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Client client = this.tableView.getSelectionModel().getSelectedItem();
            

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
        stage.setResizable(false);
        stage.setTitle("HandelsvertreterApp");
        stage.setScene(scene);
        stage.show();
    }
}

