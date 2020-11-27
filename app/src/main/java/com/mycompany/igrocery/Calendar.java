package com.mycompany.igrocery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;


public class Calendar extends AppCompatActivity {
    //Initialize Drawer Navigation variable
    DrawerLayout drawerLayout;

    //variables to connect firebase
    private DatabaseReference reference, mFirebase;
    private FirebaseUser user; //Firebase obj
    private String  userEmail;

    //variable for calendar
    CalendarView calendarView;
    TextView dateTV;
    EditText eventET;
    String date, time;
    TimePicker picker;
    Button saveBtn, seeAllEvents;

    //array to store the data from firebase
    ArrayList<Events> arrayEvents;
    ArrayList<String> arrayStringlist;
    Map<String, Events> map;

    //fragment variable
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        getCurrentUser();

        //hook ids
        calendarView = (CalendarView)findViewById(R.id.calendarView2);
        dateTV = (TextView) findViewById(R.id.dateTV);
        eventET = (EditText) findViewById(R.id.eventInput);
        picker = (TimePicker) findViewById(R.id.timePicker1);
        saveBtn = (Button) findViewById(R.id.saveEventBtn);
        seeAllEvents = (Button) findViewById(R.id.seeAllEvents);
        arrayEvents = new ArrayList<>();
        arrayStringlist = new ArrayList<>();
        map = new Map<String, Events>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(@Nullable Object o) {
                return false;
            }

            @Override
            public boolean containsValue(@Nullable Object o) {
                return false;
            }

            @Nullable
            @Override
            public Events get(@Nullable Object o) {
                return null;
            }

            @Nullable
            @Override
            public Events put(String s, Events events) {
                return null;
            }

            @Nullable
            @Override
            public Events remove(@Nullable Object o) {
                return null;
            }

            @Override
            public void putAll(@NonNull Map<? extends String, ? extends Events> map) {

            }

            @Override
            public void clear() {

            }

            @NonNull
            @Override
            public Set<String> keySet() {
                return null;
            }

            @NonNull
            @Override
            public Collection<Events> values() {
                return null;
            }

            @NonNull
            @Override
            public Set<Entry<String, Events>> entrySet() {
                return null;
            }
        };

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = month + "/" + dayOfMonth + "/" + year;
                dateTV.setText(date);
                picker.setIs24HourView(false);
                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reference = FirebaseDatabase.getInstance().getReference().child("Events");
                        int hour, minute;
                        hour = picker.getCurrentHour();
                        minute = picker.getCurrentMinute();
                        time = hour + ":" + minute;

                        Events eventsToAdd = new Events(eventET.getText().toString(), date, time);

                        reference.child(userEmail).child(eventET.getText().toString()).setValue(eventsToAdd);
                    }
                });
            }
        });

        mFirebase = FirebaseDatabase.getInstance().getReference().child("Events").child(userEmail);
        mFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayEvents.clear();
                arrayStringlist.clear();
                map.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Events eachEvent = dataSnapshot.getValue(Events.class);
                    arrayEvents.add(eachEvent);
                    Map<String, Events> map = (Map<String, Events>)dataSnapshot.getValue();
                    arrayStringlist.add(map.entrySet() + "");
                }

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("eventsArray", arrayStringlist);

                seeAllEvents.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventsFragment myFragment = new EventsFragment();
                        myFragment.setArguments(bundle);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.fragment_eventscontainer, myFragment);
                        fragmentTransaction.commit();
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

    public void getCurrentUser() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = user.getEmail().replace(".", "&");
    }

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
        redirectActivity(this, PermissionActivity.class);
    }

    public void ClickLogout(View view){
        logout(this);
    }

    public void ClickStoreMap(View view) {
        redirectActivity(this, StoreMap.class);
    }

    public void ClickCalendar(View view) {
        redirectActivity(this, Calendar.class);
    }
    //My Shared List for Edurado
    public void ClickMyShared(View view) {
        redirectActivity(this, MySharedListActivity.class);
    }

    // This adds the navigation functionality for the main_nav_drawer Share my list menu link
    public void shareMyList(View view) {
        redirectActivity(this, ShareList.class);
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




}