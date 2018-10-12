package com.squizzard.Reminder;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.squizzard.util.DateUtil;

@DatabaseTable(tableName = "reminders")
public class Reminder {

	@DatabaseField(generatedId=true)
	private int id;

	@DatabaseField
	private String reminderText;

	@DatabaseField
	private int gregorianDay;

	@DatabaseField
	private int gregorianMonth;

	@DatabaseField
	private int misriDay;

	@DatabaseField
	private int misriMonth;
	
	@DatabaseField
	private int hour;
	
	@DatabaseField
	private int minute;
	
	@DatabaseField
	private char type;
	
	@DatabaseField 
	private boolean active;


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

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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
