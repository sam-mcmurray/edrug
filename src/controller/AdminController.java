package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.IntegerStringConverter;
import model.*;

import java.net.URL;
import java.sql.Date;

import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminController implements Initializable {


    @FXML
    public ComboBox filterPatient;
    public ComboBox doctorFilter;
    public ComboBox addFilter;
    public Button confirmAddButton;
    public Button cancel_button;
    public Button save_button;
    public TextField SSNtext;
    public TextField firstName_text;
    public TextField lastName_text;
    public TextField zip_text;
    public TextField address_text;
    public TextField phone_text;
    public TextField email_text;
    public TextField pass1_text;
    public TextField pass2_text;
    public TableView<User> patientTableView;
    public TableColumn<User, String> patientSSNtable;
    public TableColumn<User, String> patientPhoneTable;
    public TableColumn<User, String> patientEmailTable;
    public TableColumn<User, String> patientLastNameTable;
    public TableColumn<User, String> patientFirstNameTable;
    public TableView<User> doctorTable;
    public TableColumn<User, String> doctorSSNtable;
    public TableColumn<User, String> doctorLastNameTable;
    public TableColumn<User, String> doctorFirstNameTable;
    public TableColumn<User, String> doctorPhoneTable;
    public TableColumn<User, String> doctorEmailTable;
    public TableColumn<User, String> changeLastNameTable;
    public TableColumn<User, String> changeFirstNameTable;
    public TableColumn<User, String> changePhoneTable;
    public TableColumn<User, String> changeEmailTable;
    public TableColumn<User, String> changeSSNTable;
    public TableView<User> changeTable;
    public TableColumn<User, Integer> changeRoleTable;
    public Label SSNstar;
    public Label BDateStar;
    public Label firstNameStar;
    public Label lastNameStar;
    public Label zipStar;
    public Label addressStar;
    public Label phoneStar;
    public Label emailStar;
    public Label pass2star;
    public Label pass1star;
    public Button save_buttonAdd;
    public Button cancel_buttonAdd;
    public TextField SSNtextAdd;
    public TextField firstName_textAdd;
    public DatePicker datePicker;
    public TextField lastName_textAdd;
    public TextField zip_textAdd;
    public TextField address_textAdd;
    public TextField phone_textAdd;
    public TextField email_textAdd;
    public TextField pass1_textAdd;
    public TextField pass2_textAdd;
    public Label SSNstarAdd;
    public Label BDateStarAdd;
    public Label firstNameStarAdd;
    public Label lastNameStarAdd;
    public Label zipStarAdd;
    public Label addressStarAdd;
    public Label phoneStarAdd;
    public Label emailStarAdd;
    public Label pass2starAdd;
    public Label pass1starAdd;
    public TextField roleTextAdd;
    public DatePicker datePickerAdd;
    public Label roleStarAdd;
    public TableView<Medicine> storeView;
    public TableColumn<Medicine, Integer> storeArticle;
    public TableColumn<Medicine, String> storeName;
    public TableColumn <Medicine, String> storeSize;
    public TableColumn <Medicine, Double> storePrice;
    public TableColumn<Medicine, Integer> storeAvailability;
    public TableColumn<Medicine, String> storeDescription;
    public TableColumn<Medicine, String> storeProducer;
    public TextField storeSearchTextField;
    public Button storeGoButton;
    public ComboBox storeSearchCombo;
    public ComboBox storeFilterCombo;

    CommonMethods methods = new CommonMethods();
    User currentUser = UserSingleton.getOurInstance().getUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SSNstar.setVisible(false);
        BDateStar.setVisible(false);
        firstNameStar.setVisible(false);
        lastNameStar.setVisible(false);
        zipStar.setVisible(false);
        addressStar.setVisible(false);
        phoneStar.setVisible(false);
        emailStar.setVisible(false);
        pass1star.setVisible(false);
        pass2star.setVisible(false);

        fillStore();
        fillPatientTable();
        fillDoctorTable();
        fillEditTable();
        fillMe();
        makeEditable();





        cancel_button.setOnAction(actionEvent -> {
            fillMe();
            SSNstar.setVisible(false);
            BDateStar.setVisible(false);
            firstNameStar.setVisible(false);
            lastNameStar.setVisible(false);
            zipStar.setVisible(false);
            addressStar.setVisible(false);
            phoneStar.setVisible(false);
            emailStar.setVisible(false);
            pass1star.setVisible(false);
            pass2star.setVisible(false);

        });
        cancel_buttonAdd.setOnAction(actionEvent -> {
            SSNstarAdd.setVisible(false);
            BDateStarAdd.setVisible(false);
            firstNameStarAdd.setVisible(false);
            lastNameStarAdd.setVisible(false);
            zipStarAdd.setVisible(false);
            addressStarAdd.setVisible(false);
            phoneStarAdd.setVisible(false);
            emailStarAdd.setVisible(false);
            pass1starAdd.setVisible(false);
            pass2starAdd.setVisible(false);


            SSNtextAdd.clear();
            firstName_textAdd.clear();
            lastName_textAdd.clear();
            phone_textAdd.clear();
            zip_textAdd.clear();
            address_textAdd.clear();
            email_textAdd.clear();
            pass1_text.setText("******");
            pass2_text.setText("******");
            roleTextAdd.clear();

            datePickerAdd.setValue(LocalDate.now());

        });
        save_button.setOnAction(actionEvent -> {
            if (isItOk()) {
                if (Validation.isName(firstName_text.getText(), firstNameStar) && Validation.isName(lastName_text.getText(), lastNameStar) &&
                        Validation.isZipcode(zip_text.getText(), zipStar) && Validation.isPhoneNumber(phone_text.getText(), phoneStar)
                        && Validation.isEmail(email_text.getText(), emailStar)) {
                    try {
                        currentUser.setSsn(SSNtext.getText());
                        currentUser.setLastName(lastName_text.getText());
                        currentUser.setBDate(Date.valueOf(datePicker.getValue().plusDays(1)));
                        currentUser.setZipCode(zip_text.getText());
                        currentUser.setAddress(address_text.getText());
                        currentUser.setPhoneNumber(phone_text.getText());
                        currentUser.setEmail(email_text.getText());
                        currentUser.setPassword(pass1_text.getText());
                        methods.updateAdmin((Admin) currentUser);

                        pass1_text.setText("******");
                        pass2_text.setText("******");


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        save_buttonAdd.setOnAction(actionEvent -> {
            if (isItOkAdd()) {
                if (Validation.isSSN(SSNtextAdd.getText(), SSNstarAdd) && Validation.isName(firstName_textAdd.getText(), firstNameStarAdd) && Validation.isName(lastName_textAdd.getText(), lastNameStarAdd) &&
                        Validation.isZipcode(zip_textAdd.getText(), zipStarAdd) && Validation.isPhoneNumber(phone_textAdd.getText(), phoneStarAdd)
                        && Validation.isEmail(email_textAdd.getText(), emailStarAdd) && Validation.isRole(roleTextAdd.getText())) {
                    try {
                        if (Integer.parseInt(roleTextAdd.getText())==1) {
                            Patient patient = new Patient(SSNtextAdd.getText(), firstName_textAdd.getText(), lastName_textAdd.getText(),
                                    Date.valueOf(datePickerAdd.getValue()), zip_textAdd.getText(), address_textAdd.getText(),
                                    email_textAdd.getText(), phone_textAdd.getText(), pass1_textAdd.getText(), true);
                            methods.addPatient(patient);
                        }else if (Integer.parseInt(roleTextAdd.getText())==2) {
                            Doctor doctor = new Doctor(SSNtextAdd.getText(), firstName_textAdd.getText(), lastName_textAdd.getText(),
                                    Date.valueOf(datePickerAdd.getValue()), zip_textAdd.getText(), address_textAdd.getText(),
                                    email_textAdd.getText(), phone_textAdd.getText(), pass1_textAdd.getText(), true);
                            methods.addDoctor(doctor);

                        }

                        SSNtextAdd.clear();
                        firstName_textAdd.clear();
                        lastName_textAdd.clear();
                        phone_textAdd.clear();
                        zip_textAdd.clear();
                        address_textAdd.clear();
                        email_textAdd.clear();
                        pass1_textAdd.setText("******");
                        pass2_textAdd.setText("******");
                        roleTextAdd.clear();

                        datePickerAdd.setValue(LocalDate.now());

                        fillPatientTable();
                        fillDoctorTable();
                        fillEditTable();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });



    }



    public void fillPatientTable() {

        patientSSNtable.setCellValueFactory(new PropertyValueFactory<>("Ssn"));
        patientFirstNameTable.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        patientLastNameTable.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patientPhoneTable.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        patientEmailTable.setCellValueFactory(new PropertyValueFactory<>("Email"));


        ObservableList<User> listOfPatients = FXCollections.observableArrayList(methods.getPatientList());

        patientTableView.setItems(listOfPatients);

    }

    public void fillDoctorTable() {
        doctorSSNtable.setCellValueFactory(new PropertyValueFactory<>("Ssn"));
        doctorFirstNameTable.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        doctorLastNameTable.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        doctorPhoneTable.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        doctorEmailTable.setCellValueFactory(new PropertyValueFactory<>("Email"));

        ObservableList<User> listOfDoctors = FXCollections.observableArrayList(methods.getDoctorList());

        doctorTable.setItems(listOfDoctors);
        doctorPhoneTable.setEditable(true);

    }

    public void fillEditTable() {
        changeSSNTable.setCellValueFactory(new PropertyValueFactory<>("Ssn"));
        changeFirstNameTable.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        changeLastNameTable.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        changePhoneTable.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        changeEmailTable.setCellValueFactory(new PropertyValueFactory<>("Email"));
        changeRoleTable.setCellValueFactory(new PropertyValueFactory<>("userType"));


        ObservableList<User> listOfAll = FXCollections.observableArrayList(methods.getDoctorList());
        listOfAll.addAll(FXCollections.observableArrayList(methods.getPatientList()));

        changeTable.setItems(listOfAll);

    }

    public void fillStore() {
        storeArticle.setCellValueFactory(new PropertyValueFactory<>("articleNo"));
        storeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        storeAvailability.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        storeDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        storePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        storeProducer.setCellValueFactory(new PropertyValueFactory<>("producer"));
        storeSize.setCellValueFactory(new PropertyValueFactory<>("packageSize"));



        ObservableList<Medicine> listOfAll = FXCollections.observableArrayList(methods.getMedicineList());
        listOfAll.addAll(FXCollections.observableArrayList(methods.getMedicineList()));

        storeView.setItems(listOfAll);

    }
    public void fillMe() {
        SSNtext.setText(currentUser.getSsn());
        datePicker.setValue(currentUser.getBDate().toLocalDate());
        firstName_text.setText(currentUser.getFirstName());
        lastName_text.setText(currentUser.getLastName());
        zip_text.setText(currentUser.getZipCode());
        address_text.setText(currentUser.getAddress());
        phone_text.setText(currentUser.getPhoneNumber());
        email_text.setText(currentUser.getEmail());
        pass1_text.setText("******");
        pass2_text.setText("******");

    }

    public void buttonPressed(KeyEvent e)
    {
        if(e.getCode() == KeyCode.ENTER)
        {
            fillEditTable();
            fillPatientTable();
            fillDoctorTable();
        }
    }

    private boolean isItOk() {
        if (firstName_text.getText().isEmpty() || lastName_text.getText().isEmpty() || datePicker.getValue()==null
                || zip_text.getText().isEmpty() || address_text.getText().isEmpty() || email_text.getText().isEmpty() || phone_text.getText().isEmpty()) {
            if (firstName_text.getText().isEmpty()) {
                firstNameStar.setVisible(true);
            }
            if (lastName_text.getText().isEmpty()) {
                lastNameStar.setVisible(true);
            }
            if (datePicker.getValue()==null) {
                BDateStar.setVisible(true);
            }
            if (zip_text.getText().isEmpty()) {
                zipStar.setVisible(true);
            }
            if (address_text.getText().isEmpty()) {
                addressStar.setVisible(true);
            }
            if (email_text.getText().isEmpty()) {
                emailStar.setVisible(true);
            }
            if (phone_text.getText().isEmpty()) {
                phoneStar.setVisible(true);
            }
            if (pass1_text.getText().isEmpty()) {
                pass1star.setVisible(true);
            }
            if (pass2_text.getText().isEmpty()) {
                pass2star.setVisible(true);
            }
            Validation.alertPopup("Please enter your information into all fields", "Empty Fields", "Contains empty fields");
            return false;
        } else if (!pass1_text.getText().equals(pass2_text.getText())) {
            pass1star.setVisible(true);
            pass2star.setVisible(true);
            Validation.alertPopup("Password does not match", "Password Mismatch", "Password doesn't match");
            return false;
        } else if (!Validation.isPassword(pass1_text.getText(), pass1star)) {
            return false;
        } else {
            return true;
        }
    }
    private boolean isItOkAdd() {
        if (SSNtextAdd.getText().isEmpty() || firstName_textAdd.getText().isEmpty() || lastName_textAdd.getText().isEmpty() || datePickerAdd.getValue()==null
                || zip_textAdd.getText().isEmpty() || address_textAdd.getText().isEmpty() || email_textAdd.getText().isEmpty() || phone_textAdd.getText().isEmpty()
        || roleTextAdd.getText().isEmpty()) {

            if (SSNtextAdd.getText().isEmpty()) {
                SSNstarAdd.setVisible(true);
            }
            if (firstName_textAdd.getText().isEmpty()) {
                firstNameStarAdd.setVisible(true);
            }
            if (lastName_textAdd.getText().isEmpty()) {
                lastNameStarAdd.setVisible(true);
            }
            if (datePickerAdd.getValue()==null) {
                BDateStarAdd.setVisible(true);
            }
            if (zip_textAdd.getText().isEmpty()) {
                zipStarAdd.setVisible(true);
            }
            if (address_textAdd.getText().isEmpty()) {
                addressStarAdd.setVisible(true);
            }
            if (email_textAdd.getText().isEmpty()) {
                emailStarAdd.setVisible(true);
            }
            if (phone_textAdd.getText().isEmpty()) {
                phoneStarAdd.setVisible(true);
            }
            if (pass1_textAdd.getText().isEmpty()) {
                pass1starAdd.setVisible(true);
            }
            if (pass2_textAdd.getText().isEmpty()) {
                pass2starAdd.setVisible(true);
            }
            if (roleTextAdd.getText().isEmpty()) {
                roleStarAdd.setVisible(true);
            }
            Validation.alertPopup("Please enter your information into all fields", "Empty Fields", "Contains empty fields");
            return false;
        } else if (!pass1_textAdd.getText().equals(pass2_textAdd.getText())) {
            pass1starAdd.setVisible(true);
            pass2starAdd.setVisible(true);
            Validation.alertPopup("Password does not match", "Password Mismatch", "Password doesn't match");
            return false;
        } else if (!Validation.isPassword(pass1_textAdd.getText(), pass1starAdd)) {
            return false;
        } else {
            return true;
        }
    }

    public void makeEditable() {
        changeSSNTable.setCellFactory(TextFieldTableCell.forTableColumn());
        changeSSNTable.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<User, String>>() {

                    @Override
                    public void handle(TableColumn.CellEditEvent<User, String> t) {
                        if (Validation.isSSN(t.getNewValue(), SSNstar)) {
                            SSNstar.setVisible(false);
                            ((User) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setSsn(t.getNewValue());
                            methods.updateUser(t.getTableView().getItems().get(t.getTablePosition().getRow()));
                        }
                        fillDoctorTable();
                        fillPatientTable();
                    }
                }
        );
        changeFirstNameTable.setCellFactory(TextFieldTableCell.forTableColumn());
        changeFirstNameTable.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<User, String>>() {

                    @Override
                    public void handle(TableColumn.CellEditEvent<User, String> t) {
                        if (Validation.isName(t.getNewValue(), firstNameStar)) {
                            firstNameStar.setVisible(false);
                            ((User) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setFirstName(t.getNewValue());
                            methods.updateUser(t.getTableView().getItems().get(t.getTablePosition().getRow()));
                        }
                        fillDoctorTable();
                        fillPatientTable();
                    }
                }
        );
        changeLastNameTable.setCellFactory(TextFieldTableCell.forTableColumn());
        changeLastNameTable.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<User, String>>() {

                    @Override
                    public void handle(TableColumn.CellEditEvent<User, String> t) {
                        if (Validation.isName(t.getNewValue(), lastNameStar)) {
                            lastNameStar.setVisible(false);
                            ((User) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setLastName(t.getNewValue());
                            methods.updateUser(t.getTableView().getItems().get(t.getTablePosition().getRow()));
                        }
                        fillDoctorTable();
                        fillPatientTable();
                    }
                }
        );
        changeEmailTable.setCellFactory(TextFieldTableCell.forTableColumn());
        changeEmailTable.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<User, String>>() {

                    @Override
                    public void handle(TableColumn.CellEditEvent<User, String> t) {
                        if (Validation.isEmail(t.getNewValue(), emailStar)) {
                            emailStar.setVisible(false);
                            ((User) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setEmail(t.getNewValue());
                            methods.updateUser(t.getTableView().getItems().get(t.getTablePosition().getRow()));
                        }
                        fillDoctorTable();
                        fillPatientTable();
                    }
                }
        );
        changePhoneTable.setCellFactory(TextFieldTableCell.forTableColumn());
        changePhoneTable.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<User, String>>() {

                    @Override
                    public void handle(TableColumn.CellEditEvent<User, String> t) {
                        if (Validation.isPhoneNumber(t.getNewValue(), phoneStar)) {
                            phoneStar.setVisible(false);
                            ((User) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            ).setPhoneNumber(t.getNewValue());
                            methods.updateUser(t.getTableView().getItems().get(t.getTablePosition().getRow()));

                        }
                        fillDoctorTable();
                        fillPatientTable();
                    }
                });
        
    }

}

