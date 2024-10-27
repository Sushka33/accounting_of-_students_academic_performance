package com.example.popitka;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private VBox Vbox;
    @FXML
    public TableView<User> table;
    @FXML
    private TableColumn<User, Integer> bel;

    @FXML
    private TableColumn<User, Integer> inform;

    @FXML
    private TableColumn<User, Integer> mathh;

    @FXML
    private TableColumn<User, String> spisok;


    @FXML
    private TextField spisokinput;

    @FXML
    private TextField mathhinput;

    @FXML
    private TextField informinput;

    @FXML
    private TextField belinput;


    @FXML
    private UserData data;


    @FXML
    void submit(ActionEvent event) throws IOException {

        String s = null;
        try {
            s = spisokinput.getText();
        } catch (StringIndexOutOfBoundsException exception) {
            Tooltip r = new Tooltip("Ошибка! Введите фамилию!");
            spisokinput.setTooltip(r);
        }
        int b = 404;
        int m = 404;
        try {
            m = Integer.parseInt(mathhinput.getText());
        } catch (Exception e) {
            Tooltip q = new Tooltip("Ошибка! Введите отметку!");
            mathhinput.setTooltip(q);
        }
        int i = 404;
        try {
            i = Integer.parseInt(informinput.getText());
        } catch (Exception e) {
            Tooltip w = new Tooltip("Ошибка! Введите отметку!");
            informinput.setTooltip(w);
        }

        try {
            b = Integer.parseInt(belinput.getText());
        } catch (Exception e) {
            Tooltip t = new Tooltip("Ошибка! Введите отметку!");
            belinput.setTooltip(t);
        }

        User customer = new User(s, m, i, b);
        ObservableList<User> customers = table.getItems();
        if (mathhinput.getText() != null && Integer.parseInt(mathhinput.getText()) > 0 && Integer.parseInt(mathhinput.getText()) < 11 && informinput.getText() != null && Integer.parseInt(informinput.getText()) > 0 && Integer.parseInt(informinput.getText()) < 11 && belinput.getText() != null && Integer.parseInt(belinput.getText()) > 0 && Integer.parseInt(belinput.getText()) < 11 && spisokinput.getText() != null) {
            customers.add(customer);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Введите значения");
            alert.setTitle("Недстоверные данные!");
            alert.showAndWait();
        }

        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        spisokinput.setText("");
        mathhinput.setText("");
        informinput.setText("");
        belinput.setText("");
        data.saveCustomers();
    }


    @FXML
    void removeCustomer(ActionEvent event) {
        User customer = table.getSelectionModel().getSelectedItem();
        if (customer != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("уверены, что хотите удалить?");
            confirm.setTitle("удалить " + customer.getSpisok());
            Optional<ButtonType> res = confirm.showAndWait();
            if (res.get() == ButtonType.OK) {
                data.deleteCustomer(customer);
                data.saveCustomers();

            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Выберите ученика!");
            alert.setTitle("Не выбран ученик");
            alert.showAndWait();
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spisok.setCellValueFactory(new PropertyValueFactory<User, String>("spisok"));
        mathh.setCellValueFactory(new PropertyValueFactory<User, Integer>("mathh"));
        inform.setCellValueFactory(new PropertyValueFactory<User, Integer>("inform"));
        bel.setCellValueFactory(new PropertyValueFactory<User, Integer>("bel"));
        data = new UserData();
        table.setItems(data.getCustomers());
    }


    public void sred(ActionEvent actionEvent) {
        if (!table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Калькулятор среднего балла");
            alert.setHeaderText(null);
            alert.setContentText("Средний балл учащегося " + "" + table.getSelectionModel().getSelectedItem().getSpisok() + ": " + (double) (table.getSelectionModel().getSelectedItem().getBel() + table.getSelectionModel().getSelectedItem().getInform() + table.getSelectionModel().getSelectedItem().getMathh()) / 3);
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Выберите ученика!");
            alert.setTitle("Не выбран ученик");
            alert.showAndWait();
        }
    }

    @FXML
    private void info(ActionEvent event) throws IOException {
        User user = new User();
        if (!table.getSelectionModel().isEmpty()) {
            user.setSpisok(table.getSelectionModel().getSelectedItem().getSpisok());
            user.setBel(table.getSelectionModel().getSelectedItem().getBel());
            user.setMathh(table.getSelectionModel().getSelectedItem().getMathh());
            user.setInform(table.getSelectionModel().getSelectedItem().getInform());
            FXMLLoader destinationLoader = new FXMLLoader(getClass().getResource("info.fxml"));
            Parent root = destinationLoader.load();
            Info info = destinationLoader.getController();
            info.showPersonDetails(user);
            Stage destinationStage = new Stage();
            destinationStage.setTitle("Информация о ученике");
            destinationStage.setScene(new Scene(root));
            destinationStage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Выберите ученика!");
            alert.setTitle("Не выбран ученик");
            alert.showAndWait();
        }
    }

    @FXML
    private void nezach(ActionEvent event) throws IOException {
        ObservableList<Object> customers = FXCollections.observableArrayList();
        for (User user: data.getCustomers()){
            if (user.getInform() < 4 || user.getBel() < 4 || user.getMathh() < 4) {
                customers.add(user);
            }
        }
        if (customers.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Все допущены!");
            alert.setTitle("");
            alert.showAndWait();
        }
        else {
            FXMLLoader destinationLoader = new FXMLLoader(getClass().getResource("nezach.fxml"));
            Parent root = destinationLoader.load();
            Stage destinationStage = new Stage();
            destinationStage.setTitle("Список недопущенных");
            destinationStage.setScene(new Scene(root));
            destinationStage.show();
            Nezach nezach = destinationLoader.getController();
            nezach.Shownezach(customers);
        }
    }
    @FXML
    private void ball(ActionEvent actionEvent) throws IOException {
        int m = 0;
        int i = 0;
        int b = 0;
        for (User user : data.getCustomers()) {
            m += user.getMathh();
            i += user.getInform();
            b += user.getBel();
        }
        if (!table.getItems().isEmpty()) {
            double srm = (double) m / table.getItems().size();
            double sri = (double) i / table.getItems().size();
            double srb = (double) b / table.getItems().size();
            FXMLLoader destinationLoader = new FXMLLoader(getClass().getResource("sredd.fxml"));
            Parent root = destinationLoader.load();
            Stage destinationStage = new Stage();
            destinationStage.setTitle("Средний балл предметов");
            destinationStage.setScene(new Scene(root));
            destinationStage.show();
            Sredd sredd = destinationLoader.getController();
            sredd.showSredBall(srm, sri, srb);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Сначала заполните таблицу!!");
            alert.setTitle("");
            alert.showAndWait();
        }
    }
}