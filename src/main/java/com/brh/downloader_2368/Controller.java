package com.brh.downloader_2368;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    @FXML
    private VBox downloadItemContainer;
    @FXML
    private TextField targetTf;

    private ArrayList<DownloadItem> downloadItemList;
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    /**
     * Ersatz für den Konstruktor in JavaFX - Controllern
     * für initiale Anweisungen
     * Erstellt/Initialisiert die Liste zum Speichern
     * der DownloadItems
     */
    @FXML
    private void initialize(){
        downloadItemList = new ArrayList<>();
        LOGGER.addHandler( App.getLogFileHandler() );
    }

    /**
     * Löscht die Liste des DownloadItems im Controller
     * @param item DownloadItem das gelöscht werden soll
     */
    public void deleteItem( DownloadItem item){
        downloadItemList.remove(item);
    }

    /**
     * Fügt ein neues Textfeld zur Url-Eingabe hinzu
     * @param event Clickevent des Button "+Download"
     */
    @FXML
    private void onAddDownloader( ActionEvent event ) {
      downloadItemList.add( new DownloadItem( downloadItemContainer, this::deleteItem, this::singleDownload ));
    }

    public String getTextOfLastDownloadItem(){

        if(downloadItemList.isEmpty()) return "";
        DownloadItem lastItem = downloadItemList.getLast();
        String lastUrl = lastItem.getUrl();

        return lastUrl;
    }

    /**
     * Directory-Dialog wird geöffnet und bei gültigem Directory
     * wird der absolute Pfad in das Textfeld für den Zielpfad eingetragen
     * @param event Onclick Event des Suchbuttons
     */
    @FXML
    private void onSearch(ActionEvent event) {

       DirectoryChooser directory = new DirectoryChooser();

       directory.setInitialDirectory(
               new File( System.getProperty("user.home")+"/Downloads" )
       );

       File file = directory.showDialog( App.getStage() );

       if(file != null){ // objekt ist gültig
           targetTf.setText(file.getAbsolutePath());
           LOGGER.log(Level.INFO, "Zielordner für Downloads per Dialog ausgewählt");
       }
       else{
           LOGGER.log(Level.INFO, "Auswahl Zielordner abgebrochen");
       }
    }

    /**
     * Veranlasst den Downlaod aller eingegebenen Urls
     * Textfelder mit Urls befinden sich  im downloadItemContainer
     * @param event Clickevent des Button "Download"
     */
    @FXML
    private void onDownload(ActionEvent event) {

        for( DownloadItem downloadItem : downloadItemList){
            singleDownload(downloadItem);
        }
    }

    /**
     * Veranlasst einen einzelnen Download
     * @param downloadItem
     */
    private void singleDownload( DownloadItem downloadItem){
        String target = targetTf.getText();

        if( target.isBlank() ) {
            DialogUtils.showErrorDialog("Fehler", "Es wurde kein Zielordner angegeben");
            LOGGER.log(Level.WARNING, "Kein Zielordner - Download kann nicht durchgeführt werden");
            return;
        }

        Download download = new Download(downloadItem.getUrl(), target, downloadItem::updateProgress );
        download.start();
    }



}
