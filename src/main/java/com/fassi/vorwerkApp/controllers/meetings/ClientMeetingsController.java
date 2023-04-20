package com.fassi.vorwerkApp.controllers.meetings;

import com.fassi.vorwerkApp.Application;
import com.fassi.vorwerkApp.controllers.clients.AddClientController;
import com.fassi.vorwerkApp.core.DialogResult;
import com.fassi.vorwerkApp.models.Client;
import com.fassi.vorwerkApp.models.Meeting;
import com.fassi.vorwerkApp.repositories.ClientRepository;
import com.fassi.vorwerkApp.repositories.MeetingRepository;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ClientMeetingsController implements Initializable {

    private Client client;

    @FXML
    private Text clientFullNameText;
    @FXML
    private Text clientActualMeetsCountText;
    @FXML
    private Text clientTotalMeetsCountText;
    private List<Meeting> actualMeets = new ArrayList<>();
    private List<Meeting> oldMeets = new ArrayList<>();
    private List<Meeting> clientMeets = new ArrayList<>();


    @FXML
    private TableColumn<Meeting,String> oldMeetClientNameColumn;
    @FXML
    private TableColumn<Meeting,String> oldMeetNoteColumn;
    @FXML
    private TableColumn<Meeting,String> oldMeetDateColumn;
    @FXML
    private TableColumn<Meeting,String> oldMeetProductNameColumn;

    @FXML
    private TableColumn<Meeting,String> actualMeetClientNameColumn;
    @FXML
    private TableColumn<Meeting,String> actualMeetNoteColumn;
    @FXML
    private TableColumn<Meeting,String> actualMeetDateColumn;
    @FXML
    private TableColumn<Meeting,String> actualMeetProductNameColumn;
    @FXML
    private TableColumn<Meeting,String> oldMeetProductTypeColumn;
    @FXML
    private TableColumn<Meeting,String> actualMeetProductTypeColumn;

    @FXML
    TableView<Meeting> actualClientMeetsTableView;
    @FXML
    TableView<Meeting> oldClientMeetsTableView;

    public ClientMeetingsController(Client client) {
        this.client = client;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        this.oldMeetClientNameColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("clientFullName"));
        this.oldMeetNoteColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("note"));
        this.oldMeetDateColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("stringDate"));
        this.oldMeetProductNameColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("productName"));
        this.oldMeetProductTypeColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("type"));

        this.actualMeetClientNameColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("clientFullName"));
        this.actualMeetNoteColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("note"));
        this.actualMeetDateColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("stringDate"));
        this.actualMeetProductNameColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("productName"));
        this.actualMeetProductTypeColumn.setCellValueFactory(new PropertyValueFactory<Meeting, String>("type"));

        this.loadMeets();
    }

    private void loadMeets() {
        try {
            this.clientMeets = new MeetingRepository().getAllByClient(client);
            System.out.println("Size  : " + clientMeets.size());
            this.actualMeets = this.clientMeets.stream().filter(m -> m.getDate() > new Date().getTime()).collect(Collectors.toList());
            this.oldMeets = this.clientMeets.stream().filter(m -> m.getDate() <= new Date().getTime()).collect(Collectors.toList());
            this.fillData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void fillData() {
        ObservableList<Meeting> actualMeetings = this.actualClientMeetsTableView.getItems();
        actualMeetings.clear();
        actualMeetings.addAll(this.actualMeets);
        actualClientMeetsTableView.setItems(actualMeetings);

        ObservableList<Meeting> oldMeetings = this.oldClientMeetsTableView.getItems();
        oldMeetings.clear();
        oldMeetings.addAll(this.oldMeets);
        actualClientMeetsTableView.setItems(actualMeetings);

        this.clientFullNameText.setText(client.getFullName());
        this.clientTotalMeetsCountText.setText(String.valueOf(clientMeets.size()));
        this.clientActualMeetsCountText.setText(String.valueOf(actualMeets.size()));
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(Application.class.getResource("view/clients/user_clients_view.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("assets/icon.png")));
        stage.setResizable(false);
        stage.setTitle("Vorwerk");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onAddMeet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("view/meets/user_client_assign_meeting_view.fxml"));
        Stage stage = new Stage();
        loader.setController(new AddClientMeetController(client, new DialogResult<Meeting>() {
            @Override
            public void onAccept(Meeting result) {
                try {
                    new MeetingRepository().addOne(result);
                    loadMeets();
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
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("assets/icon.png")));
        stage.setTitle("Vowerk");

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onUpdateMeet(ActionEvent event) throws IOException {
        if (this.actualClientMeetsTableView.getSelectionModel().getSelectedIndex() >= 0) {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("view/meets/user_client_assign_meeting_view.fxml"));
            Stage stage = new Stage();
            loader.setController(new UpdateClientMeetController(this.actualClientMeetsTableView.getSelectionModel().getSelectedItem(), new DialogResult<Meeting>() {
                @Override
                public void onAccept(Meeting result) {
                    try {
                        new MeetingRepository().updateOne(actualClientMeetsTableView.getSelectionModel().getSelectedItem().getId(),result);
                        loadMeets();
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
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Vorwerk");
            alert.setHeaderText("Meet selection");
            alert.setContentText("Actual meet no selected for update !");
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
    void onCancelMeet(ActionEvent event) throws Exception {
       if(this.confirm("Delete meet","Vorwerk","Are u sure ?")) {
            new MeetingRepository().deleteOne(actualClientMeetsTableView.getSelectionModel().getSelectedItem().getId());
            loadMeets();
        }
    }


}
