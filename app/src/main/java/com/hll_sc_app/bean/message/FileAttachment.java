package com.hll_sc_app.bean.message;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/16/20.
 */
public class FileAttachment implements Parcelable {
    private String fileName;
    private String fileUrl;

    protected FileAttachment(Parcel in) {
        fileName = in.readString();
        fileUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileName);
        dest.writeString(fileUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FileAttachment> CREATOR = new Creator<FileAttachment>() {
        @Override
        public FileAttachment createFromParcel(Parcel in) {
            return new FileAttachment(in);
        }

        @Override
        public FileAttachment[] newArray(int size) {
            return new FileAttachment[size];
        }
    };

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
