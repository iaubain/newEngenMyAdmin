package simplebean.userbean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Hp on 11/14/2016.
 */
public class UserDetails {
    @JsonProperty("userId")
    private int userId;
    @JsonProperty("branchId")
    private int branchId;
    @JsonProperty("branchName")
    private String branchName;
    @JsonProperty("fname")
    private String fname;
    @JsonProperty("otherNames")
    private String otherNames;
    @JsonProperty("email")
    private String email;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("details")
    private String details;
    @JsonProperty("permission")
    private String permissions;
    @JsonProperty("pin")
    private
    String pin;
    @JsonProperty("roles")
    private List<UserRole> userRole;

    public UserDetails(int userId, int branchId, String branchName, String fname, String otherNames, String email, String gender, String phoneNumber, String details, String permissions, List<UserRole> userRole) {
        this.setUserId(userId);
        this.setBranchId(branchId);
        this.setBranchName(branchName);
        this.setFname(fname);
        this.setOtherNames(otherNames);
        this.setEmail(email);
        this.setGender(gender);
        this.setPhoneNumber(phoneNumber);
        this.setDetails(details);
        this.setPermissions(permissions);
        this.setUserRole(userRole);
    }

    public UserDetails() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public List<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<UserRole> userRole) {
        this.userRole = userRole;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
