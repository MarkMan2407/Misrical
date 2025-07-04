package com.squizzard.converter.model

import com.squizzard.util.DateUtil
import com.squizzard.util.DateUtil.getGregorianDateString
import com.squizzard.util.DateUtil.getMisriDateString
import com.squizzard.util.DateUtil.getMisriMonth
import java.util.Calendar

class Misri {
    var misriOrdinal: Int = 0
        private set
    var todayEvent: String? = null
        private set

    var todayMisriDay: Int = 0
    var todayMisriMonth: String? = null
    var todayMisriMonthCode: Int = 0
    var todayMisriYear: Int = 0
    var convertedGregorianDay: Int = 0
    var convertedGregorianYear: Int = 0
    var convertedGregorianMonth: Int = 0

    init {
        todayMisri
    }

    val todayMisri: String
        get() { //called by the widget
            val c = Calendar.getInstance()
            val dateArray = getMisriDate(
                c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(
                    Calendar.YEAR
                )
            )
            return getMisriDateString(dateArray[0], dateArray[1], dateArray[2])
        }

    val todayGregorian: String
        get() { //called by the widget
            val c = Calendar.getInstance()
            return getGregorianDateString(
                c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(
                    Calendar.YEAR
                )
            )
        }

    fun getGregorianDate(misriD: Int, misriM: Int, misriY: Int): IntArray {
        // find the number smaller than the year in misri_cycle_30
        var diff = 0
        var ord30 = 0
        for (j in misri_cycle_30.indices) {
            if (misri_cycle_30[j][0] >= misriY) {
                ord30 = misri_cycle_30[j - 1][1]
                diff = misriY - misri_cycle_30[j - 1][0]
                break
            }
        }
        val gregorianOrdinal = misri_month[misriM] + ord30 + misriD + misri_year[diff - 1][1]

        //Convert gregorianOrdinal to a readable date
        var gregorianCentury = 0
        for (j in gregorian_century.indices) {
            if (gregorian_century[j][1] >= gregorianOrdinal) {
                gregorianCentury = gregorian_century[j - 1][0]
                diff = gregorianOrdinal - gregorian_century[j - 1][1]
                break
            }
        }

        if ((gregorianCentury == 0) && (gregorianOrdinal > 511337)) {
            gregorianCentury = gregorian_century[gregorian_century.size - 1][0]
            diff = gregorianOrdinal - gregorian_century[gregorian_century.size - 1][1]
        }

        var gregorianYear = 0
        var spareYears = 0
        for (j in gregorian_decade.indices) {
            if (gregorian_decade[j][1] >= diff) {
                if (j == 0) {
                    gregorianYear = gregorianCentury
                    spareYears = diff
                    break
                }
                gregorianYear = gregorian_decade[j - 1][0] + gregorianCentury
                spareYears = diff - gregorian_decade[j - 1][1]
                break
            }
        }
        if ((spareYears == 0) && (diff > 35064)) {
            gregorianYear = 96 + gregorianCentury
            spareYears = diff - 35064
        }
        //look for spareYears in gregorianMonth
        var gMonth = -1
        var gDay = -1
        for (j in gregorian_month.indices) {
            if (gregorian_month[j][1] >= spareYears) {
                if (j == 0) { //if spareYears=0 then dec31
                    gMonth = gregorian_month[11][0] //dec
                    gDay = 31
                    gregorianYear -= 1 //go to the previous year
                    break
                }
                gMonth = gregorian_month[j - 1][0]
                gDay = spareYears - gregorian_month[j - 1][1] //this could generate the 0th
                break //breaks out of the for loop
            }
        }
        if (gMonth == -1) { //implying a greater number hasn't been found in the first column
            for (j in gregorian_month.indices) {
                if (gregorian_month[j][2] >= spareYears) {
                    if (j == 0) { //spareYrs=366
                        gMonth = gregorian_month[11][0] //dec
                        gDay = spareYears - gregorian_month[11][1]
                        break
                    }
                    gMonth = gregorian_month[j - 1][0]
                    gDay = spareYears - gregorian_month[j - 1][2]
                    gregorianYear += 1
                    break //breaks out of the for loop
                }
            }
        }
        if (gMonth == -1) { //implying a greater number hasn't been found in the first or second column
            for (j in gregorian_month.indices) {
                if (gregorian_month[j][3] >= spareYears) {
                    if (j == 0) {
                        gMonth = gregorian_month[11][0]
                        gDay = spareYears - gregorian_month[11][2]
                        gregorianYear += 1
                        break
                    }
                    gMonth = gregorian_month[j - 1][0]
                    gDay = spareYears - gregorian_month[j - 1][3]
                    gregorianYear += 2
                    break //breaks out of the for loop
                }
            }
        }
        if (gMonth == -1) { //implying a greater number hasn't been found in the first or second column
            for (j in gregorian_month.indices) {
                if (gregorian_month[j][4] >= spareYears) {
                    if (j == 0) {
                        gMonth = gregorian_month[11][0]
                        gDay = spareYears - gregorian_month[11][3]
                        gregorianYear += 2
                        break
                    }
                    gMonth = gregorian_month[j - 1][0]
                    gDay = spareYears - gregorian_month[j - 1][4]
                    gregorianYear += 3
                    break //breaks out of the for loop
                }
            }
        }
        if (gMonth == -1 && spareYears <= 1461) {
            gMonth = 12 //Accounting for Table 2 Stage 3 1430->1461
            gDay = spareYears - 1430
            gregorianYear += 3
        }
        val gregorianArray = IntArray(3)
        gregorianArray[0] = gDay
        gregorianArray[1] = gMonth - 1
        gregorianArray[2] = gregorianYear
        convertedGregorianDay = gDay
        convertedGregorianMonth = gMonth - 1
        convertedGregorianYear = gregorianYear

        return gregorianArray
    }

