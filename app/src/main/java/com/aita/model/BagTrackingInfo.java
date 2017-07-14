package com.aita.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.List;

public class BagTrackingInfo implements Parcelable {

    public static final Creator<BagTrackingInfo> CREATOR = new Creator<BagTrackingInfo>() {
        @Override
        public BagTrackingInfo createFromParcel(Parcel source) {
            return new BagTrackingInfo(source);
        }

        @Override
        public BagTrackingInfo[] newArray(int size) {
            return new BagTrackingInfo[size];
        }
    };
    private final String passengerName;
    private final List<BagEvent> bagEventList;
    private final List<String> bagPhotoPathsList;

    public BagTrackingInfo(@NonNull String passengerName,
                           @NonNull List<BagEvent> bagEventList,
                           @NonNull List<String> bagPhotoPathsList) {
        this.passengerName = passengerName;
        this.bagEventList = bagEventList;
        this.bagPhotoPathsList = bagPhotoPathsList;
    }

    private BagTrackingInfo(Parcel in) {
        this.passengerName = in.readString();
        this.bagEventList = in.createTypedArrayList(BagEvent.CREATOR);
        this.bagPhotoPathsList = in.createStringArrayList();
    }

    public String getPassengerName() {
        return passengerName;
    }

    public List<BagEvent> getBagEventList() {
        return bagEventList;
    }

    public List<String> getBagPhotoPathsList() {
        return bagPhotoPathsList;
    }

    public void addBagPhotoPath(@NonNull String bagPhotoPath) {
        bagPhotoPathsList.add(0, bagPhotoPath);
    }

    public void removeBagPhotoPath(@NonNull String bagPhotoPath) {
        Iterator<String> iterator = bagPhotoPathsList.iterator();
        while (iterator.hasNext()) {
            String path = iterator.next();
            if (path.equals(bagPhotoPath)) {
                iterator.remove();
                break;
            }
        }
    }

    public String formatPassengerName(Context context) {
        return passengerName;
    }

    @Override
    public int hashCode() {
        int result = passengerName != null ? passengerName.hashCode() : 0;
        result = 31 * result + (bagEventList != null ? bagEventList.hashCode() : 0);
        result = 31 * result + (bagPhotoPathsList != null ? bagPhotoPathsList.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.passengerName);
        dest.writeTypedList(this.bagEventList);
        dest.writeStringList(this.bagPhotoPathsList);
    }

}
