package controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import model.dBConnection.CommonMethods;


import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserCommon {
    private User currentUser;
    private CommonMethods commonMethods = new CommonMethods();

    @FXML
    public void onLogOutButtonPressed(ActionEvent event) throws IOException {
        try {
            switchScene(event, "/view/loginView.fxml");
            currentUser = null;
            UserSingleton.getOurInstance().setUser(currentUser);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void switchScene(Event event, String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Scene newScene = new Scene(root);
        newScene.getStylesheets().add(getClass().getResource("../FileUtil/layout.css").toExternalForm());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("eDrug");
        window.setScene(newScene);
        window.show();
    }

    public SortedList<Medicine> medFilter(FilteredList<Medicine> filteredData, TextField field, TableView<Medicine> tableView){
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(medicine -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (medicine.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (medicine.getSearchTerms().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(medicine.getArticleNo()).contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Medicine> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
        return sortedData;
    }

    public SortedList<User> userFilter(FilteredList<User> filteredData, TextField field, TableView<User> tableView){
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (user.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (user.getSsn().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<User> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
        return sortedData;
    }

    public void clearCart(List<OrderLine> cart){
        try {
            if (cart != null) {
                for (int i = 0; i < cart.size(); i++) {
                    int article = cart.get(i).getArticleNo();
                    int quantity = cart.get(i).getQuantity();
                    Medicine medicine = commonMethods.getMedicine(article);
                    int newQuantity = medicine.getQuantity() + quantity;
                    medicine.setQuantity(newQuantity);
                    commonMethods.updateQuantity(medicine);
                }
                cart.removeAll(cart);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void handleHelpMenus(TextArea helpMenu, Label helpCircle, String text) {
        helpMenu.setVisible(false);

        helpCircle.setOnMouseEntered(mouseEvent -> {
            helpMenu.setVisible(true);
            helpMenu.setText(text);

        });
        helpCircle.setOnMouseExited(mouseEvent -> helpMenu.setVisible(false));
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String hashPassword(String passwordString) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String temp = passwordString.concat("z1");
        md.update(temp.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        return String.format("%064x", new BigInteger(1, digest));
    }

    public SortedList<Pharmacy> pharmaciesFilter(FilteredList<Pharmacy> filteredData, TextField field, TableView<Pharmacy> tableView) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(pharmacy -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String id = String.valueOf(pharmacy.getStoreId());
                String lowerCaseFilter = newValue.toLowerCase();

                if (pharmacy.getStoreName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (pharmacy.getCity().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (id.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Pharmacy> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
        return sortedData;
    }

    public SortedList<Order> ordersFilter(FilteredList<Order> filteredData, TextField field, TableView<Order> tableView) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(order -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String id = String.valueOf(order.getId());
                String date = order.getDate().toString();
                String lowerCaseFilter = newValue.toLowerCase();

                if (order.getUser().getSsn().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (id.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (date.toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });
        SortedList<Order> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
        return sortedData;
    }

    public SortedList<Delivery> deliveriesFilter(FilteredList<Delivery> filteredData, TextField field, TableView<Delivery> tableView) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(delivery -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String id = String.valueOf(delivery.getOrderId());
                String date = delivery.getDate().toString();
                String lowerCaseFilter = newValue.toLowerCase();

               if (id.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (date.toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });
        SortedList<Delivery> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
        return sortedData;
    }
}
