package com.aita.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;

import com.aita.model.Airline;
import com.aita.model.Airport;
import com.example.romanabuzyarov.smartsearchline.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * romanabuzyarov
 * 13.07.17
 */

public class InitialAdapter extends ArrayAdapter {
    private List<Airport> airportsAll;
    private List<Airline> airlinesAll;

    private final List items;
    private final List suggestions;

    private LayoutInflater inflater;

    public InitialAdapter(@NonNull Context context, @LayoutRes int resource, List<Airport> airports, List<Airline> airlines) {
        super(context, resource);
        suggestions = new ArrayList();
        this.airportsAll = airports;
        this.airlinesAll = airlines;

        items = new ArrayList();
        items.addAll(airlines);
        items.addAll(airports);
        addAll(items);
        inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    private final Filter filter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            if (resultValue != null) {
                if (resultValue instanceof Airport)
                    return ((Airport) (resultValue)).getAirportName();
                if (resultValue instanceof Airline)
                    return ((Airline) resultValue).getName();
                return "";
            } else {
                return "";
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                final String search = constraint.toString().toLowerCase(Locale.getDefault());

                final List<Airline> airlinesSkipped = new ArrayList<>();
                final List<Airport> airportsSkipped = new ArrayList<>();

                for (Airline airline : airlinesAll) {
                    final String iata = airline.getIata().toLowerCase();
                    final String icao = airline.getIcao().toLowerCase();
                    final String innerCode = airline.getInnerCode().toLowerCase();
                    if (iata.startsWith(search) || icao.startsWith(search) || innerCode.startsWith(search)) {
                        suggestions.add(airline);
                    } else {
                        airlinesSkipped.add(airline);
                    }
                }

                for (Airport airport : airportsAll) {
                    if (airport.getCode().toLowerCase().startsWith(search.toLowerCase()))
                        suggestions.add(airport);
                    else
                        airportsSkipped.add(airport);
                }

                for (Airline airline : airlinesSkipped) {
                    if (airline.isTranslated() &&
                            airline.getNameTranslated().toLowerCase().contains(search) ||
                            airline.getName().toLowerCase().contains(search)) {
                        suggestions.add(airline);
                    }
                }

                for (Airport airport : airportsSkipped)
                    if (airport.isTranslated() &&
                            (airport.getAirportNameTranslated().toLowerCase().contains(search.toLowerCase()) ||
                                    airport.getCityTranslated().toLowerCase().contains(search.toLowerCase())) ||
                            airport.getAirportName().toLowerCase().contains(search.toLowerCase()) ||
                            airport.getCity().toLowerCase().contains(search.toLowerCase()))
                        suggestions.add(airport);

                final FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try {
                ArrayList filteredList = (ArrayList) results.values;
                if (results != null) {
                    clear();
                    addAll(filteredList);
                }
            } catch (Exception e) {
                notifyDataSetChanged();
            }
        }
    };

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Airline)
            return 0;
        return 1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (getItemViewType(position) == 0) {
            View v = convertView;

            v = inflater.inflate(R.layout.airline_suggestion, parent, false);

            Airline airline = (Airline) getItem(position);
            if (airline != null) {
                TextView airlineNameLabel = (TextView) v
                        .findViewById(R.id.airline_name);
                TextView airlineCodeLabel = (TextView) v
                        .findViewById(R.id.airline_code);
                if (airlineNameLabel != null) {
                    airlineNameLabel.setText(airline.isTranslated() ?
                            airline.getNameTranslated() : airline.getName());
                }
                if (airlineCodeLabel != null) {
                    airlineCodeLabel.setText(airline.getCode());
                }
            }
            return v;
        } else {
            View v = convertView;
            v = inflater.inflate(R.layout.airport_suggestion, parent, false);
            Airport airport = (Airport) getItem(position);
            if (airport != null) {
                ((TextView) v.findViewById(R.id.airport_name)).setText(airport.isTranslated() ?
                        airport.getAirportNameTranslated() : airport.getAirportName());
                ((TextView) v.findViewById(R.id.airport_code)).setText(airport.getCode());
                ((TextView) v.findViewById(R.id.airport_city)).setText(airport.isTranslated() ?
                        airport.getCityTranslated() : airport.getCity());
            }
            return v;
        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        if (position < suggestions.size())
            return suggestions.get(position);
        return null;
    }
}
