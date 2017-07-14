package com.aita.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Aircraft implements Parcelable {

    public static final Creator<Aircraft> CREATOR = new Creator<Aircraft>() {
        public Aircraft[] newArray(int size) {
            return new Aircraft[size];
        }

        public Aircraft createFromParcel(Parcel source) {
            return new Aircraft(source);
        }
    };
    private String id;
    private String code;
    private String name;
    private String model;
    private String searchString;

    public Aircraft() {
    }

    public Aircraft(String code, String model, String name) {
        this.code = code;
        this.model = model;
        this.name = name;
    }

    public Aircraft(String id, String code, String model, String name, String searchString) {
        this.id = id;
        this.code = code;
        this.model = model;
        this.name = name;
        this.searchString = searchString;
    }

    public Aircraft(JSONObject airlineJsonObject) {
        this.code = airlineJsonObject.optString("code");
        this.name = airlineJsonObject.optString("name");
        this.model = airlineJsonObject.optString("model");
    }

    public Aircraft(Parcel parcel) {
        id = parcel.readString();
        code = parcel.readString();
        name = parcel.readString();
        model = parcel.readString();
        searchString = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(code);
        parcel.writeString(name);
        parcel.writeString(model);
        parcel.writeString(searchString);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", searchString='" + searchString + '\'' +
                '}';
    }

    public String getCode() {
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

}
