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

    public DownloadItem( VBox parent, Consumer<DownloadItem> onDeleteCallback){
        this.parent = parent;
        this.onDeleteCallback = onDeleteCallback;
        init();
    }

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

    private void deleteDownloadItem( ActionEvent event ){
        Button delButton = (Button)event.getTarget();
        parent.getChildren().remove( delButton.getParent() );
        onDeleteCallback.accept(this);
    }

    public String getUrl(){
        return textField.getText();
    }

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
