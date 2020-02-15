package com.squizzard.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.squizzard.MisriCalendar.Misri;
import com.squizzard.MisriCalendar.R;
import com.squizzard.reminders.Reminder;

	
	public class DatabaseHelper extends OrmLiteSqliteOpenHelper{
		
		private static final String DATABASE_NAME = "misrical.db";
		private static final int DATABASE_VERSION = 1;
		
		private RuntimeExceptionDao<Reminder, Integer> reminderRuntimeDao = null;
		
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
		}
		
		@Override
		public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
			try {
				Log.i(DatabaseHelper.class.getName(), "Database Creation");
				TableUtils.createTable(connectionSource, Reminder.class);				
			} catch (SQLException e) {
				Log.e(DatabaseHelper.class.getName(), "Cannot create database", e);
				throw new RuntimeException(e);
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion,int newVersion) {}
		
		private RuntimeExceptionDao<Reminder, Integer> getReminderDao() {
			if (reminderRuntimeDao == null) {
				reminderRuntimeDao = getRuntimeExceptionDao(Reminder.class);
			}
			return reminderRuntimeDao;
		}
		
		public Reminder getReminder(long id){
			ArrayList<Reminder> reminder = (ArrayList<Reminder>) getReminderDao().queryForEq("id", id);
			if(reminder.size()==1){
				return reminder.get(0);
			}else{
				return null;
			}
		}
		
		public ArrayList<Reminder> getReminders(){
			return (ArrayList<Reminder>) getReminderDao().queryForAll();
		}
		
		public void deleteReminder(int id){
			DeleteBuilder<Reminder, Integer> deleteBuilder = getReminderDao().deleteBuilder();
			try {
				deleteBuilder.where().eq("id", id);
				getReminderDao().delete(deleteBuilder.prepare());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void saveReminder(Reminder reminder){
			getReminderDao().createOrUpdate(reminder);
		}
		
		//Should be in a database service class
		public ArrayList<Reminder> getReminderEvents(int daysToAdd){
			ArrayList<Reminder> events = getEventsGregorian(daysToAdd);
			events.addAll(getEventsMisri());
			return events;
		}
		
		private ArrayList<Reminder> getEventsGregorian(int daysToAdd){
			ArrayList<Reminder> events = new ArrayList<>();
			Calendar c = Calendar.getInstance();
			int day = c.get(Calendar.DAY_OF_MONTH) + daysToAdd;
			int month = c.get(Calendar.MONTH) + 1;
			QueryBuilder<Reminder, Integer> queryBuilder = getReminderDao().queryBuilder();

			try {
				queryBuilder.where().eq("gregorianDay", day).and().eq("gregorianMonth", month).and().eq("type", 'G');

				PreparedQuery<Reminder> preparedQuery = queryBuilder.prepare();
				events = (ArrayList<Reminder>) getReminderDao().query(preparedQuery);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return events;
		}
		
		private ArrayList<Reminder> getEventsMisri(){
			ArrayList<Reminder> events = new ArrayList<>();
			Misri misri = new Misri();
			Calendar c = Calendar.getInstance();
			int[] dates = misri.getMisriDate(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
			int day = dates[0];
			int month = dates[1];
			QueryBuilder<Reminder, Integer> queryBuilder = getReminderDao().queryBuilder();

			try {
				queryBuilder.where().eq("misriDay", day).and().eq("misriMonth", month).and().eq("type", 'M');

				PreparedQuery<Reminder> preparedQuery = queryBuilder.prepare();
				events = (ArrayList<Reminder>) getReminderDao().query(preparedQuery);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return events;
		}
		
		@Override
		public void close() {
			super.close();
			reminderRuntimeDao = null;
		}
}
