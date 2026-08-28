package com.brh.downloader_2368;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class DownloadItem {

    private VBox parent;
    private TextField textField;

    public DownloadItem( VBox parent){
        this.parent = parent;
        init();
    }

    private void init(){
        HBox hbox = new HBox();
        textField = new TextField();
        Button delButton = new Button("🗑");
        HBox.setHgrow(textField, Priority.ALWAYS);

        parent.getChildren().add(hbox);

        hbox.getChildren().add(textField);
        hbox.getChildren().add(delButton);

        delButton.setOnAction( this::deleteDownloadItem );
    }

    private void deleteDownloadItem( ActionEvent event ){
        Button delButton = (Button)event.getTarget();
        parent.getChildren().remove( delButton.getParent() );
    }

    public String getUrl(){
        return textField.getText();
    }

}
