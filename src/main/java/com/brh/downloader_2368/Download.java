package com.brh.downloader_2368;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

public class Download extends Thread{

    private String link;
    private String target;
    private File outputFile;
    private Consumer<Long> onProgress;

    public Download(String link, String target, Consumer<Long> onProgress) {
        this.link = link;
        this.target = target;
        this.onProgress = onProgress;
    }

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
                System.out.println("Runtergeladen("+this+"): "+downloaded);
            }
            buffOutputStream.close();
            buffInputStream.close();
            System.out.println("Download erfolgreich");
        }
        catch( IOException e){
            throw new RuntimeException(e);
        }
    }
}


