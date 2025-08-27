package com.wizarpos.payment.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class ParamsRequest implements Parcelable {
   private boolean AppIconVisible = true;
   private String LanguageCodes;
   private boolean enableBinListCheck = true;

   public ParamsRequest(){}

   protected ParamsRequest(Parcel in) {
      AppIconVisible =  in.readByte() != 0;
      LanguageCodes = in.readString();
      enableBinListCheck =  in.readByte() != 0;
   }


   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeByte((byte) (AppIconVisible ? 1 : 0));
      dest.writeString(LanguageCodes);
      dest.writeByte((byte) (enableBinListCheck ? 1 : 0));
   }

   @Override
   public int describeContents() {
      return 0;
   }



   public static final Creator<ParamsRequest> CREATOR = new Creator<ParamsRequest>() {
      @Override
      public ParamsRequest createFromParcel(Parcel in) {
         return new ParamsRequest(in);
      }

      @Override
      public ParamsRequest[] newArray(int size) {
         return new ParamsRequest[size];
      }
   };


   public boolean isAppIconVisible() {
      return AppIconVisible;
   }

   public void setAppIconVisible(boolean appIconVisible) {
      AppIconVisible = appIconVisible;
   }

   public String getLanguageCodes() {
      return LanguageCodes;
   }

   public void setLanguageCodes(String languageCodes) {
      LanguageCodes = languageCodes;
   }

   public boolean isEnableBinListCheck() {
      return enableBinListCheck;
   }

   public void setEnableBinListCheck(boolean enableBinListCheck) {
      this.enableBinListCheck = enableBinListCheck;
   }

   @Override
   public String toString() {
      return "ParamsRequest{" +
          "AppIconVisible=" + AppIconVisible +
          ", LanguageCodes='" + LanguageCodes + '\'' +
          ", enableBinListCheck=" + enableBinListCheck +
          '}';
   }
}
