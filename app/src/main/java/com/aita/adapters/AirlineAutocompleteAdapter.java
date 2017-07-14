package com.aita.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.smartsearchline.R;
import com.aita.model.Airline;

import java.util.ArrayList;
import java.util.List;

public class AirlineAutocompleteAdapter extends ArrayAdapter<Airline> {

    private final ArrayList<Airline> items;
    private final ArrayList<Airline> itemsAll;
    private final ArrayList<Airline> suggestions;
    private final int viewResourceId;

    private final Filter nameFilter = new Filter() {
        public String convertResultToString(Object resultValue) {
            return resultValue != null ? ((Airline) (resultValue)).getName() : "";
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                final String search = constraint.toString().toLowerCase();

                final List<Airline> itemsSkipped = new ArrayList<>();
                for (Airline airline : itemsAll) {
                    final String iata = airline.getIata().toLowerCase();
                    final String icao = airline.getIcao().toLowerCase();
                    final String innerCode = airline.getInnerCode().toLowerCase();
                    if (iata.startsWith(search) || icao.startsWith(search) || innerCode.startsWith(search)) {
                        suggestions.add(airline);
                    } else {
                        itemsSkipped.add(airline);
                    }
                }

                for (Airline airline : itemsSkipped) {
                    if (airline.isTranslated() &&
                            airline.getNameTranslated().toLowerCase().contains(search) ||
                            airline.getName().toLowerCase().contains(search)) {
                        suggestions.add(airline);
                    }
                }

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
                @SuppressWarnings("unchecked")
                ArrayList<Airline> filteredList = (ArrayList<Airline>) results.values;
                //noinspection ConstantConditions
                if (results != null) {
                    clear();
                    addAll(filteredList);
                }
            } catch (Exception e) {
                notifyDataSetChanged();
            }
        }
    };

    public AirlineAutocompleteAdapter(Context context, int viewResourceId, ArrayList<Airline> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = new ArrayList<>(items);
        this.suggestions = new ArrayList<>();
        this.viewResourceId = viewResourceId;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        // TODO: 11.07.17  
        Airline airline = getItem(position);
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
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    @Override
    public Airline getItem(int position) {
        if (position < items.size()) {
            return items.get(position);
        } else {
            return null;
        }
    }

}