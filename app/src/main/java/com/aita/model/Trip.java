package com.aita.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@SuppressWarnings("unused")
public class Trip implements Parcelable {

    @Retention(SOURCE)
    @IntDef({OWNERSHIP_OTHER, OWNERSHIP_PEOPLE, OWNERSHIP_MY})
    public @interface Ownership {}

    /**
     * Unknown trip (e.g. imported from mail).
     * We don't know whether the trip MY or FOREIGN
     */
    public static final int OWNERSHIP_OTHER = -1;

    /**
     * Foreign trip. E.g. trip of my relatives, friends, etc.
     */
    public static final int OWNERSHIP_PEOPLE = 0;

    /**
     * User's trip. The user has added it himself or marked as 'mine'.
     */
    public static final int OWNERSHIP_MY = 1;

    private long arrivalDate;
    private long departureDate;
    private long arrivalDateUTC;
    private long departureDateUTC;
    private long dateAdded;
    private long updated;
    private List<Note> notes;
    private List<Flight> flights;

    private boolean purchased;
    private String arrivalAirportCode;
    private String departureAirportCode;
    private String id;
    private Hotel hotel;
    private boolean purchasedPushOnly;
    private boolean pushEnabled;
    private boolean smsEnabled;
    private String calendarID;
    private List<TripLounge> lounges;
    private TripInsurance tripInsurance;
    @Ownership private int ownership;

    public Trip() {
    }

