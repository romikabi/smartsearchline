package com.aita.helpers;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aita.AitaApplication;
import com.aita.model.Airline;
import com.aita.model.ListAirlines;
import com.aita.shared.AitaContract;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.aita.helpers.MainHelper.isDummyOrNull;

public class AirlineAssetDatabaseHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "Airlines100.sqlite";
    private static final int DATABASE_VERSION = 100;
    private static AirlineAssetDatabaseHelper instance;
    private static final List<String> ADOPTED_LOCALES = Arrays.asList(
            "de",
            "es",
            "fr",
            "ja",
            "pt",
            "ru",
            "no",
            "zh"
    );

    private AirlineAssetDatabaseHelper() {
        super(
                AitaApplication.getContext(),
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
        setForcedUpgrade(DATABASE_VERSION);
    }

    @NonNull
    public synchronized static AirlineAssetDatabaseHelper getInstance() {
        if (instance == null) {
            instance = new AirlineAssetDatabaseHelper();
        }
        return instance;
    }

    @Nullable
    public Airline getAirline(String airlineCode) {
        Airline airline = null;
        final SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT id, icao, iata, checkin_url, name, code " +
                            "FROM airline " +
                            "WHERE icao=? OR iata=? OR code=?;",
                    new String[]{airlineCode, airlineCode, airlineCode}
            );
            cursor.moveToFirst();
            airline = airlineFromCursor(cursor);
        } catch (Exception e) {
            MainHelper.logException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return airline;
    }

    @Nullable
    public Airline getAirlineByName(String airlineName) {
        Airline airline = null;
        final SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT id, icao, iata, checkin_url, name, code " +
                            "FROM airline " +
                            "WHERE name LIKE '%?%';",
                    new String[]{airlineName}
            );
            cursor.moveToFirst();
            airline = airlineFromCursor(cursor);
        } catch (Exception e) {
            MainHelper.logException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return airline;
    }

    @NonNull
    public Airline getProfileAirline(String code) {
        Airline airline = new Airline();
        final SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT id, icao, iata, name, code " +
                            "FROM airline " +
                            "WHERE icao=? OR iata=? OR code=?;",
                    new String[]{code, code, code}
            );
            cursor.moveToFirst();
            airline = airlineFromCursor(cursor);
        } catch (Exception e) {
            MainHelper.logException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return airline;
    }

    @NonNull
    private Airline airlineFromCursor(Cursor cursor) {
        final int idColIndex = cursor.getColumnIndex("id");
        final int iataColIndex = cursor.getColumnIndex("iata");
        final int icaoColIndex = cursor.getColumnIndex("icao");
        final int codeColIndex = cursor.getColumnIndex("code");
        final int nameColIndex = cursor.getColumnIndex("name");
        final int checkinUrlColIndex = cursor.getColumnIndex("checkin_url");
        final int twitterColIndex = cursor.getColumnIndex("twitter");
        final int baggageUrlColIndex = cursor.getColumnIndex("baggage_url");
        String id = "";
        String innerCode = "";
        String iata = "";
        String icao = "";
        String code;
        String name = "";
        String checkinUrl = "";
        String twitter = "";
        String baggageUrl = "";
        if (idColIndex != -1) {
            id = cursor.getString(idColIndex);
        }
        if (codeColIndex != -1) {
            innerCode = cursor.getString(codeColIndex);
        }
        if (iataColIndex != -1) {
            iata = cursor.getString(iataColIndex);
        }
        if (icaoColIndex != -1) {
            icao = cursor.getString(icaoColIndex);
        }
        if (!iata.isEmpty()) {
            code = iata;
        } else if (!icao.isEmpty()) {
            code = icao;
        } else {
            code = innerCode;
        }
        if (nameColIndex != -1) {
            name = cursor.getString(nameColIndex);
        }
        if (checkinUrlColIndex != -1) {
            checkinUrl = cursor.getString(checkinUrlColIndex);
        }
        if (twitterColIndex != -1) {
            twitter = cursor.getString(twitterColIndex);
        }
        if (baggageUrlColIndex != -1) {
            baggageUrl = cursor.getString(baggageUrlColIndex);
        }
        final Airline airline = new Airline(code, name);
        airline.setId(id);
        airline.setInnerCode(innerCode);
        airline.setCheckinAvailable(!checkinUrl.isEmpty());
        airline.setWebCheckin(checkinUrl);
        airline.setMobileCheckin(checkinUrl);
        airline.setTwitter(twitter);
        airline.setBaggageUrl(baggageUrl);
        return airline;
    }

    @Nullable
    public String getTranslatedAirlineName(@NonNull String airlineId) {
        String translatedName = null;
        final String langCode = Locale.getDefault().getLanguage().toLowerCase();
        if (ADOPTED_LOCALES.contains(langCode)) {
            final String query = "SELECT name FROM airline_" + langCode + " WHERE id=? LIMIT 1;";
            final SQLiteDatabase db = getReadableDatabase();
            try {
                final SQLiteStatement statement = db.compileStatement(query);
                statement.bindString(1, airlineId);
                final String name = statement.simpleQueryForString();
                if (!isDummyOrNull(name)) {
                    translatedName = name;
                }
            } catch (SQLException e) {
                MainHelper.logException(e);
            }
        }
        return translatedName;
    }

    @NonNull
    public ListAirlines getAirlineData() {
        final String langCode = Locale.getDefault().getLanguage().toLowerCase();
        if (ADOPTED_LOCALES.contains(langCode)) {
            return getTranslatedAirlines(langCode);
        } else {
            return getAirlines();
        }
    }

    @NonNull
    private ListAirlines getTranslatedAirlines(final String langCode) {
        final ArrayList<Airline> airlines = new ArrayList<>();
        final SQLiteDatabase db = getReadableDatabase();
        final String query = "SELECT DISTINCT " +
                "airline.id, " +
                "airline.iata, " +
                "airline.icao, " +
                "airline.code, " +
                "airline.name, " +
                "airline_" + langCode + ".name AS 'name_" + langCode + "' " +
                "FROM " +
                "airline LEFT JOIN airline_" + langCode + " " +
                "ON airline.id = airline_" + langCode + ".id;";
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
            final int idColIndex = cursor.getColumnIndex("id");
            final int iataColIndex = cursor.getColumnIndex("iata");
            final int icaoColIndex = cursor.getColumnIndex("icao");
            final int codeColIndex = cursor.getColumnIndex("code");
            final int nameColIndex = cursor.getColumnIndex("name");
            final int nameTranslatedColIndex = cursor.getColumnIndex("name_" + langCode);
            while (cursor.moveToNext()) {
                final String id = cursor.getString(idColIndex);
                final String iataCode = cursor.getString(iataColIndex);
                final String icaoCode = cursor.getString(icaoColIndex);
                final String innerCode = cursor.getString(codeColIndex);
                final String name = cursor.getString(nameColIndex);
                final String nameTranslated = cursor.getString(nameTranslatedColIndex);
                final String primaryCode;
                if (iataCode != null && !iataCode.isEmpty()) {
                    primaryCode = iataCode;
                } else if (icaoCode != null && !icaoCode.isEmpty()) {
                    primaryCode = icaoCode;
                } else {
                    primaryCode = innerCode;
                }
                final Airline airline = new Airline(primaryCode, name);
                airline.setId(id);
                airline.setIata(iataCode);
                airline.setIcao(icaoCode);
                airline.setInnerCode(innerCode);
                if (nameTranslated != null && !nameTranslated.isEmpty()) {
                    airline.setNameTranslated(nameTranslated);
                }
                airlines.add(airline);
            }
        } catch (Exception e) {
            MainHelper.logException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return new ListAirlines(airlines);
    }

    @NonNull
    private ListAirlines getAirlines() {
        final ArrayList<Airline> airlines = new ArrayList<>();
        final SQLiteDatabase db = getReadableDatabase();
        final String query = "SELECT DISTINCT id, name, icao, iata, code FROM airline;";
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
            final int idColIndex = cursor.getColumnIndex("id");
            final int iataColIndex = cursor.getColumnIndex("iata");
            final int icaoColIndex = cursor.getColumnIndex("icao");
            final int codeColIndex = cursor.getColumnIndex("code");
            final int nameColIndex = cursor.getColumnIndex("name");
            while (cursor.moveToNext()) {
                final String id = cursor.getString(idColIndex);
                final String iataCode = cursor.getString(iataColIndex);
                final String icaoCode = cursor.getString(icaoColIndex);
                final String innerCode = cursor.getString(codeColIndex);
                final String name = cursor.getString(nameColIndex);
                final String primaryCode;
                if (iataCode != null && !iataCode.isEmpty()) {
                    primaryCode = iataCode;
                } else if (icaoCode != null && !icaoCode.isEmpty()) {
                    primaryCode = icaoCode;
                } else {
                    primaryCode = innerCode;
                }
                final Airline airline = new Airline(primaryCode, name);
                airline.setId(id);
                airline.setIata(iataCode);
                airline.setIcao(icaoCode);
                airline.setInnerCode(innerCode);
                airlines.add(airline);
            }
        } catch (Exception e) {
            MainHelper.logException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return new ListAirlines(airlines);
    }

    public void runUpdateQueries(final JSONArray queries) {
        final SQLiteDatabase db = getWritableDatabase();
        if (db == null) {
            return;
        }
        db.beginTransaction();
        for (int i = 0; i < queries.length(); i++) {
            final String query = queries.optString(i);
            if (query != null) {
                try {
                    db.execSQL(query);
                } catch (SQLiteException e) {
                    MainHelper.logException(e);
                }
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

}