package com.royalstil.royalstildesktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;

public class ClientPageController extends ElementController{
    //private HashMap<String, String> selectedRow;

    @FXML
    private TextField clientFIO;

    @FXML
    private void initialize(){
        System.out.println();
    }

    /**public void setSelectedRow(HashMap<String, String> selectedRow){
        clientFIO.setText(selectedRow.get("second_name"));
        this.selectedRow = selectedRow;
    }**/

    public void check(ActionEvent actionEvent) {
        System.out.println();
    }
}
