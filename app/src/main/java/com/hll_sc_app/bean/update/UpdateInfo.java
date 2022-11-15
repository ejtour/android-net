package com.hll_sc_app.bean.update;

import static com.liulishuo.okdownload.DownloadTask.Builder.DEFAULT_PASS_IF_ALREADY_COMPLETED;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 更新信息
 *
 */
public class UpdateInfo implements Parcelable {

   /**
    * 标识应用
    */
   private String appKey;
   /**
    * 应用ID
    */
   private String appID;
   /**
    * 应用版本号
    */
   private int versionCode;
   /**
    * 版本名称
    */
   private String versionName;
   /**
    * 升级描述
    */
   private String versionDesc;
   /**
    * 安装包大小
    */
   private long packageSize;
   /**
    * 0 非强制，1 强制
    */
   private int force;
   /**
    * 下载地址
    */
   private String appUrl;
   /**
    * 系统
    */
   private String system;
   /**
    * 指定目标版本
    */
   private int targetVersion;
   /**
    * 最低强制升级版本
    */
   private int minVersion;

   private boolean passIfAlreadyCompleted = DEFAULT_PASS_IF_ALREADY_COMPLETED;

   public String getAppKey() {
      return appKey;
   }

   public void setAppKey(String appKey) {
      this.appKey = appKey;
   }

   public String getAppID() {
      return appID;
   }

   public void setAppID(String appID) {
      this.appID = appID;
   }

   public int getVersionCode() {
      return versionCode;
   }

   public void setVersionCode(int versionCode) {
      this.versionCode = versionCode;
   }

   public String getVersionName() {
      return versionName;
   }

   public void setVersionName(String versionName) {
      this.versionName = versionName;
   }

   public String getVersionDesc() {
      return versionDesc;
   }

   public void setVersionDesc(String versionDesc) {
      this.versionDesc = versionDesc;
   }

   public long getPackageSize() {
      return packageSize;
   }

   public void setPackageSize(long packageSize) {
      this.packageSize = packageSize;
   }

   public int getForce() {
      return force;
   }

   public void setForce(int force) {
      this.force = force;
   }

   public String getAppUrl() {
      return appUrl;
   }

   public void setAppUrl(String appUrl) {
      this.appUrl = appUrl;
   }

   public String getSystem() {
      return system;
   }

   public void setSystem(String system) {
      this.system = system;
   }

   public int getTargetVersion() {
      return targetVersion;
   }

   public void setTargetVersion(int targetVersion) {
      this.targetVersion = targetVersion;
   }

   public int getMinVersion() {
      return minVersion;
   }

   public void setMinVersion(int minVersion) {
      this.minVersion = minVersion;
   }

   public boolean isPassIfAlreadyCompleted() {
      return passIfAlreadyCompleted;
   }

   public void setPassIfAlreadyCompleted(boolean passIfAlreadyCompleted) {
      this.passIfAlreadyCompleted = passIfAlreadyCompleted;
   }


   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.appKey);
      dest.writeString(this.appID);
      dest.writeInt(this.versionCode);
      dest.writeString(this.versionName);
      dest.writeString(this.versionDesc);
      dest.writeLong(this.packageSize);
      dest.writeInt(this.force);
      dest.writeString(this.appUrl);
      dest.writeString(this.system);
      dest.writeInt(this.targetVersion);
      dest.writeInt(this.minVersion);
      dest.writeByte(this.passIfAlreadyCompleted ? (byte) 1 : (byte) 0);
   }

   public void readFromParcel(Parcel source) {
      this.appKey = source.readString();
      this.appID = source.readString();
      this.versionCode = source.readInt();
      this.versionName = source.readString();
      this.versionDesc = source.readString();
      this.packageSize = source.readLong();
      this.force = source.readInt();
      this.appUrl = source.readString();
      this.system = source.readString();
      this.targetVersion = source.readInt();
      this.minVersion = source.readInt();
      this.passIfAlreadyCompleted = source.readByte() != 0;
   }

   public UpdateInfo() {
   }

   protected UpdateInfo(Parcel in) {
      this.appKey = in.readString();
      this.appID = in.readString();
      this.versionCode = in.readInt();
      this.versionName = in.readString();
      this.versionDesc = in.readString();
      this.packageSize = in.readLong();
      this.force = in.readInt();
      this.appUrl = in.readString();
      this.system = in.readString();
      this.targetVersion = in.readInt();
      this.minVersion = in.readInt();
      this.passIfAlreadyCompleted = in.readByte() != 0;
   }

   public static final Creator<UpdateInfo> CREATOR = new Creator<UpdateInfo>() {
      @Override
      public UpdateInfo createFromParcel(Parcel source) {
         return new UpdateInfo(source);
      }

      @Override
      public UpdateInfo[] newArray(int size) {
         return new UpdateInfo[size];
      }
   };
}

