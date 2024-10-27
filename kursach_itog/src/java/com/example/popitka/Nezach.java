package com.example.popitka;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class Nezach  {

    @FXML
    public ListView<User> listview;


    @FXML
    public void Shownezach (ObservableList customers) {
        ListView<User> listView = new ListView<User>(customers);
        listview.getItems().addAll(customers);
    }
}

