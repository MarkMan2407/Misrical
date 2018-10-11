package com.squizzard.util;

import java.util.Map;
import java.util.TreeMap;

public class DateUtil {

	public static String getGregorianDateString(int day, int month){
		return "" + day + getDaySuffix(day) + getGregorianMonth(month);
	}
	
	public static String getGregorianDateString(int day, int month, int year){
		return getGregorianDateString(day, month) + year;
	}
	
	public static String getMisriDateString(int day, int month){
		return "" + day + DateUtil.getDaySuffix(day) +  getMisriMonth(month);
	}

	public static String getMisriDateString(int day, int month, int year) {
		return getMisriDateString(day, month) + year;
	}
	

	public static String getGregorianMonth(final int month) {
		switch(month){
		case 0: return " January ";
		case 1: return " February ";
		case 2: return " March ";
		case 3: return " April ";
		case 4: return " May ";
		case 5: return " June ";
		case 6: return " July ";
		case 7: return " August ";
		case 8: return " September ";
		case 9: return " October ";
		case 10: return " November ";
		case 11: return " December ";
		default: return "";
		}
	}

	public static String getDaySuffix(final int day) {
		if (day >= 11 && day <= 13) {
			return "th";
		}
		switch (day % 10) {
		case 1: return "st";
		case 2: return "nd";
		case 3: return "rd";
		default: return "th";
		}
	}
	
	public static String getMisriMonth(final int month) {
		switch(month-1){
		case 0: return " Moharram-al-Haram ";
		case 1: return " Safar-al-Muzaffar ";
		case 2: return " Rabi-al-Awwal ";
		case 3: return " Rabi-al-Aakhar ";
		case 4: return " Jumada-al-Ulaa ";
		case 5: return " Jamada-al-Ukhra ";
		case 6: return " Rajab-al-Asab ";
		case 7: return " Shaban-al-Karim ";
		case 8: return " Ramadan-al-Moazzam ";
		case 9: return " Shawwal-al-Mukarram ";
		case 10: return " Zilqad-al-Haraam ";
		case 11: return " Zilhajj-al-Haraam ";
		default: return "";
		}
	}
	
