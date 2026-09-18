package com.brh.downloader_2368;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class DialogUtils {

    /**
     * Zeigt Infofenster an
     * @param title Fenstertitel
     * @param text Nachricht
     */
    public static void showInfoDialog(String title, String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Zeigt Fehlerfenster an
     * @param title Fenstertitel
     * @param text Nachricht
     */
    public static void showErrorDialog(String title, String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Zeigt Bestätigungsfenster an
     * @param title Fenstertitel
     * @param text Nachricht
     * @return result Boolean für Zustimmung
     */
    public static boolean showConfirmDialog(String title, String text){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        Optional<ButtonType> result =alert.showAndWait();
        return result.get() == ButtonType.OK; //nur true wenn ok gedrückt
    }

}
