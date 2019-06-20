package com.hll_sc_app.bean.goods;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 规格的 Bean
 *
 * @author zhuyingsong
 * @date 2019-06-11
 */
public class NicknamesBean implements Parcelable {
    /**
     * 类别（1-商品名称，2-昵称）
     */
    private String nicknameType;
    /**
     * 商品别称
     */
    private String nickname;

    public String getNicknameType() {
        return nicknameType;
    }

    public void setNicknameType(String nicknameType) {
        this.nicknameType = nicknameType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public static final Parcelable.Creator<NicknamesBean> CREATOR = new Parcelable.Creator<NicknamesBean>() {
        @Override
        public NicknamesBean createFromParcel(Parcel source) {
            return new NicknamesBean(source);
        }

        @Override
        public NicknamesBean[] newArray(int size) {
            return new NicknamesBean[size];
        }
    };

    public NicknamesBean() {
    }

    protected NicknamesBean(Parcel in) {
        this.nicknameType = in.readString();
        this.nickname = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nicknameType);
        dest.writeString(this.nickname);
    }
}
