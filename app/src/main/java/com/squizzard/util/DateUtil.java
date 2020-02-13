package com.squizzard.util;

import androidx.annotation.NonNull;

import java.util.Map;
import java.util.TreeMap;

public class DateUtil {

	@NonNull
	public static String getGregorianDateString(int day, int month){
		return "" + day + getDaySuffix(day) + getGregorianMonth(month);
	}
	
	@NonNull
	public static String getGregorianDateString(int day, int month, int year){
		return getGregorianDateString(day, month) + year;
	}
	
	@NonNull
	public static String getMisriDateString(int day, int month){
		return "" + day + DateUtil.getDaySuffix(day) +  getMisriMonth(month);
	}

	@NonNull
	public static String getMisriDateString(int day, int month, int year) {
		return getMisriDateString(day, month) + year;
	}
	

	private static String getGregorianMonth(final int month) {
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
	
	public static final Map<Integer, String[]> eventMap= new TreeMap<>();
	public static final Map<Integer, String[]> priorityEventMap= new TreeMap<>();
	static{
		eventMap.put(1, new String[]{"Urus Mawlai Abdullah Saheb Khambat"});
		eventMap.put(2, new String[]{"Urus Syedi Shaikh Pir Jamaluddin Jamnagar","Urus Syedi Khanji Fir Saheb Udaipur","Urus Mawlai Raj Bin Mawlai Hasan Saheb Ahmedabad"});
		eventMap.put(6, new String[]{"Urus Syedi Mohammed Bin Qazikhan Kapadvanj"});
		eventMap.put(7, new String[]{"Urus Syedna Ismail Badruddin AQ [38th Dai] Jamnagar"});
		eventMap.put(10, new String[]{"Urus Syedna Zoeb Bin Musa AQ [1st Dai] Yemen","Urus Mawlai Ahmed Saheb Khambat"});
		priorityEventMap.put(10, new String[]{"Yawme Ashura"});
		eventMap.put(14, new String[]{"Urus Mawlai Lukmanji Mulla Alibhai Saheb Wankaner"});
		eventMap.put(15, new String[]{"Urus Mawlai Nuruddin Saheb Dongaon"});
		priorityEventMap.put(16, new String[]{"Urus Syedna Hatim bin Syedna Ibrahim AQ [3rd Dai] Yemen"});
		priorityEventMap.put(17, new String[]{"Urus Shahadat Imam Ali Zainulabedin SA Medina"});
		eventMap.put(17, new String[]{"Urus Syedna Ibrahim Vajihuddin AQ [39th Dai] Ujain","Urus Mulla Mohammedali bin Syedi Najam Khan Pune", "Urus Mawlai Masud bin Sulaiman Dhrangadhra","Urus Seven Shahid Sahebo Mujpur"});
		eventMap.put(18, new String[]{"Urus Shahid Gani Pir Ibne Dawoodji Kalavad"});
		eventMap.put(23, new String[]{"Urus Syedi Hasan Fir Saheb Shahid Denmal", "Urus Noor Bibi Umme Syedna Yusuf Najmuddin Dandigam", "Urus Fatema Bibi Ukhte Syedna Yusuf Najmuddin Dandigam"});
		eventMap.put(24, new String[]{"Urus Syedi Dada Sulemanji Bundi, Kota"});
		priorityEventMap.put(27, new String[]{"Urus Syedi Fakhruddin Shahid AQ Taherabad (Galiakot)"});
		eventMap.put(28, new String[]{"Urus Syedi Musaji bin Taj Baroda"});
		eventMap.put(29, new String[]{"Urus Mawlai Hasan Bin Mawlai Adam Ahmedabad"});
		eventMap.put(31, new String[]{"Urus Syedna Ali Bin Syedna Husain AQ [10th Dai] Yemen"});
		eventMap.put(33, new String[]{"Urus Syedna Ali Shamsuddin Bin Syedna Abdullah AQ [18th Dai] Yemen"});
		eventMap.put(34, new String[]{"Urus Syedna Abdul Tayyib Zakiyuddin AQ [41st Dai] Burhanpur"});
		eventMap.put(36, new String[]{"Urus Syedi Abdeali Imaduddin Surat"});
		eventMap.put(38, new String[]{"Urus Syedna Khattab Bin Hasan Hamdani AQ Yemen"});
		eventMap.put(39, new String[]{"Urus Syedi Tayyib bs Zainuddin Surat"});
		eventMap.put(42, new String[]{"Urus Syedna Ahmed Hamiduddin Kirmani RA Cairo"});
		eventMap.put(43, new String[]{"Urus Mawlai Adam bin Sulaimanji Ahmedabad"});
		eventMap.put(44, new String[]{"Urus Kaka Akela - Kaki Akeli Khambat", "Urus Mawlai Noorbhai Saheb Dhinoj"});
		eventMap.put(45, new String[]{"Urus Syedi Hamza Bhaisaheb Surat"});
		eventMap.put(47, new String[]{"Urus Shaikh AbdulHusain Shahid Chechat","Urus Mawlai Shaikh Saheb bin Sulaimanji","Urus Syedi Shaikh Ibrahim Chechat"});
		priorityEventMap.put(50, new String[]{"Chelum Imam Husain SA"});
		eventMap.put(52, new String[]{"Urus Syedna Husain bin Syedna Ali AQ [8th Dai] Yemen"});
		eventMap.put(57, new String[]{"Urus Syedna Mohammed Izzuddin AQ [23rd Dai] Yemen"});
		priorityEventMap.put(58, new String[]{"Shahadat Imam Hasan SA"});
		eventMap.put(59, new String[]{"Urus Syedi Hasan Zakiyuddin Surat"});
		eventMap.put(60, new String[]{"Urus Syedi Shaikh Adam Safiyuddin Jamnagar","Urus Syedi Jamaluddin bin Shaikh Adam Jamnagar"});
		eventMap.put(61, new String[]{"Urus Syedna Abdul Tayyib Zakiyuddin bin Syedna Dawood bin Qutbub  Shah AQ [29th Dai] Ahmedabad"});
		eventMap.put(63, new String[]{"Urus Syedi Habibullah bin Mulla Adamji Ujjain"});
		eventMap.put(66, new String[]{"Urus Syedi Shaikh Dawood Bhai Mulla Mehmoodji Udaipur","Urus Syedi Abdeali Mohyiddin Surat"});
		eventMap.put(69, new String[]{"Urus Syedna Abdullah Badruddin AQ [50th Dai] Surat"});
		priorityEventMap.put(71, new String[]{"Milad Eid Milad un Nabi"});
		eventMap.put(71, new String[]{"Urus Ummul Mumineen Amatullah Aaisaheba London"});
		eventMap.put(73, new String[]{"Urus Syedi Miaji Mulla Taj Saheb Amaryaath"});
		priorityEventMap.put(75, new String[]{"Urus Syedna Mohammad Burhanuddin RA"});//16th Rabi Ul Awwal Added
		eventMap.put(81, new String[]{"Urus Syedna Ali bin Hanzala AQ [6th Dai] Yemen","Urus Mawlai Dawood bin Raj Saheb Morbi"});
		eventMap.put(82, new String[]{"Urus Syedi Qazi Khan bin Ameen Shah Halwad","Urus Mawlai Raj Saheb Morbi"});
		eventMap.put(84, new String[]{"Urus Syedna Ali Shamsuddin bin Mawlai Hasan [30th Dai] Yemen"});
		eventMap.put(87, new String[]{"Urus Mohammad bin Hasan Saheb Dhinoj"});

		priorityEventMap.put(93, new String[]{"Milad Imam uz Zaman"});
		eventMap.put(94, new String[]{"Urus Mia Saheb Maati Bhai Mulla Noor Bhai Balasinor","Urus Mia Saheb Tayebji Shaikh Shams Khan Balasnwar"});
		eventMap.put(97, new String[]{"Urus Mawlai Raj bin Mulla Adam Saheb Jamnagar"});
		eventMap.put(99, new String[]{"Urus Syedi AbdulRasul Shahid Baanswara"});
		eventMap.put(103, new String[]{"Urus Syedi Ismailji Shahid bin Abde Musa Godhra"});
		eventMap.put(105, new String[]{"Urus Syedna Jalal Shamsuddin AQ bin Hasan [25th Dai] Ahmedabad"});
		priorityEventMap.put(109, new String[]{"Milad Dai Uz Zaman Syedna Mohammed Burhanuddin RA [52nd Dai]"});
		eventMap.put(111, new String[]{"Urus Syedna Musa Kalimuddin bin Syedna Abdul Tayyib Zakiyuddin AQ [36th Dai] Jamnagar","Urus Syedi Mulla Habibullah bin Shaikh Sultanali Bharuch"});
		eventMap.put(116, new String[]{"Urus Syedna Dawood Burhanuddin AQ bin Ajab Shah [26th Dai] Ahmedabad"});
		eventMap.put(117, new String[]{"Urus Kakaji Mulla Isa Bhai Partapghar"});

		eventMap.put(119, new String[]{"Urus Syedna Ahmed Al Mukaraam Yemen"});
		eventMap.put(121, new String[]{"Urus Syedi Qazi Khan bin Ali Sidhpur"});
		eventMap.put(126, new String[]{"Urus Syedi Mulla Wahid Bhaisaheb bin Mulla Ibrahimji Surat"});
		priorityEventMap.put(128, new String[]{"Wafat Maulatena Fatema tuz Zahra"});
		eventMap.put(129, new String[]{"Urus Mawlai Nooruddin Saheb Dongaon"});
		eventMap.put(133, new String[]{"Urus Mawlai Dawood bin Qazi Ahmed Dongaon"});
		eventMap.put(135, new String[]{"Urus Syedi Dawood Bhaisaheb Shihabuddin Surat"});
		eventMap.put(139, new String[]{"Urus Seth Chanda bhai ibne Karim Bhai Mumbai"});
		eventMap.put(141, new String[]{"Urus Mulla Jaferji Jiwaji Amreli"});
		eventMap.put(147, new String[]{"Urus Syedi Jivanji bin Shaikh Dawood Bhaisaheb Burhanpur"});

		eventMap.put(156, new String[]{"Urus Syedi Luqmaanji bin Mulla Habibullah Surat"});
		eventMap.put(160, new String[]{"Urus Mulla Tayyib Bawa bin Mulla Ibrahimji Ranala"});
		eventMap.put(162, new String[]{"Urus Ganje Shohoda Ahmednagar"});
		eventMap.put(163, new String[]{"Urus Syedna Dawood Burhanuddin AQ bin Qutub Shah [27th Dai] Ahmedabad","Urus Mawlai Ali bhai Shahid (Indore) Kamlapur"});
		eventMap.put(166, new String[]{"Urus Syedna Yusuf Najmuddin AQ [42nd Dai] Surat","Urus Moulai Burhanuddin Ibne Khoj Khan Pisawada","Urus Mawlai Adam ibne Dawood Jamnagar"});
		priorityEventMap.put(171, new String[]{"Urus Syedna Ismail Badruddin AQ bin Mawlai Raj [34th Dai] Jamnagar"});
		eventMap.put(174, new String[]{"Salgirah Syedi Mukasir Saheb Husain Bhaisaheb Husamuddin"});
		eventMap.put(175, new String[]{"Urus Syedna Lamak bin Malik RA Lahab (Yemen)"});
		priorityEventMap.put(175, new String[]{"Urus Syedna Qutub Khan Qutubuddin Shahid AQ [32nd Dai] Ahmedabad"});
		eventMap.put(176, new String[]{"Urus Syedna Ahmed bin Mubarak AQ (Hamdaan) [7th Dai] Yemen","Urus Syedna Yahya bin Syedna Lamak AQ Lahab (Yemen)"});
		eventMap.put(177, new String[]{"Urus Syedna Qadi Numan bin Mohammed AQ Misr","Urus Syedna Mohammed Badruddin AQ [46th Dai] Surat","Urus Ajab Busaheba Binte Syedna Qutubuddin Shahid AQ Ahmedabad"});
		priorityEventMap.put(177, new String[]{"Washeq Raat Pehli Raat Washeq"});

		priorityEventMap.put(178, new String[]{"Rozu"});
		eventMap.put(179, new String[]{"Urus Bhaiji Bhai Ibne Qazi Bhai Karachi","Urus Mawlai Raj bin Dawood Ahmedabad"});
		priorityEventMap.put(181, new String[]{"Urus Syedna Noor Mohammed Nooruddin AQ [37th Dai] Kutch Mandvi"});
		eventMap.put(181, new String[]{"Urus Syedi Hasanji Badshah Ujjain"});
		eventMap.put(184, new String[]{"Urus Syedna Shaikh Adam Safiyuddin AQ [28th Dai] Ahmedabad"});
		eventMap.put(185, new String[]{"Urus Syedi Saifuddin Saheb Jamnagar"});
		eventMap.put(189, new String[]{"Urus Syedi Najam Khan bin Syedna Fir Khan Shujahuddin AQ Aurangabad"});
		priorityEventMap.put(190, new String[]{"Ayyam ul Biz Rozu"});
		eventMap.put(190, new String[]{"Milad Amirul Mumineen, Maulana Ali (SA)"});
		priorityEventMap.put(191, new String[]{"Ayyam ul Biz Rozu"});
		eventMap.put(191, new String[]{"Urus Mawlai Yaqub Saheb Paatan"});
		priorityEventMap.put(192, new String[]{"Ayyam ul Biz & Salaat uz-Zawal Rozu"});
		eventMap.put(195, new String[]{"Urus Syedna Ali Shamsuddin AQ bin Syedna Ibrahim bin Husain AQ [13th Dai] Yemen Zamarmar"});
		priorityEventMap.put(196, new String[]{"Urus Syedna Taher Saifuddin AQ [51st Dai] Mumbai"});
		eventMap.put(201, new String[]{"Urus Syedna Abdul Mutalib Najmuddin AQ bin Syedna Mohammed bin Hatim AQ [14th Dai] Yemen Zamarmar","Urus Syedi Qamruddin Bhaisaheb bin Syedna Haibatullah Al Muaid AQ Ujjain"});
		priorityEventMap.put(203, new String[]{"Urus Syedna Abdul Qadr Najmuddin AQ [47th Dai] Ujjain","Laylat ul-Meraj Washeq Raat"});
		priorityEventMap.put(204, new String[]{"Yawm al-Mabath (Motius Sawalat) Rozu"});
		eventMap.put(204, new String[]{"Urus Syedi Miasaheb Alibhai bin Peeriji Radhanpur","Urus Aminji Shahid [Urus done on the 24th] Paddhari"});
		eventMap.put(206, new String[]{"Urus Syedi Luqmaanji bin Syedi Dawood bhai Udaipur"});

		eventMap.put(208, new String[]{"Urus Syedna Hebatullah Muayyad Fiddin AQ [40th Dai] Ujjain"});
		priorityEventMap.put(221, new String[]{"Washeq Raat Shabb-e-Baraat"});
		priorityEventMap.put(222, new String[]{"Urus Syedna Hasan Badruddin AQ [20th Dai] Yemen Masaar"});
		eventMap.put(223, new String[]{"Urus Syedna Ibrahim bin Husain AQ [2nd Dai] Yemen Hamdaan"});
		eventMap.put(226, new String[]{"Urus Syedi Saleh bhaisaheb Saifuddin Mumbai"});
		priorityEventMap.put(229, new String[]{"Urus Maulatina Hurratul Malika (RA) Sanaa, Yemen"});
		eventMap.put(229, new String[]{"Urus Syedi Shaikhfir bin Dawood Shahid Ranpur","Urus Syedi Shaikh Valibhai bin Shaikh Habibullah Urus on 2nd of Ramadan, recited on 22nd Shaban Paarda"});
		eventMap.put(232, new String[]{"Urus Syedi Shams Khan bin Syedi Yusufji Surat"});
		eventMap.put(236, new String[]{"Urus Syedna Ali bin Mawla Mohammed al-Walid AQ [5th Dai] Yemen","Urus Syedi Jiwanji bin Shaikh Dawood bhai Burhanpur"});

		eventMap.put(237, new String[]{"Urus Shaikh Dawood Bhaisaheb Dhinoj"});
		eventMap.put(238, new String[]{"Urus Syedi Wali Bhaisaheb bin Syedi Habibullah Pardha"});
		eventMap.put(240, new String[]{"Urus Syedi Tayyib Bhaisaheb Zainuddin AQ Surat"});
		eventMap.put(244, new String[]{"Urus Syedi Fazal Bhaisaheb Qutubuddin bin Syedna Abdullah AQ Surat"});
		eventMap.put(245, new String[]{"Urus Syedna Abdullah Fakhruddin AQ bin Ali [16th Dai] Yemen"});
		priorityEventMap.put(252, new String[]{"Washeq Raat"});
		eventMap.put(252, new String[]{"Urus Syedi Hebatullah Jamaluddin Jamnagar"});
		priorityEventMap.put(254, new String[]{"Washeq Raat"});
		priorityEventMap.put(255, new String[]{"Shahadat Amir ul Mumineen, Maulana Ali SA"});
		eventMap.put(255, new String[]{"Urus Syedna Mohammed Izziuddin AQ bin Syedi Jiwanji [44th Dai] Surat"});
		priorityEventMap.put(256, new String[]{"Washeq Raat"});
		priorityEventMap.put(257, new String[]{"Wafaat Amir ul Mumineen, Maulana Ali SA"});
		priorityEventMap.put(258, new String[]{"Laylatul Qadr Laylatul Qadr"});
		priorityEventMap.put(259, new String[]{"Milad Dai uz Zaman Syedna Muffadal Saifuddin (TUS)"});
		priorityEventMap.put(265, new String[]{"Washeq Raat"});
		priorityEventMap.put(266, new String[]{"Laylatul Eid ul Fitr Takbira, Washeq Raat"});

		priorityEventMap.put(267, new String[]{"Eid ul Fitr Takbira (Fajr, Zohar)"});
		eventMap.put(269, new String[]{"Urus Shehzadi Sakina Bhensaheba binte Syedna Taher Saifuddin AQ Mumbai"});
		eventMap.put(270, new String[]{"Urus Syedi Yusufji Ahmedabad","Urus Syedi Taiyebji Shahid Ahmedabad","Urus Syedi Abdul Qadir Hakimuddin [First Urus] Burhanpur"});
		eventMap.put(272, new String[]{"Urus Syedna Hasan Badruddin bin Syedba Abdullah Fakhruddin AQ [17th Dai] Yemen"});
		eventMap.put(273, new String[]{"Urus Syedna Mohammed bin Taher AQ [Writer of Dua Al Aql Al Awwal, Bawesani Dua] Yemen"});
		eventMap.put(274, new String[]{"Urus Syedna Abbas bin Syedna Mohammed bin Hatim AQ [15th Dai] Yemen"});
		eventMap.put(275, new String[]{"Urus Syedna Qasim Khan Zainuddin AQ [31st Dai] Ahmedabad"});
		eventMap.put(276, new String[]{"Urus Syedna Hebatullah Muayyadfiddin Shirazi AQ Cairo","Urus Syedna Husain Husamuddin AQ [21st Dai] Yemen","Urus Syedna Ibrahim bin Syedna Husain AQ [11th Dai] Yemen"});
		eventMap.put(279, new String[]{"Urus Syedi Aminji bin Jalal Ahmedabad"});
		eventMap.put(290, new String[]{"Urus Shaikh Qutub Bhai bin Sulaimanji Pune"});
		priorityEventMap.put(293, new String[]{"Urus Syedi Abdul Qadir Hakimuddin [Second Urus] Burhanpur"});
		eventMap.put(293, new String[]{"Urus Mia Saheb Abdeali Waliullah Jaawarah"});
		eventMap.put(295, new String[]{"Urus Mulla Salehbhai Ibne Najamkhan Ahmedabad","Urus Syedi Bawa Mulla Khan Saheb bin Syedi Habibullah Rampurah","Urus Syedi Qasim Khan bin Hamza Bhai Surat"});

		eventMap.put(304, new String[]{"Urus Syedna Fir Khan Shujahuddin AQ [33rd Dai] Ahmedabad"});
		eventMap.put(306, new String[]{"Urus Syedi Hasan bin Nuh Bharuji Yemen, Masaar","Urus Syedna Ali bin Mohammed Sulayhi RA Yemen"});
		eventMap.put(307, new String[]{"Urus Syedna Abdul Tayyib Zakiyuddin bin Syedna Ismail Badruddin AQ [35th Dai] Jamnagar"});
		priorityEventMap.put(307, new String[]{"Urus Syedna Abdeali Saifuddin AQ [43rd Dai] Surat"});
		eventMap.put(308, new String[]{"Urus Syedna Ali bin Syedna Husain [9th Dai] Yemen"});
		eventMap.put(310, new String[]{"Urus Syedna Tayyib Zainuddin bin Syedi Jewanji [45th Dai] Surat","Urus Bai Saheba Raani Baisaheba binte Syedna Ismail Badruddin AQ Mundra"});
		eventMap.put(314, new String[]{"Urus Syedna Idris Imaduddin AQ [19th Dai] Yemen, Shaam"});
		eventMap.put(316, new String[]{"Urus Syedna Ali Shamsuddin AQ [22nd Dai] Yemen"});
		eventMap.put(317, new String[]{"Urus Syedi Shaikh Sadiq Ali Saheb Surat"});
		eventMap.put(320, new String[]{"Urus Syedna Ali Shamsuddin bin Syedna Hatim AQ [4th Dai] Yemen"});
		eventMap.put(322, new String[]{"Urus Syedi Yusuf Khan bin Syedi Shams Khan Shajapur"});
		priorityEventMap.put(322, new String[]{"Milad Syedna Taher Saifuddin AQ [51st Dai]"});

		eventMap.put(326, new String[]{"Urus Syedna Mohammed AQ bin Hatim bin Husain in Ali [12th Dai] Yemen"});
		eventMap.put(331, new String[]{"Urus Syedi Khoj bin Malak Kaparwanj"});
		priorityEventMap.put(334, new String[]{"Yawm ul-Arafa Takbira and Washeq"});
		priorityEventMap.put(335, new String[]{"Eid al Adha Takbira"});
		priorityEventMap.put(336, new String[]{"Takbira"});
		priorityEventMap.put(337, new String[]{"Takbira"});
		priorityEventMap.put(338, new String[]{"Takbira"});
		eventMap.put(338, new String[]{"Urus Mawlai Feroz bin Ismail Ahmedabad"});
		eventMap.put(340, new String[]{"Bu saheba, Amatullah Aai saheba Milad"});
		eventMap.put(341, new String[]{"Urus Syedna Yusuf Najmuddin bin Sulaiman AQ [24th Dai] Taybah, Yemen","Urus Syedi Ishaq Bhaishaeb Jamaluddin AQ Mumbai"});
		priorityEventMap.put(343, new String[]{"Eid e Gadhir e Khum Rozu"});
		eventMap.put(352, new String[]{"Urus Syedna Abdul Husain Husamuddin bin Syedna Tayyib Zainuddin AQ [48th Dai] Ahmedabad","Urus Syedna Mohammed Burhanuddin bin Syedna AbdulQadir Najmuddin AQ [49th Dai] Surat"});
		eventMap.put(354, new String[]{"Urus Ghanj Shuhada Ahmednagar"});
	}
}
