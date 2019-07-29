package com.hll_sc_app.bean.report.resp.group;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 搜索采购商列表
 */
public class PurchaserGroupBean implements Parcelable {

	private Long purchaserID;
	
	private String purchaserName;
	
	private String logoUrl;
	
	private String linkman;
	
	private String mobile;

	private String groupArea;

	private String customMnemonicCode;


	protected PurchaserGroupBean(Parcel in) {
		if (in.readByte() == 0) {
			purchaserID = null;
		} else {
			purchaserID = in.readLong();
		}
		purchaserName = in.readString();
		logoUrl = in.readString();
		linkman = in.readString();
		mobile = in.readString();
		groupArea = in.readString();
		customMnemonicCode = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		if (purchaserID == null) {
			dest.writeByte((byte) 0);
		} else {
			dest.writeByte((byte) 1);
			dest.writeLong(purchaserID);
		}
		dest.writeString(purchaserName);
		dest.writeString(logoUrl);
		dest.writeString(linkman);
		dest.writeString(mobile);
		dest.writeString(groupArea);
		dest.writeString(customMnemonicCode);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<PurchaserGroupBean> CREATOR = new Creator<PurchaserGroupBean>() {
		@Override
		public PurchaserGroupBean createFromParcel(Parcel in) {
			return new PurchaserGroupBean(in);
		}

		@Override
		public PurchaserGroupBean[] newArray(int size) {
			return new PurchaserGroupBean[size];
		}
	};

	public Long getPurchaserID() {
		return purchaserID;
	}

	public void setPurchaserID(Long purchaserID) {
		this.purchaserID = purchaserID;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGroupArea() {
		return groupArea;
	}

	public void setGroupArea(String groupArea) {
		this.groupArea = groupArea;
	}

	public String getCustomMnemonicCode() {
		return customMnemonicCode;
	}

	public void setCustomMnemonicCode(String customMnemonicCode) {
		this.customMnemonicCode = customMnemonicCode;
	}
}