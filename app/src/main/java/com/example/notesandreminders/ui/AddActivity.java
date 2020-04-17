package com.example.notesandreminders.ui;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesandreminders.MyBroadcastReceiver;
import com.example.notesandreminders.R;
import com.example.notesandreminders.model.DbManager;
import com.example.notesandreminders.model.Note;
import com.example.notesandreminders.model.Reminder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity implements
        View.OnClickListener {

    private TextView title,date,time,remindMeOn;
    private EditText yourTitle, addNote;
    private ImageView notesBack, saveNote, editNote,selectTime,selectDate,deletesNote;
    private int mode;
    private Note note;
    private Reminder reminder;
    private boolean isDateSelected,isTimeSelected;
    private boolean editable = false;
    private String dates,times;
   private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private DateFormat dateF = new SimpleDateFormat("dd-MM-yyyy");
    private DateFormat timeF = new SimpleDateFormat("HH:mm");

    public static Intent getStartIntent(Context context, int mode) {
        Intent intent = new Intent(context, AddActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("mode", mode);
        intent.putExtra("bundle", bundle);
        return intent;
    }


    public static Intent getStartIntent(Context context, int mode, Reminder reminder) {
        Intent intent = new Intent(context, AddActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("mode", mode);
        bundle.putSerializable("reminder", reminder);
        intent.putExtra("bundle", bundle);
        return intent;
    }
    public static Intent getStartIntent(Context context, int mode, Note note) {
        Intent intent = new Intent(context, AddActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("mode", mode);
        bundle.putSerializable("note", note);
        intent.putExtra("bundle", bundle);
        return intent;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//        setContentView(R.layout.activity_main);
//        FloatingActionButton mClear = findViewById(R.id.clearMissed);
//        mClear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DbManager.getInstance().appDao().nukeTable();
//                Toast.makeText(AddActivity.this, "All missed cleared", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.layout_add_activity);
        initViews();
        initClicks();
        Bundle bundle = this.getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            mode = bundle.getInt("mode");
            if (mode == 1) {
                editable=true;
                title.setText(R.string.notes);
                selectTime.setVisibility(View.GONE);
                selectDate.setVisibility(View.GONE);
                remindMeOn.setVisibility(View.GONE);
                date.setVisibility(View.GONE);
                time.setVisibility(View.GONE);
                deletesNote.setVisibility(View.GONE);

            } else if (mode == 2) {
                selectTime.setVisibility(View.GONE);
                selectDate.setVisibility(View.GONE);
                remindMeOn.setVisibility(View.GONE);
                date.setVisibility(View.GONE);
                time.setVisibility(View.GONE);
                title.setText(R.string.notes); //back button after opening activity to add Notes
                editNote.setVisibility(View.VISIBLE);
                deletesNote.setVisibility(View.VISIBLE);
                editable = false;
                yourTitle.setEnabled(false);
                addNote.setEnabled(false);
                note = (Note) bundle.getSerializable("note");
                if (note != null) {
                    yourTitle.setText(note.getTitle());
                    addNote.setText(note.getDescription());
                }
            }


            else if(mode==3)
            {
                editable=true;
                deletesNote.setVisibility(View.VISIBLE);

                title.setText(R.string.reminder);
                selectTime.setVisibility(View.VISIBLE);
                selectDate.setVisibility(View.VISIBLE);
                remindMeOn.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                time.setVisibility(View.VISIBLE);
            }
            else if(mode==4)
            {
                editable=false;
                title.setText(R.string.reminder);
                editNote.setVisibility(View.VISIBLE);
                deletesNote.setVisibility(View.VISIBLE);

                selectTime.setVisibility(View.VISIBLE);
                selectDate.setVisibility(View.VISIBLE);
                remindMeOn.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                time.setVisibility(View.VISIBLE);
                selectDate.setEnabled(false);
                selectTime.setEnabled(false);
                reminder = (Reminder) bundle.getSerializable("reminder");
                yourTitle.setEnabled(false);
                addNote.setEnabled(false);
                isDateSelected = true;
                isTimeSelected = true;
                if (reminder != null) {
                    yourTitle.setText(reminder.getTitle());
                    addNote.setText(reminder.getDescription());
                    dates=dateF.format(reminder.getRemindOn());
                    times=timeF.format(reminder.getRemindOn());
                    date.setText(String.format("Date - %s",String.valueOf(dateF.format(reminder.getRemindOn()))));
                    time.setText(String.format("Time - %s",String.valueOf(timeF.format(reminder.getRemindOn()))));
                }
            }
        }

    }


    private void initViews() {
        editNote = findViewById(R.id.editNote);
        notesBack = findViewById(R.id.notesBack);
        saveNote = findViewById(R.id.saveNote);
        title = findViewById(R.id.title);
        yourTitle = findViewById(R.id.yourTitle);
        addNote = findViewById(R.id.addNote);
        selectTime = findViewById(R.id.selectTime);
        selectDate = findViewById(R.id.selectDate);
        deletesNote = findViewById(R.id.deleteNote);
        selectDate.setOnClickListener(this);
        selectTime.setOnClickListener(this);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        remindMeOn = findViewById(R.id.remindeMeOn);

    }

    private void initClicks() {
        notesBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////////
        deletesNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == 1 || mode ==2 ){

                    DbManager.getInstance().appDao().deleteNote(note);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                } else if ( mode == 3 || mode == 4)
                {
                    DbManager.getInstance().appDao().deleteReminder(reminder);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                }
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == 1) {
                    Note note = new Note();
                    note.setTitle(yourTitle.getText().toString());
                    note.setDescription(addNote.getText().toString());
                    note.setCreatedOn(new Date().getTime());
                    DbManager.getInstance().appDao().addNote(note);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else if(mode==2){
                    note.setTitle(yourTitle.getText().toString());
                    note.setDescription(addNote.getText().toString());
                    note.setCreatedOn(new Date().getTime());
                    DbManager.getInstance().appDao().updateNote(note);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else if(mode==3)
                {
                    if(isReminderValidation()) {
                        String t = dates+" "+times;
                        Date date = null;
                        try {
                             date = dateFormat.parse(t);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        System.out.println("SETTING DATE"+date);
                        Reminder reminder = new Reminder();
                        reminder.setTitle(yourTitle.getText().toString());
                        reminder.setDescription(addNote.getText().toString());
                        reminder.setCreatedOn(new Date().getTime());
                        assert date != null;
                        reminder.setRemindOn(date.getTime());
                        DbManager.getInstance().appDao().addReminder(reminder);
                        Intent intent2= MainActivity.getStartIntent(getApplicationContext(),"reminder");
                        startActivity(intent2);
                        Intent intent = new Intent(getApplicationContext(), MyBroadcastReceiver.class);
                        Bundle b = new Bundle();
                        b.putSerializable("reminder",reminder);
                        intent.putExtra("bundle",b);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 234324243, intent, 0);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminder.getRemindOn(), pendingIntent);

                    }
                }
                else if(mode==4)
                {
                    if(isReminderValidation()) {
                        String t = dates+" "+times;
                        Date date = null;
                        try {
                            date = dateFormat.parse(t);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        System.out.println("SETTING DATE"+date);
                        reminder.setTitle(yourTitle.getText().toString());
                        reminder.setDescription(addNote.getText().toString());
                        reminder.setCreatedOn(new Date().getTime());
                        assert date != null;
                        reminder.setRemindOn(date.getTime());
                        DbManager.getInstance().appDao().updateReminder(reminder);
                        Intent intent = MainActivity.getStartIntent(getApplicationContext(),"reminder");
                        startActivity(intent);
                        Intent intent1 = new Intent(getApplicationContext(), MyBroadcastReceiver.class);
                        Bundle b = new Bundle();
                        b.putSerializable("reminder",reminder);
                        intent1.putExtra("bundle",b);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 234324243, intent1, 0);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminder.getRemindOn(), pendingIntent);
                    }
                }
            }
        });


        editNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editable = true;
                yourTitle.setEnabled(true);
                addNote.setEnabled(true);
                editNote.setVisibility(View.GONE);
                saveNote.setVisibility(View.VISIBLE);
                selectDate.setEnabled(true);
                selectTime.setEnabled(true);
                deletesNote.setVisibility(View.VISIBLE);
            }
        });

        addNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editable) {
                    if (s.toString().length() > 1) {
                        saveNote.setVisibility(View.VISIBLE);
                        deletesNote.setVisibility(View.VISIBLE);
                    } else {
                        saveNote.setVisibility(View.GONE);
                        deletesNote.setVisibility(View.GONE);

                    }
                }
            }
        });
    }

    private boolean isReminderValidation()
    {
        if(!isDateSelected)
        {
            Toast.makeText(this, "Please Select Reminder Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!isTimeSelected)
        {
            Toast.makeText(this, "Please Select Reminder Time", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v==selectDate)
        {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            isDateSelected = true;
                            if(monthOfYear<=8) {
                                dates = dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year;
                                date.setText(String.format("Date - %s", dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year));
                            }else {
                                dates = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                date.setText(String.format("Date - %s", dayOfMonth + "-" + (monthOfYear + 1) + "-" + year));
                            }
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        else if(v==selectTime)
        {
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            isTimeSelected=true;
                            times = hourOfDay+":"+minute;
                            time.setText(String.format("Time - %s",hourOfDay + ":" + minute));
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
}
