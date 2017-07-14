package com.aita.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.io.File;

public class Note implements Parcelable {

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
    private String id;
    private long dbId;
    private File file;
    private String text;
    private String tripId;

    public Note(String id, long dbId, String filePath, String text, String tripId) {
        this.id = id;
        this.dbId = dbId;
        if (filePath != null && !filePath.isEmpty()) {
            this.file = new File(filePath);
        }
        this.text = text;
        this.tripId = tripId;
    }

    public Note(Parcel parcel) {
        id = parcel.readString();
        dbId = parcel.readLong();
        String filePath = parcel.readString();
        if (filePath != null && !filePath.isEmpty()) {
            file = new File(filePath);
        }
        text = parcel.readString();
        tripId = parcel.readString();
    }

    public static void loadLargePreviewForFile(final Context context, final File file,
                                               final OnPreviewLoadedListener onPreviewLoadedListener) {
        //STUB
    }

    public static String getExtensionForFile(File file) {
        if (file != null) {
            int index = file.getPath().lastIndexOf(".");
            return index == -1 ? "" : file.getPath().substring(index + 1);
        } else {
            return "";
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeLong(dbId);
        parcel.writeString(file != null ? file.getAbsolutePath() : "");
        parcel.writeString(text);
        parcel.writeString(tripId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Bitmap getSmallFilePreview(Context context) {
        //todo:stub
        return null;
    }

    public void loadLargeFilePreview(Context context, OnPreviewLoadedListener onPreviewLoadedListener) {
        loadLargePreviewForFile(context, file, onPreviewLoadedListener);
    }

    public String getFullFilePath() {
        return file != null ? file.getAbsolutePath() : "";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getExtension() {
        return getExtensionForFile(file);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Note note = (Note) o;

        if (dbId != note.dbId) {
            return false;
        }
        if (id != null ? !id.equals(note.id) : note.id != null) {
            return false;
        }
        if (file != null ? !file.equals(note.file) : note.file != null) {
            return false;
        }
        if (text != null ? !text.equals(note.text) : note.text != null) {
            return false;
        }
        return !(tripId != null ? !tripId.equals(note.tripId) : note.tripId != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) (dbId ^ (dbId >>> 32));
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (tripId != null ? tripId.hashCode() : 0);
        return result;
    }

    public interface OnPreviewLoadedListener {
        void onPreviewLoaded(@Nullable Bitmap bitmap);
    }

}
