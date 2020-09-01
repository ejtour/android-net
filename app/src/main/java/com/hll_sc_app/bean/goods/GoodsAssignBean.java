package com.hll_sc_app.bean.goods;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hll_sc_app.app.goods.assign.GoodsAssignType;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/25
 */
public class GoodsAssignBean implements Parcelable {
    public static final Creator<GoodsAssignBean> CREATOR = new Creator<GoodsAssignBean>() {
        @Override
        public GoodsAssignBean createFromParcel(Parcel in) {
            return new GoodsAssignBean(in);
        }

        @Override
        public GoodsAssignBean[] newArray(int size) {
            return new GoodsAssignBean[size];
        }
    };
    private String id;
    private int productNum;
    private String purchaserID;
    private String purchaserName;
    private String purchaserShopIDs;
    private List<GoodsAssignDetailBean> productList;
    private GoodsAssignType assignType;

    public GoodsAssignBean() {
    }

    protected GoodsAssignBean(Parcel in) {
        id = in.readString();
        productNum = in.readInt();
        purchaserID = in.readString();
        purchaserName = in.readString();
        purchaserShopIDs = in.readString();
        productList = in.createTypedArrayList(GoodsAssignDetailBean.CREATOR);
        assignType = (GoodsAssignType) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(productNum);
        dest.writeString(purchaserID);
        dest.writeString(purchaserName);
        dest.writeString(purchaserShopIDs);
        dest.writeTypedList(productList);
        dest.writeSerializable(assignType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public GoodsAssignReq convertToReq() {
        GoodsAssignReq req = new GoodsAssignReq();
        req.setGroupID(UserConfig.getGroupID());
        req.setFlag(TextUtils.isEmpty(id) ? 1 : 2);
        req.setMainID(id);
        req.setPurchaserID(purchaserID);
        req.setPurchaserName(purchaserName);
        req.setPurchaserShopIDs(Arrays.asList(purchaserShopIDs.split(",")));
        req.setType(assignType.getType());
        if (!CommonUtils.isEmpty(productList)) {
            List<GoodsAssignReq.GoodsAssignProductReq> list = new ArrayList<>();
            req.setDetails(list);
            for (GoodsAssignDetailBean bean : productList) {
                GoodsAssignReq.GoodsAssignProductReq productReq = new GoodsAssignReq.GoodsAssignProductReq();
                list.add(productReq);
                productReq.setProductID(bean.getProductID());
                List<String> ids = new ArrayList<>();
                productReq.setSpecIDList(ids);
                for (GoodsAssignSpecBean spec : bean.getSpecs()) {
                    ids.add(spec.getSpecID());
                }
            }
        }
        return req;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getPurchaserShopIDs() {
        return purchaserShopIDs;
    }

    public void setPurchaserShopIDs(String purchaserShopIDs) {
        this.purchaserShopIDs = purchaserShopIDs;
    }

    public List<GoodsAssignDetailBean> getProductList() {
        return productList;
    }

    public void setProductList(List<GoodsAssignDetailBean> productList) {
        this.productList = productList;
    }

    public GoodsAssignType getAssignType() {
        return assignType;
    }

    public void setAssignType(GoodsAssignType assignType) {
        this.assignType = assignType;
    }
}
