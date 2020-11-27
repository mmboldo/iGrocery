package com.mycompany.igrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;


import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;


import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Calendar extends AppCompatActivity {
    //Initialize Drawer Navigation variable
    DrawerLayout drawerLayout;

    //variables to connect firebase
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseUser user; //Firebase obj
    private String userId;

    //variable to store data retrieve from firebase
    List<Events> eventsList = new ArrayList<>();

    //list to hold the date
    List<Date> dates = new ArrayList<>();

    //variables of calendar clickables
    ImageButton NextButton, PreviousButton;
    TextView CurrentDate;
    GridView gridView;
    private static final int MAX_CALENDAR_DAYS = 42;
    java.util.Calendar calendar = java.util.Calendar.getInstance(Locale.ENGLISH);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    SimpleDateFormat eventDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    MyGridAdapter myGridAdapter;
    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //get the current user
        getCurrentUser();

        //get the events of the user
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Events").child(userId);
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            Events events1 = null;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventsList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String eventName = dataSnapshot.getKey();
                    events1 = dataSnapshot.getValue(Events.class);
                    eventsList.add(events1);
                    //Toast.makeText(Calendar.this, "Event List size: " +eventsList.size() , Toast.LENGTH_SHORT).show();

                    //hook the variables to ids
                    NextButton = findViewById(R.id.nextbtn);
                    PreviousButton = findViewById(R.id.previousbtn);
                    CurrentDate = findViewById(R.id.current_Date);
                    gridView = findViewById(R.id.gridview);

                    //call method to set up the calendar
                    SetUpCalendar();

                    //set the image buttons
                    PreviousButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            calendar.add(java.util.Calendar.MONTH, -1);
                            SetUpCalendar();
                        }
                    });
                    NextButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            calendar.add(java.util.Calendar.MONTH, 1);
                            SetUpCalendar();
                        }
                    });

                    //set the gridview
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Calendar.this);
                            builder.setCancelable(true);
                            final View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_newevent_layout, null);
                            final EditText EventName = addView.findViewById(R.id.eventname);
                            final TextView EventTime = addView.findViewById(R.id.eventtime);
                            ImageButton SetTime = addView.findViewById(R.id.seteventtime);
                            Button AddEvent = addView.findViewById(R.id.addevent);

                            SetTime.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                                    int hours = calendar.get(java.util.Calendar.HOUR_OF_DAY);
                                    int minutes = calendar.get(java.util.Calendar.MINUTE);
                                    TimePickerDialog timePickerDialog = new TimePickerDialog(addView.getContext(), R.style.Theme_AppCompat_DayNight_Dialog,
                                            new TimePickerDialog.OnTimeSetListener() {
                                                @Override
                                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                    java.util.Calendar calendar1 = java.util.Calendar.getInstance();
                                                    calendar1.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
                                                    calendar1.set(java.util.Calendar.MINUTE, minute);
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

                            //adding events to firebase
                            AddEvent.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mFirebaseInstance = FirebaseDatabase.getInstance();
                                    mFirebaseDatabase = mFirebaseInstance.getReference("Events");

                                    Events events = new Events(EventName.getText().toString(), EventTime.getText().toString(),
                                            date, month, year);

                                    mFirebaseDatabase.child(userId).child("eventName: " + EventName.getText().toString()).setValue(events);
                                    Toast.makeText(Calendar.this, "Event has been created", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }
                            });
                            builder.setView(addView);
                            alertDialog = builder.create();
                            alertDialog.show();
                        }
                    });
                }

                //show the list of events
                ListView listView = (ListView) findViewById(R.id.eventListView);
                gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        return true;
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Drawer Navigation
        drawerLayout = findViewById(R.id.drawer_layout);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    }

    //get the current user
    public void getCurrentUser() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
    }
    //set up months, populate the grid view
    private void SetUpCalendar() {
        String currentDate = dateFormat.format(calendar.getTime());
        CurrentDate.setText(currentDate);
        dates.clear();
        java.util.Calendar monthCalendar = (java.util.Calendar) calendar.clone();
        monthCalendar.set(java.util.Calendar.DAY_OF_MONTH, 1);
        int FirstDayofMonth = monthCalendar.get(java.util.Calendar.DAY_OF_WEEK) - 1;
        monthCalendar.add(java.util.Calendar.DAY_OF_MONTH, -FirstDayofMonth);

        while (dates.size() < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
        }
        myGridAdapter = new MyGridAdapter(this, dates, calendar, eventsList);
        gridView.setAdapter(myGridAdapter);
    }

    //methods for the menu bar
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }
    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickList(View view){
        redirectActivity(this, CreateList.class);
    }


    public void ClickSearchStore(View view){
        redirectActivity(this, MapActivity.class);
    }
    public void ClickLogout(View view){
        logout(this);
    }

    public void ClickCalendar(View view) {
        redirectActivity(this, Calendar.class);
    }

    public void ClickStoreMap(View view) {
        redirectActivity(this, StoreMap.class);
    }

    public void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Back to login page
                Intent intent = new Intent(Calendar.this, Login.class);
                activity.startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    public static void redirectActivity(Activity activity, Class aclass) {
        Intent intent = new Intent(activity, aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //close drawer
        closeDrawer(drawerLayout);
    }
    //toolbar settings




}