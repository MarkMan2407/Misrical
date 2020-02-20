package com.squizzard.converter.model;

import java.util.Calendar;

import com.squizzard.util.DateUtil;

public class Misri {
	private static final int[][] gregorian_century = {{600, 0}, {700, 36525}, {800, 73050}, {900, 109575}, {1000, 146100}, {1100, 182625}, {1200, 219150}, {1300, 255675},
            {1400, 292200}, {1500, 328725}, {1582, 358665}, {1600, 365240}, {1700, 401764}, {1800, 438288}, {1900, 474812}, {2000, 511337}};

	private static final int[][] gregorian_decade = {{4, 1461}, {8, 2922}, {12, 4383}, {16, 5844}, {20, 7305}, {24, 8766}, {28, 10227}, {32, 11688},
            {36, 13149}, {40, 14610}, {44, 16071}, {48, 17532}, {52, 18993}, {56, 20454}, {60, 21915}, {64, 23376},
            {68, 24837}, {72, 26298}, {76, 27759}, {80, 29220}, {84, 30681}, {88, 32142}, {92, 33603}, {96, 35064}};

	private static final int[][] gregorian_month = {{1, 0, 366, 731, 1096}, {2, 31, 397, 762, 1127}, {3, 60, 425, 790, 1155}, {4, 91, 456, 821, 1186},
            {5, 121, 486, 851, 1216}, {6, 152, 517, 882, 1247}, {7, 182, 547, 912, 1277}, {8, 213, 578, 943, 1308},
            {9, 244, 609, 974, 1339}, {10, 274, 639, 1004, 1369}, {11, 305, 670, 1035, 1400}, {12, 335, 700, 1065, 1430}};

	private static final int[][] misri_cycle_30 = {{0, 8231}, {30, 18862}, {60, 29493}, {90, 40124}, {120, 50755}, {150, 61386}, {180, 72017}, {210, 82648}, {240, 93279}, {270, 103910},
            {300, 114541}, {330, 125172}, {360, 135803}, {390, 146434}, {420, 157065}, {450, 167696}, {480, 178327}, {510, 188958}, {540, 199589}, {570, 210220},
            {600, 220851}, {630, 231482}, {660, 242113}, {690, 252744}, {720, 263375}, {750, 274006}, {780, 284637}, {810, 295268}, {840, 305899}, {870, 316530},
            {900, 327161}, {930, 337792}, {960, 348423}, {990, 359054}, {1020, 369685}, {1050, 380316}, {1080, 390947}, {1110, 401578}, {1140, 412209},
            {1170, 422840}, {1200, 433471}, {1230, 444102}, {1260, 454733}, {1290, 465364}, {1320, 475995}, {1350, 486626}, {1380, 497257}, {1410, 507888}, {1440, 518519},
            {1470, 529150}, {1500, 539781}};

    private static final int[][] misri_year = {{1, 0}, {2, 354}, {3, 709}, {4, 1063}, {5, 1417}, {6, 1772}, {7, 2126}, {8, 2480}, {9, 2835}, {10, 3189},
            {11, 3544}, {12, 3898}, {13, 4252}, {14, 4607}, {15, 4961}, {16, 5315}, {17, 5670}, {18, 6024}, {19, 6378}, {20, 6733},
            {21, 7087}, {22, 7442}, {23, 7796}, {24, 8150}, {25, 8505}, {26, 8859}, {27, 9213}, {28, 9568}, {29, 9922}, {30, 10277}};

    public static final int[] misri_month = {0, 30, 59, 89, 118, 148, 177, 207, 236, 266, 295, 325};

	private int todayMisriOrdinal = 0;
	private String todayEvent;
	private Calendar c;
    private int todayMisriDay;
    private String todayMisriMonth;
    private int todayMisriMonthCode;
    private int todayMisriYear;
    private int convertedGregorianDay;
    private int convertedGregorianYear;
    private int convertedGregorianMonth;
    
    public Misri(){
    	getTodayMisri();
    }

	public int getMisriOrdinal(){
		return todayMisriOrdinal;
	}

