package simplebean.loginbean;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hp on 9/26/2016.
 */
public class User implements Parcelable {
    @JsonProperty("userId")
    private int userId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("branchId")
    private int branchId;
    @JsonProperty("branchName")
    private String branchName;
    @JsonProperty("permission")
    private String permission;

    public User(int userId, String name, int branchId, String branchName, String permission) {
        this.setUserId(userId);
        this.setName(name);
        this.setBranchId(branchId);
        this.setBranchName(branchName);
        this.setPermission(permission);
    }

    public User() {

    }

    protected User(Parcel in) {
        userId = in.readInt();
        name = in.readString();
        branchId = in.readInt();
        branchName = in.readString();
        permission = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(String.valueOf(userId));
        parcel.writeString(name);
        parcel.writeString(String.valueOf(branchId));
        parcel.writeString(branchName);
        parcel.writeString(permission);
    }
}
