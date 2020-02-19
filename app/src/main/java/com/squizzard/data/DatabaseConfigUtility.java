package com.squizzard.data;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.squizzard.reminders.model.Reminder;

public class DatabaseConfigUtility extends OrmLiteConfigUtil{

	private static final Class<?>[] classes = new Class[] {
		Reminder.class,
	};

	public static void main(String[] args) throws Exception {
		writeConfigFile("ormlite_config.txt", classes);
	}
}
