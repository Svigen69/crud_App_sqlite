package com.fassi.vorwerkApp.controllers.meetings;

import com.fassi.vorwerkApp.Application;
import com.fassi.vorwerkApp.models.Meeting;
import com.fassi.vorwerkApp.repositories.MeetingRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.*;

public class CalendarViewController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawMeeting();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawMeeting();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawMeeting();
    }

    private void drawMeeting() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double MeetingWidth = calendar.getPrefWidth();
        double MeetingHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        //List of activities for a given month
        Map<Integer, List<Meeting>> MeetingActivityMap = getMeetingActivitiesMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (MeetingWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (MeetingHeight / 6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = -(rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        List<Meeting> MeetingActivities = MeetingActivityMap.get(currentDate);
                        if (MeetingActivities != null) {
                            createMeetingActivity(MeetingActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createMeetingActivity(List<Meeting> MeetingActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox MeetingActivityBox = new VBox();
        for (int k = 0; k < MeetingActivities.size(); k++) {
            if (k >= 2) {
                Text moreActivities = new Text("...");
                MeetingActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    //On ... click print all activities for given date
                    System.out.println(MeetingActivities);
                });
                break;
            }
            Text text = new Text(MeetingActivities.get(k).getClient().getFullName());
            MeetingActivityBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                //On Text clicked
                System.out.println(text.getText());
            });
        }
        MeetingActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        MeetingActivityBox.setMaxWidth(rectangleWidth * 0.8);
        MeetingActivityBox.setMaxHeight(rectangleHeight * 0.65);
        MeetingActivityBox.setStyle("-fx-background-color:Grey");
        stackPane.getChildren().add(MeetingActivityBox);
    }

    private Map<Integer, List<Meeting>> createMeetingMap(List<Meeting> MeetingActivities) {
        Map<Integer, List<Meeting>> MeetingActivityMap = new HashMap<>();

        for (Meeting activity : MeetingActivities) {
            Date activityDate = new Date(activity.getDate());
            if (!MeetingActivityMap.containsKey(activityDate.getMonth())) {
                MeetingActivityMap.put(activityDate.getMonth(), List.of(activity));
            } else {
                List<Meeting> OldListByDate = MeetingActivityMap.get(activityDate.getMonth());

                List<Meeting> newList = new ArrayList<>(OldListByDate);
                newList.add(activity);
                MeetingActivityMap.put(activityDate.getMonth(), newList);
            }
        }
        return MeetingActivityMap;
    }

    private Map<Integer, List<Meeting>> getMeetingActivitiesMonth(ZonedDateTime dateFocus) {
        try {
            List<Meeting> MeetingActivities = new MeetingRepository().getAll();
            int year = dateFocus.getYear();
            int month = dateFocus.getMonth().getValue();
            return createMeetingMap(MeetingActivities);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void onBackAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(Application.class.getResource("view/app_home_view.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getIcons().add(new Image(Application.class.getResourceAsStream("assets/icon.png")));
        stage.setResizable(false);
        stage.setTitle("Vorwerk");
        stage.setScene(scene);
        stage.show();
    }
}
