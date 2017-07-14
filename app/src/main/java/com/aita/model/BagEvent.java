package com.aita.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import org.json.JSONException;
import org.json.JSONObject;

public class BagEvent implements Parcelable {

    public static final Creator<BagEvent> CREATOR = new Creator<BagEvent>() {
        @Override
        public BagEvent createFromParcel(Parcel source) {
            return new BagEvent(source);
        }

        @Override
        public BagEvent[] newArray(int size) {
            return new BagEvent[size];
        }
    };
    private static final String JSON_KEY_AIRPORT = "airport";
    private static final String JSON_KEY_STATUS_CODE = "status_code";
    private static final String JSON_KEY_CREATED_AT = "created_at";
    private static final String CHECKED_IN = "CHECKED_IN";
    private static final String PASSENGER_BOARDED = "PAX_BOARDED";
    private static final String SCREENED = "SCREENED";
    private static final String SCREENING_PASSED = "SCREENING_PASSED";
    private static final String SCREENING_FAILED = "SCREENING_FAILED";
    private static final String SORTED = "SORTED";
    private static final String LOADED_IN_CONTAINER = "LOADED_IN_CONTAINER";
    private static final String LOADED_ON_AIRCRAFT = "LOADED_ON_AIRCRAFT";
    private static final String OFFLOADED = "OFFLOADED";
    private static final String EXPECTED = "EXPECTED";
    private static final String REROUTED = "REROUTED";
    private static final String REFLIGHTED = "REFLIGHTED";
    private static final String CANCELED = "CANCELLED";
    private static final String MISHANDLED = "MISHANDLED";
    private static final String ON_HAND_NOT_LOADED = "ONA";
    private static final String ON_HAND_NOT_LOADED_NOT_AUTHORIZED = "OND";
    private static final String BAG_LOADED = "NAL";
    private static final String BAG_UNSEEN = "UNS";
    private final String airportCode;
    private final String statusCode;
    private final long createdAt;

    public BagEvent(@NonNull String airportCode, @NonNull String statusCode, long createdAt) {
        this.airportCode = airportCode;
        this.statusCode = statusCode;
        this.createdAt = createdAt;
    }

    public BagEvent(@NonNull JSONObject bagEventJson) {
        airportCode = bagEventJson.optString(JSON_KEY_AIRPORT);
        statusCode = bagEventJson.optString(JSON_KEY_STATUS_CODE);
        createdAt = bagEventJson.optLong(JSON_KEY_CREATED_AT);
    }

    private BagEvent(Parcel in) {
        this.airportCode = in.readString();
        this.statusCode = in.readString();
        this.createdAt = in.readLong();
    }

    public String getAirportCode() {
        return airportCode;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    @StringRes
    public int getDescription() {
        //TODO: STUB
        return 0;
    }
/*

    public String formatEventAirport(Context context) {
        return context.getString(R.string.airport) + ": " + airportCode;
    }
*/

  /*  public String formatEventDate(Context context) {
        long dateMillis = TimeUnit.SECONDS.toMillis(createdAt);
        long dateMillisWithOffset = dateMillis + TimeZone.getDefault().getRawOffset();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        return String.format("%s %s",
                MainHelper.formatLocaleDate(context, dateMillisWithOffset),
                sdf.format(new Date(dateMillisWithOffset)));
    }*/

    @NonNull
    JSONObject toJson() throws JSONException {
        JSONObject bagEventJson = new JSONObject();
        bagEventJson.put(JSON_KEY_AIRPORT, airportCode);
        bagEventJson.put(JSON_KEY_STATUS_CODE, statusCode);
        bagEventJson.put(JSON_KEY_CREATED_AT, createdAt);
        return bagEventJson;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public int hashCode() {
        int result = airportCode != null ? airportCode.hashCode() : 0;
        result = 31 * result + (statusCode != null ? statusCode.hashCode() : 0);
        result = 31 * result + (int) (createdAt ^ (createdAt >>> 32));
        return result;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.airportCode);
        dest.writeString(this.statusCode);
        dest.writeLong(this.createdAt);
    }

}