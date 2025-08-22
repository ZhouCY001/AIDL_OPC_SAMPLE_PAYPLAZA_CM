package com.wizarpos.payment.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class ParamsRequest  implements Parcelable {
   private boolean AppIconVisible = true;
   private String LanguageCodes;

   public ParamsRequest(){}

   protected ParamsRequest(Parcel in) {
      AppIconVisible =  in.readByte() != 0;
      LanguageCodes = in.readString();
   }


   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeByte((byte) (AppIconVisible ? 1 : 0));
      dest.writeString(LanguageCodes);
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

   @Override
   public String toString() {
      return "ParamsRequest{" +
          "AppIconVisible=" + AppIconVisible +
          ", LanguageCodes='" + LanguageCodes + '\'' +
          '}';
   }
}
