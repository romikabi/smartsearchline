package com.aita.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aita.AitaApplication;
import com.aita.model.Airport;
import com.aita.model.ListAirports;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@SuppressWarnings({"WeakerAccess", "unused"})
public class AirportsAssetDatabaseHelper extends SQLiteAssetHelper {

    public static final String TABLE_MAIN = "airport";
    public static final String TABLE_DE = "airport_de";
    public static final String TABLE_ES = "airport_es";
    public static final String TABLE_FR = "airport_fr";
    public static final String TABLE_JA = "airport_ja";
    public static final String TABLE_NO = "airport_no";
    public static final String TABLE_PT = "airport_pt";
    public static final String TABLE_RU = "airport_ru";
    public static final String TABLE_ZH = "airport_zh";
    private static final String DB_NAME = "Airports.sqlite";
    private static final int DB_VERSION = 2;
    private static final String COL_ID = "id";
    private static final String COL_CODE = "code";
    private static final String COL_NAME = "name";
    private static final String COL_CITY = "city";
    private static final String COL_COUNTRY_CODE = "country_code";
    private static final String COL_COUNTRY = "country";
    private static final String COL_SEARCH_STR = "search_string";
    private static final List<String> ADOPTED_LOCALES = Arrays.asList("de", "es", "fr", "ja", "pt", "ru", "no", "zh");
    private static AirportsAssetDatabaseHelper instance;

