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

	int getGregorianDay() {
		return gregorianDay;
	}

	void setGregorianDay(int gregorianDay) {
		this.gregorianDay = gregorianDay;
	}

	int getGregorianMonth() {
		return gregorianMonth;
	}

	void setGregorianMonth(int gregorianMonth) {
		this.gregorianMonth = gregorianMonth;
	}

	int getMisriDay() {
		return misriDay;
	}

	void setMisriDay(int misriDay) {
		this.misriDay = misriDay;
	}

	int getMisriMonth() {
		return misriMonth;
	}

	void setMisriMonth(int misriMonth) {
		this.misriMonth = misriMonth;
	}

	char getType() {
		return type;
	}

	void setType(char type) {
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

	void setReminderText(String reminderText) {
		this.reminderText = reminderText;
	}
	
	String getMisriDateText(){
		return DateUtil.getMisriDateString(getMisriDay(), getMisriMonth());
	}
	
	String getGregorianDateText(){
		return DateUtil.getGregorianDateString(getGregorianDay(), getGregorianMonth());
	}
}
