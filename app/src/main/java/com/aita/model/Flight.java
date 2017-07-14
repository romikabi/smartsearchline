package com.aita.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;

import com.aita.helpers.MainHelper;
import com.aita.shared.AitaContract;
import com.aita.shared.AitaContract.FlightEntry;

import java.lang.annotation.Retention;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class Flight implements Parcelable {

    private TripLounge tripLounge;
    private Hotel hotel;
    private Aircraft aircraft;
    private TripInsurance tripInsurance;

    @Retention(SOURCE)
    @IntDef({EVENT_CHECKIN, EVENT_BOARDING, EVENT_TAKE_OFF, EVENT_LANDING, NOTHING})
    public @interface Event {}

    public static final int EVENT_CHECKIN = 0;
    public static final int EVENT_BOARDING = 1;
    public static final int EVENT_TAKE_OFF = 2;
    public static final int EVENT_LANDING = 3;
    public static final int NOTHING = 4;

    @Retention(SOURCE)
    @IntDef({
            AUTOCHECKIN_NOT_AVAILABLE,
            AUTOCHECKIN_AVAILABLE,
            AUTOCHECKIN_PENDING,
            AUTOCHECKIN_ENABLED_FAILED,
            AUTOCHECKIN_DISABLED,
            AUTOCHECKIN_SUCCESS,
            AUTOCHECKIN_CANCELED,
            AUTOCHECKIN_FILL_MORE
    })
    @interface AutocheckinStatus {}

    public static final int AUTOCHECKIN_NOT_AVAILABLE = 0;
    public static final int AUTOCHECKIN_AVAILABLE = 1;
    public static final int AUTOCHECKIN_PENDING = 2;
    public static final int AUTOCHECKIN_ENABLED_FAILED = 3;
    public static final int AUTOCHECKIN_DISABLED = 4;
    public static final int AUTOCHECKIN_SUCCESS = 5;
    public static final int AUTOCHECKIN_CANCELED = 6;
    public static final int AUTOCHECKIN_FILL_MORE = 7;

    @SuppressWarnings("unused")
    public static final Creator<Flight> CREATOR = new Creator<Flight>() {
        @Override
        public Flight createFromParcel(Parcel in) {
            return new Flight(in);
        }

        @Override
        public Flight[] newArray(int size) {
            return new Flight[size];
        }
    };
    private static final String EDITED_FLIGHTS_IDS_SET_KEY = "edited_flights_ids_set";
    private String status;
    private String seat;
    private String fare;
    private String source;
    private String seatZone;
    private String tripID;
    private String bookingReference;
    private String bookrefLastName;
    private long dateAdded;
    private String departureGate;
    private long duration;
    private String number;
    private long arrivalDelayRunway;//
    private long departureDate;
    private long departureDateUTC;
    private long arrivalDateEstimatedGate;
    private long departureDateActualRunway;
    private long departureDateEstimatedRunway;
    private long arrivalDelayGate;
    private String arrivalGate;
    private long departureDelayRunway;
    private String airlineCode;
    private long arrivalDateFlightplan;
    private long arrivalDate;
    private long arrivalDateUTC;
    private long arrivalDateActualGate;
    private long arrivalDateScheduledGate;
    private long arrivalDatePublished;
    private long arrivalDateEstimatedRunway;
    private long departureDateScheduledGate;
    private long departureDateFlightplan;
    private long departureDateActualGate;
    private long departureDatePublished;
    private long userReportedDepartureDelay;
    private long userReportedArrivalDelay;
    private long lastUpdated;
    private long departureDateEstimatedGate;
    private String arrivalCode;
    private long departureDelayGate;
    private String equipment;
    private String arrivalTerminal;
    private String baggage;
    private String departureTerminal;

    private String id;
    private String departureCode;
    /**
     * distance in kilometres
     */
    private Float distance;
    private long arrivalDateActualRunway;
    private boolean purchased;
    private boolean purchasedPushOnly;
    private boolean pushEnabled;
    private boolean smsEnabled;
    private String calendarID;
    private boolean fromCalendar = false;
    /*
     * Airport objects
     */
    private Airport arrivalAirport;
    private Airport departureAirport;
    /*
     * Airline object
     */
    private Airline airline;
    /**
     * JSON string for the flight checkin scheme
     */
    private String checkinSchemeJsonString;
    /**
     * JSON string for the flight checkin entry
     */
    private String checkinEntry;
    /**
     * checkin available if {@link Flight#checkinSchemeJsonString} String is not empty
     */
    @AutocheckinStatus private int checkinAvailable;
    /**
     * true if checkin request for the flight has been already sent, false otherwise
     */
    private boolean checkinRequestSent;
    private BagTrackingInfo bagTrackingInfo;
    private boolean isFinishedByUser;
    private String airlineAircraftImageUrl;
    private int ownership;

    /*
     * Default constructor
     */
    public Flight() {
        super();
    }

    /*
     * Base constructor
     */
    public Flight(String id, String airlineCode, String number,
                  long departureDate, long arrivalDate) {
        super();
        this.airlineCode = airlineCode;
        this.number = number;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.id = id;
    }

    protected Flight(Parcel in) {
        status = in.readString();
        seat = in.readString();
        fare = in.readString();
        source = in.readString();
        seatZone = in.readString();
        tripID = in.readString();
        bookingReference = in.readString();
        dateAdded = in.readLong();
        departureGate = in.readString();
        duration = in.readLong();
        number = in.readString();
        arrivalDelayRunway = in.readLong();
        departureDate = in.readLong();
        departureDateUTC = in.readLong();
        arrivalDateEstimatedGate = in.readLong();
        departureDateActualRunway = in.readLong();
        departureDateEstimatedRunway = in.readLong();
        arrivalDelayGate = in.readLong();
        arrivalGate = in.readString();
        departureDelayRunway = in.readLong();
        airlineCode = in.readString();
        arrivalDateFlightplan = in.readLong();
        arrivalDate = in.readLong();
        arrivalDateUTC = in.readLong();
        arrivalDateActualGate = in.readLong();
        arrivalDateScheduledGate = in.readLong();
        arrivalDatePublished = in.readLong();
        arrivalDateEstimatedRunway = in.readLong();
        departureDateScheduledGate = in.readLong();
        departureDateFlightplan = in.readLong();
        departureDateActualGate = in.readLong();
        departureDatePublished = in.readLong();
        userReportedDepartureDelay = in.readLong();
        userReportedArrivalDelay = in.readLong();
        lastUpdated = in.readLong();
        departureDateEstimatedGate = in.readLong();
        arrivalCode = in.readString();
        departureDelayGate = in.readLong();
        equipment = in.readString();
        arrivalTerminal = in.readString();
        baggage = in.readString();
        departureTerminal = in.readString();
        aircraft = (Aircraft) in.readValue(Aircraft.class.getClassLoader());
        hotel = (Hotel) in.readValue(Hotel.class.getClassLoader());
        tripLounge = (TripLounge) in.readValue(TripLounge.class.getClassLoader());
        id = in.readString();
        departureCode = in.readString();
        distance = in.readByte() == 0x00 ? null : in.readFloat();
        arrivalDateActualRunway = in.readLong();
        purchased = in.readByte() != 0x00;
        purchasedPushOnly = in.readByte() != 0x00;
        pushEnabled = in.readByte() != 0x00;
        smsEnabled = in.readByte() != 0x00;
        calendarID = in.readString();
        fromCalendar = in.readByte() != 0x00;
        arrivalAirport = (Airport) in.readValue(Airport.class.getClassLoader());
        departureAirport = (Airport) in.readValue(Airport.class.getClassLoader());
        airline = (Airline) in.readValue(Airline.class.getClassLoader());
        checkinSchemeJsonString = in.readString();
        checkinEntry = in.readString();
        //noinspection WrongConstant
        checkinAvailable = in.readInt();
        checkinRequestSent = in.readByte() != 0x00;
        airlineAircraftImageUrl = in.readString();
        bagTrackingInfo = in.readParcelable(BagTrackingInfo.class.getClassLoader());
        //noinspection WrongConstant
        ownership = in.readInt();
    }

    public static int getDelayTextColor(Context context, long deltaSeconds) {
        //TODO: replace with colors
        return 0;
        /*return deltaSeconds > 0 ? ContextCompat.getColor(context, R.color.timeline_red) :
                ContextCompat.getColor(context, R.color.timeline_green);*/
    }

    @Nullable
    public static String getDelayText(Context context, long deltaSeconds) {
        String result;
        if (Math.abs(deltaSeconds) < TimeUnit.MINUTES.toSeconds(1)) {
            result = null;
        } else {
            String sign = (deltaSeconds > 0) ? "+" : "-";
            deltaSeconds = Math.abs(deltaSeconds);
            result = sign + MainHelper.getTimeToEventText(deltaSeconds);
        }

        return result;
    }

    public long calculateDepartureDelayDelta() {
        long departureDelayDelta = 0;
        if (departureDateEstimatedGate != 0) {
            departureDelayDelta = departureDateEstimatedGate - departureDate;
        } else if (hasSignificantUserReportedDepartureDelay()) {
            departureDelayDelta = userReportedDepartureDelay;
        }

        return departureDelayDelta;
    }

    public long calculateArrivalDelayDelta() {
        long arrivalDelayDelta = 0;
        if (arrivalDateEstimatedGate != 0) {
            arrivalDelayDelta = arrivalDateEstimatedGate - arrivalDate;
        } else if (hasSignificantUserReportedArrivalDelay()) {
            arrivalDelayDelta = userReportedArrivalDelay;
        }

        return arrivalDelayDelta;
    }

    @NonNull
    private Date calculateDateWithPrecisionToDay(long seconds, @Nullable TimeZone timeZone) {
        if (timeZone == null) {
            timeZone = TimeZone.getTimeZone("UTC");
        }

        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(TimeUnit.SECONDS.toMillis(seconds));

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        calendar.clear();
        calendar.setTimeZone(timeZone);

        calendar.set(year, month, day);
        return calendar.getTime();
    }

    @NonNull
    public String getDayChangeText() {
        Date departureDate = calculateDateWithPrecisionToDay(this.departureDate, null);
        Date arrivalDate = calculateDateWithPrecisionToDay(this.arrivalDate, null);

        if (departureDate.equals(arrivalDate)) {
            return "";
        } else if (departureDate.before(arrivalDate)) {
            return "+1";
        } else {
            return "-1";
        }
    }

    @AutocheckinStatus
    public int getCheckinAvailable() {
        return checkinAvailable;
    }

    public void setCheckinAvailable(@AutocheckinStatus int checkinAvailable) {
        this.checkinAvailable = checkinAvailable;
    }

    public String getBaggage() {
        return baggage;
    }

    public void setBaggage(String baggage) {
        this.baggage = baggage;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public float calculateDistance() {
        int R = 6371;
        double dLat = ((arrivalAirport.getLatitude() - departureAirport
                .getLatitude()) * Math.PI / 180.0f);
        double dLon = ((arrivalAirport.getLongitude() - departureAirport
                .getLongitude()) * Math.PI / 180.0f);
        double a = (Math.sin(dLat / 2.0f) * Math.sin(dLat / 2.0f) + Math
                .cos(arrivalAirport.getLatitude() * Math.PI / 180.0f)
                * Math.cos(departureAirport.getLatitude() * Math.PI / 180.0f)
                * Math.sin(dLon / 2.0f) * Math.sin(dLon / 2.0f));
        double c = (2.0f * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
        // if (METRIC) return d;
        // else return d / 1.60f;
        return (float) (R * c);
    }

    public String getAddressConcat(boolean isDeparture) {
        return isDeparture ? (departureCode + ", " + departureAirport.getCity()
                + ", " + departureAirport.getCountryCode()) : arrivalCode
                + ", " + arrivalAirport.getCity() + ", "
                + arrivalAirport.getCountryCode();
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getArrivalCode() {
        return arrivalCode;
    }

    public void setArrivalCode(String arrivalCode) {
        this.arrivalCode = arrivalCode;
    }

    public long getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(long arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public long getArrivalDateActualGate() {
        return arrivalDateActualGate;
    }

    public void setArrivalDateActualGate(long arrivalDateActualGate) {
        this.arrivalDateActualGate = arrivalDateActualGate;
    }

    public long getArrivalDateActualRunway() {
        return arrivalDateActualRunway;
    }

    public void setArrivalDateActualRunway(long arrivalDateActualRunway) {
        this.arrivalDateActualRunway = arrivalDateActualRunway;
    }

    public long getArrivalDateEstimatedGate() {
        return arrivalDateEstimatedGate;
    }

    public void setArrivalDateEstimatedGate(long arrivalDateEstimatedGate) {
        this.arrivalDateEstimatedGate = arrivalDateEstimatedGate;
    }
    /*public String getSavedDate(boolean isDeparture, Context context) {
        Calendar myDate = Calendar.getInstance();
		if (isDeparture)
			myDate.setTimeInMillis((long) getDepartureDate() * 1000L);
		else
			myDate.setTimeInMillis((long) getArrivalDate() * 1000L);

		SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat
				.getMediumDateFormat(context.getApplicationContext());

		// SimpleDateFormat dateFormat1 = null;
		// if(dateFormat.toLocalizedPattern().charAt(0) == 'd')
		// dateFormat1 = new SimpleDateFormat("dd.MM\nyyyy", Locale.US);

		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		// SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMM-d\nyyyy",
		// Locale.US);

		return dateFormat.format(myDate.getTime());
	}*/

    public long getArrivalDateEstimatedRunway() {
        return arrivalDateEstimatedRunway;
    }

    public void setArrivalDateEstimatedRunway(long arrivalDateEstimatedRunway) {
        this.arrivalDateEstimatedRunway = arrivalDateEstimatedRunway;
    }

    public long getArrivalDateFlightplan() {
        return arrivalDateFlightplan;
    }

    public void setArrivalDateFlightplan(long arrivalDateFlightplan) {
        this.arrivalDateFlightplan = arrivalDateFlightplan;
    }

    public long getArrivalDatePublished() {
        return arrivalDatePublished;
    }

    public void setArrivalDatePublished(long arrivalDatePublished) {
        this.arrivalDatePublished = arrivalDatePublished;
    }

    public long getArrivalDateScheduledGate() {
        return arrivalDateScheduledGate;
    }

    public void setArrivalDateScheduledGate(long arrivalDateScheduledGate) {
        this.arrivalDateScheduledGate = arrivalDateScheduledGate;
    }

    public long getArrivalDelayGate() {
        return arrivalDelayGate;
    }

    public void setArrivalDelayGate(long arrivalDelayGate) {
        this.arrivalDelayGate = arrivalDelayGate;
    }

    public long getArrivalDelayRunway() {
        return arrivalDelayRunway;
    }

    public void setArrivalDelayRunway(long arrivalDelayRunway) {
        this.arrivalDelayRunway = arrivalDelayRunway;
    }

    public String getArrivalGate() {
        return arrivalGate;
    }

    public void setArrivalGate(String arrivalGate) {
        this.arrivalGate = arrivalGate;
    }

    public String getArrivalTerminal() {
        return arrivalTerminal;
    }

    public void setArrivalTerminal(String arrivalTerminal) {
        this.arrivalTerminal = arrivalTerminal;
    }

    public String getSearchDate(boolean isDeparture, Context context) {
        Calendar myDate = Calendar.getInstance();
        if (isDeparture) {
            myDate.setTimeInMillis(getDepartureDate() * 1000L);
        } else {
            myDate.setTimeInMillis(getArrivalDate() * 1000L);
        }

        java.text.DateFormat dateFormat = DateFormat.getTimeFormat(context
                .getApplicationContext());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        java.text.DateFormat dateFormat1 = DateFormat.getDateFormat(context
                .getApplicationContext());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat1.format(myDate.getTime());
    }

    public String getSearchTime(boolean isDeparture, Context context) {
        Calendar myDate = Calendar.getInstance();
        if (isDeparture) {
            myDate.setTimeInMillis(getDepartureDate() * 1000L);
        } else {
            myDate.setTimeInMillis(getArrivalDate() * 1000L);
        }

        java.text.DateFormat dateFormat = DateFormat.getTimeFormat(context
                .getApplicationContext());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        return dateFormat.format(myDate.getTime());
    }

    public String getFullDate(boolean isDeparture, Context context) {
        Calendar myDate = Calendar.getInstance();
        if (isDeparture) {
            myDate.setTimeInMillis(getDepartureDate() * 1000L);
        } else {
            myDate.setTimeInMillis(getArrivalDate() * 1000L);
        }
        java.text.DateFormat dateFormat = DateFormat.getDateFormat(context
                .getApplicationContext());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(myDate.getTime());
    }

    public String getDate(boolean isDeparture, Context context) {
        Calendar myDate = Calendar.getInstance();
        if (isDeparture) {
            myDate.setTimeInMillis(getDepartureDate() * 1000L);
        } else {
            myDate.setTimeInMillis(getArrivalDate() * 1000L);
        }

        SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat
                .getMediumDateFormat(context.getApplicationContext());

        // SimpleDateFormat dateFormat1 = null;
        // if(dateFormat.toLocalizedPattern().charAt(0) == 'd')
        // dateFormat1 = new SimpleDateFormat("dd.MM\nyyyy", Locale.US);

        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        // SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMM-d\nyyyy",
        // Locale.US);

        return dateFormat.format(myDate.getTime());
    }

    public String getDetailedDate(boolean isDeparture) {
        Calendar myDate = Calendar.getInstance();
        if (isDeparture) {
            myDate.setTimeInMillis(getDepartureDate() * 1000L);
        } else {
            myDate.setTimeInMillis(getArrivalDate() * 1000L);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MMM d\nyyyy",
                Locale.getDefault()
        );
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        return dateFormat.format(myDate.getTime());
    }

    public String getSearchDate2(boolean isDeparture, Context context) {
        Calendar myDate = Calendar.getInstance();
        if (isDeparture) {
            myDate.setTimeInMillis(getDepartureDate() * 1000L);
        } else {
            myDate.setTimeInMillis(getArrivalDate() * 1000L);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "d MMMM yyyy",
                Locale.getDefault()
        );
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(myDate.getTime());
    }

    public String getArrivalTime(Context context) {
        if (isSameDay(context)) {

            return MainHelper.formatTimeMinutesFromMillis(getLandingTime() * 1000L);
        } else {
            return String.format("%s %s",
                    MainHelper.formatTimeMinutesFromMillis(getLandingTime() * 1000L),
                    getArrivalDate()
            );
        }
    }

    private boolean isSameDay(Context context) {
        Calendar myDepartureDate = Calendar.getInstance();
        myDepartureDate.setTimeInMillis(getTakeOffTime() * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", context
                .getResources().getConfiguration().locale);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar myDate = Calendar.getInstance();
        myDate.setTimeInMillis(getTakeOffTime() * 1000L);
        return dateFormat.format(myDate.getTime()).equals(dateFormat.format(myDepartureDate.getTime()));
    }

    public long getDepartureWeekOfYear() {
        Calendar myDate = Calendar.getInstance();
        myDate.setTimeInMillis(departureDate * 1000L);
        myDate.get(GregorianCalendar.WEEK_OF_YEAR);
        return (departureDate - 1388534400) / 604800 + 1;
    }

    public String getMonth(Context context) {
        Calendar myDate = Calendar.getInstance();
        myDate.setTimeInMillis(getDepartureDate() * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM", context
                .getResources().getConfiguration().locale);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(myDate.getTime()).toUpperCase();
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getDepartureCode() {
        return departureCode;
    }

    public void setDepartureCode(String departureCode) {
        this.departureCode = departureCode;
    }

    public long getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(long departureDate) {
        this.departureDate = departureDate;
    }

    public long getDepartureDateActualGate() {
        return departureDateActualGate;
    }

    public void setDepartureDateActualGate(long departureDateActualGate) {
        this.departureDateActualGate = departureDateActualGate;
    }

    public long getDepartureDateActualRunway() {
        return departureDateActualRunway;
    }

    public void setDepartureDateActualRunway(long departureDateActualRunway) {
        this.departureDateActualRunway = departureDateActualRunway;
    }

    public long getUserReportedDepartureDelay() {
        return userReportedDepartureDelay;
    }

    public void setUserReportedDepartureDelay(long userReportedDepartureDelay) {
        this.userReportedDepartureDelay = userReportedDepartureDelay;
    }

    public long getUserReportedArrivalDelay() {
        return userReportedArrivalDelay;
    }

    public void setUserReportedArrivalDelay(long userReportedArrivalDelay) {
        this.userReportedArrivalDelay = userReportedArrivalDelay;
    }

    public long getDepartureDateEstimatedGate() {
        return departureDateEstimatedGate;
    }

    public void setDepartureDateEstimatedGate(long departureDateEstimatedGate) {
        this.departureDateEstimatedGate = departureDateEstimatedGate;
    }

    public long getDepartureDateEstimatedRunway() {
        return departureDateEstimatedRunway;
    }

    public void setDepartureDateEstimatedRunway(long departureDateEstimatedRunway) {
        this.departureDateEstimatedRunway = departureDateEstimatedRunway;
    }

    public long getDepartureDateFlightplan() {
        return departureDateFlightplan;
    }

    public void setDepartureDateFlightplan(long departureDateFlightplan) {
        this.departureDateFlightplan = departureDateFlightplan;
    }

    public long getDepartureDatePublished() {
        return departureDatePublished;
    }

    public void setDepartureDatePublished(long departureDatePublished) {
        this.departureDatePublished = departureDatePublished;
    }

    public long getDepartureDateScheduledGate() {
        return departureDateScheduledGate;
    }

    public void setDepartureDateScheduledGate(long departureDateScheduledGate) {
        this.departureDateScheduledGate = departureDateScheduledGate;
    }

    public long getDepartureDelayGate() {
        return departureDelayGate;
    }

    public void setDepartureDelayGate(long departureDelayGate) {
        this.departureDelayGate = departureDelayGate;
    }

    public long getDepartureDelayRunway() {
        return departureDelayRunway;
    }

    public void setDepartureDelayRunway(long departureDelayRunway) {
        this.departureDelayRunway = departureDelayRunway;
    }

    public String getDepartureGate() {
        return departureGate;
    }

    public void setDepartureGate(String departureGate) {
        this.departureGate = departureGate;
    }

    public String getDepartureTerminal() {
        return departureTerminal;
    }

    public void setDepartureTerminal(String departureTerminal) {
        this.departureTerminal = departureTerminal;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFullNumber() {
        return String.format("%s %s", airlineCode, number);
    }

    public String getFullNumberWelcome() {
        return String.format("%s-%s", airlineCode, number);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Status getStatusEnum() {
        if (status == null) {
            return Status.UNKNOWN;
        } else {
            switch (status.toLowerCase()) {
                case "a":
                case "en route":
                    return Status.EN_ROUTE;
                case "c":
                case "canceled":
                case "cancelled":
                    return Status.CANCELED;
                case "d":
                case "diverted":
                    return Status.DIVERTED;
                case "dn":
                case "data source needed":
                    return Status.DATA_SOURCE_NEEDED;
                case "l":
                case "landed":
                    return Status.LANDED;
                case "no":
                case "not operational":
                    return Status.NOT_OPERATIONAL;
                case "r":
                case "redirected":
                    return Status.REDIRECTED;
                case "s":
                case "scheduled":
                    return Status.SCHEDULED;
                default:
                    return Status.UNKNOWN;
            }
        }
    }

    public void setStatusEnum(Status statusEnum) {
        switch (statusEnum) {
            case EN_ROUTE:
                status = "En route";
                break;
            case CANCELED:
                status = "Canceled";
                break;
            case DIVERTED:
                status = "Diverted";
                break;
            case DATA_SOURCE_NEEDED:
                status = "Data source needed";
                break;
            case LANDED:
                status = "Landed";
                break;
            case NOT_OPERATIONAL:
                status = "Not operational";
                break;
            case REDIRECTED:
                status = "Redirected";
                break;
            case SCHEDULED:
                status = "Scheduled";
                break;
            case UNKNOWN:
                status = "Unknown";
                break;
        }
    }

    public String getTime(boolean isDeparture, Context context) {
        Calendar myDate = Calendar.getInstance();
        if (isDeparture) {
            myDate.setTimeInMillis(getDepartureDate() * 1000L);
        } else {
            myDate.setTimeInMillis(getArrivalDate() * 1000L);
        }
        java.text.DateFormat dateFormat = DateFormat.getTimeFormat(context);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(myDate.getTime());
    }

    /*
     * method for search list
     */
    public HashMap<String, String> returnHashMap(Context context) {
        HashMap<String, String> map = new HashMap<>();
        map.put(FlightEntry.arrivalCodeKey, getAddressConcat(false));
        map.put(FlightEntry.departureCodeKey, getAddressConcat(true));
        map.put(FlightEntry.departureDateKey, getDate(true, context));
        map.put(AitaContract.AirlineEntry.codeKey, airlineCode);
        map.put(AitaContract.AirlineEntry.nameKey, airline.getName());
        map.put(FlightEntry.flightNumberKey, number);
        map.put(FlightEntry.idKey, id);
        return map;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
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

    public boolean isSmsEnabled() {
        return smsEnabled;
    }

    public void setSmsEnabled(boolean smsEnabled) {
        this.smsEnabled = smsEnabled;
    }

    public long getArrivalDelay() {
        if (arrivalDelayGate != 0) {
            return arrivalDelayGate;
        } else if (arrivalDelayRunway != 0) {
            return arrivalDelayRunway;
        } else {
            return 0;
        }
    }

    public long getDepartureDelay() {
        if (departureDelayGate != 0) {
            return departureDelayGate;
        } else if (departureDelayRunway != 0) {
            return departureDelayRunway;
        } else {
            return 0;
        }
    }

    public String getDepartureTerminalAndGate(Context context) {
        return "";
        //TODO:Stub - create proper strings and uncomment
        /*if ((departureTerminal.equals("null") || departureTerminal.isEmpty()) && (departureGate.equals("null") || departureGate.isEmpty()))
            return String.format("%s %s %s %s", context.getString(R.string.terminal), getDepartureTerminal(), context.getString(R.string.gate), getDepartureGate());
        else if (departureGate.equals("null") || departureGate.equals("null"))
            return String.format("%s %s", context.getString(R.string.terminal), getDepartureGate());
        else if (departureTerminal.equals("null ") || departureTerminal.isEmpty())
            return String.format("%s %s", context.getString(R.string.gate), getDepartureGate());
        else return "";*/
    }

    public String getArrivalTerminalAndGate(Context context) {
        return "";
        //TODO:Stub - create proper strings and uncomment
        /*if ((arrivalTerminal.equals("null") || arrivalTerminal.isEmpty()) && (arrivalGate.equals("null") || arrivalGate.isEmpty()))
            return String.format("%s %s %s %s", context.getString(R.string.terminal), getArrivalTerminal(), context.getString(R.string.gate), getArrivalGate());
        else if (arrivalGate.equals("null") || arrivalGate.equals("null"))
            return String.format("%s %s", context.getString(R.string.terminal), getArrivalGate());
        else if (arrivalTerminal.equals("null ") || arrivalTerminal.isEmpty())
            return String.format("%s %s", context.getString(R.string.gate), getArrivalGate());
        else return "";*/
    }

    public long getTimeToLanding() {
        if (hasSignificantUserReportedArrivalDelay()) {
            return calcTimeToEventArrival(arrivalDate + userReportedArrivalDelay);
        } else if (arrivalDateEstimatedGate != 0) {
            return calcTimeToEventArrival(arrivalDateEstimatedGate);
        } else if (arrivalDateEstimatedRunway != 0) {
            return calcTimeToEventArrival(arrivalDateEstimatedRunway);
        } else {
            return calcTimeToEventArrival(arrivalDate);
        }
    }

    public long getTimeToTakeOff() {
        if (hasSignificantUserReportedDepartureDelay()) {
            return calcTimeToEventDeparture(departureDate + userReportedDepartureDelay);
        } else if (departureDateEstimatedGate != 0) {
            return calcTimeToEventDeparture(departureDateEstimatedGate);
        } else if (departureDateEstimatedRunway != 0) {
            return calcTimeToEventDeparture(departureDateEstimatedRunway);
        } else {
            return calcTimeToEventDeparture(departureDate);
        }
    }

    private int getAirlineMinutesToBoarding() {
        return (airline == null) ? 20 : airline.getMinutesTillBoarding();
    }

    public long getTimeToBoarding() {
        return calcTimeToEventDeparture(departureDate) - TimeUnit.MINUTES
                .toSeconds(getAirlineMinutesToBoarding());
    }

    public long getTimeToCheckin() {
        long timeToCheckin = calcTimeToEventDeparture(departureDate);
        if (departureAirport.getCountryCode() != null && departureAirport.getCountryCode()
                .equals(arrivalAirport.getCountryCode())) {
            if (airline != null && airline.getCheckinCloseDomestic() != 0) {
                timeToCheckin -= airline.getCheckinCloseDomestic() * 60;
            } else {
                timeToCheckin -= 40 * 60;
            }
        } else {
            if (airline != null && airline.getCheckinCloseInternational() != 0) {
                timeToCheckin -= airline.getCheckinCloseInternational() * 60;
            } else {
                timeToCheckin -= 60 * 60;
            }
        }
        return timeToCheckin;
    }

    public static final long MILLIS_PER_HOUR = 3600000;

    private long calcTimeToEventDeparture(long time) {
        return (long) (time - departureAirport.getOffset() * MILLIS_PER_HOUR / 1000 - System
                .currentTimeMillis() / 1000);
    }

    private long calcTimeToEventArrival(long time) {
        return (long) (time - arrivalAirport.getOffset() * MILLIS_PER_HOUR / 1000 - System
                .currentTimeMillis() / 1000);
    }

    public long getLandingTime() {
        if (arrivalDateEstimatedGate != 0) {
            return arrivalDateEstimatedGate;
        } else if (arrivalDateEstimatedRunway != 0) {
            return arrivalDateEstimatedRunway;
        } else {
            return arrivalDate;
        }
    }

    public long getTakeOffTime() {
        if (departureDateEstimatedGate != 0) {
            return departureDateEstimatedGate;
        } else if (departureDateEstimatedRunway != 0) {
            return departureDateEstimatedRunway;
        } else {
            return departureDate;
        }
    }

    public boolean hasSignificantUserReportedDepartureDelay() {
        return Math.abs(userReportedDepartureDelay) >= 60;
    }

    public boolean hasSignificantUserReportedArrivalDelay() {
        return Math.abs(userReportedArrivalDelay) >= 60;
    }

    public long getBoardingTime() {
        return departureDate - TimeUnit.MINUTES.toSeconds(getAirlineMinutesToBoarding());
    }

    public long getCheckinTime() {
        long timeToCheckin = departureDate;
        if (departureAirport.getCountryCode() != null && departureAirport.getCountryCode()
                .equals(arrivalAirport.getCountryCode())) {
            if (airline != null && airline.getCheckinCloseDomestic() != 0) {
                timeToCheckin -= airline.getCheckinCloseDomestic() * 60;
            } else {
                timeToCheckin -= 40 * 60;
            }
        } else {
            if (airline != null && airline.getCheckinCloseInternational() != 0) {
                timeToCheckin -= airline.getCheckinCloseInternational() * 60;
            } else {
                timeToCheckin -= 60 * 60;
            }
        }
        return timeToCheckin;
    }

    @Event
    public int determinateNearestEvent() {
        long beforeCheckin = getTimeToCheckin();
        long beforeBoarding = getTimeToBoarding();
        long beforeTakeOff = getTimeToTakeOff();
        long beforeLanding = getTimeToLanding();
        if (beforeCheckin > 60) {
            return EVENT_CHECKIN;
        } else if (beforeBoarding > 60) {
            return EVENT_BOARDING;
        } else if (beforeTakeOff > 60) {
            return EVENT_TAKE_OFF;
        } else if (beforeLanding > 60) {
            return EVENT_LANDING;
        } else {
            return NOTHING;
        }
    }

    public long determinateTimeToNearestEvent() {
        long beforeCheckin = getTimeToCheckin();
        long beforeBoarding = getTimeToBoarding();
        long beforeTakeOff = getTimeToTakeOff();
        long beforeLanding = getTimeToLanding();
        if (beforeCheckin > 60) {
            return beforeCheckin;
        } else if (beforeBoarding > 60) {
            return beforeBoarding;
        } else if (beforeTakeOff > 60) {
            return beforeTakeOff;
        } else if (beforeLanding > 60) {
            return beforeLanding;
        } else {
            return getLandingTime();
        }
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "status='" + status + '\'' +
                ", seat='" + seat + '\'' +
                ", seatZone='" + seatZone + '\'' +
                ", tripID='" + tripID + '\'' +
                ", bookingReference='" + bookingReference + '\'' +
                ", dateAdded=" + dateAdded +
                ", departureGate='" + departureGate + '\'' +
                ", duration=" + duration +
                ", number='" + number + '\'' +
                ", arrivalDelayRunway=" + arrivalDelayRunway +
                ", departureDate=" + departureDate +
                ", departureDateUTC=" + departureDateUTC +
                ", arrivalDateEstimatedGate=" + arrivalDateEstimatedGate +
                ", departureDateActualRunway=" + departureDateActualRunway +
                ", departureDateEstimatedRunway=" + departureDateEstimatedRunway +
                ", arrivalDelayGate=" + arrivalDelayGate +
                ", arrivalGate='" + arrivalGate + '\'' +
                ", departureDelayRunway=" + departureDelayRunway +
                ", airlineCode='" + airlineCode + '\'' +
                ", arrivalDateFlightplan=" + arrivalDateFlightplan +
                ", arrivalDate=" + arrivalDate +
                ", arrivalDateUTC=" + arrivalDateUTC +
                ", arrivalDateActualGate=" + arrivalDateActualGate +
                ", arrivalDateScheduledGate=" + arrivalDateScheduledGate +
                ", arrivalDatePublished=" + arrivalDatePublished +
                ", arrivalDateEstimatedRunway=" + arrivalDateEstimatedRunway +
                ", departureDateScheduledGate=" + departureDateScheduledGate +
                ", departureDateFlightplan=" + departureDateFlightplan +
                ", departureDateActualGate=" + departureDateActualGate +
                ", departureDatePublished=" + departureDatePublished +
                ", lastUpdated=" + lastUpdated +
                ", departureDateEstimatedGate=" + departureDateEstimatedGate +
                ", arrivalCode='" + arrivalCode + '\'' +
                ", departureDelayGate=" + departureDelayGate +
                ", equipment='" + equipment + '\'' +
                ", arrivalTerminal='" + arrivalTerminal + '\'' +
                ", departureTerminal='" + departureTerminal + '\'' +
                ", aircraft=" + aircraft +
                ", id='" + id + '\'' +
                ", departureCode='" + departureCode + '\'' +
                ", distance=" + distance +
                ", arrivalDateActualRunway=" + arrivalDateActualRunway +
                ", purchased=" + purchased +
                ", purchasedPushOnly=" + purchasedPushOnly +
                ", pushEnabled=" + pushEnabled +
                ", smsEnabled=" + smsEnabled +
                ", calendarID='" + calendarID + '\'' +
                ", fromCalendar=" + fromCalendar +
                ", arrivalAirport=" + arrivalAirport +
                ", departureAirport=" + departureAirport +
                ", airline=" + airline +
                ", checkinSchemeJsonString =" + checkinSchemeJsonString +
                ", checkinAvailable =" + checkinAvailable +
                ", checkinRequestSent =" + checkinRequestSent +
                '}';
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getCalendarID() {
        return calendarID;
    }

    public void setCalendarID(String calendarID) {
        this.calendarID = calendarID;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {

        this.seat = seat;
    }

    public String getSeatZone() {
        return seatZone;
    }

    public void setSeatZone(String seatZone) {
        this.seatZone = seatZone;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public String getBookrefLastName() {
        return bookrefLastName;
    }

    public void setBookrefLastName(String bookrefLastName) {
        this.bookrefLastName = bookrefLastName;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public boolean isFromCalendar() {
        return fromCalendar;
    }

    public void setFromCalendar(boolean fromCalendar) {
        this.fromCalendar = fromCalendar;
    }

    public String getCheckinSchemeJsonString() {
        return checkinSchemeJsonString;
    }

    public void setCheckinSchemeJsonString(String checkinSchemeJsonString) {
        this.checkinSchemeJsonString = checkinSchemeJsonString;
    }

    public boolean isCheckinRequestSent() {
        return checkinRequestSent;
    }

    public void setCheckinRequestSent(Boolean checkinRequestSent) {
        this.checkinRequestSent = checkinRequestSent;
    }

    public BagTrackingInfo getBagTrackingInfo() {
        return bagTrackingInfo;
    }

    public void setBagTrackingInfo(BagTrackingInfo bagTrackingInfo) {
        this.bagTrackingInfo = bagTrackingInfo;
    }

    public int getOwnership() {
        return ownership;
    }

    public void setOwnership(int ownership) {
        this.ownership = ownership;
    }

    public void setUpFlight(long dtstart,
                            long dtend,
                            Airport departureAirport,
                            Airport arrivalAirport,
                            Aircraft aircraft,
                            Airline airline,
                            String tripId,
                            String calendarId) {
        setDepartureAirport(departureAirport);
        setArrivalAirport(arrivalAirport);
        setAirline(airline);
        setAircraft(aircraft);
        setFromCalendar(true);
        setCalendarID(calendarId);
        setSeat("");
        setSeatZone("");
        setTripID(tripId);
        setDepartureCode(departureAirport.getCode());
        setArrivalCode(arrivalAirport.getCode());
        if (System.currentTimeMillis() < dtstart) {
            setStatus("Scheduled");
        } else {
            setStatus("Landed");
        }
        setBookingReference("");
        setDateAdded(System.currentTimeMillis() / 1000L);
        setDepartureGate("XX");
        setDuration(TimeUnit.HOURS.toSeconds(3));
        setArrivalDelayRunway(0);

        long dtstartSeconds = dtstart / 1000L;
        long dtendSeconds = dtend / 1000L;

        setDepartureDate(dtstartSeconds);
        setDepartureDateUTC(dtstartSeconds);

        setArrivalDate(dtendSeconds);
        setArrivalDateUTC(dtendSeconds);

        setArrivalGate("XX");
        setDepartureDelayRunway(0);
        setLastUpdated(System.currentTimeMillis() / 1000L);
        setEquipment("XXX");
        setArrivalTerminal("X");
        setDepartureTerminal("X");
        setDistance(1000F);
        setPurchased(false);
        //   setPurchasedWithDatabase(false);
        setPushEnabled(false);
        setSmsEnabled(false);
        setSource("calendar_import");
    }

    public String getCheckinEntry() {
        return checkinEntry;
    }

    public void setCheckinEntry(String checkinEntry) {
        this.checkinEntry = checkinEntry;
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

    public TripLounge getTripLounge() {
        return tripLounge;
    }

    public void setTripLounge(TripLounge tripLounge) {
        this.tripLounge = tripLounge;
    }

    public boolean isFinishedByUser() {
        return isFinishedByUser;
    }

    public void setFinishedByUser(boolean finishedByUser) {
        isFinishedByUser = finishedByUser;
    }

    public boolean isPast() {
        return arrivalDateUTC < TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }

    public String getAirlineAircraftImageUrl() {
        return airlineAircraftImageUrl;
    }

    public void setAirlineAircraftImageUrl(String airlineAircraftImageUrl) {
        this.airlineAircraftImageUrl = airlineAircraftImageUrl;
    }

    public String getFlightDetailsAnalyticsLabel() {
        return MainHelper.getCurrentTimeStampWithMinutes() + "; " +
                MainHelper.getTimeStampWithMinutes(getTakeOffTime()) + "; " +
                getFullNumber() + "; " +
                getDepartureCode() + "; " +
                getArrivalCode() + "; ";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(seat);
        dest.writeString(fare);
        dest.writeString(source);
        dest.writeString(seatZone);
        dest.writeString(tripID);
        dest.writeString(bookingReference);
        dest.writeLong(dateAdded);
        dest.writeString(departureGate);
        dest.writeLong(duration);
        dest.writeString(number);
        dest.writeLong(arrivalDelayRunway);
        dest.writeLong(departureDate);
        dest.writeLong(departureDateUTC);
        dest.writeLong(arrivalDateEstimatedGate);
        dest.writeLong(departureDateActualRunway);
        dest.writeLong(departureDateEstimatedRunway);
        dest.writeLong(arrivalDelayGate);
        dest.writeString(arrivalGate);
        dest.writeLong(departureDelayRunway);
        dest.writeString(airlineCode);
        dest.writeLong(arrivalDateFlightplan);
        dest.writeLong(arrivalDate);
        dest.writeLong(arrivalDateUTC);
        dest.writeLong(arrivalDateActualGate);
        dest.writeLong(arrivalDateScheduledGate);
        dest.writeLong(arrivalDatePublished);
        dest.writeLong(arrivalDateEstimatedRunway);
        dest.writeLong(departureDateScheduledGate);
        dest.writeLong(departureDateFlightplan);
        dest.writeLong(departureDateActualGate);
        dest.writeLong(departureDatePublished);
        dest.writeLong(userReportedDepartureDelay);
        dest.writeLong(userReportedArrivalDelay);
        dest.writeLong(lastUpdated);
        dest.writeLong(departureDateEstimatedGate);
        dest.writeString(arrivalCode);
        dest.writeLong(departureDelayGate);
        dest.writeString(equipment);
        dest.writeString(arrivalTerminal);
        dest.writeString(baggage);
        dest.writeString(departureTerminal);
        dest.writeValue(aircraft);
        dest.writeValue(hotel);
        dest.writeValue(tripLounge);
        dest.writeString(id);
        dest.writeString(departureCode);
        if (distance == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeFloat(distance);
        }
        dest.writeLong(arrivalDateActualRunway);
        dest.writeByte((byte) (purchased ? 0x01 : 0x00));
        dest.writeByte((byte) (purchasedPushOnly ? 0x01 : 0x00));
        dest.writeByte((byte) (pushEnabled ? 0x01 : 0x00));
        dest.writeByte((byte) (smsEnabled ? 0x01 : 0x00));
        dest.writeString(calendarID);
        dest.writeByte((byte) (fromCalendar ? 0x01 : 0x00));
        dest.writeValue(arrivalAirport);
        dest.writeValue(departureAirport);
        dest.writeValue(airline);
        dest.writeString(checkinSchemeJsonString);
        dest.writeString(checkinEntry);
        dest.writeInt(checkinAvailable);
        dest.writeByte((byte) (checkinRequestSent ? 0x01 : 0x00));
        dest.writeString(airlineAircraftImageUrl);
        dest.writeParcelable(bagTrackingInfo, 0);
        dest.writeInt(ownership);
    }

    public String getLabel() {
        return String.format(
                "%s-%s-%s-%s-%s-%s",
                MainHelper.getCurrentTimeStamp(),
                MainHelper.getDateStamp(departureDate),
                airlineCode,
                number,
                departureCode,
                arrivalCode
        );
    }

    public enum Status {
        EN_ROUTE,
        CANCELED,
        DIVERTED,
        DATA_SOURCE_NEEDED,
        LANDED,
        NOT_OPERATIONAL,
        REDIRECTED,
        SCHEDULED,
        UNKNOWN;
    }

}