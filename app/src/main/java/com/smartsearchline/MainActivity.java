package com.smartsearchline;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.aita.adapters.AirportAutocompleteAdapter;
import com.aita.adapters.InitialAdapter;
import com.aita.helpers.AirlineAssetDatabaseHelper;
import com.aita.helpers.AirportsAssetDatabaseHelper;
import com.aita.model.Airline;
import com.aita.model.Airport;
import com.smartsearchline.model.searchmodel.ConcreteProvider;
import com.smartsearchline.model.searchmodel.Provider;
import com.smartsearchline.model.searchmodel.SearchModel;
import com.smartsearchline.model.searchmodel.StateSearchModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    // Views from xml.

    private EditText input;
    private ListView suggestionList;
    private LinearLayout statePictures;
    private SearchModel model;
    private LinearLayout headers;

    private static LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get views from xml.
        input = (EditText) findViewById(R.id.inputText);
        suggestionList = (ListView) findViewById(R.id.suggestionList);
        statePictures = (LinearLayout) findViewById(R.id.statePictures);

        model = getModel();

        inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        // Create layout for listview headers (since they can't be added after setting the adapter in API 16)/
        headers = new LinearLayout(getApplicationContext());
        headers.setOrientation(LinearLayout.VERTICAL);
        suggestionList.addHeaderView(headers, null, true);

        // Set initials.
        if (model.getAdapter() != null)
            suggestionList.setAdapter(model.getAdapter());
        input.setHint(model.getHint());
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditTextClick(v);
            }
        });
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (model.getAdapter() != null) {
                    try {
                        ((HeaderViewListAdapter) suggestionList.getAdapter()).getFilter().filter(s);
                    } catch (Exception ex) {
                        ((ArrayAdapter) suggestionList.getAdapter()).getFilter().filter(s);
                    }
                }

                headers.removeAllViews();
                for (View view : model.getHeaders(s.toString()))
                    headers.addView(view);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Set backspace catcher.
        input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL &&
                        ((EditText) v).getText().length() == 0 &&
                        passed > 0) {
                    unpass();
                    return true;
                }
                return false;
            }
        });

        suggestionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position) instanceof Airport)
                    pass(dbAirportToData((Airport) parent.getItemAtPosition(position)));
                else if (parent.getItemAtPosition(position) instanceof Airline)
                    pass(dbAirlineToData((Airline) parent.getItemAtPosition(position)));
            }
        });

        input.setText("");
    }

    /**
     * Move model back for one state.
     */
    private void unpass() {
        String prevInput = "";

        passed--;
        prevInput = model.unpass();
        statePictures.removeViewAt(statePictures.getChildCount() - 1);

        resetStateView();
        input.setText(prevInput);
        input.setSelection(input.getText().length());
    }

    /**
     * Amount of passed pieces of data.
     */
    private int passed = 0;

    /**
     * Add data piece's selected view to layout of selected views (Top left corner).
     * @param data data to add.
     */
    void addSelectedView(Provider.Data data) {
        View view = data.getSelectedView(null, statePictures);
        statePictures.addView(view);
        passed++;
        final int count = passed;

        // Set click listener to unpass states.
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prevInput = "";
                for (int i = passed; i >= count; i--) {
                    passed--;
                    prevInput = model.unpass();
                    statePictures.removeViewAt(statePictures.getChildCount() - 1);
                }
                resetStateView();
                input.setText(prevInput);
                input.setSelection(input.getText().length());
            }
        });
    }

    /**
     * Pass data piece to model and do related layout redraw.
     * @param data data to pass.
     */
    void pass(Provider.Data data) {
        addSelectedView(data);
        model.pass(data);
        if (!model.isFinal()) {
            resetStateView();
            onEditTextClick(input);
        } else {
            String res = model.getResult();
            AlertDialog dialog = new AlertDialog.Builder(this).setMessage(res).create();
            dialog.show();
            model = getModel();
            resetStateView();
            statePictures.removeAllViews();
            passed = 0;
        }
    }

    /**
     * Redraw elements related to state changing.
     */
    void resetStateView() {
        suggestionList.setAdapter(model.getAdapter());
        selectInputMode(model.getInputType());
        input.setText("");
        if (model.getHint() != null)
            input.setHint(model.getHint());
        else {
            input.setHint("");
        }
    }

    /**
     * Determine the input mode and apply it.
     * @param inputType input type of the model.
     */
    void selectInputMode(Provider.InputType inputType) {
        switch (inputType) {
            case DEFAULT:
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case NUMERIC:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (input.getInputType() == InputType.TYPE_CLASS_NUMBER && input.getText().length() > 0) {
                            pass(codeToData(input.getText().toString()));
                        }
                        return false;
                    }
                });
                break;
            case DATE:
                input.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
                break;
            case TIME:
                input.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
                break;
        }
    }

    /**
     * Create view for top left corner using a string.
     * @param string string to show.
     * @param parent parent layout.
     * @return view to show.
     */
    static View stringToSelectedView(String string, ViewGroup parent) {
        View view = inflater.inflate(R.layout.string_selected, parent, false);
        ((TextView) view.findViewById(R.id.stringId)).setText(string);
        return view;
    }

    /**
     * Get AITA search model.
     * @return
     */
    private StateSearchModel getModel() {
        Provider initial = new ConcreteProvider(
                // Input type
                Provider.InputType.DEFAULT,

                // User hint.
                getString(R.string.initial_enter_tip),

                // Is final.
                false,

                // ListView adapter.
                new InitialAdapter(getApplicationContext(), 0,
                        AirportsAssetDatabaseHelper.getInstance().getAirports().getAirports(),
                        AirlineAssetDatabaseHelper.getInstance().getAirlineData().getAirlines()),
                // Headers provider/
                new ConcreteProvider.Suggestor() {
                    @Override
                    public List<View> getSuggestions(final String request) {
                        List<View> res = new ArrayList<>();
                        boolean add = false;
                        if (request.length() == 2 || request.length() == 3) {
                            View view = inflater.inflate(R.layout.default_suggestion, headers, false);
                            ((TextView) view.findViewById(R.id.title)).setText(getString(R.string.use_as_airline));
                            ((TextView) view.findViewById(R.id.addition)).setText(getString(R.string.airline_usual_length));
                            ((TextView) view.findViewById(R.id.code)).setText(request.toUpperCase());

                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pass(new Provider.Data() {
                                        @Override
                                        public Provider.DataType getType() {
                                            return Provider.DataType.AIRLINE;
                                        }

                                        @Override
                                        public Object getValue() {
                                            return request.toUpperCase();
                                        }

                                        @Override
                                        public View getSelectedView(View empty, ViewGroup parent) {
                                            return stringToSelectedView(request.toUpperCase(), statePictures);
                                        }

                                        @Override
                                        public String toString() {
                                            return getValue().toString();
                                        }
                                    });
                                }
                            });
                            res.add(view);
                        } else if (request.length() == 5 || request.length() == 6) {
                            View view = inflater.inflate(R.layout.default_suggestion, headers, false);
                            ((TextView) view.findViewById(R.id.title)).setText(getString(R.string.use_as_booking));
                            ((TextView) view.findViewById(R.id.addition)).setText(getString(R.string.booking_usual_length));
                            ((TextView) view.findViewById(R.id.code)).setText(request.toUpperCase());

                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pass(bookingToData(request.toUpperCase()));
                                }
                            });
                            res.add(view);
                        }
                        return res;
                    }
                }
        );

        // Same in another states.

        Provider airportAirport = new ConcreteProvider(
                Provider.InputType.DEFAULT,
                getString(R.string.arrival_airport_enter_tip),
                false,
                new AirportAutocompleteAdapter(
                        getApplicationContext(),
                        R.layout.airport_suggestion,
                        AirportsAssetDatabaseHelper.getInstance().getAirportData().getAirports()),

                // can be history provider here
                null
        );

        Provider airportAirportDate = new ConcreteProvider(
                Provider.InputType.DATE,
                getString(R.string.departure_date_enter_tip),
                false,
                new ArrayAdapter(getApplicationContext(), R.layout.default_suggestion),
                null
        );

        Provider airlineFlightcode = new ConcreteProvider(
                Provider.InputType.NUMERIC,
                getString(R.string.flight_number_enter_tip),
                false,
                new ArrayAdapter(getApplicationContext(), R.layout.default_suggestion),
                null
        );

        Provider airlineFlightcodeDate = new ConcreteProvider(
                Provider.InputType.DATE,
                getString(R.string.departure_date_enter_tip),
                false,
                new ArrayAdapter(getApplicationContext(), R.layout.default_suggestion),
                null);

        Provider bookingSurname = new ConcreteProvider(Provider.InputType.DEFAULT,
                getString(R.string.surname_enter_tip),
                false,
                new ArrayAdapter(getApplicationContext(), R.layout.default_suggestion),
                new ConcreteProvider.Suggestor() {
                    @Override
                    public List<View> getSuggestions(final String request) {
                        if (request.length() == 0)
                            return new ArrayList<>();

                        List<View> res = new ArrayList<>();
                        View view = inflater.inflate(R.layout.default_suggestion, headers, false);
                        String title = request.length() == 0 ? "" : request.length() == 1 ? request.toUpperCase()
                                : Character.toUpperCase(request.charAt(0)) + request.substring(1).toLowerCase();
                        ((TextView) view.findViewById(R.id.title)).setText(title);
                        ((TextView) view.findViewById(R.id.addition)).setText(getString(R.string.beta));
                        ((TextView) view.findViewById(R.id.code)).setText("");

                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pass(surnameToData(request));
                            }
                        });

                        res.add(view);
                        return res;
                    }
                });

        Provider finalstate = new ConcreteProvider(Provider.InputType.DEFAULT,
                null,
                true,
                new ArrayAdapter(getApplicationContext(), R.layout.default_suggestion),
                null);

        // Maps for each state to know to what state to switch after data receiving.

        Map<Provider.DataType, Provider> initialMap = new TreeMap<>();
        initialMap.put(Provider.DataType.AIRPORT, airportAirport);
        initialMap.put(Provider.DataType.AIRLINE, airlineFlightcode);
        initialMap.put(Provider.DataType.BOOKING, bookingSurname);
        initial.setMap(initialMap);

        Map<Provider.DataType, Provider> airportAirportMap = new TreeMap<>();
        airportAirportMap.put(Provider.DataType.AIRPORT, airportAirportDate);
        airportAirport.setMap(airportAirportMap);

        Map<Provider.DataType, Provider> airlineFlightcodeMap = new TreeMap<>();
        airlineFlightcodeMap.put(Provider.DataType.CODE, airlineFlightcodeDate);
        airlineFlightcode.setMap(airlineFlightcodeMap);

        Map<Provider.DataType, Provider> airportAirportDateMap = new TreeMap<>();
        airportAirportDateMap.put(Provider.DataType.DATE, finalstate);
        airportAirportDate.setMap(airportAirportDateMap);

        Map<Provider.DataType, Provider> airlineFlightcodeDateMap = new TreeMap<>();
        airlineFlightcodeDateMap.put(Provider.DataType.DATE, finalstate);
        airlineFlightcodeDate.setMap(airlineFlightcodeDateMap);

        Map<Provider.DataType, Provider> bookingSurnameMap = new TreeMap<>();
        bookingSurnameMap.put(Provider.DataType.SURNAME, finalstate);
        bookingSurname.setMap(bookingSurnameMap);

        return new StateSearchModel(initial);
    }

    // Methods to transform stuff to data entries.

    public static Provider.Data dbAirlineToData(final Airline airline) {
        return new Provider.Data() {
            @Override
            public Provider.DataType getType() {
                return Provider.DataType.AIRLINE;
            }

            @Override
            public Object getValue() {
                return airline;
            }

            @Override
            public View getSelectedView(View empty, ViewGroup parent) {
                return stringToSelectedView(airline.getCode(), parent);
            }

            @Override
            public String toString() {
                return airline.getCode();
            }
        };
    }

    public static Provider.Data dbAirportToData(final Airport airport) {
        return new Provider.Data() {
            @Override
            public Provider.DataType getType() {
                return Provider.DataType.AIRPORT;
            }

            @Override
            public Object getValue() {
                return airport;
            }

            @Override
            public View getSelectedView(View empty, ViewGroup parent) {
                return stringToSelectedView(airport.getCode(), parent);
            }

            @Override
            public String toString() {
                return airport.getCode();
            }
        };
    }

    private Provider.Data bookingToData(final String booking) {
        return new Provider.Data() {
            @Override
            public Provider.DataType getType() {
                return Provider.DataType.BOOKING;
            }

            @Override
            public Object getValue() {
                return booking;
            }

            @Override
            public View getSelectedView(View empty, ViewGroup parent) {
                return stringToSelectedView(booking, parent);
            }

            @Override
            public String toString() {
                return booking;
            }
        };
    }

    private Provider.Data surnameToData(final String surname) {
        return new Provider.Data() {
            @Override
            public Provider.DataType getType() {
                return Provider.DataType.SURNAME;
            }

            @Override
            public Object getValue() {
                return surname;
            }

            @Override
            public View getSelectedView(View empty, ViewGroup parent) {
                return stringToSelectedView(surname, parent);
            }

            @Override
            public String toString() {
                return surname;
            }
        };
    }

    private Provider.Data codeToData(final String code) {
        return new Provider.Data() {
            @Override
            public Provider.DataType getType() {
                return Provider.DataType.CODE;
            }

            @Override
            public Object getValue() {
                return String.valueOf(code);
            }

            @Override
            public View getSelectedView(View empty, ViewGroup parent) {
                return stringToSelectedView(input.getText().toString().toUpperCase(), statePictures);
            }

            @Override
            public String toString() {
                return getValue().toString();
            }
        };
    }

    private Provider.Data dateToData(final int year, final int month, final int day) {
        return new Provider.Data() {
            @Override
            public Provider.DataType getType() {
                return Provider.DataType.DATE;
            }

            @Override
            public Object getValue() {
                return String.format("%d.%d.%d", day, month, year);
            }

            @Override
            public View getSelectedView(View empty, ViewGroup parent) {
                return stringToSelectedView(getValue().toString(), statePictures);
            }

            @Override
            public String toString() {
                return getValue().toString();
            }
        };
    }

    private Provider.Data timeToData(final int hour, final int minute) {
        return new Provider.Data() {
            @Override
            public Provider.DataType getType() {
                return Provider.DataType.TIME;
            }

            @Override
            public Object getValue() {
                return String.format("%d:%d", hour, minute);
            }

            @Override
            public View getSelectedView(View empty, ViewGroup parent) {
                return stringToSelectedView(getValue().toString(), statePictures);
            }

            @Override
            public String toString() {
                return getValue().toString();
            }
        };
    }

    // Date and time management stuff.

    public void onEditTextClick(View view) {
        EditText et = (EditText) view;
        switch (et.getInputType()) {
            case InputType.TYPE_DATETIME_VARIATION_DATE:
                DatePickerDialog dpd = new DatePickerDialog(this, dateListener,
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DATE));
                dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        unpass();
                    }
                });
                dpd.show();
                break;
            case InputType.TYPE_DATETIME_VARIATION_TIME:
                TimePickerDialog tpd = new TimePickerDialog(this, timeSetListener,
                        Calendar.getInstance().get(Calendar.HOUR),
                        Calendar.getInstance().get(Calendar.MINUTE), true);
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        unpass();
                    }
                });
                tpd.show();
                break;
        }
    }

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if (view.isShown())
                pass(dateToData(year, month, dayOfMonth));
        }
    };

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (view.isShown())
                pass(timeToData(hourOfDay, minute));
        }
    };
}
