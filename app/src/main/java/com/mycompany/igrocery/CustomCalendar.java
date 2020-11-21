package com.mycompany.igrocery;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CustomCalendar extends LinearLayout {
    ImageButton NextButton, PreviousButton;
    TextView CurrentDate;
    GridView gridView;
    private static final int MAX_CALENDAR_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    SimpleDateFormat eventDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseUser user; //Firebase obj
    private String userId, eventName;

    MyGridAdapter myGridAdapter;
    AlertDialog alertDialog;
    List<Date> dates = new ArrayList<>();
    List<Events> eventsList = new ArrayList<>();

    public CustomCalendar(Context context) {
        super(context);
    }

    public void getCurrentUser() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
    }

    public CustomCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        InitializeLayout();
        SetUpCalendar();

        PreviousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                SetUpCalendar();
            }
        });

        NextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1);
                SetUpCalendar();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                final View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_newevent_layout, null);
                final EditText EventName = addView.findViewById(R.id.eventname);
                final TextView EventTime = addView.findViewById(R.id.eventtime);
                ImageButton SetTime = addView.findViewById(R.id.seteventtime);
                Button AddEvent = addView.findViewById(R.id.addevent);

                SetTime.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int hours = calendar.get(Calendar.HOUR_OF_DAY);
                        int minutes = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(addView.getContext(), R.style.Theme_AppCompat_Dialog,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        Calendar calendar1 = Calendar.getInstance();
                                        calendar1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        calendar1.set(Calendar.MINUTE, minute);
                                        calendar1.setTimeZone(TimeZone.getDefault());
                                        SimpleDateFormat hformat = new SimpleDateFormat("K:mm a", Locale.ENGLISH);
                                        String event_Time = hformat.format(calendar1.getTime());
                                        EventTime.setText(event_Time);
                                    }
                                }, hours, minutes, false);
                        timePickerDialog.show();
                    }
                });

                final String date = eventDateFormat.format(dates.get(position));
                final String month = monthFormat.format(dates.get(position));
                final String year = yearFormat.format(dates.get(position));

                getCurrentUser();

                AddEvent.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFirebaseInstance = FirebaseDatabase.getInstance();
                        mFirebaseDatabase = mFirebaseInstance.getReference("Events");

                        Events events = new Events(EventName.getText().toString(), EventTime.getText().toString(),
                                date, month, year);

                        mFirebaseDatabase.child(userId).child("eventName:" + EventName.getText().toString()).setValue(events);

                        SetUpCalendar();
                        alertDialog.dismiss();
                    }
                });

                builder.setView(addView);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    public CustomCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void InitializeLayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.thecalendar_layout, this);
        NextButton = view.findViewById(R.id.nextbtn);
        PreviousButton = view.findViewById(R.id.previousbtn);
        CurrentDate = view.findViewById(R.id.current_Date);
        gridView = view.findViewById(R.id.gridview);
    }

    private void SetUpCalendar() {
        String currentDate = dateFormat.format(calendar.getTime());
        CurrentDate.setText(currentDate);
        dates.clear();
        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int FirstDayofMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -FirstDayofMonth);

        CollectEventsPerMonths(monthFormat.format(calendar.getTime()), yearFormat.format(calendar.getTime()));
        Toast.makeText(getContext(), ("List size "+ (CollectEventsPerMonths(monthFormat.format(calendar.getTime()), yearFormat.format(calendar.getTime())))), Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), ("Calendar time is "+ monthFormat.format(calendar.getTime())+" "+yearFormat.format(calendar.getTime())) +"", Toast.LENGTH_SHORT).show();
        while (dates.size() < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        myGridAdapter = new MyGridAdapter(context, dates, calendar, eventsList);
        gridView.setAdapter(myGridAdapter);
    }

    private int CollectEventsPerMonths(String Month, String year) {
        getCurrentUser();
        eventsList.clear();

        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(userId).child("eventName");
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Events p = dataSnapshot.getValue(Events.class);
                    eventsList.add(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return eventsList.size();
    }
}

