package com.aita.model;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("unused")
public class Airline implements Parcelable {

    public static final Creator<Airline> CREATOR = new Creator<Airline>() {
        public Airline[] newArray(int size) {
            return new Airline[size];
        }

        public Airline createFromParcel(Parcel source) {
            return new Airline(source);
        }
    };

    private int checkinCloseInternational;
    private int checkinOpenHrs;
    private int checkinCloseDomestic;
    private String id;
    private String iata;
    private String icao;
    private String twitter;
    private String email;
    private String phone;
    private String website;
    private String code;
    private String innerCode;
    private String name;
    private String nameTranslated;
    private boolean isNameTranslated = false;
    private String webCheckin;
    private String mobileCheckin;
    private boolean checkinAvailable;
    private String baggageUrl;
    private double foodRate = 0;
    private double serviceRate = 0;
    private double staffRate = 0;
    private double rating;
    private int reportsCount;
    private int minutesTillBoarding;

    public Airline() {
    }

    public Airline(String code, String name) {
        this.code = code;
        this.innerCode = this.code;
        this.name = name;
    }

    public Airline(Parcel parcel) {
        code = parcel.readString();
        name = parcel.readString();
        nameTranslated = parcel.readString();
        if (nameTranslated != null && !nameTranslated.isEmpty()) {
            isNameTranslated = true;
        }
        minutesTillBoarding = parcel.readInt();
        innerCode = parcel.readString();
        id = parcel.readString();
        iata = parcel.readString();
        icao = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(code);
        parcel.writeString(name);
        parcel.writeString(nameTranslated);
        parcel.writeInt(minutesTillBoarding);
        parcel.writeString(innerCode);
        parcel.writeString(id);
        parcel.writeString(iata);
        parcel.writeString(icao);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Airline airline = (Airline) o;

        if (checkinCloseInternational != airline.checkinCloseInternational) {
            return false;
        }
        if (checkinOpenHrs != airline.checkinOpenHrs) {
            return false;
        }
        if (checkinCloseDomestic != airline.checkinCloseDomestic) {
            return false;
        }
        if (isNameTranslated != airline.isNameTranslated) {
            return false;
        }
        if (checkinAvailable != airline.checkinAvailable) {
            return false;
        }
        if (Double.compare(airline.foodRate, foodRate) != 0) {
            return false;
        }
        if (Double.compare(airline.serviceRate, serviceRate) != 0) {
            return false;
        }
        if (Double.compare(airline.staffRate, staffRate) != 0) {
            return false;
        }
        if (Double.compare(airline.rating, rating) != 0) {
            return false;
        }
        if (reportsCount != airline.reportsCount) {
            return false;
        }
        if (minutesTillBoarding != airline.minutesTillBoarding) {
            return false;
        }
        if (id != null ? !id.equals(airline.id) : airline.id != null) {
            return false;
        }
        if (iata != null ? !iata.equals(airline.iata) : airline.iata != null) {
            return false;
        }
        if (icao != null ? !icao.equals(airline.icao) : airline.icao != null) {
            return false;
        }
        if (twitter != null ? !twitter.equals(airline.twitter) : airline.twitter != null) {
            return false;
        }
        if (email != null ? !email.equals(airline.email) : airline.email != null) {
            return false;
        }
        if (phone != null ? !phone.equals(airline.phone) : airline.phone != null) {
            return false;
        }
        if (website != null ? !website.equals(airline.website) : airline.website != null) {
            return false;
        }
        if (code != null ? !code.equals(airline.code) : airline.code != null) {
            return false;
        }
        if (innerCode != null ? !innerCode.equals(airline.innerCode) : airline.innerCode != null) {
            return false;
        }
        if (name != null ? !name.equals(airline.name) : airline.name != null) {
            return false;
        }
        if (nameTranslated != null ? !nameTranslated
                .equals(airline.nameTranslated) : airline.nameTranslated != null) {
            return false;
        }
        if (webCheckin != null ? !webCheckin.equals(airline.webCheckin) : airline.webCheckin != null) {
            return false;
        }
        if (mobileCheckin != null ? !mobileCheckin
                .equals(airline.mobileCheckin) : airline.mobileCheckin != null) {
            return false;
        }
        return baggageUrl != null ? baggageUrl.equals(airline.baggageUrl) : airline.baggageUrl == null;
    }