	public static final Map<Integer, String[]> eventMap= new TreeMap<Integer, String[]>();
	public static final Map<Integer, String[]> priorityEventMap= new TreeMap<Integer, String[]>();
	static{
		eventMap.put(Integer.valueOf(1), new String[]{"Urus Mawlai Abdullah Saheb Khambat"});
		eventMap.put(Integer.valueOf(2), new String[]{"Urus Syedi Shaikh Pir Jamaluddin Jamnagar","Urus Syedi Khanji Fir Saheb Udaipur","Urus Mawlai Raj Bin Mawlai Hasan Saheb Ahmedabad"});
		eventMap.put(Integer.valueOf(6), new String[]{"Urus Syedi Mohammed Bin Qazikhan Kapadvanj"});
		eventMap.put(Integer.valueOf(7), new String[]{"Urus Syedna Ismail Badruddin AQ [38th Dai] Jamnagar"});
		eventMap.put(Integer.valueOf(10), new String[]{"Urus Syedna Zoeb Bin Musa AQ [1st Dai] Yemen","Urus Mawlai Ahmed Saheb Khambat"});
		priorityEventMap.put(Integer.valueOf(10), new String[]{"Yawme Ashura"});
		eventMap.put(Integer.valueOf(14), new String[]{"Urus Mawlai Lukmanji Mulla Alibhai Saheb Wankaner"});
		eventMap.put(Integer.valueOf(15), new String[]{"Urus Mawlai Nuruddin Saheb Dongaon"});
		priorityEventMap.put(Integer.valueOf(16), new String[]{"Urus Syedna Hatim bin Syedna Ibrahim AQ [3rd Dai] Yemen"});
		priorityEventMap.put(Integer.valueOf(17), new String[]{"Urus Shahadat Imam Ali Zainulabedin SA Medina"});	
		eventMap.put(Integer.valueOf(17), new String[]{"Urus Syedna Ibrahim Vajihuddin AQ [39th Dai] Ujain","Urus Mulla Mohammedali bin Syedi Najam Khan Pune", "Urus Mawlai Masud bin Sulaiman Dhrangadhra","Urus Seven Shahid Sahebo Mujpur"});
		eventMap.put(Integer.valueOf(18), new String[]{"Urus Shahid Gani Pir Ibne Dawoodji Kalavad"});
		eventMap.put(Integer.valueOf(23), new String[]{"Urus Syedi Hasan Fir Saheb Shahid Denmal", "Urus Noor Bibi Umme Syedna Yusuf Najmuddin Dandigam", "Urus Fatema Bibi Ukhte Syedna Yusuf Najmuddin Dandigam"});
		eventMap.put(Integer.valueOf(24), new String[]{"Urus Syedi Dada Sulemanji Bundi, Kota"});
		priorityEventMap.put(Integer.valueOf(27), new String[]{"Urus Syedi Fakhruddin Shahid AQ Taherabad (Galiakot)"});
		eventMap.put(Integer.valueOf(28), new String[]{"Urus Syedi Musaji bin Taj Baroda"});
		eventMap.put(Integer.valueOf(29), new String[]{"Urus Mawlai Hasan Bin Mawlai Adam Ahmedabad"});
		eventMap.put(Integer.valueOf(31), new String[]{"Urus Syedna Ali Bin Syedna Husain AQ [10th Dai] Yemen"});
		eventMap.put(Integer.valueOf(33), new String[]{"Urus Syedna Ali Shamsuddin Bin Syedna Abdullah AQ [18th Dai] Yemen"});
		eventMap.put(Integer.valueOf(34), new String[]{"Urus Syedna Abdul Tayyib Zakiyuddin AQ [41st Dai] Burhanpur"});
		eventMap.put(Integer.valueOf(36), new String[]{"Urus Syedi Abdeali Imaduddin Surat"});
		eventMap.put(Integer.valueOf(38), new String[]{"Urus Syedna Khattab Bin Hasan Hamdani AQ Yemen"});
		eventMap.put(Integer.valueOf(39), new String[]{"Urus Syedi Tayyib bs Zainuddin Surat"});
		eventMap.put(Integer.valueOf(42), new String[]{"Urus Syedna Ahmed Hamiduddin Kirmani RA Cairo"});
		eventMap.put(Integer.valueOf(43), new String[]{"Urus Mawlai Adam bin Sulaimanji Ahmedabad"});
		eventMap.put(Integer.valueOf(44), new String[]{"Urus Kaka Akela - Kaki Akeli Khambat", "Urus Mawlai Noorbhai Saheb Dhinoj"});
		eventMap.put(Integer.valueOf(45), new String[]{"Urus Syedi Hamza Bhaisaheb Surat"});
		eventMap.put(Integer.valueOf(47), new String[]{"Urus Shaikh AbdulHusain Shahid Chechat","Urus Mawlai Shaikh Saheb bin Sulaimanji","Urus Syedi Shaikh Ibrahim Chechat"});
		priorityEventMap.put(Integer.valueOf(50), new String[]{"Chelum Imam Husain SA"});
		eventMap.put(Integer.valueOf(52), new String[]{"Urus Syedna Husain bin Syedna Ali AQ [8th Dai] Yemen"});
		eventMap.put(Integer.valueOf(57), new String[]{"Urus Syedna Mohammed Izzuddin AQ [23rd Dai] Yemen"});
		priorityEventMap.put(Integer.valueOf(58), new String[]{"Shahadat Imam Hasan SA"});
		eventMap.put(Integer.valueOf(59), new String[]{"Urus Syedi Hasan Zakiyuddin Surat"});
		eventMap.put(Integer.valueOf(60), new String[]{"Urus Syedi Shaikh Adam Safiyuddin Jamnagar","Urus Syedi Jamaluddin bin Shaikh Adam Jamnagar"});
		eventMap.put(Integer.valueOf(61), new String[]{"Urus Syedna Abdul Tayyib Zakiyuddin bin Syedna Dawood bin Qutbub  Shah AQ [29th Dai] Ahmedabad"});
		eventMap.put(Integer.valueOf(63), new String[]{"Urus Syedi Habibullah bin Mulla Adamji Ujjain"});
		eventMap.put(Integer.valueOf(66), new String[]{"Urus Syedi Shaikh Dawood Bhai Mulla Mehmoodji Udaipur","Urus Syedi Abdeali Mohyiddin Surat"});
		eventMap.put(Integer.valueOf(69), new String[]{"Urus Syedna Abdullah Badruddin AQ [50th Dai] Surat"});
		priorityEventMap.put(Integer.valueOf(71), new String[]{"Milad Eid Milad un Nabi"});
		eventMap.put(Integer.valueOf(71), new String[]{"Urus Ummul Mumineen Amatullah Aaisaheba London"});
		eventMap.put(Integer.valueOf(73), new String[]{"Urus Syedi Miaji Mulla Taj Saheb Amaryaath"});
		priorityEventMap.put(Integer.valueOf(75), new String[]{"Urus Syedna Mohammad Burhanuddin RA"});//16th Rabi Ul Awwal Added
		eventMap.put(Integer.valueOf(81), new String[]{"Urus Syedna Ali bin Hanzala AQ [6th Dai] Yemen","Urus Mawlai Dawood bin Raj Saheb Morbi"});
		eventMap.put(Integer.valueOf(82), new String[]{"Urus Syedi Qazi Khan bin Ameen Shah Halwad","Urus Mawlai Raj Saheb Morbi"});
		eventMap.put(Integer.valueOf(84), new String[]{"Urus Syedna Ali Shamsuddin bin Mawlai Hasan [30th Dai] Yemen"});
		eventMap.put(Integer.valueOf(87), new String[]{"Urus Mohammad bin Hasan Saheb Dhinoj"});

		priorityEventMap.put(Integer.valueOf(93), new String[]{"Milad Imam uz Zaman"});
		eventMap.put(Integer.valueOf(94), new String[]{"Urus Mia Saheb Maati Bhai Mulla Noor Bhai Balasinor","Urus Mia Saheb Tayebji Shaikh Shams Khan Balasnwar"});
		eventMap.put(Integer.valueOf(97), new String[]{"Urus Mawlai Raj bin Mulla Adam Saheb Jamnagar"});
		eventMap.put(Integer.valueOf(99), new String[]{"Urus Syedi AbdulRasul Shahid Baanswara"});
		eventMap.put(Integer.valueOf(103), new String[]{"Urus Syedi Ismailji Shahid bin Abde Musa Godhra"});
		eventMap.put(Integer.valueOf(105), new String[]{"Urus Syedna Jalal Shamsuddin AQ bin Hasan [25th Dai] Ahmedabad"});
		priorityEventMap.put(Integer.valueOf(109), new String[]{"Milad Dai Uz Zaman Syedna Mohammed Burhanuddin RA [52nd Dai]"});
		eventMap.put(Integer.valueOf(111), new String[]{"Urus Syedna Musa Kalimuddin bin Syedna Abdul Tayyib Zakiyuddin AQ [36th Dai] Jamnagar","Urus Syedi Mulla Habibullah bin Shaikh Sultanali Bharuch"});
		eventMap.put(Integer.valueOf(116), new String[]{"Urus Syedna Dawood Burhanuddin AQ bin Ajab Shah [26th Dai] Ahmedabad"});
		eventMap.put(Integer.valueOf(117), new String[]{"Urus Kakaji Mulla Isa Bhai Partapghar"});

		eventMap.put(Integer.valueOf(119), new String[]{"Urus Syedna Ahmed Al Mukaraam Yemen"});
		eventMap.put(Integer.valueOf(121), new String[]{"Urus Syedi Qazi Khan bin Ali Sidhpur"});
		eventMap.put(Integer.valueOf(126), new String[]{"Urus Syedi Mulla Wahid Bhaisaheb bin Mulla Ibrahimji Surat"});
		priorityEventMap.put(Integer.valueOf(128), new String[]{"Wafat Maulatena Fatema tuz Zahra"});
		eventMap.put(Integer.valueOf(129), new String[]{"Urus Mawlai Nooruddin Saheb Dongaon"});
		eventMap.put(Integer.valueOf(133), new String[]{"Urus Mawlai Dawood bin Qazi Ahmed Dongaon"});
		eventMap.put(Integer.valueOf(135), new String[]{"Urus Syedi Dawood Bhaisaheb Shihabuddin Surat"});
		eventMap.put(Integer.valueOf(139), new String[]{"Urus Seth Chanda bhai ibne Karim Bhai Mumbai"});
		eventMap.put(Integer.valueOf(141), new String[]{"Urus Mulla Jaferji Jiwaji Amreli"});
		eventMap.put(Integer.valueOf(147), new String[]{"Urus Syedi Jivanji bin Shaikh Dawood Bhaisaheb Burhanpur"});

		eventMap.put(Integer.valueOf(156), new String[]{"Urus Syedi Luqmaanji bin Mulla Habibullah Surat"});
		eventMap.put(Integer.valueOf(160), new String[]{"Urus Mulla Tayyib Bawa bin Mulla Ibrahimji Ranala"});
		eventMap.put(Integer.valueOf(162), new String[]{"Urus Ganje Shohoda Ahmednagar"});
		eventMap.put(Integer.valueOf(163), new String[]{"Urus Syedna Dawood Burhanuddin AQ bin Qutub Shah [27th Dai] Ahmedabad","Urus Mawlai Ali bhai Shahid (Indore) Kamlapur"});
		eventMap.put(Integer.valueOf(166), new String[]{"Urus Syedna Yusuf Najmuddin AQ [42nd Dai] Surat","Urus Moulai Burhanuddin Ibne Khoj Khan Pisawada","Urus Mawlai Adam ibne Dawood Jamnagar"});
		priorityEventMap.put(Integer.valueOf(171), new String[]{"Urus Syedna Ismail Badruddin AQ bin Mawlai Raj [34th Dai] Jamnagar"});
		eventMap.put(Integer.valueOf(174), new String[]{"Salgirah Syedi Mukasir Saheb Husain Bhaisaheb Husamuddin"});
		eventMap.put(Integer.valueOf(175), new String[]{"Urus Syedna Lamak bin Malik RA Lahab (Yemen)"});
		priorityEventMap.put(Integer.valueOf(175), new String[]{"Urus Syedna Qutub Khan Qutubuddin Shahid AQ [32nd Dai] Ahmedabad"});
		eventMap.put(Integer.valueOf(176), new String[]{"Urus Syedna Ahmed bin Mubarak AQ (Hamdaan) [7th Dai] Yemen","Urus Syedna Yahya bin Syedna Lamak AQ Lahab (Yemen)"});
		eventMap.put(Integer.valueOf(177), new String[]{"Urus Syedna Qadi Numan bin Mohammed AQ Misr","Urus Syedna Mohammed Badruddin AQ [46th Dai] Surat","Urus Ajab Busaheba Binte Syedna Qutubuddin Shahid AQ Ahmedabad"});
		priorityEventMap.put(Integer.valueOf(177), new String[]{"Washeq Raat Pehli Raat Washeq"});

		priorityEventMap.put(Integer.valueOf(178), new String[]{"Rozu"});
		eventMap.put(Integer.valueOf(179), new String[]{"Urus Bhaiji Bhai Ibne Qazi Bhai Karachi","Urus Mawlai Raj bin Dawood Ahmedabad"});
		priorityEventMap.put(Integer.valueOf(181), new String[]{"Urus Syedna Noor Mohammed Nooruddin AQ [37th Dai] Kutch Mandvi"});
		eventMap.put(Integer.valueOf(181), new String[]{"Urus Syedi Hasanji Badshah Ujjain"});
		eventMap.put(Integer.valueOf(184), new String[]{"Urus Syedna Shaikh Adam Safiyuddin AQ [28th Dai] Ahmedabad"});
		eventMap.put(Integer.valueOf(185), new String[]{"Urus Syedi Saifuddin Saheb Jamnagar"});
		eventMap.put(Integer.valueOf(189), new String[]{"Urus Syedi Najam Khan bin Syedna Fir Khan Shujahuddin AQ Aurangabad"});
		priorityEventMap.put(Integer.valueOf(190), new String[]{"Ayyam ul Biz Rozu"});
		eventMap.put(Integer.valueOf(190), new String[]{"Milad Amirul Mumineen, Maulana Ali (SA)"});
		priorityEventMap.put(Integer.valueOf(191), new String[]{"Ayyam ul Biz Rozu"});
		eventMap.put(Integer.valueOf(191), new String[]{"Urus Mawlai Yaqub Saheb Paatan"});
		priorityEventMap.put(Integer.valueOf(192), new String[]{"Ayyam ul Biz & Salaat uz-Zawal Rozu"});
		eventMap.put(Integer.valueOf(195), new String[]{"Urus Syedna Ali Shamsuddin AQ bin Syedna Ibrahim bin Husain AQ [13th Dai] Yemen Zamarmar"});
		priorityEventMap.put(Integer.valueOf(196), new String[]{"Urus Syedna Taher Saifuddin AQ [51st Dai] Mumbai"});
		eventMap.put(Integer.valueOf(201), new String[]{"Urus Syedna Abdul Mutalib Najmuddin AQ bin Syedna Mohammed bin Hatim AQ [14th Dai] Yemen Zamarmar","Urus Syedi Qamruddin Bhaisaheb bin Syedna Haibatullah Al Muaid AQ Ujjain"});
		priorityEventMap.put(Integer.valueOf(203), new String[]{"Urus Syedna Abdul Qadr Najmuddin AQ [47th Dai] Ujjain","Laylat ul-Meraj Washeq Raat"});
		priorityEventMap.put(Integer.valueOf(204), new String[]{"Yawm al-Mabath (Motius Sawalat) Rozu"});
		eventMap.put(Integer.valueOf(204), new String[]{"Urus Syedi Miasaheb Alibhai bin Peeriji Radhanpur","Urus Aminji Shahid [Urus done on the 24th] Paddhari"});
		eventMap.put(Integer.valueOf(206), new String[]{"Urus Syedi Luqmaanji bin Syedi Dawood bhai Udaipur"});

		eventMap.put(Integer.valueOf(208), new String[]{"Urus Syedna Hebatullah Muayyad Fiddin AQ [40th Dai] Ujjain"});
		priorityEventMap.put(Integer.valueOf(221), new String[]{"Washeq Raat Shabb-e-Baraat"});
		priorityEventMap.put(Integer.valueOf(222), new String[]{"Urus Syedna Hasan Badruddin AQ [20th Dai] Yemen Masaar"});
		eventMap.put(Integer.valueOf(223), new String[]{"Urus Syedna Ibrahim bin Husain AQ [2nd Dai] Yemen Hamdaan"});
		eventMap.put(Integer.valueOf(226), new String[]{"Urus Syedi Saleh bhaisaheb Saifuddin Mumbai"});
		priorityEventMap.put(Integer.valueOf(229), new String[]{"Urus Maulatina Hurratul Malika (RA) Sanaa, Yemen"});
		eventMap.put(Integer.valueOf(229), new String[]{"Urus Syedi Shaikhfir bin Dawood Shahid Ranpur","Urus Syedi Shaikh Valibhai bin Shaikh Habibullah Urus on 2nd of Ramadan, recited on 22nd Shaban Paarda"});
		eventMap.put(Integer.valueOf(232), new String[]{"Urus Syedi Shams Khan bin Syedi Yusufji Surat"});
		eventMap.put(Integer.valueOf(236), new String[]{"Urus Syedna Ali bin Mawla Mohammed al-Walid AQ [5th Dai] Yemen","Urus Syedi Jiwanji bin Shaikh Dawood bhai Burhanpur"});

		eventMap.put(Integer.valueOf(237), new String[]{"Urus Shaikh Dawood Bhaisaheb Dhinoj"});
		eventMap.put(Integer.valueOf(238), new String[]{"Urus Syedi Wali Bhaisaheb bin Syedi Habibullah Pardha"});
		eventMap.put(Integer.valueOf(240), new String[]{"Urus Syedi Tayyib Bhaisaheb Zainuddin AQ Surat"});
		eventMap.put(Integer.valueOf(244), new String[]{"Urus Syedi Fazal Bhaisaheb Qutubuddin bin Syedna Abdullah AQ Surat"});
		eventMap.put(Integer.valueOf(245), new String[]{"Urus Syedna Abdullah Fakhruddin AQ bin Ali [16th Dai] Yemen"});
		priorityEventMap.put(Integer.valueOf(252), new String[]{"Washeq Raat"});
		eventMap.put(Integer.valueOf(252), new String[]{"Urus Syedi Hebatullah Jamaluddin Jamnagar"});
		priorityEventMap.put(Integer.valueOf(254), new String[]{"Washeq Raat"});
		priorityEventMap.put(Integer.valueOf(255), new String[]{"Shahadat Amir ul Mumineen, Maulana Ali SA"});
		eventMap.put(Integer.valueOf(255), new String[]{"Urus Syedna Mohammed Izziuddin AQ bin Syedi Jiwanji [44th Dai] Surat"});
		priorityEventMap.put(Integer.valueOf(256), new String[]{"Washeq Raat"});
		priorityEventMap.put(Integer.valueOf(257), new String[]{"Wafaat Amir ul Mumineen, Maulana Ali SA"});
		priorityEventMap.put(Integer.valueOf(258), new String[]{"Laylatul Qadr Laylatul Qadr"});
		priorityEventMap.put(Integer.valueOf(259), new String[]{"Milad Dai uz Zaman Syedna Muffadal Saifuddin (TUS)"});
		priorityEventMap.put(Integer.valueOf(265), new String[]{"Washeq Raat"});
		priorityEventMap.put(Integer.valueOf(266), new String[]{"Laylatul Eid ul Fitr Takbira, Washeq Raat"});

		priorityEventMap.put(Integer.valueOf(267), new String[]{"Eid ul Fitr Takbira (Fajr, Zohar)"});
		eventMap.put(Integer.valueOf(269), new String[]{"Urus Shehzadi Sakina Bhensaheba binte Syedna Taher Saifuddin AQ Mumbai"});
		eventMap.put(Integer.valueOf(270), new String[]{"Urus Syedi Yusufji Ahmedabad","Urus Syedi Taiyebji Shahid Ahmedabad","Urus Syedi Abdul Qadir Hakimuddin [First Urus] Burhanpur"});
		eventMap.put(Integer.valueOf(272), new String[]{"Urus Syedna Hasan Badruddin bin Syedba Abdullah Fakhruddin AQ [17th Dai] Yemen"});
		eventMap.put(Integer.valueOf(273), new String[]{"Urus Syedna Mohammed bin Taher AQ [Writer of Dua Al Aql Al Awwal, Bawesani Dua] Yemen"});
		eventMap.put(Integer.valueOf(274), new String[]{"Urus Syedna Abbas bin Syedna Mohammed bin Hatim AQ [15th Dai] Yemen"});
		eventMap.put(Integer.valueOf(275), new String[]{"Urus Syedna Qasim Khan Zainuddin AQ [31st Dai] Ahmedabad"});
		eventMap.put(Integer.valueOf(276), new String[]{"Urus Syedna Hebatullah Muayyadfiddin Shirazi AQ Cairo","Urus Syedna Husain Husamuddin AQ [21st Dai] Yemen","Urus Syedna Ibrahim bin Syedna Husain AQ [11th Dai] Yemen"});
		eventMap.put(Integer.valueOf(279), new String[]{"Urus Syedi Aminji bin Jalal Ahmedabad"});
		eventMap.put(Integer.valueOf(290), new String[]{"Urus Shaikh Qutub Bhai bin Sulaimanji Pune"});
		priorityEventMap.put(Integer.valueOf(293), new String[]{"Urus Syedi Abdul Qadir Hakimuddin [Second Urus] Burhanpur"});
		eventMap.put(Integer.valueOf(293), new String[]{"Urus Mia Saheb Abdeali Waliullah Jaawarah"});
		eventMap.put(Integer.valueOf(295), new String[]{"Urus Mulla Salehbhai Ibne Najamkhan Ahmedabad","Urus Syedi Bawa Mulla Khan Saheb bin Syedi Habibullah Rampurah","Urus Syedi Qasim Khan bin Hamza Bhai Surat"});

		eventMap.put(Integer.valueOf(304), new String[]{"Urus Syedna Fir Khan Shujahuddin AQ [33rd Dai] Ahmedabad"});
		eventMap.put(Integer.valueOf(306), new String[]{"Urus Syedi Hasan bin Nuh Bharuji Yemen, Masaar","Urus Syedna Ali bin Mohammed Sulayhi RA Yemen"});
		eventMap.put(Integer.valueOf(307), new String[]{"Urus Syedna Abdul Tayyib Zakiyuddin bin Syedna Ismail Badruddin AQ  [35th Dai] Jamnagar"});
		priorityEventMap.put(Integer.valueOf(307), new String[]{"Urus Syedna Abdeali Saifuddin AQ [43rd Dai] Surat"});
		eventMap.put(Integer.valueOf(308), new String[]{"Urus Syedna Ali bin Syedna Husain [9th Dai] Yemen"});
		eventMap.put(Integer.valueOf(310), new String[]{"Urus Syedna Tayyib Zainuddin bin Syedi Jewanji [45th Dai] Surat","Urus Bai Saheba Raani Baisaheba binte Syedna Ismail Badruddin AQ Mundra"});
		eventMap.put(Integer.valueOf(314), new String[]{"Urus Syedna Idris Imaduddin AQ [19th Dai] Yemen, Shaam"});
		eventMap.put(Integer.valueOf(316), new String[]{"Urus Syedna Ali Shamsuddin AQ [22nd Dai] Yemen"});
		eventMap.put(Integer.valueOf(317), new String[]{"Urus Syedi Shaikh Sadiq Ali Saheb Surat"});
		eventMap.put(Integer.valueOf(320), new String[]{"Urus Syedna Ali Shamsuddin bin Syedna Hatim AQ [4th Dai] Yemen"});
		eventMap.put(Integer.valueOf(322), new String[]{"Urus Syedi Yusuf Khan bin Syedi Shams Khan Shajapur"});
		priorityEventMap.put(Integer.valueOf(322), new String[]{"Milad Syedna Taher Saifuddin AQ [51st Dai]"});

		eventMap.put(Integer.valueOf(326), new String[]{"Urus Syedna Mohammed AQ bin Hatim bin Husain in Ali [12th Dai] Yemen"});
		eventMap.put(Integer.valueOf(331), new String[]{"Urus Syedi Khoj bin Malak Kaparwanj"});
		priorityEventMap.put(Integer.valueOf(334), new String[]{"Yawm ul-Arafa Takbira and Washeq"});
		priorityEventMap.put(Integer.valueOf(335), new String[]{"Eid al Adha Takbira"});
		priorityEventMap.put(Integer.valueOf(336), new String[]{"Takbira"});
		priorityEventMap.put(Integer.valueOf(337), new String[]{"Takbira"});
		priorityEventMap.put(Integer.valueOf(338), new String[]{"Takbira"});
		eventMap.put(Integer.valueOf(338), new String[]{"Urus Mawlai Feroz bin Ismail Ahmedabad"});
		eventMap.put(Integer.valueOf(340), new String[]{"Bu saheba, Amatullah Aai saheba Milad"});
		eventMap.put(Integer.valueOf(341), new String[]{"Urus Syedna Yusuf Najmuddin bin Sulaiman AQ [24th Dai] Taybah, Yemen","Urus Syedi Ishaq Bhaishaeb Jamaluddin AQ Mumbai"});
		priorityEventMap.put(Integer.valueOf(343), new String[]{"Eid e Gadhir e Khum Rozu"});
		eventMap.put(Integer.valueOf(352), new String[]{"Urus Syedna Abdul Husain Husamuddin bin Syedna Tayyib Zainuddin AQ [48th Dai] Ahmedabad","Urus Syedna Mohammed Burhanuddin bin Syedna AbdulQadir Najmuddin AQ [49th Dai] Surat"});
		eventMap.put(Integer.valueOf(354), new String[]{"Urus Ghanj Shuhada Ahmednagar"});
	}
}
