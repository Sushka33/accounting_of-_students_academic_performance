package com.example.popitka;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Info {

    @FXML
    private Label blabel;

    @FXML
    private Label flabel;

    @FXML
    private Label ilabel;

    @FXML
    private Label mlabel;
    private User user;

    public void showPersonDetails(User user) {
        this.user = user;

            flabel.setText(user.getSpisok());
            blabel.setText(String.valueOf(user.getBel()));
            ilabel.setText(String.valueOf(user.getInform()));
            mlabel.setText(String.valueOf(user.getMathh()));

    }
}



