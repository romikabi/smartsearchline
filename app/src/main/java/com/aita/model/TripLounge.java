package com.aita.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tim on 25-Oct-16.
 */

public class TripLounge implements Parcelable {
    @SuppressWarnings("unused")
    public static final Creator<TripLounge> CREATOR = new Creator<TripLounge>() {
        @Override
        public TripLounge createFromParcel(Parcel in) {
            return new TripLounge(in);
        }

        @Override
        public TripLounge[] newArray(int size) {
            return new TripLounge[size];
        }
    };
    private String currency;
    private int people;
    private int totalSum;
    private long bookingDate;
    private long bookingDateUtc;
    private JSONObject bookingType;
    private String codeUrls;
    private String source;
    private String sourceId;
    private String paymentId;
    private String tripId;
    private boolean canceled;
    private int price;

    public TripLounge(String currency, int people, int totalSum, long bookingDate, long bookingDateUtc,
                      String codeUrls, boolean canceled) {
        this.currency = currency;
        this.people = people;
        this.totalSum = totalSum;
        this.bookingDate = bookingDate;
        this.bookingDateUtc = bookingDateUtc;
        this.codeUrls = codeUrls;
        this.canceled = canceled;
    }

    protected TripLounge(Parcel in) {
        currency = in.readString();
        people = in.readInt();
        totalSum = in.readInt();
        bookingDate = in.readLong();
        bookingDateUtc = in.readLong();
        try {
            bookingType = new JSONObject(in.readString());
        } catch (JSONException e) {

        }
        codeUrls = in.readString();
        source = in.readString();
        sourceId = in.readString();
        paymentId = in.readString();
        tripId = in.readString();
        canceled = in.readByte() != 0x00;
        price = in.readInt();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    public long getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(long bookingDate) {
        this.bookingDate = bookingDate;
    }

    public long getBookingDateUtc() {
        return bookingDateUtc;
    }

    public void setBookingDateUtc(long bookingDateUtc) {
        this.bookingDateUtc = bookingDateUtc;
    }

    public JSONObject getBookingType() {
        return bookingType;
    }

    public void setBookingType(JSONObject bookingType) {
        this.bookingType = bookingType;
    }

    public String getCodeUrls() {
        return codeUrls;
    }

    public void setCodeUrls(String codeUrls) {
        this.codeUrls = codeUrls;
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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "TripLounge{" +
                "currency='" + currency + '\'' +
                ", people=" + people +
                ", totalSum=" + totalSum +
                ", bookingDate=" + bookingDate +
                ", bookingDateUtc=" + bookingDateUtc +
                ", bookingType=" + bookingType +
                ", codeUrls='" + codeUrls + '\'' +
                ", source='" + source + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", tripId='" + tripId + '\'' +
                ", canceled=" + canceled +
                ", price=" + price +
                '}';
    }

    public String getTripId() {
        return tripId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currency);
        dest.writeInt(people);
        dest.writeInt(totalSum);
        dest.writeLong(bookingDate);
        dest.writeLong(bookingDateUtc);
        if (bookingType != null) {
            dest.writeString(bookingType.toString());
        } else {
            dest.writeString(null);
        }
        dest.writeString(codeUrls);
        dest.writeString(source);
        dest.writeString(sourceId);
        dest.writeString(paymentId);
        dest.writeString(tripId);
        dest.writeByte((byte) (canceled ? 0x01 : 0x00));
        dest.writeInt(price);
    }
}