    fun getMisriDate(d: Int, m: Int, y: Int): IntArray {
        var month = -1
        var misriYear = -1
        var monthDay = -1
        var cycle30Diff = -1
        var yearDiff = -1
        val returnArray = IntArray(3)

        val gregorianOrdinal = getGregorianAsOrdinal(y, m, d)
        for (j in misri_cycle_30.indices) {
            if (misri_cycle_30[j][1] >= gregorianOrdinal) {
                try {
                    misriYear = misri_cycle_30[j - 1][0]
                } catch (e: ArrayIndexOutOfBoundsException) {
                    //return "Out of calendar range!";
                }
                cycle30Diff = gregorianOrdinal - misri_cycle_30[j - 1][1]
                break
            }
        }
        for (j in misri_year.indices) {
            if (misri_year[j][1] >= cycle30Diff) {
                try {
                    misriYear += misri_year[j - 1][0]
                    yearDiff = cycle30Diff - misri_year[j - 1][1]
                } catch (e: ArrayIndexOutOfBoundsException) {
                    //return "Out of calendar range!";
                }
                break
            }
        }
        if ((yearDiff == -1) && (cycle30Diff > 10277)) { //V1.01
            misriYear += misri_year[29][0]
            yearDiff = cycle30Diff - misri_year[29][1]
        }
        //using yearDiff find the exact date in the year from misri_month
        for (j in misri_month.indices) {
            if (misri_month[j] >= yearDiff) {
                month = j
                monthDay = yearDiff - misri_month[j - 1]
                break
            }
        }

        if ((month == -1) && (yearDiff > 325) && (yearDiff <= 355)) { //V1.01 - changed from 354 to 355
            month = 12
            monthDay = yearDiff - misri_month[11]
        }


        todayMisriMonth = getMisriMonth(month)
        todayMisriDay = monthDay
        todayMisriMonthCode = month
        todayMisriYear = misriYear
        setEvent(month, monthDay)
        returnArray[0] = monthDay
        returnArray[1] = month
        returnArray[2] = misriYear
        return returnArray
    }

