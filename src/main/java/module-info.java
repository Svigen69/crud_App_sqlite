module com.fassi.vorwerk {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires ormlite.jdbc;
    requires ormlite.core;
    requires commons.validator;
    /*requires org.kordamp.ikonli.fontawesome;*/



    opens com.fassi.vorwerkApp to javafx.fxml;
    exports com.fassi.vorwerkApp;
    exports com.fassi.vorwerkApp.controllers;
    exports com.fassi.vorwerkApp.models;
    opens com.fassi.vorwerkApp.controllers to javafx.fxml;
    opens com.fassi.vorwerkApp.models to ormlite.core;
    exports com.fassi.vorwerkApp.controllers.meetings;
    opens com.fassi.vorwerkApp.controllers.meetings to javafx.fxml;
    exports com.fassi.vorwerkApp.controllers.clients;
    opens com.fassi.vorwerkApp.controllers.clients to javafx.fxml;
    exports com.fassi.vorwerkApp.controllers.products;
    opens com.fassi.vorwerkApp.controllers.products to javafx.fxml;
}