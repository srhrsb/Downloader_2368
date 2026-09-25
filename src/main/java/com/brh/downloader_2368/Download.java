package com.brh.downloader_2368;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Download extends Thread{

    private String link;
    private String target;
    private File outputFile;
    private Consumer<Long> onProgress;
    private Consumer<Long> updateTotalProgress;
    private static final Logger LOGGER = Logger.getLogger(Download.class.getName());

    /**
     * Konstruktor
     * @param link Url die runtergeladen werden soll
     * @param target Pfad des Zielordners
     * @param onProgress Callback der regelmäßig während des Download
     *                   den Fortschritt updatet
     */
    public Download(String link, String target, Consumer<Long> onProgress,
                    Consumer<Long> updateTotalProgress ) {
        this.link = link;
        this.target = target;
        this.onProgress = onProgress;
        this.updateTotalProgress = updateTotalProgress;
        LOGGER.addHandler(App.getLogFileHandler());
    }

    /**
     * Geerbte Methode von Threads wird intern vom Thread aufgerufen
     * wenn thread.start() aufgerufen wird.
     * Führt den Download durch
     */
    @Override
    public void run(){
        try{
            URL url = new URL(link);
            //öffnen der Verbindung
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Input Stream
            BufferedInputStream buffInputStream =
                    new BufferedInputStream( connection.getInputStream() );

            //File Objekt um Dateinamen des Links zu ermitteln
            File file =  new File(link);

            //Ausgabefile mit dem Target-Pfad und übernommenem Dateinamen
            outputFile = new File(target, file.getName());

            //Outputstream mit Ziel
            OutputStream outputStream = new FileOutputStream(outputFile);
            BufferedOutputStream buffOutputStream =new BufferedOutputStream( outputStream , 1024);

            byte[] buffer = new byte[1024];
            long downloaded = 0;
            int readByte = 0;
            while((readByte = buffInputStream.read(buffer, 0, 1024)) >= 0){
                buffOutputStream.write(buffer, 0, readByte);
                downloaded += readByte;
                onProgress.accept(downloaded);
                updateTotalProgress.accept((long)readByte);
                System.out.println("Runtergeladen("+this+"): "+downloaded);
            }
            buffOutputStream.close();
            buffInputStream.close();
           LOGGER.log(Level.INFO,"Download erfolgreich");
        }
        catch( IOException e){
            throw new RuntimeException(e);
        }
    }
}


