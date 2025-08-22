package com.wizarpos.payment.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class ParamsResponse implements Parcelable {
    private String RespCode;

    public ParamsResponse(String respCode) {
            RespCode = respCode;
    }

    protected ParamsResponse(Parcel in) {
        RespCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(RespCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParamsResponse> CREATOR = new Creator<ParamsResponse>() {
        @Override
        public ParamsResponse createFromParcel(Parcel in) {
            return new ParamsResponse(in);
        }

        @Override
        public ParamsResponse[] newArray(int size) {
            return new ParamsResponse[size];
        }
    };
}