	public String getTodayMisri(){//called by the widget
		c=Calendar.getInstance();
		int[] dateArray = getMisriDate(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
		return DateUtil.getMisriDateString(dateArray[0], dateArray[1], dateArray[2]);
	}
	
	public CharSequence getTomorrowMisri() {
		c=Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		int[] dateArray = getMisriDate(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
		return DateUtil.getMisriDateString(dateArray[0], dateArray[1], dateArray[2]);
	}

	public String getTodayGregorian(){//called by the widget
		c=Calendar.getInstance();
		return DateUtil.getGregorianDateString(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
	}

	public int[] getGregorianDate(int misriD, int misriM, int misriY) {
		// find the number smaller than the year in misri_cycle_30
		int diff=0;
		int ord30=0;
		for(int j=0;j<misri_cycle_30.length; j++){
			if(misri_cycle_30[j][0]>=misriY){
				ord30=misri_cycle_30[j-1][1];
				diff = misriY-misri_cycle_30[j-1][0];
				break;
			}
		}
		int gregorianOrdinal = misri_month[misriM] + ord30 + misriD + misri_year[diff-1][1];

		//Convert gregorianOrdinal to a readable date
		int gregorianCentury = 0;
		for(int j=0;j<gregorian_century.length;j++){
			if(gregorian_century[j][1]>=gregorianOrdinal){
				gregorianCentury=gregorian_century[j-1][0];
				diff=gregorianOrdinal-gregorian_century[j-1][1];
				break;
			}
		}

		if((gregorianCentury == 0) && (gregorianOrdinal>511337)){
			gregorianCentury=gregorian_century[gregorian_century.length-1][0];
			diff=gregorianOrdinal-gregorian_century[gregorian_century.length-1][1];
		}

		int gregorianYear=0;
		int spareYears=0;
		for(int j=0;j<gregorian_decade.length;j++){
			if(gregorian_decade[j][1]>=diff){
				if(j==0){
					gregorianYear=gregorianCentury;
					spareYears=diff;
					break;
				}
				gregorianYear=gregorian_decade[j-1][0]+gregorianCentury;
				spareYears=diff-gregorian_decade[j-1][1];
				break;
			}
		}
		if((spareYears==0)&& (diff>35064)){
			gregorianYear = 96 + gregorianCentury;
			spareYears = diff-35064;
		}
		//look for spareYears in gregorianMonth
		int gMonth=-1;
		int gDay=-1;
		for(int j=0;j<gregorian_month.length;j++){
			if(gregorian_month[j][1]>=spareYears){
				if(j==0){//if spareYers=0 then dec31
					gMonth=gregorian_month[11][0];//dec
					gDay = 31;
					gregorianYear-=1;//go to the previous year
					break;
				}
				gMonth=gregorian_month[j-1][0];
				gDay=spareYears-gregorian_month[j-1][1];//this could generate the 0th
				break;//breaks out of the for loop
			}
		}
		if(gMonth==-1){//implying a greater number hasn't been found in the first column
			for(int j=0;j<gregorian_month.length;j++){
				if(gregorian_month[j][2]>=spareYears){
					if(j==0){//spreYrs=366
						gMonth=gregorian_month[11][0];//dec
						gDay=spareYears-gregorian_month[11][1];
						break;
					}
					gMonth=gregorian_month[j-1][0];
					gDay=spareYears-gregorian_month[j-1][2];
					gregorianYear+=1;
					break;//breaks out of the for loop
				}
			}
		}
		if(gMonth==-1){//implying a greater number hasn't been found in the first or second column
			for(int j=0;j<gregorian_month.length;j++){
				if(gregorian_month[j][3]>=spareYears){
					if(j==0){
						gMonth=gregorian_month[11][0];
						gDay=spareYears-gregorian_month[11][2];
						gregorianYear+=1;
						break;
					}
					gMonth=gregorian_month[j-1][0];
					gDay=spareYears-gregorian_month[j-1][3];
					gregorianYear+=2;
					break;//breaks out of the for loop
				}
			}
		}
		if(gMonth==-1){//implying a greater number hasn't been found in the first or second column
			for(int j=0;j<gregorian_month.length;j++){
				if(gregorian_month[j][4]>=spareYears){
					if(j==0){
						gMonth=gregorian_month[11][0];
						gDay=spareYears-gregorian_month[11][3];
						gregorianYear+=2;
						break;
					}
					gMonth=gregorian_month[j-1][0];
					gDay=spareYears-gregorian_month[j-1][4];
					gregorianYear+=3;
					break;//breaks out of the for loop
				}
			}
		}
		if(gMonth==-1&&spareYears<=1461){ 
			gMonth = 12;//Accounting for Table 2 Stage 3 1430->1461
			gDay = spareYears-1430;
			gregorianYear+=3;
		}
		int[] gregorianArray = new int[3];
		gregorianArray[0] = gDay;
		gregorianArray[1] = gMonth-1;
		gregorianArray[2] = gregorianYear;
		setConvertedGregorianDay(gDay);
		setConvertedGregorianMonth(gMonth-1);
		setConvertedGregorianYear(gregorianYear);
			
		return gregorianArray;
	}

	public int[] getMisriDate(int d, int m, int y){
		int month = -1;
		int misriYear = -1;
		int monthDay = -1;
		int cycle30Diff = -1;
		int yearDiff = -1;
		int[] returnArray = new int[3];

		int gregorianOrdinal = getGregorianAsOrdinal(y, m, d);
		for(int j=0;j<misri_cycle_30.length; j++){
			if(misri_cycle_30[j][1]>=gregorianOrdinal){
				try{
				misriYear=misri_cycle_30[j-1][0];
				}catch(ArrayIndexOutOfBoundsException e){
					//return "Out of calendar range!";
				}
				cycle30Diff = gregorianOrdinal-misri_cycle_30[j-1][1];
				break;
			}
		}
		for (int j=0;j<misri_year.length;j++) {
			if (misri_year[j][1]>=cycle30Diff) {
				try {
				misriYear=misriYear+misri_year[j-1][0];
				yearDiff = cycle30Diff-misri_year[j-1][1];
				} catch (ArrayIndexOutOfBoundsException e) {
					//return "Out of calendar range!";
				}
				break;
			}
		}
		if((yearDiff==-1) && (cycle30Diff>10277)){ //V1.01
			misriYear=misriYear+misri_year[29][0];
			yearDiff = cycle30Diff-misri_year[29][1];
		}
		//using yearDiff find the exact date in the year from misri_month
		for(int j=0;j<misri_month.length;j++){
			if(misri_month[j]>=yearDiff){
				month = j;
				monthDay = yearDiff-misri_month[j-1];
				break;
			}
		}

		if((month==-1) && (yearDiff>325) && (yearDiff <=355)){//V1.01 - changed from 354 to 355
			month=12;
			monthDay = yearDiff-misri_month[11];
		}

		
		setTodayMisriMonth(DateUtil.getMisriMonth(month));
		setTodayMisriDay(monthDay);
		setTodayMisriMonthCode(month);
		setTodayMisriYear(misriYear);
		setEvent(month, monthDay);
		returnArray[0] = monthDay;
		returnArray[1] = month;
		returnArray[2] = misriYear;
		return returnArray;
	}

	private int getGregorianAsOrdinal(int year, int month, int day){
		int ordinal=0;
		int century = year-(year%100);
		int decade = year%100;
		int spareYears=0;
		int spareMonth;
		//search the gregorian_century array for the correct entry and return the ordinal value
		for(int j=0; j<gregorian_century.length; j++)
			if(gregorian_century[j][0]==century)
			{
				ordinal=gregorian_century[j][1];
				break;
			}
		//search through gregorian_decade and find the first entry greater than decade
		for(int j=0;j<gregorian_decade.length;j++){
			if(gregorian_decade[j][0]>decade){
				if(j!=0){
					ordinal=ordinal+gregorian_decade[j-1][1];
					spareYears=decade-gregorian_decade[j-1][0];
					break;}
				else{//no need to add to ordinal
					spareYears=decade;
					break;
				}
			}
		}
		//if decade >=96 and <100 then the value will not have been found in gregorian_decade
		if((decade>=96)&&(decade<100)){//Adding this fixed error with years 1996-1999
			ordinal=ordinal+gregorian_decade[gregorian_decade.length-1][1];//35064
			spareYears=decade-96;
		}	
		
		//now index into the table for month data
		spareMonth = gregorian_month[month][spareYears+1];
		ordinal = ordinal + spareMonth + day;

		return ordinal;
	}

	public void setEvent(int monthOfYear, int dayOfMonth) {
		todayMisriOrdinal = dayOfMonth + misri_month[monthOfYear-1];
		String eventString="";
        String[] allEventsString;
		if(DateUtil.priorityEventMap.containsKey(todayMisriOrdinal)){
			allEventsString=DateUtil.priorityEventMap.get(todayMisriOrdinal);

			for(int x=0;x<allEventsString.length; x++){
				eventString += allEventsString[x];
				if(allEventsString.length>1&&x<allEventsString.length){
					eventString +=", ";
				}
			}
		}
		todayEvent = eventString;
	}
	
	public String getTodayEvent(){
		return todayEvent;
	}

	public int getTodayMisriDay() {
		return todayMisriDay;
	}
	
	public void setTodayMisriDay(int todayMisriDay) {
		this.todayMisriDay = todayMisriDay;
	}

	public String getTodayMisriMonth() {
		return todayMisriMonth;
	}

	public void setTodayMisriMonth(String todayMisriMonth) {
		this.todayMisriMonth = todayMisriMonth;
	}

	public int getTodayMisriYear() {
		return todayMisriYear;
	}

	public void setTodayMisriYear(int todayMisriYear) {
		this.todayMisriYear = todayMisriYear;
	}

	public int getTodayMisriMonthCode() {
		return todayMisriMonthCode;
	}

	public void setTodayMisriMonthCode(int todayMisriMonthCode) {
		this.todayMisriMonthCode = todayMisriMonthCode;
	}

	public int getConvertedGregorianDay() {
		return convertedGregorianDay;
	}

	public void setConvertedGregorianDay(int todayGregorianDay) {
		this.convertedGregorianDay = todayGregorianDay;
	}

	public int getConvertedGregorianYear() {
		return convertedGregorianYear;
	}

	public void setConvertedGregorianYear(int todayGregorianYear) {
		this.convertedGregorianYear = todayGregorianYear;
	}

	public int getConvertedGregorianMonth() {
		return convertedGregorianMonth;
	}

	public void setConvertedGregorianMonth(int todayGregorianMonth) {
		this.convertedGregorianMonth = todayGregorianMonth;
	}
}