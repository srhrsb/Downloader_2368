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
import java.util.logging.Level;
import java.util.logging.Logger;

public class DownloadItem {

    private VBox parent;
    private HBox hbox;
    private TextField textField;
    private Label downloadProgressLabel;
    private Consumer<DownloadItem> onDeleteCallback;
    private Consumer<DownloadItem> onSingleDownloadCallback;
    private static final Logger LOGGER = Logger.getLogger(DownloadItem.class.getName());

    /**
     * Konstruktor um den Parentcontainer, onDeleteCallback zuzuweisen
     * @param parent Container für DownloadItems (UI)
     * @param onDeleteCallback callback im Falle des Löschens
     */
    public DownloadItem( VBox parent, Consumer<DownloadItem> onDeleteCallback, Consumer<DownloadItem> onSingleDownloadCallback ){
        this.parent = parent;
        this.onDeleteCallback = onDeleteCallback;
        this.onSingleDownloadCallback = onSingleDownloadCallback;
        init();
    }

    /**
     * Erstellen der Bedienelemente und einbringen in die Oberfläche
     */
    private void init(){
        hbox = new HBox();
        textField = new TextField();

        String lastUrl = App.getController().getTextOfLastDownloadItem();
        textField.setText(lastUrl);

        downloadProgressLabel = new Label("0");
        downloadProgressLabel .setMinWidth(100);
        downloadProgressLabel .setFont(new Font(16));
        downloadProgressLabel .setPadding( new Insets(2) );

        Button delButton = new Button("🗑");
        HBox.setHgrow(textField, Priority.ALWAYS);

        Button downloadButton = new Button("DL");

        parent.getChildren().add(hbox);

        hbox.getChildren().add(textField);
        hbox.getChildren().add(delButton);
        hbox.getChildren().add(downloadButton);
        hbox.getChildren().add(downloadProgressLabel );

        delButton.setOnAction( this::deleteDownloadItem );
        downloadButton.setOnAction( this::singleDownload );
        LOGGER.log(Level.INFO, "UserInterface ist erstellt");

    }

    /**
     * Clickevent wenn Delete-Button gedrückt wird
     * @param event Clickevent des Buttons
     */
    private void deleteDownloadItem( ActionEvent event ){
        boolean confirm = DialogUtils.showConfirmDialog("Bitte Bestätigen", "Wollen Sie wirklich dieses Item löschen?");

        if(!confirm){
            LOGGER.log(Level.INFO, "Löschen des Items durch Nutzer abgebrochen");
            return;
        }

        Button delButton = (Button)event.getTarget();
        parent.getChildren().remove( delButton.getParent() );
        onDeleteCallback.accept(this);
    }

    private void singleDownload( ActionEvent event ){
           onSingleDownloadCallback.accept(this);
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
