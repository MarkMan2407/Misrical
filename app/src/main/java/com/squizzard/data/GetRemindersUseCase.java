package com.squizzard.data;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.squizzard.reminders.Reminder;

import java.util.ArrayList;

public class GetRemindersUseCase {

    public GetRemindersUseCase(Context context) {
        this.context = context;
    }

    private Context context;

    private DatabaseHelper databaseHelper;

    public Reminder getReminder(long id) {
        return getHelper().getReminder(id);
    }

    public ArrayList<Reminder> getReminders() {
        return getHelper().getReminders();
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
