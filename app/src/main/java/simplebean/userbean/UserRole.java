package simplebean.userbean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Hp on 11/14/2016.
 */
public class UserRole {
    @JsonProperty("roleId")
    private int roleId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("permissions")
    private String permissions;
    @JsonProperty("status")
    private int status;
    @JsonProperty("typeId")
    private int typeId;
    @JsonProperty("descr")
    private String descr;

    public UserRole(int roleId, String name, String permissions, int status, int typeId, String descr) {
        this.setRoleId(roleId);
        this.setName(name);
        this.setPermissions(permissions);
        this.setStatus(status);
        this.setTypeId(typeId);
        this.setDescr(descr);
    }

    public UserRole() {

    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