    @Override
    public String toString() {
        return "Airline{" +
                "checkinCloseInternational=" + checkinCloseInternational +
                ", checkinOpenHrs=" + checkinOpenHrs +
                ", checkinCloseDomestic=" + checkinCloseDomestic +
                ", id='" + id + '\'' +
                ", iata='" + iata + '\'' +
                ", icao='" + icao + '\'' +
                ", twitter='" + twitter + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", code='" + code + '\'' +
                ", innerCode='" + innerCode + '\'' +
                ", name='" + name + '\'' +
                ", nameTranslated='" + nameTranslated + '\'' +
                ", isNameTranslated=" + isNameTranslated +
                ", webCheckin='" + webCheckin + '\'' +
                ", mobileCheckin='" + mobileCheckin + '\'' +
                ", checkinAvailable=" + checkinAvailable +
                ", baggageUrl='" + baggageUrl + '\'' +
                ", foodRate=" + foodRate +
                ", serviceRate=" + serviceRate +
                ", staffRate=" + staffRate +
                ", rating=" + rating +
                ", reportsCount=" + reportsCount +
                ", minutesTillBoarding=" + minutesTillBoarding +
                '}';
    }

    public String getAllInfo() {
        return String.format("%s %s", this.code, this.name);
    }

    public String getFeedInfo() {
        return String.format(
                "%s (%s)",
                isNameTranslated ? this.nameTranslated : this.name,
                this.code
        );
    }

    public String getCode() {
        final String code;
        if (iata != null && !iata.isEmpty()) {
            code = iata;
        } else if (icao != null && !icao.isEmpty()) {
            code = icao;
        } else if (innerCode != null && !innerCode.isEmpty()) {
            code = innerCode;
        } else {
            code = this.code;
        }
        return code;
    }

    public void setCode(String airlineCode) {
        this.code = airlineCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String airlineName) {
        this.name = airlineName;
    }

    public String getMobileCheckin() {
        return mobileCheckin;
    }

    public void setMobileCheckin(String mobileCheckin) {
        this.mobileCheckin = mobileCheckin;
    }

    public boolean isCheckinAvailable() {
        return checkinAvailable;
    }

    public void setCheckinAvailable(boolean checkinAvailable) {
        this.checkinAvailable = checkinAvailable;
    }

    public String getWebCheckin() {
        return webCheckin;
    }

    public void setWebCheckin(String webCheckin) {
        this.webCheckin = webCheckin;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNameTranslated() {
        return nameTranslated;
    }

    public void setNameTranslated(String nameTranslated) {
        isNameTranslated = true;
        this.nameTranslated = nameTranslated.replace("$", "");
    }

    public boolean isTranslated() {
        return isNameTranslated;
    }

    public String getBaggageUrl() {
        return baggageUrl;
    }

    public void setBaggageUrl(String baggageUrl) {
        this.baggageUrl = baggageUrl;
    }

    public int getCheckinCloseInternational() {
        return checkinCloseInternational;
    }

    public void setCheckinCloseInternational(int checkinCloseInternational) {
        this.checkinCloseInternational = checkinCloseInternational;
    }

    public int getCheckinOpenHrs() {
        return checkinOpenHrs;
    }

    public void setCheckinOpenHrs(int checkinOpenHrs) {
        this.checkinOpenHrs = checkinOpenHrs;
    }

    public int getCheckinCloseDomestic() {
        return checkinCloseDomestic;
    }

    public void setCheckinCloseDomestic(int checkinCloseDomestic) {
        this.checkinCloseDomestic = checkinCloseDomestic;
    }

    public double getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(double foodRate) {
        this.foodRate = foodRate;
    }

    public double getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(double serviceRate) {
        this.serviceRate = serviceRate;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getStaffRate() {
        return staffRate;
    }

    public void setStaffRate(double staffRate) {
        this.staffRate = staffRate;
    }

    public int getReportsCount() {
        return reportsCount;
    }

    public void setReportsCount(int reportsCount) {
        this.reportsCount = reportsCount;
    }

    public int getMinutesTillBoarding() {
        return (minutesTillBoarding > 0) ? minutesTillBoarding : 20;
    }

    public void setMinutesTillBoarding(int minutesTillBoarding) {
        this.minutesTillBoarding = minutesTillBoarding;
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}