    private fun getGregorianAsOrdinal(year: Int, month: Int, day: Int): Int {
        var ordinal = 0
        val century = year - (year % 100)
        val decade = year % 100
        var spareYears = 0
        //search the gregorian_century array for the correct entry and return the ordinal value
        for (ints in gregorian_century) if (ints[0] == century) {
            ordinal = ints[1]
            break
        }
        //search through gregorian_decade and find the first entry greater than decade
        for (j in gregorian_decade.indices) {
            if (gregorian_decade[j][0] > decade) {
                if (j != 0) {
                    ordinal += gregorian_decade[j - 1][1]
                    spareYears = decade - gregorian_decade[j - 1][0]
                    break
                } else { //no need to add to ordinal
                    spareYears = decade
                    break
                }
            }
        }
        //if decade >=96 and <100 then the value will not have been found in gregorian_decade
        if (decade >= 96) { //Adding this fixed error with years 1996-1999
            ordinal += gregorian_decade[gregorian_decade.size - 1][1] //35064
            spareYears = decade - 96
        }


        //now index into the table for month data
        val spareMonth = gregorian_month[month][spareYears + 1]
        ordinal += spareMonth + day

        return ordinal
    }

    fun setEvent(monthOfYear: Int, dayOfMonth: Int) {
        misriOrdinal = dayOfMonth + misri_month[monthOfYear - 1]
        val eventString = StringBuilder()
        val allEventsString: Array<String>
        if (DateUtil.priorityEventMap.containsKey(misriOrdinal)) {
            allEventsString = DateUtil.priorityEventMap[misriOrdinal]!!

            for (s in allEventsString) {
                eventString.append(s)
                if (allEventsString.size > 1) {
                    eventString.append(", ")
                }
            }
        }
        todayEvent = eventString.toString()
    }

