package com.squizzard.util

import java.util.TreeMap

object DateUtil {
    @JvmStatic
	fun getGregorianDateString(day: Int, month: Int): String {
        return day.toString() + getDaySuffix(day) + getGregorianMonth(month)
    }

    @JvmStatic
	fun getGregorianDateString(day: Int, month: Int, year: Int): String {
        return getGregorianDateString(day, month) + year
    }

    @JvmStatic
	fun getMisriDateString(day: Int, month: Int): String {
        return day.toString() + getDaySuffix(day) + getMisriMonth(month)
    }

    @JvmStatic
	fun getMisriDateString(day: Int, month: Int, year: Int): String {
        return getMisriDateString(day, month) + year
    }


    private fun getGregorianMonth(month: Int): String {
        return when (month) {
            0 -> " January "
            1 -> " February "
            2 -> " March "
            3 -> " April "
            4 -> " May "
            5 -> " June "
            6 -> " July "
            7 -> " August "
            8 -> " September "
            9 -> " October "
            10 -> " November "
            11 -> " December "
            else -> ""
        }
    }

    @JvmStatic
	fun getDaySuffix(day: Int): String {
        if (day in 11..13) {
            return "th"
        }
        return when (day % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }

    @JvmStatic
	fun getMisriMonth(month: Int): String {
        return when (month - 1) {
            0 -> " Moharram-al-Haram "
            1 -> " Safar-al-Muzaffar "
            2 -> " Rabi-al-Awwal "
            3 -> " Rabi-al-Aakhar "
            4 -> " Jumada-al-Ulaa "
            5 -> " Jamada-al-Ukhra "
            6 -> " Rajab-al-Asab "
            7 -> " Shaban-al-Karim "
            8 -> " Ramadan-al-Moazzam "
            9 -> " Shawwal-al-Mukarram "
            10 -> " Zilqad-al-Haraam "
            11 -> " Zilhajj-al-Haraam "
            else -> ""
        }
    }

    @JvmField
	val eventMap: MutableMap<Int, Array<String>> = TreeMap()
    @JvmField
	val priorityEventMap: MutableMap<Int, Array<String>> = TreeMap()

    init {
        eventMap[1] = arrayOf("Urus Mawlai Abdullah Saheb Khambat")
        eventMap[2] = arrayOf(
            "Urus Syedi Shaikh Pir Jamaluddin Jamnagar",
            "Urus Syedi Khanji Fir Saheb Udaipur",
            "Urus Mawlai Raj Bin Mawlai Hasan Saheb Ahmedabad"
        )
        eventMap[6] =
            arrayOf("Urus Syedi Mohammed Bin Qazikhan Kapadvanj")
        eventMap[7] = arrayOf("Urus Syedna Ismail Badruddin AQ [38th Dai] Jamnagar")
        eventMap[10] = arrayOf(
            "Urus Syedna Zoeb Bin Musa AQ [1st Dai] Yemen",
            "Urus Mawlai Ahmed Saheb Khambat"
        )
        priorityEventMap[10] = arrayOf("Yawme Ashura")
        eventMap[14] =
            arrayOf("Urus Mawlai Lukmanji Mulla Alibhai Saheb Wankaner")
        eventMap[15] = arrayOf("Urus Mawlai Nuruddin Saheb Dongaon")
        priorityEventMap[16] = arrayOf("Urus Syedna Hatim bin Syedna Ibrahim AQ [3rd Dai] Yemen")
        priorityEventMap[17] = arrayOf("Urus Shahadat Imam Ali Zainulabedin SA Medina")
        eventMap[17] = arrayOf(
            "Urus Syedna Ibrahim Vajihuddin AQ [39th Dai] Ujain",
            "Urus Mulla Mohammedali bin Syedi Najam Khan Pune",
            "Urus Mawlai Masud bin Sulaiman Dhrangadhra",
            "Urus Seven Shahid Sahebo Mujpur"
        )
        eventMap[18] =
            arrayOf("Urus Shahid Gani Pir Ibne Dawoodji Kalavad")
        eventMap[23] = arrayOf(
            "Urus Syedi Hasan Fir Saheb Shahid Denmal",
            "Urus Noor Bibi Umme Syedna Yusuf Najmuddin Dandigam",
            "Urus Fatema Bibi Ukhte Syedna Yusuf Najmuddin Dandigam"
        )
        eventMap[24] = arrayOf("Urus Syedi Dada Sulemanji Bundi, Kota")
        priorityEventMap[27] =
            arrayOf("Urus Syedi Fakhruddin Shahid AQ Taherabad (Galiakot)")
        eventMap[28] = arrayOf("Urus Syedi Musaji bin Taj Baroda")
        eventMap[29] = arrayOf("Urus Mawlai Hasan Bin Mawlai Adam Ahmedabad")
        eventMap[31] =
            arrayOf("Urus Syedna Ali Bin Syedna Husain AQ [10th Dai] Yemen")
        eventMap[33] =
            arrayOf("Urus Syedna Ali Shamsuddin Bin Syedna Abdullah AQ [18th Dai] Yemen")
        eventMap[34] =
            arrayOf("Urus Syedna Abdul Tayyib Zakiyuddin AQ [41st Dai] Burhanpur")
        eventMap[36] = arrayOf("Urus Syedi Abdeali Imaduddin Surat")
        eventMap[38] = arrayOf("Urus Syedna Khattab Bin Hasan Hamdani AQ Yemen")
        eventMap[39] = arrayOf("Urus Syedi Tayyib bs Zainuddin Surat")
        eventMap[42] =
            arrayOf("Urus Syedna Ahmed Hamiduddin Kirmani RA Cairo")
        eventMap[43] =
            arrayOf("Urus Mawlai Adam bin Sulaimanji Ahmedabad")
        eventMap[44] = arrayOf(
            "Urus Kaka Akela - Kaki Akeli Khambat",
            "Urus Mawlai Noorbhai Saheb Dhinoj"
        )
        eventMap[45] = arrayOf("Urus Syedi Hamza Bhaisaheb Surat")
        eventMap[47] = arrayOf(
            "Urus Shaikh AbdulHusain Shahid Chechat",
            "Urus Mawlai Shaikh Saheb bin Sulaimanji",
            "Urus Syedi Shaikh Ibrahim Chechat"
        )
        priorityEventMap[50] = arrayOf("Chelum Imam Husain SA")
        eventMap[52] =
            arrayOf("Urus Syedna Husain bin Syedna Ali AQ [8th Dai] Yemen")
        eventMap[57] = arrayOf("Urus Syedna Mohammed Izzuddin AQ [23rd Dai] Yemen")
        priorityEventMap[58] = arrayOf("Shahadat Imam Hasan SA")
        eventMap[59] = arrayOf("Urus Syedi Hasan Zakiyuddin Surat")
        eventMap[60] = arrayOf(
            "Urus Syedi Shaikh Adam Safiyuddin Jamnagar",
            "Urus Syedi Jamaluddin bin Shaikh Adam Jamnagar"
        )
        eventMap[61] =
            arrayOf("Urus Syedna Abdul Tayyib Zakiyuddin bin Syedna Dawood bin Qutbub  Shah AQ [29th Dai] Ahmedabad")
        eventMap[63] =
            arrayOf("Urus Syedi Habibullah bin Mulla Adamji Ujjain")
        eventMap[66] = arrayOf(
            "Urus Syedi Shaikh Dawood Bhai Mulla Mehmoodji Udaipur",
            "Urus Syedi Abdeali Mohyiddin Surat"
        )
        eventMap[69] =
            arrayOf("Urus Syedna Abdullah Badruddin AQ [50th Dai] Surat")
        priorityEventMap[71] = arrayOf("Milad Eid Milad un Nabi")
        eventMap[71] = arrayOf("Urus Ummul Mumineen Amatullah Aaisaheba London")
        eventMap[73] =
            arrayOf("Urus Syedi Miaji Mulla Taj Saheb Amaryaath")
        priorityEventMap[75] =
            arrayOf("Urus Syedna Mohammad Burhanuddin RA") //16th Rabi Ul Awwal Added
        eventMap[81] = arrayOf(
            "Urus Syedna Ali bin Hanzala AQ [6th Dai] Yemen",
            "Urus Mawlai Dawood bin Raj Saheb Morbi"
        )
        eventMap[82] = arrayOf(
            "Urus Syedi Qazi Khan bin Ameen Shah Halwad",
            "Urus Mawlai Raj Saheb Morbi"
        )
        eventMap[84] =
            arrayOf("Urus Syedna Ali Shamsuddin bin Mawlai Hasan [30th Dai] Yemen")
        eventMap[87] = arrayOf("Urus Mohammad bin Hasan Saheb Dhinoj")

        priorityEventMap[93] = arrayOf("Milad Imam uz Zaman")
        eventMap[94] = arrayOf(
            "Urus Mia Saheb Maati Bhai Mulla Noor Bhai Balasinor",
            "Urus Mia Saheb Tayebji Shaikh Shams Khan Balasnwar"
        )
        eventMap[97] =
            arrayOf("Urus Mawlai Raj bin Mulla Adam Saheb Jamnagar")
        eventMap[99] = arrayOf("Urus Syedi AbdulRasul Shahid Baanswara")
        eventMap[103] =
            arrayOf("Urus Syedi Ismailji Shahid bin Abde Musa Godhra")
        eventMap[105] =
            arrayOf("Urus Syedna Jalal Shamsuddin AQ bin Hasan [25th Dai] Ahmedabad")
        priorityEventMap[109] =
            arrayOf("Milad Dai Uz Zaman Syedna Mohammed Burhanuddin RA [52nd Dai]")
        eventMap[111] = arrayOf(
            "Urus Syedna Musa Kalimuddin bin Syedna Abdul Tayyib Zakiyuddin AQ [36th Dai] Jamnagar",
            "Urus Syedi Mulla Habibullah bin Shaikh Sultanali Bharuch"
        )
        eventMap[116] =
            arrayOf("Urus Syedna Dawood Burhanuddin AQ bin Ajab Shah [26th Dai] Ahmedabad")
        eventMap[117] = arrayOf("Urus Kakaji Mulla Isa Bhai Partapghar")

        eventMap[119] = arrayOf("Urus Syedna Ahmed Al Mukaraam Yemen")
        eventMap[121] = arrayOf("Urus Syedi Qazi Khan bin Ali Sidhpur")
        eventMap[126] =
            arrayOf("Urus Syedi Mulla Wahid Bhaisaheb bin Mulla Ibrahimji Surat")
        priorityEventMap[128] = arrayOf("Wafat Maulatena Fatema tuz Zahra")
        eventMap[129] = arrayOf("Urus Mawlai Nooruddin Saheb Dongaon")
        eventMap[133] = arrayOf("Urus Mawlai Dawood bin Qazi Ahmed Dongaon")
        eventMap[135] = arrayOf("Urus Syedi Dawood Bhaisaheb Shihabuddin Surat")
        eventMap[139] =
            arrayOf("Urus Seth Chanda bhai ibne Karim Bhai Mumbai")
        eventMap[141] = arrayOf("Urus Mulla Jaferji Jiwaji Amreli")
        eventMap[147] = arrayOf("Urus Syedi Jivanji bin Shaikh Dawood Bhaisaheb Burhanpur")

        eventMap[156] =
            arrayOf("Urus Syedi Luqmaanji bin Mulla Habibullah Surat")
        eventMap[160] =
            arrayOf("Urus Mulla Tayyib Bawa bin Mulla Ibrahimji Ranala")
        eventMap[162] = arrayOf("Urus Ganje Shohoda Ahmednagar")
        eventMap[163] = arrayOf(
            "Urus Syedna Dawood Burhanuddin AQ bin Qutub Shah [27th Dai] Ahmedabad",
            "Urus Mawlai Ali bhai Shahid (Indore) Kamlapur"
        )
        eventMap[166] = arrayOf(
            "Urus Syedna Yusuf Najmuddin AQ [42nd Dai] Surat",
            "Urus Moulai Burhanuddin Ibne Khoj Khan Pisawada",
            "Urus Mawlai Adam ibne Dawood Jamnagar"
        )
        priorityEventMap[171] =
            arrayOf("Urus Syedna Ismail Badruddin AQ bin Mawlai Raj [34th Dai] Jamnagar")
        eventMap[174] =
            arrayOf("Salgirah Syedi Mukasir Saheb Husain Bhaisaheb Husamuddin")
        eventMap[175] =
            arrayOf("Urus Syedna Lamak bin Malik RA Lahab (Yemen)")
        priorityEventMap[175] =
            arrayOf("Urus Syedna Qutub Khan Qutubuddin Shahid AQ [32nd Dai] Ahmedabad")
        eventMap[176] = arrayOf(
            "Urus Syedna Ahmed bin Mubarak AQ (Hamdaan) [7th Dai] Yemen",
            "Urus Syedna Yahya bin Syedna Lamak AQ Lahab (Yemen)"
        )
        eventMap[177] = arrayOf(
            "Urus Syedna Qadi Numan bin Mohammed AQ Misr",
            "Urus Syedna Mohammed Badruddin AQ [46th Dai] Surat",
            "Urus Ajab Busaheba Binte Syedna Qutubuddin Shahid AQ Ahmedabad"
        )
        priorityEventMap[177] = arrayOf("Washeq Raat Pehli Raat Washeq")

        priorityEventMap[178] = arrayOf("Rozu")
        eventMap[179] = arrayOf(
            "Urus Bhaiji Bhai Ibne Qazi Bhai Karachi",
            "Urus Mawlai Raj bin Dawood Ahmedabad"
        )
        priorityEventMap[181] =
            arrayOf("Urus Syedna Noor Mohammed Nooruddin AQ [37th Dai] Kutch Mandvi")
        eventMap[181] = arrayOf("Urus Syedi Hasanji Badshah Ujjain")
        eventMap[184] =
            arrayOf("Urus Syedna Shaikh Adam Safiyuddin AQ [28th Dai] Ahmedabad")
        eventMap[185] = arrayOf("Urus Syedi Saifuddin Saheb Jamnagar")
        eventMap[189] =
            arrayOf("Urus Syedi Najam Khan bin Syedna Fir Khan Shujahuddin AQ Aurangabad")
        priorityEventMap[190] = arrayOf("Ayyam ul Biz Rozu")
        eventMap[190] = arrayOf("Milad Amirul Mumineen, Maulana Ali (SA)")
        priorityEventMap[191] = arrayOf("Ayyam ul Biz Rozu")
        eventMap[191] = arrayOf("Urus Mawlai Yaqub Saheb Paatan")
        priorityEventMap[192] =
            arrayOf("Ayyam ul Biz & Salaat uz-Zawal Rozu")
        eventMap[195] =
            arrayOf("Urus Syedna Ali Shamsuddin AQ bin Syedna Ibrahim bin Husain AQ [13th Dai] Yemen Zamarmar")
        priorityEventMap[196] =
            arrayOf("Urus Syedna Taher Saifuddin AQ [51st Dai] Mumbai")
        eventMap[201] = arrayOf(
            "Urus Syedna Abdul Mutalib Najmuddin AQ bin Syedna Mohammed bin Hatim AQ [14th Dai] Yemen Zamarmar",
            "Urus Syedi Qamruddin Bhaisaheb bin Syedna Haibatullah Al Muaid AQ Ujjain"
        )
        priorityEventMap[203] = arrayOf(
            "Urus Syedna Abdul Qadr Najmuddin AQ [47th Dai] Ujjain",
            "Laylat ul-Meraj Washeq Raat"
        )
        priorityEventMap[204] =
            arrayOf("Yawm al-Mabath (Motius Sawalat) Rozu")
        eventMap[204] = arrayOf(
            "Urus Syedi Miasaheb Alibhai bin Peeriji Radhanpur",
            "Urus Aminji Shahid [Urus done on the 24th] Paddhari"
        )
        eventMap[206] = arrayOf("Urus Syedi Luqmaanji bin Syedi Dawood bhai Udaipur")

        eventMap[208] =
            arrayOf("Urus Syedna Hebatullah Muayyad Fiddin AQ [40th Dai] Ujjain")
        priorityEventMap[221] = arrayOf("Washeq Raat Shabb-e-Baraat")
        priorityEventMap[222] = arrayOf("Urus Syedna Hasan Badruddin AQ [20th Dai] Yemen Masaar")
        eventMap[223] =
            arrayOf("Urus Syedna Ibrahim bin Husain AQ [2nd Dai] Yemen Hamdaan")
        eventMap[226] =
            arrayOf("Urus Syedi Saleh bhaisaheb Saifuddin Mumbai")
        priorityEventMap[229] =
            arrayOf("Urus Maulatina Hurratul Malika (RA) Sanaa, Yemen")
        eventMap[229] = arrayOf(
            "Urus Syedi Shaikhfir bin Dawood Shahid Ranpur",
            "Urus Syedi Shaikh Valibhai bin Shaikh Habibullah Urus on 2nd of Ramadan, recited on 22nd Shaban Paarda"
        )
        eventMap[232] = arrayOf("Urus Syedi Shams Khan bin Syedi Yusufji Surat")
        eventMap[236] = arrayOf(
            "Urus Syedna Ali bin Mawla Mohammed al-Walid AQ [5th Dai] Yemen",
            "Urus Syedi Jiwanji bin Shaikh Dawood bhai Burhanpur"
        )

        eventMap[237] = arrayOf("Urus Shaikh Dawood Bhaisaheb Dhinoj")
        eventMap[238] = arrayOf("Urus Syedi Wali Bhaisaheb bin Syedi Habibullah Pardha")
        eventMap[240] = arrayOf("Urus Syedi Tayyib Bhaisaheb Zainuddin AQ Surat")
        eventMap[244] =
            arrayOf("Urus Syedi Fazal Bhaisaheb Qutubuddin bin Syedna Abdullah AQ Surat")
        eventMap[245] =
            arrayOf("Urus Syedna Abdullah Fakhruddin AQ bin Ali [16th Dai] Yemen")
        priorityEventMap[252] = arrayOf("Washeq Raat")
        eventMap[252] =
            arrayOf("Urus Syedi Hebatullah Jamaluddin Jamnagar")
        priorityEventMap[254] = arrayOf("Washeq Raat")
        priorityEventMap[255] =
            arrayOf("Shahadat Amir ul Mumineen, Maulana Ali SA")
        eventMap[255] =
            arrayOf("Urus Syedna Mohammed Izziuddin AQ bin Syedi Jiwanji [44th Dai] Surat")
        priorityEventMap[256] = arrayOf("Washeq Raat")
        priorityEventMap[257] =
            arrayOf("Wafaat Amir ul Mumineen, Maulana Ali SA")
        priorityEventMap[258] = arrayOf("Laylatul Qadr Laylatul Qadr")
        priorityEventMap[259] =
            arrayOf("Milad Dai uz Zaman Syedna Muffadal Saifuddin (TUS)")
        priorityEventMap[265] = arrayOf("Washeq Raat")
        priorityEventMap[266] = arrayOf("Laylatul Eid ul Fitr Takbira, Washeq Raat")

        priorityEventMap[267] = arrayOf("Eid ul Fitr Takbira (Fajr, Zohar)")
        eventMap[269] =
            arrayOf("Urus Shehzadi Sakina Bhensaheba binte Syedna Taher Saifuddin AQ Mumbai")
        eventMap[270] = arrayOf(
            "Urus Syedi Yusufji Ahmedabad",
            "Urus Syedi Taiyebji Shahid Ahmedabad",
            "Urus Syedi Abdul Qadir Hakimuddin [First Urus] Burhanpur"
        )
        eventMap[272] =
            arrayOf("Urus Syedna Hasan Badruddin bin Syedba Abdullah Fakhruddin AQ [17th Dai] Yemen")
        eventMap[273] =
            arrayOf("Urus Syedna Mohammed bin Taher AQ [Writer of Dua Al Aql Al Awwal, Bawesani Dua] Yemen")
        eventMap[274] =
            arrayOf("Urus Syedna Abbas bin Syedna Mohammed bin Hatim AQ [15th Dai] Yemen")
        eventMap[275] = arrayOf("Urus Syedna Qasim Khan Zainuddin AQ [31st Dai] Ahmedabad")
        eventMap[276] = arrayOf(
            "Urus Syedna Hebatullah Muayyadfiddin Shirazi AQ Cairo",
            "Urus Syedna Husain Husamuddin AQ [21st Dai] Yemen",
            "Urus Syedna Ibrahim bin Syedna Husain AQ [11th Dai] Yemen"
        )
        eventMap[279] = arrayOf("Urus Syedi Aminji bin Jalal Ahmedabad")
        eventMap[290] =
            arrayOf("Urus Shaikh Qutub Bhai bin Sulaimanji Pune")
        priorityEventMap[293] =
            arrayOf("Urus Syedi Abdul Qadir Hakimuddin [Second Urus] Burhanpur")
        eventMap[293] =
            arrayOf("Urus Mia Saheb Abdeali Waliullah Jaawarah")
        eventMap[295] = arrayOf(
            "Urus Mulla Salehbhai Ibne Najamkhan Ahmedabad",
            "Urus Syedi Bawa Mulla Khan Saheb bin Syedi Habibullah Rampurah",
            "Urus Syedi Qasim Khan bin Hamza Bhai Surat"
        )

        eventMap[304] =
            arrayOf("Urus Syedna Fir Khan Shujahuddin AQ [33rd Dai] Ahmedabad")
        eventMap[306] = arrayOf(
            "Urus Syedi Hasan bin Nuh Bharuji Yemen, Masaar",
            "Urus Syedna Ali bin Mohammed Sulayhi RA Yemen"
        )
        eventMap[307] =
            arrayOf("Urus Syedna Abdul Tayyib Zakiyuddin bin Syedna Ismail Badruddin AQ [35th Dai] Jamnagar")
        priorityEventMap[307] =
            arrayOf("Urus Syedna Abdeali Saifuddin AQ [43rd Dai] Surat")
        eventMap[308] = arrayOf("Urus Syedna Ali bin Syedna Husain [9th Dai] Yemen")
        eventMap[310] = arrayOf(
            "Urus Syedna Tayyib Zainuddin bin Syedi Jewanji [45th Dai] Surat",
            "Urus Bai Saheba Raani Baisaheba binte Syedna Ismail Badruddin AQ Mundra"
        )
        eventMap[314] =
            arrayOf("Urus Syedna Idris Imaduddin AQ [19th Dai] Yemen, Shaam")
        eventMap[316] = arrayOf("Urus Syedna Ali Shamsuddin AQ [22nd Dai] Yemen")
        eventMap[317] =
            arrayOf("Urus Syedi Shaikh Sadiq Ali Saheb Surat")
        eventMap[320] =
            arrayOf("Urus Syedna Ali Shamsuddin bin Syedna Hatim AQ [4th Dai] Yemen")
        eventMap[322] = arrayOf("Urus Syedi Yusuf Khan bin Syedi Shams Khan Shajapur")
        priorityEventMap[322] =
            arrayOf("Milad Syedna Taher Saifuddin AQ [51st Dai]")

        eventMap[326] =
            arrayOf("Urus Syedna Mohammed AQ bin Hatim bin Husain in Ali [12th Dai] Yemen")
        eventMap[331] = arrayOf("Urus Syedi Khoj bin Malak Kaparwanj")
        priorityEventMap[334] = arrayOf("Yawm ul-Arafa Takbira and Washeq")
        priorityEventMap[335] = arrayOf("Eid al Adha Takbira")
        priorityEventMap[336] = arrayOf("Takbira")
        priorityEventMap[337] = arrayOf("Takbira")
        priorityEventMap[338] = arrayOf("Takbira")
        eventMap[338] = arrayOf("Urus Mawlai Feroz bin Ismail Ahmedabad")
        eventMap[340] = arrayOf("Bu saheba, Amatullah Aai saheba Milad")
        eventMap[341] = arrayOf(
            "Urus Syedna Yusuf Najmuddin bin Sulaiman AQ [24th Dai] Taybah, Yemen",
            "Urus Syedi Ishaq Bhaishaeb Jamaluddin AQ Mumbai"
        )
        priorityEventMap[343] = arrayOf("Eid e Gadhir e Khum Rozu")
        eventMap[352] = arrayOf(
            "Urus Syedna Abdul Husain Husamuddin bin Syedna Tayyib Zainuddin AQ [48th Dai] Ahmedabad",
            "Urus Syedna Mohammed Burhanuddin bin Syedna AbdulQadir Najmuddin AQ [49th Dai] Surat"
        )
        eventMap[354] = arrayOf("Urus Ghanj Shuhada Ahmednagar")
    }
}
