package com.hll_sc_app.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.base.utils.UIUtils;

import java.util.Arrays;

/**
 * 分类 item
 *
 * @author zhuyingsong
 * @date 2019-06-10
 */
public class CategoryItem implements Parcelable {
    public static final Creator<CategoryItem> CREATOR = new Creator<CategoryItem>() {
        @Override
        public CategoryItem createFromParcel(Parcel source) {
            return new CategoryItem(source);
        }

        @Override
        public CategoryItem[] newArray(int size) {
            return new CategoryItem[size];
        }
    };
    private String imgUrl;
    private String categoryPID;
    private String categoryLevel;
    private String categoryName;
    private String categoryID;
    private boolean selected;

    protected CategoryItem(Parcel in) {
        this.imgUrl = in.readString();
        this.categoryPID = in.readString();
        this.categoryLevel = in.readString();
        this.categoryName = in.readString();
        this.categoryID = in.readString();
        this.selected = in.readByte() != 0;
    }

    public CategoryItem() {
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{imgUrl, categoryPID, categoryLevel, categoryName, categoryID});
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final CategoryItem other = (CategoryItem) obj;
        return UIUtils.equals(this.imgUrl, other.imgUrl)
            && UIUtils.equals(this.categoryPID, other.categoryPID)
            && UIUtils.equals(this.categoryLevel, other.categoryLevel)
            && UIUtils.equals(this.categoryName, other.categoryName)
            && UIUtils.equals(this.categoryID, other.categoryID);
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCategoryPID() {
        return categoryPID;
    }

    public void setCategoryPID(String categoryPID) {
        this.categoryPID = categoryPID;
    }

    public String getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(String categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imgUrl);
        dest.writeString(this.categoryPID);
        dest.writeString(this.categoryLevel);
        dest.writeString(this.categoryName);
        dest.writeString(this.categoryID);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
