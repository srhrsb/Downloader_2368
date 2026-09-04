package com.brh.downloader_2368;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.function.Consumer;

public class DownloadItem {

    private VBox parent;
    private TextField textField;
    private Label downloadProgressLabel;
    private Consumer<DownloadItem> onDeleteCallback;

    /**
     * Konstruktor um den Parentcontainer, onDeleteCallback zuzuweisen
     * @param parent Container für DownloadItems (UI)
     * @param onDeleteCallback callback im Falle des Löschens
     */
    public DownloadItem( VBox parent, Consumer<DownloadItem> onDeleteCallback){
        this.parent = parent;
        this.onDeleteCallback = onDeleteCallback;
        init();
    }

    /**
     * Erstellen der Bedienelemente und einbringen in die Oberfläche
     */
    private void init(){
        HBox hbox = new HBox();
        textField = new TextField();

        downloadProgressLabel = new Label("0");
        downloadProgressLabel .setMinWidth(100);
        downloadProgressLabel .setFont(new Font(16));
        downloadProgressLabel .setPadding( new Insets(2) );

        Button delButton = new Button("🗑");
        HBox.setHgrow(textField, Priority.ALWAYS);

        parent.getChildren().add(hbox);

        hbox.getChildren().add(textField);
        hbox.getChildren().add(delButton);
        hbox.getChildren().add(downloadProgressLabel );

        delButton.setOnAction( this::deleteDownloadItem );
    }

    /**
     * Clickevent wenn Delete-Button gedrückt wird
     * @param event Clickevent des Buttons
     */
    private void deleteDownloadItem( ActionEvent event ){
        Button delButton = (Button)event.getTarget();
        parent.getChildren().remove( delButton.getParent() );
        onDeleteCallback.accept(this);
    }

    /**
     * Get Methode für den Donwloadlink aus dem Textfeld
     * @return Url
     */
    public String getUrl(){
        return textField.getText();
    }

    /**
     * Aktualisiert den Download-Fortschritt von diesem
     * DownloadItem
     * @param downloadedBytes Long Anzahl der bytes
     */
    public void updateProgress( Long downloadedBytes ){
        //ToDo: verwendete Syntax => Lambda Expression
        //      besser erklären -> KW38
        Platform.runLater(
                //kann nur so in "runLater" ausgeführt werden
                //weil sonst ein Thread auf einen anderen zugreifen
                //würde
                ()->downloadProgressLabel.setText(
                        downloadedBytes.toString()
                )
        );
    }
}