    public long getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(long arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public long getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(long departureDate) {
        this.departureDate = departureDate;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public String getId() {
        return id;
    }

    public String getTripFlights() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getArrivalDateUTC() {
        return arrivalDateUTC;
    }

    public void setArrivalDateUTC(long arrivalDateUTC) {
        this.arrivalDateUTC = arrivalDateUTC;
    }

    public long getDepartureDateUTC() {
        return departureDateUTC;
    }

    public void setDepartureDateUTC(long departureDateUTC) {
        this.departureDateUTC = departureDateUTC;
    }

    public boolean isPurchasedPushOnly() {
        return purchasedPushOnly;
    }

    public void setPurchasedPushOnly(boolean purchasedPushOnly) {
        this.purchasedPushOnly = purchasedPushOnly;
    }

    public boolean isPushEnabled() {
        return pushEnabled;
    }

    public void setPushEnabled(boolean pushEnabled) {
        this.pushEnabled = pushEnabled;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getCalendarID() {
        return calendarID;
    }

    public void setCalendarID(String calendarID) {
        this.calendarID = calendarID;
    }

    public boolean isSmsEnabled() {
        return smsEnabled;
    }

    public void setSmsEnabled(boolean smsEnabled) {
        this.smsEnabled = smsEnabled;
    }

    public List<TripLounge> getLounges() {
        return lounges;
    }

    public void setLounges(List<TripLounge> lounges) {
        this.lounges = lounges;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public TripInsurance getTripInsurance() {
        return tripInsurance;
    }

    public void setTripInsurance(TripInsurance tripInsurance) {
        this.tripInsurance = tripInsurance;
    }

    @Ownership
    public int getOwnership() {
        return ownership;
    }

    public void setOwnership(@Ownership int ownership) {
        this.ownership = ownership;
    }

    public void sortFlights() {
        Collections.sort(flights, new FlightDepartureDateUtcComparator());
    }

    /**
     * Helper method. Indicates whether this {@link Trip} is a "trip" or just a single {@link Flight}.
     *
     * @return {@code true} if there are only 1 {@link Flight} in this {@link Trip}, {@code false} otherwise.
     */
    public boolean isFlight() {
        return flights != null && flights.size() == 1;
    }

    public String getLabel() {
        StringBuilder sb = new StringBuilder();
        if (flights != null) {
            for (Flight flight : flights) {
                sb.append(flight.getLabel())
                        .append(";");
            }
        }
        return sb.toString();
    }

    public int determineCurrentFlightIndex() {
        long currentSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        int currentFlightIndex = 0;
        for (int i = 0, size = flights.size(); i < size; i++) {
            Flight flight = flights.get(i);
            if (currentSeconds < flight.getArrivalDateUTC()) {
                currentFlightIndex = i;
                break;
            }
        }
        return currentFlightIndex;
    }

    @Nullable
    public String nextFlightLayoverText(int flightIndex) {
        if (flights != null && flightIndex >= 0 && flightIndex < flights.size() - 1) {
            final Flight currentFlight = flights.get(flightIndex);
            final Flight nextFlight = flights.get(flightIndex + 1);
            return getLayoverDurationText(currentFlight, nextFlight);
        }
        return null;
    }

    @Nullable
    public String previousFlightLayoverText(int flightIndex) {
        if (flights != null && flightIndex >= 1 && flightIndex < flights.size()) {
            final Flight currentFlight = flights.get(flightIndex);
            final Flight previousFlight = flights.get(flightIndex - 1);
            return getLayoverDurationText(previousFlight, currentFlight);
        }
        return null;
    }

    private String getLayoverDurationText(@NonNull Flight firstFlight, @NonNull Flight secondFlight) {
        final long landingDelay;
        if (firstFlight.hasSignificantUserReportedArrivalDelay()) {
            landingDelay = firstFlight.getUserReportedArrivalDelay();
        } else {
            landingDelay = 0;
        }
        final long landingTime = firstFlight.getLandingTime() + landingDelay;

        final long takeOffDelay;
        if (secondFlight.hasSignificantUserReportedDepartureDelay()) {
            takeOffDelay = secondFlight.getUserReportedDepartureDelay();
        } else {
            takeOffDelay = 0;
        }
        final long takeOffTime = secondFlight.getTakeOffTime() + takeOffDelay;

        final long duration = Math.abs(takeOffTime - landingTime);

        //TODO: Format time
        return String.valueOf(duration);
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

        Trip trip = (Trip) o;

        if (arrivalDate != trip.arrivalDate) {
            return false;
        }
        if (departureDate != trip.departureDate) {
            return false;
        }
        if (arrivalDateUTC != trip.arrivalDateUTC) {
            return false;
        }
        if (departureDateUTC != trip.departureDateUTC) {
            return false;
        }
        if (dateAdded != trip.dateAdded) {
            return false;
        }
        if (updated != trip.updated) {
            return false;
        }
        if (purchased != trip.purchased) {
            return false;
        }
        if (purchasedPushOnly != trip.purchasedPushOnly) {
            return false;
        }
        if (pushEnabled != trip.pushEnabled) {
            return false;
        }
        if (smsEnabled != trip.smsEnabled) {
            return false;
        }
        if (ownership != trip.ownership) {
            return false;
        }
        if (notes != null ? !notes.equals(trip.notes) : trip.notes != null) {
            return false;
        }
        if (flights != null ? !flights.equals(trip.flights) : trip.flights != null) {
            return false;
        }
        if (arrivalAirportCode != null ? !arrivalAirportCode
                .equals(trip.arrivalAirportCode) : trip.arrivalAirportCode != null) {
            return false;
        }
        if (departureAirportCode != null ? !departureAirportCode
                .equals(trip.departureAirportCode) : trip.departureAirportCode != null) {
            return false;
        }
        if (id != null ? !id.equals(trip.id) : trip.id != null) {
            return false;
        }
        if (hotel != null ? !hotel.equals(trip.hotel) : trip.hotel != null) {
            return false;
        }
        if (calendarID != null ? !calendarID.equals(trip.calendarID) : trip.calendarID != null) {
            return false;
        }
        if (lounges != null ? !lounges.equals(trip.lounges) : trip.lounges != null) {
            return false;
        }
        return tripInsurance != null ? tripInsurance.equals(trip.tripInsurance) : trip.tripInsurance == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (arrivalDate ^ (arrivalDate >>> 32));
        result = 31 * result + (int) (departureDate ^ (departureDate >>> 32));
        result = 31 * result + (int) (arrivalDateUTC ^ (arrivalDateUTC >>> 32));
        result = 31 * result + (int) (departureDateUTC ^ (departureDateUTC >>> 32));
        result = 31 * result + (int) (dateAdded ^ (dateAdded >>> 32));
        result = 31 * result + (int) (updated ^ (updated >>> 32));
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (flights != null ? flights.hashCode() : 0);
        result = 31 * result + (purchased ? 1 : 0);
        result = 31 * result + (arrivalAirportCode != null ? arrivalAirportCode.hashCode() : 0);
        result = 31 * result + (departureAirportCode != null ? departureAirportCode.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (hotel != null ? hotel.hashCode() : 0);
        result = 31 * result + (purchasedPushOnly ? 1 : 0);
        result = 31 * result + (pushEnabled ? 1 : 0);
        result = 31 * result + (smsEnabled ? 1 : 0);
        result = 31 * result + (calendarID != null ? calendarID.hashCode() : 0);
        result = 31 * result + (lounges != null ? lounges.hashCode() : 0);
        result = 31 * result + (tripInsurance != null ? tripInsurance.hashCode() : 0);
        result = 31 * result + ownership;
        return result;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.arrivalDate);
        dest.writeLong(this.departureDate);
        dest.writeLong(this.arrivalDateUTC);
        dest.writeLong(this.departureDateUTC);
        dest.writeLong(this.dateAdded);
        dest.writeLong(this.updated);
        dest.writeTypedList(this.notes);
        dest.writeTypedList(this.flights);
        dest.writeByte(this.purchased ? (byte) 1 : (byte) 0);
        dest.writeString(this.arrivalAirportCode);
        dest.writeString(this.departureAirportCode);
        dest.writeString(this.id);
        dest.writeParcelable(this.hotel, flags);
        dest.writeByte(this.purchasedPushOnly ? (byte) 1 : (byte) 0);
        dest.writeByte(this.pushEnabled ? (byte) 1 : (byte) 0);
        dest.writeByte(this.smsEnabled ? (byte) 1 : (byte) 0);
        dest.writeString(this.calendarID);
        dest.writeTypedList(this.lounges);
        dest.writeParcelable(this.tripInsurance, flags);
        dest.writeInt(ownership);
    }

    protected Trip(Parcel in) {
        this.arrivalDate = in.readLong();
        this.departureDate = in.readLong();
        this.arrivalDateUTC = in.readLong();
        this.departureDateUTC = in.readLong();
        this.dateAdded = in.readLong();
        this.updated = in.readLong();
        this.notes = in.createTypedArrayList(Note.CREATOR);
        this.flights = in.createTypedArrayList(Flight.CREATOR);
        this.purchased = in.readByte() != 0;
        this.arrivalAirportCode = in.readString();
        this.departureAirportCode = in.readString();
        this.id = in.readString();
        this.hotel = in.readParcelable(Hotel.class.getClassLoader());
        this.purchasedPushOnly = in.readByte() != 0;
        this.pushEnabled = in.readByte() != 0;
        this.smsEnabled = in.readByte() != 0;
        this.calendarID = in.readString();
        this.lounges = in.createTypedArrayList(TripLounge.CREATOR);
        this.tripInsurance = in.readParcelable(TripInsurance.class.getClassLoader());
        //noinspection WrongConstant
        this.ownership = in.readInt();
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel source) {return new Trip(source);}

        @Override
        public Trip[] newArray(int size) {return new Trip[size];}
    };

    @Override
    public String toString() {
        return "Trip{" +
                "arrivalDate=" + arrivalDate +
                ", departureDate=" + departureDate +
                ", arrivalDateUTC=" + arrivalDateUTC +
                ", departureDateUTC=" + departureDateUTC +
                ", dateAdded=" + dateAdded +
                ", updated=" + updated +
                ", notes=" + notes +
                ", flights=" + flights +
                ", purchased=" + purchased +
                ", arrivalAirportCode='" + arrivalAirportCode + '\'' +
                ", departureAirportCode='" + departureAirportCode + '\'' +
                ", id='" + id + '\'' +
                ", hotel=" + hotel +
                ", purchasedPushOnly=" + purchasedPushOnly +
                ", pushEnabled=" + pushEnabled +
                ", smsEnabled=" + smsEnabled +
                ", calendarID='" + calendarID + '\'' +
                ", lounges=" + lounges +
                ", tripInsurance=" + tripInsurance +
                ", ownership=" + ownership +
                '}';
    }

}