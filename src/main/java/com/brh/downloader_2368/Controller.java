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

public class Controller {

    @FXML
    private VBox downloadItemContainer;
    @FXML
    private TextField targetTf;
    @FXML
    private Button toggleDownloadBtn;

    private ArrayList<DownloadItem> downloadItemList;

    private ArrayList<Download> downloadList;

    private boolean isDownloading;

    /**
     * Ersatz für den Konstruktor in JavaFX - Controllern
     * für initiale Anweisungen
     * Erstellt/Initialisiert die Liste zum Speichern
     * der DownloadItems
     */
    @FXML
    private void initialize(){
        downloadItemList = new ArrayList<>();
        downloadList = new ArrayList<>();
        toggleDownloadBtn.setDisable(true);
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
      downloadItemList.add( new DownloadItem( downloadItemContainer, this::deleteItem ));
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
       }
    }

    /**
     * Veranlasst den Downlaod aller eingegebenen Urls
     * Textfelder mit Urls befinden sich  im downloadItemContainer
     * @param event Clickevent des Button "Download"
     */
    @FXML
    private void onDownload(ActionEvent event) {

        isDownloading = true;
        toggleDownloadBtn.setDisable(false);
        for( DownloadItem downloadItem : downloadItemList){
            String target = targetTf.getText();
            Download download = new Download(downloadItem.getUrl(), target, downloadItem::updateProgress );
            downloadList.add(download);
            download.start();
        }
    }

    @FXML
    private void onToggleDownload(ActionEvent event) {

         isDownloading = !isDownloading;
         System.out.println(isDownloading);

         //ToDo: Unterscheidung download anhalten oder weiterlaufen lassen
         //      und dazu jeweils die Button Beschriftung ändern

        setToggleDownloadBtnLabel(isDownloading);

        if(isDownloading){
            try {
                stopAllDownloads();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            resumeAllDownloads();
        }
    }

    private void setToggleDownloadBtnLabel(boolean isDownloading) {
        if(isDownloading){
            toggleDownloadBtn.setText("anhalten");
        }
        else{
            toggleDownloadBtn.setText("fortsetzen");
        }
    }

    private void stopAllDownloads() throws InterruptedException {
        for(  var download : downloadList){
            if(download.isAlive()) {
                download.wait();
            }
        }
    }

    private void resumeAllDownloads(){
        notifyAll();
    }


}
