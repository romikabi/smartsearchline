package com.aita.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.romanabuzyarov.smartsearchline.R;
import com.aita.model.Airport;

import java.util.ArrayList;
import java.util.Locale;

public class AirportAutocompleteAdapter extends ArrayAdapter<Airport> {
    private final ArrayList<Airport> items;
    private final ArrayList<Airport> itemsAll;
    private final ArrayList<Airport> suggestions;
    private final int viewResourceId;
    private final LayoutInflater layoutInflater;
    private final Filter nameFilter = new Filter() {
        public String convertResultToString(Object resultValue) {
            if (resultValue != null) {
                return ((Airport) (resultValue)).getAirportName();
            } else {
                return "";
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                final String str = constraint.toString().toLowerCase(
                        Locale.getDefault());
                final ArrayList<Airport> itemsSkipped = new ArrayList<>();
                for (Airport airport : itemsAll)
                    if (airport.getCode().toLowerCase().startsWith(str.toLowerCase()))
                        suggestions.add(airport);
                    else
                        itemsSkipped.add(airport);

                for (Airport airport : itemsSkipped)
                    if (airport.isTranslated() &&
                            (airport.getAirportNameTranslated().toLowerCase().contains(str.toLowerCase()) ||
                                    airport.getCityTranslated().toLowerCase().contains(str.toLowerCase())) ||
                            airport.getAirportName().toLowerCase().contains(str.toLowerCase()) ||
                            airport.getCity().toLowerCase().contains(str.toLowerCase()))
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
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            try {
                ArrayList<Airport> filteredList = (ArrayList<Airport>) results.values;
                if (results != null) {
                    clear();
                    addAll(filteredList);
                }
            } catch (Exception e) {
                notifyDataSetChanged();
            }
        }
    };

    public AirportAutocompleteAdapter(Context context, int viewResourceId,
                                      ArrayList<Airport> items) {
        super(context, viewResourceId, items);
        this.items = items;
        layoutInflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.itemsAll = (ArrayList<Airport>) items.clone();
        this.suggestions = new ArrayList<>();
        this.viewResourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
// TODO: 11.07.17
        if (convertView == null) {
            convertView = layoutInflater.inflate(viewResourceId, null);
            holder = new ViewHolder();
            holder.customerCityLabel = (TextView) convertView
                    .findViewById(R.id.airport_city);
            holder.customerNameLabel = (TextView) convertView
                    .findViewById(R.id.airport_name);
            holder.customerCodeLabel = (TextView) convertView
                    .findViewById(R.id.airport_code);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Airport airport = getItem(position);
        if (airport != null) {
            holder.customerNameLabel.setText(airport.isTranslated() ?
                    airport.getAirportNameTranslated() : airport.getAirportName());
            holder.customerCodeLabel.setText(airport.getCode());
            holder.customerCityLabel.setText(airport.isTranslated() ?
                    airport.getCityTranslated() : airport.getCity());
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    @Override
    public Airport getItem(int position) {
        if (position < suggestions.size()) {
            return suggestions.get(position);
        } else {
            return null;
        }
    }

    private static class ViewHolder {
        TextView customerNameLabel;
        TextView customerCodeLabel;
        TextView customerCityLabel;
    }

}
