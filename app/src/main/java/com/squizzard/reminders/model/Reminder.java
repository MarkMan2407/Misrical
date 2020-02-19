package com.squizzard.reminders.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.squizzard.util.DateUtil;

@Entity (tableName = "reminders")
@DatabaseTable(tableName = "reminders")
public class Reminder {

    @PrimaryKey
	@DatabaseField(generatedId=true)
    @ColumnInfo(name = "id")
	int id;

	@DatabaseField
    @ColumnInfo(name = "reminderText")
	String reminderText;

	@DatabaseField
    @ColumnInfo(name = "gregorianDay")
	int gregorianDay;

	@DatabaseField
    @ColumnInfo(name = "gregorianMonth")
	int gregorianMonth;

	@DatabaseField
    @ColumnInfo(name = "misriDay")
	int misriDay;

	@DatabaseField
    @ColumnInfo(name = "misriMonth")
	int misriMonth;
	
	@DatabaseField
    @ColumnInfo(name = "hour")
	private int hour;
	
	@DatabaseField
    @ColumnInfo(name = "minute")
	int minute;
	
	@DatabaseField
    @ColumnInfo(name = "type")
	char type;
	
	@DatabaseField
    @ColumnInfo(name = "active")
	boolean active;


	@Ignore
	public Reminder(){
	}

	public Reminder(String reminderText, int gregorianDay, int gregorianMonth, int misriDay, int misriMonth, int hour, int minute, char type){
		this.reminderText = reminderText;
		this.gregorianDay = gregorianDay;
		this.gregorianMonth = gregorianMonth;
		this.misriDay = misriDay;
		this.misriMonth = misriMonth;
		this.hour = 0;
		this.minute = 0;
		this.type = type;
		this.active = true;
	}

	public int getGregorianDay() {
		return gregorianDay;
	}

	public void setGregorianDay(int gregorianDay) {
		this.gregorianDay = gregorianDay;
	}

	public int getGregorianMonth() {
		return gregorianMonth;
	}

	public void setGregorianMonth(int gregorianMonth) {
		this.gregorianMonth = gregorianMonth;
	}

	public int getMisriDay() {
		return misriDay;
	}

	public void setMisriDay(int misriDay) {
		this.misriDay = misriDay;
	}

	public int getMisriMonth() {
		return misriMonth;
	}

	public void setMisriMonth(int misriMonth) {
		this.misriMonth = misriMonth;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReminderText() {
		return reminderText;
	}

	public void setReminderText(String reminderText) {
		this.reminderText = reminderText;
	}
	
	public String getMisriDateText(){
		return DateUtil.getMisriDateString(getMisriDay(), getMisriMonth());
	}
	
	public String getGregorianDateText(){
		return DateUtil.getGregorianDateString(getGregorianDay(), getGregorianMonth());
	}
}
