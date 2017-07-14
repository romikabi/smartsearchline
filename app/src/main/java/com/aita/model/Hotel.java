package com.aita.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tim on 13-Oct-16.
 */

public class Hotel implements Parcelable {
    @SuppressWarnings("unused")
    public static final Creator<Hotel> CREATOR = new Creator<Hotel>() {
        @Override
        public Hotel createFromParcel(Parcel in) {
            return new Hotel(in);
        }

        @Override
        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };
    private String source;
    private String sourceId;
    private String id;
    private String tripId;
    private String passbookUrl;
    private String imageUrl;
    private String name;
    private String address;
    private String phone;
    private String city;
    private long dateFrom;
    private long dateTo;
    private int guests;
    private int stars;

    public Hotel(String source, String sourceId, String id, String tripId, String passbookUrl, long dateFrom,
                 long dateTo, int guests, String imageUrl, String name, int stars, String address,
                 String phone, String city) {
        this.source = source;
        this.sourceId = sourceId;
        this.id = id;
        this.tripId = tripId;
        this.passbookUrl = passbookUrl;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.guests = guests;
        this.imageUrl = imageUrl;
        this.name = name;
        this.stars = stars;
        this.address = address;
        this.phone = phone;
        this.city = city;
    }

    protected Hotel(Parcel in) {
        source = in.readString();
        sourceId = in.readString();
        id = in.readString();
        tripId = in.readString();
        passbookUrl = in.readString();
        imageUrl = in.readString();
        name = in.readString();
        address = in.readString();
        phone = in.readString();
        city = in.readString();
        dateFrom = in.readLong();
        dateTo = in.readLong();
        guests = in.readInt();
        stars = in.readInt();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getPassbookUrl() {
        return passbookUrl;
    }

    public void setPassbookUrl(String passbookUrl) {
        this.passbookUrl = passbookUrl;
    }

    public long getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(long dateFrom) {
        this.dateFrom = dateFrom;
    }

    public long getDateTo() {
        return dateTo;
    }

    public void setDateTo(long dateTo) {
        this.dateTo = dateTo;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(source);
        dest.writeString(sourceId);
        dest.writeString(id);
        dest.writeString(tripId);
        dest.writeString(passbookUrl);
        dest.writeString(imageUrl);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(phone);
        dest.writeString(city);
        dest.writeLong(dateFrom);
        dest.writeLong(dateTo);
        dest.writeInt(guests);
        dest.writeInt(stars);
    }
}