/*
 * Holday.kt
 *
 * Copyright 2018 by MicMun
 */
package com.luteapp.deufeitage.model

import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Holiday data.
 *
 * @author MicMun
 * @version 1.1, 04.05.19
 */
data class Holiday(val id: Int, val name: String, val states: List<String>, val desc: String) {
    var datum: Calendar = Calendar.getInstance()

    /**
     * Returns the number of days as difference to now.
     *
     * @return the number of days as difference to now.
     */
    fun getDiffToNow(): Int {
        val heute = GregorianCalendar.getInstance()
        heute[Calendar.HOUR_OF_DAY] = 0
        heute[Calendar.MINUTE] = 0
        heute[Calendar.SECOND] = 0

        return when (compareDate(heute, datum)) {
            -1 -> (TimeUnit.DAYS.convert(heute.time.time - datum.time.time,
                    TimeUnit.MILLISECONDS).toInt()) * (-1)
            1 -> (TimeUnit.DAYS.convert(datum.time.time - heute.time.time,
                    TimeUnit.MILLISECONDS).toInt() + 1)
            else -> 0
        }
    }

    /**
     * Returns -1, when date is in past, 1, when date is in future and 0, when date is the same.
     */
    private fun compareDate(cal1: Calendar, cal2: Calendar): Int {
        return when {
            cal1[Calendar.YEAR] > cal2[Calendar.YEAR] -> -1
            cal2[Calendar.YEAR] > cal1[Calendar.YEAR] -> 1
            cal1[Calendar.MONTH] > cal2[Calendar.MONTH] -> -1
            cal2[Calendar.MONTH] > cal1[Calendar.MONTH] -> 1
            cal1[Calendar.DAY_OF_MONTH] > cal2[Calendar.DAY_OF_MONTH] -> -1
            cal2[Calendar.DAY_OF_MONTH] > cal1[Calendar.DAY_OF_MONTH] -> 1
            else -> 0
        }
    }
}