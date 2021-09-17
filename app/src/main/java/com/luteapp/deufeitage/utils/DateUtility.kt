/*
 * DateUtility.kt
 *
 * Copyright 2018 by MicMun
 */
package com.luteapp.deufeitage.utils

import com.luteapp.deufeitage.model.Holiday
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

/**
 * Methods for date and time.
 *
 * @author MicMun
 * @version 1.2, 16.08.21
 */
class DateUtility {
    companion object {
        /**
         * Returns a formatted date string.
         *
         * @param date Date to format.
         * @return formatted date string.
         */
        fun getFormattedDate(date: Calendar): String {
            var df = SimpleDateFormat("EE MM/dd", Locale.getDefault())

            if (Locale.getDefault() == Locale.GERMANY || Locale.getDefault() == Locale("de_AT") ||
                    Locale.getDefault() == Locale("de_CH")) {
                df = SimpleDateFormat("EE dd.MM.", Locale.getDefault())
            }

            return df.format(date.time)
        }

        /**
         * Returns the next coming holiday.
         *
         * @param holidays list of holidays.
         * @return next coming holiday or <code>null</code>, if there is no holiday anymore in the
         *         current year.
         */
        fun getNextHoliday(holidays: List<Holiday>): Holiday? {
            return holidays.find { h -> h.getDiffToNow() >= 0 }
        }

        /**
         * Returns the current year.
         *
         * @return current year.
         */
        fun currentYear() = LocalDate.now().year
    }
}