    companion object {
        private val gregorian_century = arrayOf(
            intArrayOf(600, 0),
            intArrayOf(700, 36525),
            intArrayOf(800, 73050),
            intArrayOf(900, 109575),
            intArrayOf(1000, 146100),
            intArrayOf(1100, 182625),
            intArrayOf(1200, 219150),
            intArrayOf(1300, 255675),
            intArrayOf(1400, 292200),
            intArrayOf(1500, 328725),
            intArrayOf(1582, 358665),
            intArrayOf(1600, 365240),
            intArrayOf(1700, 401764),
            intArrayOf(1800, 438288),
            intArrayOf(1900, 474812),
            intArrayOf(2000, 511337)
        )

        private val gregorian_decade = arrayOf(
            intArrayOf(4, 1461),
            intArrayOf(8, 2922),
            intArrayOf(12, 4383),
            intArrayOf(16, 5844),
            intArrayOf(20, 7305),
            intArrayOf(24, 8766),
            intArrayOf(28, 10227),
            intArrayOf(32, 11688),
            intArrayOf(36, 13149),
            intArrayOf(40, 14610),
            intArrayOf(44, 16071),
            intArrayOf(48, 17532),
            intArrayOf(52, 18993),
            intArrayOf(56, 20454),
            intArrayOf(60, 21915),
            intArrayOf(64, 23376),
            intArrayOf(68, 24837),
            intArrayOf(72, 26298),
            intArrayOf(76, 27759),
            intArrayOf(80, 29220),
            intArrayOf(84, 30681),
            intArrayOf(88, 32142),
            intArrayOf(92, 33603),
            intArrayOf(96, 35064)
        )

        private val gregorian_month = arrayOf(
            intArrayOf(1, 0, 366, 731, 1096),
            intArrayOf(2, 31, 397, 762, 1127),
            intArrayOf(3, 60, 425, 790, 1155),
            intArrayOf(4, 91, 456, 821, 1186),
            intArrayOf(5, 121, 486, 851, 1216),
            intArrayOf(6, 152, 517, 882, 1247),
            intArrayOf(7, 182, 547, 912, 1277),
            intArrayOf(8, 213, 578, 943, 1308),
            intArrayOf(9, 244, 609, 974, 1339),
            intArrayOf(10, 274, 639, 1004, 1369),
            intArrayOf(11, 305, 670, 1035, 1400),
            intArrayOf(12, 335, 700, 1065, 1430)
        )

        private val misri_cycle_30 = arrayOf(
            intArrayOf(0, 8231),
            intArrayOf(30, 18862),
            intArrayOf(60, 29493),
            intArrayOf(90, 40124),
            intArrayOf(120, 50755),
            intArrayOf(150, 61386),
            intArrayOf(180, 72017),
            intArrayOf(210, 82648),
            intArrayOf(240, 93279),
            intArrayOf(270, 103910),
            intArrayOf(300, 114541),
            intArrayOf(330, 125172),
            intArrayOf(360, 135803),
            intArrayOf(390, 146434),
            intArrayOf(420, 157065),
            intArrayOf(450, 167696),
            intArrayOf(480, 178327),
            intArrayOf(510, 188958),
            intArrayOf(540, 199589),
            intArrayOf(570, 210220),
            intArrayOf(600, 220851),
            intArrayOf(630, 231482),
            intArrayOf(660, 242113),
            intArrayOf(690, 252744),
            intArrayOf(720, 263375),
            intArrayOf(750, 274006),
            intArrayOf(780, 284637),
            intArrayOf(810, 295268),
            intArrayOf(840, 305899),
            intArrayOf(870, 316530),
            intArrayOf(900, 327161),
            intArrayOf(930, 337792),
            intArrayOf(960, 348423),
            intArrayOf(990, 359054),
            intArrayOf(1020, 369685),
            intArrayOf(1050, 380316),
            intArrayOf(1080, 390947),
            intArrayOf(1110, 401578),
            intArrayOf(1140, 412209),
            intArrayOf(1170, 422840),
            intArrayOf(1200, 433471),
            intArrayOf(1230, 444102),
            intArrayOf(1260, 454733),
            intArrayOf(1290, 465364),
            intArrayOf(1320, 475995),
            intArrayOf(1350, 486626),
            intArrayOf(1380, 497257),
            intArrayOf(1410, 507888),
            intArrayOf(1440, 518519),
            intArrayOf(1470, 529150),
            intArrayOf(1500, 539781)
        )

        private val misri_year = arrayOf(
            intArrayOf(1, 0),
            intArrayOf(2, 354),
            intArrayOf(3, 709),
            intArrayOf(4, 1063),
            intArrayOf(5, 1417),
            intArrayOf(6, 1772),
            intArrayOf(7, 2126),
            intArrayOf(8, 2480),
            intArrayOf(9, 2835),
            intArrayOf(10, 3189),
            intArrayOf(11, 3544),
            intArrayOf(12, 3898),
            intArrayOf(13, 4252),
            intArrayOf(14, 4607),
            intArrayOf(15, 4961),
            intArrayOf(16, 5315),
            intArrayOf(17, 5670),
            intArrayOf(18, 6024),
            intArrayOf(19, 6378),
            intArrayOf(20, 6733),
            intArrayOf(21, 7087),
            intArrayOf(22, 7442),
            intArrayOf(23, 7796),
            intArrayOf(24, 8150),
            intArrayOf(25, 8505),
            intArrayOf(26, 8859),
            intArrayOf(27, 9213),
            intArrayOf(28, 9568),
            intArrayOf(29, 9922),
            intArrayOf(30, 10277)
        )

        val misri_month: IntArray =
            intArrayOf(0, 30, 59, 89, 118, 148, 177, 207, 236, 266, 295, 325)
    }
}