    private AirportsAssetDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        setForcedUpgrade(DB_VERSION);
    }

    public synchronized static AirportsAssetDatabaseHelper getInstance() {
        if (instance == null) {
            instance = new AirportsAssetDatabaseHelper(AitaApplication.getContext());
        }
        return instance;
    }

    public ListAirports getAirportData(String table) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

            String[] sqlSelect = {COL_NAME, COL_CITY, COL_CODE};

            qb.setTables(table);
            Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, COL_NAME);

            if (cursor == null) {
                return null;
            }

            cursor.moveToNext();

            ArrayList<Airport> airportArrayList = new ArrayList<>();
            ListAirports result = new ListAirports();

            int codeColIndex = cursor.getColumnIndex(COL_CODE);
            int cityColIndex = cursor.getColumnIndex(COL_CITY);
            int nameColIndex = cursor.getColumnIndex(COL_NAME);

            if (cursor.moveToFirst()) {
                do {
                    String code = cursor.getString(codeColIndex);
                    String name = cursor.getString(nameColIndex);
                    String city = cursor.getString(cityColIndex);
                    Airport airport = new Airport(city, name, code);
                    airportArrayList.add(airport);
                } while (cursor.moveToNext());
            }

            cursor.close();
            result.setAirports(airportArrayList);

            return result;
        } catch (SQLException mSQLException) {
            return null;
        }
    }

    public ArrayList<Airport> airportListFromCursor(Cursor cursor) {
        if (cursor == null) {
            return new ArrayList<>();
        }

        ArrayList<Airport> airportArrayList = new ArrayList<>();
        int codeColIndex = cursor.getColumnIndex(COL_CODE);
        int cityColIndex = cursor.getColumnIndex(COL_CITY);
        int nameColIndex = cursor.getColumnIndex(COL_NAME);

        if (cursor.moveToFirst()) {
            do {
                String code = cursor.getString(codeColIndex);
                String name = cursor.getString(nameColIndex);
                String city = cursor.getString(cityColIndex);
                Airport airport = new Airport(city, name, code);
                airportArrayList.add(airport);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return airportArrayList;
    }

    @Nullable
    public String getTranslatedAirportName(@NonNull String airportCode) {
        String translatedAirportName = null;

        final String langCode = Locale.getDefault().getLanguage().toLowerCase();
        if (ADOPTED_LOCALES.contains(langCode)) {
            try {
                String query = "SELECT DISTINCT " + COL_NAME +
                        " FROM " + TABLE_MAIN + "_" + langCode +
                        " WHERE " + COL_CODE + " = '" + airportCode + "'" +
                        " LIMIT 1;";
                translatedAirportName = getReadableDatabase().compileStatement(query).simpleQueryForString();
            } catch (SQLException e) {
                // Do nothing
            }
        }

        return translatedAirportName;
    }

    @Nullable
    public String getTranslatedCityName(@NonNull String airportCode) {
        String translatedCityName = null;

        final String langCode = Locale.getDefault().getLanguage().toLowerCase();
        if (ADOPTED_LOCALES.contains(langCode)) {
            try {
                String query = "SELECT DISTINCT " + COL_CITY +
                        " FROM " + TABLE_MAIN + "_" + langCode +
                        " WHERE " + COL_CODE + " = '" + airportCode + "'" +
                        " LIMIT 1;";
                translatedCityName = getReadableDatabase().compileStatement(query).simpleQueryForString();
            } catch (SQLException e) {
                // Do nothing
            }
        }

        return translatedCityName;
    }

    @NonNull
    public String getTranslatedCountryName(@NonNull String country) {
        String translatedCountryName = country;

        final String langCode = Locale.getDefault().getLanguage().toLowerCase();
        if (ADOPTED_LOCALES.contains(langCode)) {
            try {
                String query = "SELECT " + COL_COUNTRY +
                        " FROM " + TABLE_MAIN + "_" + langCode +
                        " WHERE " + COL_ID + " IN " +
                        "(SELECT " + COL_ID + " FROM " +
                        TABLE_MAIN + " WHERE " + COL_COUNTRY + " = '" + country + "') AND " +
                        COL_COUNTRY + " != '' AND " +
                        COL_COUNTRY + " IS NOT NULL " +
                        "LIMIT 1;";
                translatedCountryName = getReadableDatabase().compileStatement(query).simpleQueryForString();
            } catch (SQLException e) {
                // Do nothing
            }
        }

        return translatedCountryName;
    }

    @NonNull
    public ListAirports getAirportData() {
        final String langCode = Locale.getDefault().getLanguage().toLowerCase();
        if (ADOPTED_LOCALES.contains(langCode)) {
            return getTranslatedAirports(langCode);
        } else {
            return getAirports();
        }
    }

    @NonNull
    private ListAirports getTranslatedAirports(final String langCode) {
        final ArrayList<Airport> airports = new ArrayList<>();
        final SQLiteDatabase db = getReadableDatabase();
        final String query = "SELECT DISTINCT " +
                TABLE_MAIN + "." + COL_NAME + ", " +
                TABLE_MAIN + "." + COL_CITY + ", " +
                TABLE_MAIN + "." + COL_CODE + ", " +
                TABLE_MAIN + "_" + langCode + "." + COL_NAME + " AS '" + COL_NAME + "_" + langCode + "', " +
                TABLE_MAIN + "_" + langCode + "." + COL_CITY + " AS '" + COL_CITY + "_" + langCode + "' " +
                "FROM " + TABLE_MAIN + " LEFT JOIN " + TABLE_MAIN + "_" + langCode + " ON " +
                "(" + TABLE_MAIN + "." + COL_CODE + " = " + TABLE_MAIN + "_" + langCode + "." + COL_CODE + ") " +
                "ORDER BY " + TABLE_MAIN + "." + COL_NAME + " ASC;";
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
            final int codeColIndex = cursor.getColumnIndex(COL_CODE);
            final int cityColIndex = cursor.getColumnIndex(COL_CITY);
            final int nameColIndex = cursor.getColumnIndex(COL_NAME);
            final int nameTranslatedColIndex = cursor.getColumnIndex(COL_NAME + "_" + langCode);
            final int cityTranslatedColIndex = cursor.getColumnIndex(COL_CITY + "_" + langCode);
            while (cursor.moveToNext()) {
                final String code = cursor.getString(codeColIndex);
                final String name = cursor.getString(nameColIndex);
                final String city = cursor.getString(cityColIndex);
                final String nameTranslated = cursor.getString(nameTranslatedColIndex);
                final String cityTranslated = cursor.getString(cityTranslatedColIndex);
                final Airport airport = new Airport(city, name, code);
                if (nameTranslated != null && !nameTranslated.isEmpty()) {
                    airport.setAirportNameTranslated(nameTranslated);
                }
                if (cityTranslated != null && !cityTranslated.isEmpty()) {
                    airport.setCityTranslated(cityTranslated);
                }
                airports.add(airport);
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return new ListAirports(airports);
    }

    @NonNull
    public ListAirports getAirports() {
        MainHelper.log(DB_NAME, "getAirports()");
        final ArrayList<Airport> airports = new ArrayList<>();
        final SQLiteDatabase db = getReadableDatabase();
        final String query = "SELECT DISTINCT " +
                COL_NAME + ", " + COL_CITY + ", " + COL_CODE +
                " FROM " + TABLE_MAIN +
                " ORDER BY " + COL_NAME + " ASC;";
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
            final int codeColIndex = cursor.getColumnIndex(COL_CODE);
            final int cityColIndex = cursor.getColumnIndex(COL_CITY);
            final int nameColIndex = cursor.getColumnIndex(COL_NAME);
            while (cursor.moveToNext()) {
                final String code = cursor.getString(codeColIndex);
                final String name = cursor.getString(nameColIndex);
                final String city = cursor.getString(cityColIndex);
                final Airport airport = new Airport(city, name, code);
                airports.add(airport);
            }
        } catch (Exception e) {
            MainHelper.logException(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return new ListAirports(airports);
    }

    public Airport getAirport(String airportCode) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

            String[] sqlSelect = {COL_NAME, COL_CITY, COL_CODE};
            String selection = COL_CODE + " = '" + airportCode + "'";

            qb.setTables(TABLE_MAIN);
            Cursor cursor = qb.query(db, sqlSelect, selection, null,
                    null, null, null);

            if (cursor == null) {
                return null;
            }

            cursor.moveToNext();
            int codeColIndex = cursor.getColumnIndex(COL_CODE);
            int cityColIndex = cursor.getColumnIndex(COL_CITY);
            int nameColIndex = cursor.getColumnIndex(COL_NAME);
            if (cursor.moveToFirst()) {
                if (cursor.moveToNext()) {
                    String code = cursor.getString(codeColIndex);
                    String name = cursor.getString(nameColIndex);
                    String city = cursor.getString(cityColIndex);
                    return new Airport(city, name, code);
                }
            }
            cursor.close();
            return null;
        } catch (SQLException mSQLException) {
            return new Airport("", "", "");
        }
    }

    public Airport findAirportWithCityLike(String placeName) {
        try {
            final String langCode = Locale.getDefault().getLanguage().toLowerCase();
            String tables;
            if (ADOPTED_LOCALES.contains(langCode)) {
                tables = TABLE_MAIN + "_" + langCode;
            } else {
                tables = TABLE_MAIN;
            }

            SQLiteDatabase db = getReadableDatabase();
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

            String[] sqlSelect = {COL_NAME, COL_CITY, COL_CODE};
            String selection = COL_CITY + " LIKE '%" + placeName + "%'";

            qb.setTables(tables);
            Cursor cursor = qb.query(db, sqlSelect, selection, null,
                    null, null, null);

            if (cursor == null) {
                return null;
            }

            cursor.moveToNext();
            int codeColIndex = cursor.getColumnIndex(COL_CODE);
            int cityColIndex = cursor.getColumnIndex(COL_CITY);
            int nameColIndex = cursor.getColumnIndex(COL_NAME);
            if (cursor.moveToFirst()) {
                if (cursor.moveToNext()) {
                    String code = cursor.getString(codeColIndex);
                    String name = cursor.getString(nameColIndex);
                    String city = cursor.getString(cityColIndex);
                    return new Airport(city, name, code);
                }
            }
            cursor.close();
            return null;
        } catch (SQLException mSQLException) {
            return null;
        }
    }

    public void runUpdateQueries(final JSONArray queries) {
        final SQLiteDatabase db = getWritableDatabase();

        if (db == null) return;

        db.beginTransaction();
        for (int i = 0; i < queries.length(); i++) {
            final String query = queries.optString(i);
            if (query != null) {
                try {
                    db.execSQL(query);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                    MainHelper.logException(e);
                }
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

}
