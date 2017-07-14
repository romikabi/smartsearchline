package com.aita.shared;

import android.provider.BaseColumns;

public final class AitaContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public AitaContract() {
    }

    public static abstract class NotificationsEntry implements BaseColumns {
        public static final String TABLE_NAME = "notifications";

        public static final String idKey = "nid";
        public static final String flightIdKey = "history_id";
        public static final String dateKey = "date";
        public static final String textKey = "text";
        public static final String typeKey = "type";
        public static final String formatKey = "format";
        public static final String oldValueKey = "old_value";
        public static final String newValueKey = "new_value";
        public static final String paramsKey = "params";
    }

    public static abstract class TripEntry implements BaseColumns {
        public static final String TABLE_PREFIX = "trip.";
        public static final String TABLE_NAME = "trip";

        public static final String idKey = "id";
        public static final String departureDateKey = "departure_date";
        public static final String arrivalDateKey = "arrival_date";
        public static final String updatedKey = "updated";
        public static final String arrivalAirportCodeKey = "arrivalAirportCode";
        public static final String departureAirportCodeKey = "departureAirportCode";
        public static final String departureDateUtcKey = "departure_date_utc";
        public static final String arrivalDateUtcKey = "arrival_date_utc";
        public static final String calendarIdKey = "calendar_id";
        public static final String purchasedKey = "purchased";
        public static final String purchasedPushOnlyKey = "purchased_push_only";
        public static final String pushEnabledKey = "push_enabled";
        public static final String dateAddedKey = "date_added";
        public static final String smsEnabledKey = "sms_enabled";
        public static final String ownershipKey = "ownership";
    }

    public static abstract class HotelEntry implements BaseColumns {
        public static final String TABLE_PREFIX = "hotel.";
        public static final String TABLE_NAME = "hotel";

        public static final String sourceKey = "source";
        public static final String sourceIdKey = "source_id";
        public static final String passbookUrlKey = "passbook_url";
        public static final String dateFromKey = "date_from";
        public static final String dateToKey = "date_to";
        public static final String guestsKey = "guests";
        public static final String imageUrlKey = "image_url";
        public static final String nameKey = "name";
        public static final String starsKey = "stars";
        public static final String addressKey = "address";
        public static final String phoneKey = "phone";
        public static final String cityKey = "city";
        public static final String idKey = "hotel_id";
        public static final String tripIdKey = "trip_id";
    }

    public static abstract class TripLoungeEntry implements BaseColumns {
        public static final String TABLE_PREFIX = "trip_lounge.";
        public static final String TABLE_NAME = "trip_lounge";

        public static final String sourceKey = "source";
        public static final String sourceIdKey = "source_id";
        public static final String tripIdKey = "trip_id";
        public static final String currencyKey = "currency";
        public static final String bookingDateKey = "booking_date";
        public static final String bookingDateUtcKey = "booking_date_utc";
        public static final String peopleKey = "people";
        public static final String paymentIdKey = "payment_id";
        public static final String priceKey = "price";
        public static final String canceledKey = "canceled";
        public static final String loungeKey = "lounge";
        public static final String codeUrlsKey = "code_urls";
        public static final String bookingTypeKey = "booking_type";
        public static final String totalSumKey = "total_sum";

    }

    public static abstract class CarRentalEntry implements BaseColumns {
        public static final String TABLE_PREFIX = "car_rental.";
        public static final String TABLE_NAME = "car_rental";

        public static final String jsonKey = "json_object";
        public static final String sourceKey = "source";
        public static final String sourceIdKey = "source_id";
        public static final String tripIdKey = "trip_id";
        public static final String providerKey = "provider";

        public static final String pickupDateKey = "pickup_date";
        public static final String dropoffDateKey = "dropoff_date";
        public static final String pickupAddressKey = "pickup_address";
        public static final String dropoffAddressKey = "dropoff_address";

        public static final String reservationCodeKey = "reservation_code";
        public static final String deletedKey = "deleted";
        public static final String carNameKey = "car_name";
        public static final String dropoffLatKey = "dropoff_lat";

        public static final String dropoffLonKey = "dropoff_lon";
        public static final String pickupLatKey = "pickup_lat";
        public static final String pickupLonKey = "pickup_lon";

    }

    public static abstract class FlightEntry implements BaseColumns {
        public static final String TABLE_PREFIX = "status.";
        public static final String TABLE_NAME = "status";
        public static final int dbVersion = 24;
        /*
         * Key values
         */
        public static final String departureDatePublishedKey = "departure_date_published";
        public static final String departureDateKey = "departure_date";
        public static final String durationKey = "duration";
        public static final String fromCalendarKey = "from_calendar";
        public static final String flightNumberKey = "number";
        public static final String idKey = "id";
        public static final String departureCodeKey = "departure_code";
        public static final String arrivalCodeKey = "arrival_code";
        public static final String statusObjectKey = "status";
        public static final String arrivalAirportObjectKey = "airport_arrival";
        public static final String departureAirportObjectKey = "airport_departure";
        public static final String airlineObjectKey = "airline";
        public static final String distanceKey = "distance";
        public static final String departureDateUTCKey = "departure_date_utc";
        public static final String arrivalDateUTCKey = "arrival_date_utc";
        public static final String dateAddedKey = "date_added";
        public static final String pushEnabledKey = "push_enabled";
        public static final String calendarIdKey = "calendar_id";
        public static final String purchasedKey = "purchased";
        public static final String pusrchasedPushOnlyKey = "purchased_push_only";
        public static final String smsEnabledKey = "sms_enabled";
        /*
         * "status" json keys
         */
        public static final String departureDateActualRunwayKey = "departure_date_actual_runway";
        public static final String statusKey = "status";
        public static final String seatZoneKey = "seat_zone";
        public static final String seatKey = "seat";
        public static final String bookingReferenceKey = "booking_reference";
        public static final String bookrefLastNameKey = "bookref_last_name";
        public static final String departureGateKey = "departure_gate";
        public static final String arrivalDelayRunwayKey = "arrival_delay_runway";
        public static final String arrivalDateEstimatedGateKey = "arrival_date_estimated_gate";
        public static final String departureDateEstimatedRunwayKey = "departure_date_estimated_runway";
        public static final String arrivalDelayGateKey = "arrival_delay_gate";
        public static final String arrivalGateKey = "arrival_gate";
        public static final String departureDelayRunwayKey = "departure_delay_runway";
        public static final String arrivalTerminalKey = "arrival_terminal";
        public static final String departureTerminalKey = "departure_terminal";
        public static final String arrivalDateActualRunwayKey = "arrival_date_actual_runway";
        public static final String arrivalDateFlightplanKey = "arrival_date_flightplan";
        public static final String arrivalDateKey = "arrival_date";
        public static final String arrivalDateActualGateKey = "arrival_date_actual_gate";
        public static final String arrivalDateScheduledGateKey = "arrival_date_scheduled_gate";
        public static final String arrivalDatePublishedKey = "arrival_date_published";
        public static final String arrivalDateEstimatedRunwayKey = "arrival_date_estimated_runway";
        public static final String departureDateScheduledGateKey = "arrival_date_scheduled_gate";
        public static final String departureDateActualGateKey = "departure_date_actual_gate";
        public static final String departureDateFlightplanKey = "departure_date_flightplan";
        public static final String lastUpdatedKey = "last_updated";
        public static final String departureDateEstimatedGateKey = "departure_date_estimated_gate";
        public static final String departureDelayGateKey = "departure_delay_runway";
        public static final String equipmentKey = "equipment";
        public static final String airlineCodeKey = "carrier";
        public static final String checkinSchemeKey = "checkin_scheme_json_str";
        public static final String checkinRequestSentKey = "checkin_request_sent";
        public static final String checkinEntryKey = "checkin_entry";
        public static final String userReportedDepartureDelayKey = "user_reported_departure_delay";
        public static final String userReportedArrivalDelayKey = "user_reported_arrival_delay";
        public static final String baggageKey = "baggage";
        public static final String tripIdKey = "trip_id";
        public static final String isFinishedByUserKey = "is_finished_by_user";
        public static final String airlineAircraftImageKey = "airline_aircraft_image_url";
        public static final String ownershipKey = "ownership";

    }

    public static abstract class TripItEntry {
        public static final String CONSUMER_KEY = "d68c512f4cb314e648d70377fb69b2ee28bda2d1";
        public static final String CONSUMER_SECRET = "915527fba5832a55a50d76fac1807cd035f5a8dc";
        public static final String DEFAULT_API_URI_PREFIX = "https://api.tripit.com";
        public static final String DEFAULT_WEB_URI_PREFIX = "https://www.tripit.com";

    }

    public static abstract class AirlineEntry implements BaseColumns {
        public static final String TABLE_NAME = "airline";
        public static final String TABLE_PREFIX = "airline.";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        public static final String codeKey = "code";
        public static final String nameKey = "name";
        public static final String iataKey = "iata";
        public static final String icaoKey = "icao";
        public static final String foodRate = "food";
        public static final String serviceRate = "service";
        public static final String staffRate = "staff";
        public static final String checkinOpenHours = "checkin_open_hrs";
        public static final String checkinCloseInt = "checkin_close_int";
        public static final String checkinCloseDom = "checkin_close_dom";
        public static final String rating = "rating";
        public static final String reportsCount = "reports_count";
        public static final String boardingTillMins = "boarding_till_mins";
    }

    public static abstract class AircraftEntry implements BaseColumns {
        public static final String TABLE_NAME = "aircraft";
        public static final String TABLE_PREFIX = "aircraft.";
        public static final String codeKey = "code";
        public static final String nameKey = "name";
        public static final String modelKey = "model";
        public static final String urlKey = "url";
    }

    public static abstract class AirportEntry implements BaseColumns {
        public static final String TABLE_NAME = "airport";
        public static final String TABLE_PREFIX = "airport.";
        public static final String TABLE_DEPARTURE_PREFIX = "departure_code.";
        public static final String TABLE_ARRIVAL_PREFIX = "arrival_code.";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String codeKey = "code";
        public static final String cityKey = "city";
        public static final String countryCodeKey = "country";
        public static final String countryFullKey = "country_full";
        public static final String nameKey = "name";
        public static final String offsetKey = "offset";
        public static final String urlKey = "url";
        public static final String phoneKey = "phone";
        public static final String latitudeKey = "latitude";
        public static final String longitudeKey = "longitude";
        public static final String delayKey = "delay";

        public static final String checkinTime = "time_checkin";
        public static final String securityTime = "time_security";
        public static final String passportTime = "time_passport";
        public static final String customsTime = "time_customs";
        public static final String baggageTime = "time_baggage";
        public static final String passportArrivalTime = "time_passport_arrival";
        public static final String customsArrivalTime = "time_customs_arrival";
        public static final String rating = "rating";
        public static final String reportsCount = "reports_count";
    }

    public static abstract class BagTrackingInfoEntry {
        public static final String TABLE_NAME = "bag_tracking_info";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_FLIGHT_ID = "flight_id";
        public static final String COLUMN_PASSENGER_NAME = "passenger_name";
        public static final String COLUMN_EVENTS_JSON = "events_json_str";
        public static final String COLUMN_PHOTO_PATHS_JSON = "photo_paths_json_str";

    }

    public static abstract class TripInsuranceEntry {
        public static final String TABLE_NAME = "trip_insurance";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TRIP_ID = "trip_id";
        public static final String COLUMN_SOURCE = "source";
        public static final String COLUMN_SOURCE_ID = "source_id";
        public static final String COLUMN_SERIES = "series";
        public static final String COLUMN_DOCUMENT_URL = "document_url";
        public static final String COLUMN_PAYMENT_ID = "payment_id";
        public static final String COLUMN_POLICY_ID = "policy_id";
        public static final String COLUMN_PRODUCT_JSON = "product_json_str";

    }

}