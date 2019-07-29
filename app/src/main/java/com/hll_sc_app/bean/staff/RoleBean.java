package com.hll_sc_app.bean.staff;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 角色
 *
 * @author zhuyingsong
 * @date 2019-07-26
 */
public class RoleBean implements Parcelable {
    private String roleID;
    private String roleName;
    public static final Creator<RoleBean> CREATOR = new Creator<RoleBean>() {
        @Override
        public RoleBean createFromParcel(Parcel source) {
            return new RoleBean(source);
        }

        @Override
        public RoleBean[] newArray(int size) {
            return new RoleBean[size];
        }
    };
    private boolean select;
    private String id;

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    private int authType;

    public RoleBean() {
    }

    protected RoleBean(Parcel in) {
        this.roleID = in.readString();
        this.roleName = in.readString();
        this.select = in.readByte() != 0;
        this.id = in.readString();
        this.authType = in.readInt();
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.roleID);
        dest.writeString(this.roleName);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
        dest.writeString(this.id);
        dest.writeInt(this.authType);
    }
}
