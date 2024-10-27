package com.example.popitka;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Sredd {

    @FXML
    private Label blabel;

    @FXML
    private Label ilabel;

    @FXML
    private Label mlabel;


    public void showSredBall(double srm, double sri, double srb) {
        blabel.setText(String.valueOf(srb));
        ilabel.setText(String.valueOf(sri));
        mlabel.setText(String.valueOf(srm));

    }
}
