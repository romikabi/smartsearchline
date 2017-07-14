package com.aita.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.aita.helpers.MainHelper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Airport implements Parcelable {
    public static final Creator<Airport> CREATOR = new Creator<Airport>() {
        public Airport[] newArray(int size) {
            return new Airport[size];
        }

        public Airport createFromParcel(Parcel source) {
            return new Airport(source);
        }
    };
    private static final List<String> countryCodeList = Arrays.asList(
            "AF",
            "AX",
            "AL",
            "DZ",
            "AS",
            "AD",
            "AO",
            "AI",
            "AQ",
            "AG",
            "AR",
            "AM",
            "AW",
            "AU",
            "AT",
            "AZ",
            "BS",
            "BH",
            "BD",
            "BB",
            "BY",
            "BE",
            "BZ",
            "BJ",
            "BM",
            "BT",
            "BO",
            "BA",
            "BW",
            "BV",
            "BR",
            "IO",
            "VG",
            "BN",
            "BG",
            "BF",
            "BI",
            "KH",
            "CM",
            "CA",
            "CV",
            "KY",
            "CF",
            "TD",
            "CL",
            "CN",
            "CX",
            "CC",
            "CO",
            "KM",
            "CD",
            "CG",
            "CK",
            "CR",
            "CI",
            "HR",
            "CU",
            "CY",
            "CZ",
            "DK",
            "DJ",
            "DM",
            "DO",
            "EC",
            "EG",
            "SV",
            "GQ",
            "ER",
            "EE",
            "ET",
            "FO",
            "FK",
            "FJ",
            "FI",
            "FR",
            "GF",
            "PF",
            "TF",
            "GA",
            "GM",
            "GE",
            "DE",
            "GH",
            "GI",
            "GR",
            "GL",
            "GD",
            "GP",
            "GU",
            "GT",
            "GG",
            "GN",
            "GW",
            "GY",
            "HT",
            "HM",
            "VA",
            "HN",
            "HK",
            "HU",
            "IS",
            "IN",
            "ID",
            "IR",
            "IQ",
            "IE",
            "IM",
            "IL",
            "IT",
            "JM",
            "JP",
            "JE",
            "JO",
            "KZ",
            "KE",
            "KI",
            "KP",
            "KR",
            "KW",
            "KG",
            "LA",
            "LV",
            "LB",
            "LS",
            "LR",
            "LY",
            "LI",
            "LT",
            "LU",
            "MO",
            "MK",
            "MG",
            "MW",
            "MY",
            "MV",
            "ML",
            "MT",
            "MH",
            "MQ",
            "MR",
            "MU",
            "YT",
            "MX",
            "FM",
            "MD",
            "MC",
            "MN",
            "ME",
            "MS",
            "MA",
            "MZ",
            "MM",
            "NA",
            "NR",
            "NP",
            "AN",
            "NL",
            "NC",
            "NZ",
            "NI",
            "NE",
            "NG",
            "NU",
            "NF",
            "MP",
            "NO",
            "OM",
            "PK",
            "PW",
            "PS",
            "PA",
            "PG",
            "PY",
            "PE",
            "PH",
            "PN",
            "PL",
            "PT",
            "PR",
            "QA",
            "RE",
            "RO",
            "RU",
            "RW",
            "BL",
            "SH",
            "KN",
            "LC",
            "MF",
            "PM",
            "VC",
            "WS",
            "SM",
            "ST",
            "SA",
            "SN",
            "RS",
            "SC",
            "SL",
            "SG",
            "SK",
            "SI",
            "SB",
            "SO",
            "ZA",
            "GS",
            "ES",
            "LK",
            "SD",
            "SR",
            "SJ",
            "SZ",
            "SE",
            "CH",
            "SY",
            "TW",
            "TJ",
            "TZ",
            "TH",
            "TL",
            "TG",
            "TK",
            "TO",
            "TT",
            "TN",
            "TR",
            "TM",
            "TC",
            "TV",
            "UG",
            "UA",
            "AE",
            "GB",
            "US",
            "UM",
            "VI",
            "UY",
            "UZ",
            "VU",
            "VE",
            "VN",
            "WF",
            "EH",
            "YE",
            "ZM",
            "ZW"
    );
    private static final List<String> continentCodeList = Arrays.asList(
            "AS",
            "EU",
            "EU",
            "AF",
            "OC",
            "EU",
            "AF",
            "NA",
            "AN",
            "NA",
            "SA",
            "AS",
            "NA",
            "OC",
            "EU",
            "AS",
            "NA",
            "AS",
            "AS",
            "NA",
            "EU",
            "EU",
            "NA",
            "AF",
            "NA",
            "AS",
            "SA",
            "EU",
            "AF",
            "AN",
            "SA",
            "AS",
            "NA",
            "AS",
            "EU",
            "AF",
            "AF",
            "AS",
            "AF",
            "NA",
            "AF",
            "NA",
            "AF",
            "AF",
            "SA",
            "AS",
            "AS",
            "AS",
            "SA",
            "AF",
            "AF",
            "AF",
            "OC",
            "NA",
            "AF",
            "EU",
            "NA",
            "AS",
            "EU",
            "EU",
            "AF",
            "NA",
            "NA",
            "SA",
            "AF",
            "NA",
            "AF",
            "AF",
            "EU",
            "AF",
            "EU",
            "SA",
            "OC",
            "EU",
            "EU",
            "SA",
            "OC",
            "AN",
            "AF",
            "AF",
            "AS",
            "EU",
            "AF",
            "EU",
            "EU",
            "NA",
            "NA",
            "NA",
            "OC",
            "NA",
            "EU",
            "AF",
            "AF",
            "SA",
            "NA",
            "AN",
            "EU",
            "NA",
            "AS",
            "EU",
            "EU",
            "AS",
            "AS",
            "AS",
            "AS",
            "EU",
            "EU",
            "AS",
            "EU",
            "NA",
            "AS",
            "EU",
            "AS",
            "AS",
            "AF",
            "OC",
            "AS",
            "AS",
            "AS",
            "AS",
            "AS",
            "EU",
            "AS",
            "AF",
            "AF",
            "AF",
            "EU",
            "EU",
            "EU",
            "AS",
            "EU",
            "AF",
            "AF",
            "AS",
            "AS",
            "AF",
            "EU",
            "OC",
            "NA",
            "AF",
            "AF",
            "AF",
            "NA",
            "OC",
            "EU",
            "EU",
            "AS",
            "EU",
            "NA",
            "AF",
            "AF",
            "AS",
            "AF",
            "OC",
            "AS",
            "NA",
            "EU",
            "OC",
            "OC",
            "NA",
            "AF",
            "AF",
            "OC",
            "OC",
            "OC",
            "EU",
            "AS",
            "AS",
            "OC",
            "AS",
            "NA",
            "OC",
            "SA",
            "SA",
            "AS",
            "OC",
            "EU",
            "EU",
            "NA",
            "AS",
            "AF",
            "EU",
            "EU",
            "AF",
            "NA",
            "AF",
            "NA",
            "NA",
            "NA",
            "NA",
            "NA",
            "OC",
            "EU",
            "AF",
            "AS",
            "AF",
            "EU",
            "AF",
            "AF",
            "AS",
            "EU",
            "EU",
            "OC",
            "AF",
            "AF",
            "AN",
            "EU",
            "AS",
            "AF",
            "SA",
            "EU",
            "AF",
            "EU",
            "EU",
            "AS",
            "AS",
            "AS",
            "AF",
            "AS",
            "AS",
            "AF",
            "OC",
            "OC",
            "NA",
            "AF",
            "AS",
            "AS",
            "NA",
            "OC",
            "AF",
            "EU",
            "AS",
            "EU",
            "NA",
            "OC",
            "NA",
            "SA",
            "AS",
            "OC",
            "SA",
            "AS",
            "OC",
            "AF",
            "AS",
            "AF",
            "AF"
    );
    /*
     * Formed from "airport_arrival"&"airport_depurture" json
     */
    private String city;
    private String cityTranslated;
    private String countryFull;
    private String code;
    private String airportName;
    private String airportNameTranslated;
    private String url;
    private String countryCode;
    private double offset;
    private String phone;
    private double latitude;
    private double longitude;
    private String delay;
    private int tipsCount;
    private float checkinTime = 0;
    private float securityTime = 0;
    private float passportTime = 0;
    private float baggageTime = 0;
    private float passportArrivalTime = 0;
    private float customsArrivalTime = 0;
    private float rating;
    private int reportsCount;
    private boolean isNameTranslated = false;
    private boolean isCityTranslated = false;

    public Airport() {

    }

    public Airport(Parcel parcel) {
        city = parcel.readString();
        cityTranslated = parcel.readString();
        countryFull = parcel.readString();
        airportName = parcel.readString();
        airportNameTranslated = parcel.readString();
        countryCode = parcel.readString();
        offset = parcel.readDouble();
        url = parcel.readString();
        phone = parcel.readString();
        latitude = parcel.readDouble();
        longitude = parcel.readDouble();
        code = parcel.readString();
        delay = parcel.readString();
        tipsCount = parcel.readInt();

        checkinTime = parcel.readFloat();
        securityTime = parcel.readFloat();
        passportTime = parcel.readFloat();
        rating = parcel.readFloat();
        reportsCount = parcel.readInt();
        baggageTime = parcel.readFloat();
        passportArrivalTime = parcel.readFloat();
        customsArrivalTime = parcel.readFloat();
    }

    public Airport(String city, String countryFull, String airportName,
                   String countryCode, String code, String delay) {
        super();
        this.city = city;
        this.countryFull = countryFull;
        this.airportName = airportName;
        this.countryCode = countryCode;
        this.code = code;
        this.setDelay(delay);
    }

    public Airport(String city, String airportName, String code) {
        super();
        this.city = city;
        this.airportName = airportName;
        this.code = code;
    }

    public static int getContinentsCount(List<String> countryCodes) {
        Set<String> continents = new HashSet<>();
        for (String code : countryCodes) {
            if (code != null && !code.isEmpty() && countryCodeList.contains(code)) {
                continents.add(continentCodeList.get(countryCodeList.indexOf(code)));
            }
        }
        return continents.size();
    }

    public static Airport getDummyAirport(Context context) {
        Airport dummyAirport = new Airport(
                "", "", "Dummy", "", "XXX", "");
        dummyAirport.setAirportNameTranslated("Translated Dummy");
        dummyAirport.setCityTranslated("");
        dummyAirport.setCheckinTime(15.0f);
        dummyAirport.setPassportTime(15.0f);
        dummyAirport.setSecurityTime(15.0f);
        dummyAirport.setCountryCode("");
        dummyAirport.setLatitude(37.621262); // SFO airport's lat and lng
        dummyAirport.setLongitude(-122.378955);
        dummyAirport.setOffset(0.0);
        dummyAirport.setRating(0f);
        dummyAirport.setReportsCount(0);
        dummyAirport.setTipsCount(0);
        dummyAirport.setUrl("");
        dummyAirport.setPhone("");

        return dummyAirport;
    }

    public String getContinentCode() {
        if (countryCode != null && continentCodeList != null && !countryCode.isEmpty() && countryCodeList
                .contains(countryCode)) {
            int index = countryCodeList.indexOf(countryCode);
            return continentCodeList.get(index);
        } else {
            return "";
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(city);
        parcel.writeString(cityTranslated);
        parcel.writeString(countryFull);
        parcel.writeString(airportName);
        parcel.writeString(airportNameTranslated);
        parcel.writeString(countryCode);
        parcel.writeDouble(offset);
        parcel.writeString(url);
        parcel.writeString(phone);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(code);
        parcel.writeString(delay);
        parcel.writeInt(tipsCount);
        parcel.writeFloat(checkinTime);
        parcel.writeFloat(securityTime);
        parcel.writeFloat(passportTime);
        parcel.writeFloat(rating);
        parcel.writeInt(reportsCount);
        parcel.writeFloat(baggageTime);
        parcel.writeFloat(passportArrivalTime);
        parcel.writeFloat(customsArrivalTime);
    }

    public String getAirportRepresentation() {
        return this.getAirportName() + " (" + this.getCode() + ")";
    }

    public String getAirportRepresentationForGoogleSearch() {
        return String.format("%s, %s (%s)", airportName, city, code);
    }

    public String getAirportForTimelineList() {
        return String.format("%s, %s", this.getCity(), this.getCountryCode());
    }

    public String getTranslatedAirportForTimelineList() {
        return String.format("%s, %s", this.getCityTranslated(), this.getCountryCode());
    }

    public String getAirportForSearch() {
        return String.format("%s%s%s", getAirportName(), getCity(), getCode()).toLowerCase();
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryFull() {
        return countryFull;
    }

    public void setCountryFull(String countryFull) {
        this.countryFull = countryFull;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSearchString() {
        return (airportName + " (" + code + ")");
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public int getTipsCount() {
        return tipsCount;
    }

    public void setTipsCount(int tipsCount) {
        this.tipsCount = tipsCount;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public int getReportsCount() {
        return reportsCount;
    }

    public void setReportsCount(int reportsCount) {
        this.reportsCount = reportsCount;
    }

    public int getPassportTime() {
        return Math.round(passportTime);
    }

    public void setPassportTime(float passportTime) {
        this.passportTime = passportTime;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * Actually it is "time_customs", not "time_security" ( ͡° ͜ʖ ͡°)
     *
     * @return minutes
     */
    public int getSecurityTime() {
        return Math.round(securityTime);
    }

    public void setSecurityTime(float securityTime) {
        this.securityTime = securityTime;
    }

    public int getCheckinTime() {
        return Math.round(checkinTime);
    }

    public void setCheckinTime(float checkinTime) {
        this.checkinTime = checkinTime;
    }

    public int getBaggageTime() {
        return Math.round(baggageTime);
    }

    public void setBaggageTime(float baggageTime) {
        this.baggageTime = baggageTime;
    }

    public int getPassportArrivalTime() {
        return Math.round(passportArrivalTime);
    }

    public void setPassportArrivalTime(float passportArrivalTime) {
        this.passportArrivalTime = passportArrivalTime;
    }

    public int getCustomsArrivalTime() {
        return Math.round(customsArrivalTime);
    }

    public void setCustomsArrivalTime(float customsArrivalTime) {
        this.customsArrivalTime = customsArrivalTime;
    }

    public boolean isTranslated() {
        return isNameTranslated || isCityTranslated;
    }

    public String getCityTranslated() {
        if (isCityTranslated) {
            return cityTranslated;
        } else {
            return city;
        }
    }

    public void setCityTranslated(String cityTranslated) {
        if (!MainHelper.isDummyOrNull(cityTranslated)) {
            isCityTranslated = true;
            this.cityTranslated = cityTranslated.replace("$", "");
        }
    }

    public String getAirportNameTranslated() {
        if (isNameTranslated) {
            return airportNameTranslated;
        } else {
            return airportName;
        }
    }

    public void setAirportNameTranslated(String airportNameTranslated) {
        if (!MainHelper.isDummyOrNull(airportNameTranslated)) {
            isNameTranslated = true;
            this.airportNameTranslated = airportNameTranslated.replace("$", "");
        }
    }

    @Override
    public String toString() {
        return "Airport{" +
                "city='" + city + '\'' +
                ", cityTranslated='" + cityTranslated + '\'' +
                ", countryFull='" + countryFull + '\'' +
                ", code='" + code + '\'' +
                ", airportName='" + airportName + '\'' +
                ", airportNameTranslated='" + airportNameTranslated + '\'' +
                ", url='" + url + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", offset=" + offset +
                ", phone='" + phone + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", delay='" + delay + '\'' +
                ", tipsCount=" + tipsCount +
                ", checkinTime=" + checkinTime +
                ", securityTime=" + securityTime +
                ", passportTime=" + passportTime +
                ", rating=" + rating +
                ", reportsCount=" + reportsCount +
                ", isNameTranslated=" + isNameTranslated +
                ", isCityTranslated=" + isCityTranslated +
                '}';
    }

}
