package model;

import model.dBConnection.DAOUser;

import java.util.Date;
import java.util.List;

public abstract class User {
    private String sSN;
    private int userType;  //   1 = Patient, 2 = Doctor, 3 = Admin
    private String firstName;
    private String lastName;
    private Date bDate;
    private String zipCode;
    private String address;
    private String email;
    private String phoneNumber;
    private String password;

    public User(){}

    public User(String sSN, int userType, String firstName, String lastName, Date birthDate, String zipCode, String address, String email, String phoneNumber, String password) {
        setSsn(sSN);
        setUserType(userType);
        setFirstName(firstName);
        setLastName(lastName);
        setBDate(birthDate);
        setZipCode(zipCode);
        setAddress(address);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setPassword(password);
    }

    public String getSsn() {
        return sSN;
    }

    public void setSsn(String sSN) {
        this.sSN = sSN;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBDate() {
        return bDate;
    }

    public void setBDate(Date bDate) {
        this.bDate = bDate;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // use this method carefully ('instance of' will LIE. getClass().toString() will show the truth)
    // since it needs to be instantiated from a subclass but it will still give you an object of the actual type
    // consider using DAOUser class method getUser() instead if the user type is unknown
    public User getUser(String sSN){
        DAOUser daoUser = new DAOUser();
        return daoUser.getUser(sSN);
    }

    public List<User> getUserList(){  // 0 - all users will be shown; values 1-3 - a corresponding type of users.
        DAOUser daoUser = new DAOUser();
        return daoUser.getUserList("0");
    }


    @Override
    public String toString() {
        return "User{" +
                "sSN=" + sSN +
                ", userType=" + userType +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bDate=" + bDate +
                ", zipCode=" + zipCode +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
