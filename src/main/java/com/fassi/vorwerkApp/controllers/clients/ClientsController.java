package com.fassi.vorwerkApp.controllers.clients;

import com.fassi.vorwerkApp.Application;
import com.fassi.vorwerkApp.controllers.meetings.ClientMeetingsController;
import com.fassi.vorwerkApp.core.DialogResult;
import com.fassi.vorwerkApp.models.Client;
import com.fassi.vorwerkApp.repositories.ClientRepository;
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

public class ClientsController implements Initializable {
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<Client> tableView;
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

        this.loadClients();
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
            search(newValue);
        });

    }

    private void loadClients() {
        try {
            ObservableList<Client> clients = tableView.getItems();
            clients.clear();
            clients.addAll(new ClientRepository().getAll());
            tableView.setItems(clients);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void search(String pattern) {
        if (pattern.length() == 0)
            loadClients();
        else try {
            ObservableList<Client> clients = tableView.getItems();
            clients.clear();
            clients.addAll(new ClientRepository().search(pattern));
            tableView.setItems(clients);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addRequest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("view/clients/user_add_client_view.fxml"));
        Stage stage = new Stage();
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("assets/icon.png")));

        loader.setController(new AddClientController(new DialogResult<Client>() {
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
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Vowerk");

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void updateRequest(ActionEvent event) throws IOException {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            Client client = this.tableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("view/clients/user_client_update_view.fxml"));
            Stage stage = new Stage();
            loader.setController(
                    new UpdateClientController(
                            new DialogResult<Client>() {
                                @Override
                                public void onAccept(Client result) {
                                    ClientRepository clientRepository = new ClientRepository();
                                    try {
                                        clientRepository.updateOne(client.getId(), result);
                                        ObservableList<Client> clients = tableView.getItems();
                                        List<Client> updateClients = clients.stream().map(e -> e.getId() == client.getId() ? result : e).toList();
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
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setTitle("Vorwerk");
            stage.getIcons().add(new Image(Application.class.getResourceAsStream("assets/icon.png")));

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

    private boolean confirm(String header, String title, String question) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(question);
        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);
    }

    @FXML
    void removeRequest(ActionEvent event) {
        int selectedID = tableView.getSelectionModel().getSelectedIndex();

        if (selectedID >= 0) {
            if (this.confirm("Client delete confirm", "Vorwerk clients", "are you sure ?")) {
                Client client = this.tableView.getSelectionModel().getSelectedItem();
                ClientRepository clientRepository = new ClientRepository();
                try {
                    clientRepository.deleteOne(client.getId());
                    tableView.getItems().remove(selectedID);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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
    void showClientMeetings(ActionEvent event) throws IOException {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("view/meets/user_client_meets_view.fxml"));
            loader.setController(new ClientMeetingsController(this.tableView.getSelectionModel().getSelectedItem()));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("Vorwerk");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Vorwerk");
            alert.setHeaderText("kein Kunde ausgewählt!");
            alert.setContentText("kunde auswählen bitte ☺");
            alert.showAndWait();
        }

    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(Application.class.getResource("view/app_home_view.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setResizable(false);
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("assets/icon.png")));
        stage.setTitle("Vorwerk");
        stage.setScene(scene);
        stage.show();
    }


}

