/*
 * HolidayListView.kt
 *
 * Copyright 2021 by MicMun
 */

package com.luteapp.deufeitage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luteapp.deufeitage.R
import com.luteapp.deufeitage.model.Holiday
import com.luteapp.deufeitage.utils.DateUtility
import java.util.*

/**
 * Composable for holidays.
 *
 * @author MicMun
 * @version 1.1, 12.08.21
 */
@Composable
fun HolidayListView(holidays: List<Holiday>) {
   val nextHoliday = DateUtility.getNextHoliday(holidays)
   val highlightColor =
      if (isSystemInDarkTheme()) // highlight color for dark theme
         colorResource(R.color.highlightNextHolidayDark)
      else colorResource(R.color.highlightNextHoliday) // light theme

   Column(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(4.dp)
   ) {
      // column titles
      HolidayTitles()

      holidays.forEach { holiday ->
         var isExpanded by remember {
            mutableStateOf(false)
         }
         if (holiday == nextHoliday) {
            Column(
               modifier = Modifier
                  .fillMaxWidth()
                  .wrapContentHeight(Alignment.Top)
                  .border(Dp.Hairline, color = Color.Gray)
                  .padding(3.dp, 0.dp)
                  .toggleable(value = false, enabled = true, role = null) {
                     isExpanded = !isExpanded
                  }
                  .background(color = highlightColor) // highlight next holiday
            ) {
               // holiday row
               HolidayRow(holiday = holiday, isExpanded = isExpanded)
            }
         } else {
            Column(
               modifier = Modifier
                  .fillMaxWidth()
                  .wrapContentHeight(Alignment.Top)
                  .border(Dp.Hairline, color = Color.Gray)
                  .padding(3.dp, 0.dp)
                  .toggleable(value = false, enabled = true, role = null) {
                     isExpanded = !isExpanded
                  }
            ) {
               // holiday row
               HolidayRow(holiday = holiday, isExpanded = isExpanded)
            }
         }
      }
   }
}

@Composable
fun HolidayTitles() {
   val textStyle = TextStyle(
      fontSize = dimensionResource(id = R.dimen.normal_text_size).value.sp,
      fontWeight = FontWeight.Bold
   )

   Row(
      modifier = Modifier
         .fillMaxWidth()
         .wrapContentHeight(Alignment.Top)
         .padding(3.dp, 0.dp)
   ) {
      Column(
         modifier = Modifier
            .padding(0.dp, 5.dp)
            .weight(1.0f)
      ) {
         Text(
            text = LocalContext.current.getString(R.string.title_date),
            modifier = Modifier.align(Alignment.Start),
            style = textStyle
         )
      }
      Column(
         modifier = Modifier
            .padding(5.dp, 5.dp)
            .weight(2.0f)
            .fillMaxWidth(0.5f)
      ) {
         Text(
            text = LocalContext.current.getString(R.string.title_holiday),
            modifier = Modifier.align(Alignment.Start),
            style = textStyle
         )
      }
      Column(
         modifier = Modifier
            .padding(0.dp, 5.dp)
            .weight(1.0f)
      ) {
         Text(
            text = LocalContext.current.getString(R.string.title_difference),
            modifier = Modifier.align(Alignment.End),
            style = textStyle
         )
      }
   }
}

@Composable
fun HolidayRow(holiday: Holiday, isExpanded: Boolean) {
   val textStyle = TextStyle(fontSize = dimensionResource(id = R.dimen.normal_text_size).value.sp)

   Row(
      modifier = Modifier
         .fillMaxWidth()
         .wrapContentHeight(Alignment.Top)
   ) {
      Column(
         modifier = Modifier
            .padding(0.dp, 5.dp)
            .weight(1.0f)
      ) {
         Text(
            text = DateUtility.getFormattedDate(holiday.datum),
            modifier = Modifier.align(Alignment.Start),
            style = textStyle
         )
      }
      Column(
         modifier = Modifier
            .padding(5.dp, 5.dp)
            .weight(2.0f)
            .fillMaxWidth(0.5f)
      ) {
         Text(
            text = holiday.name,
            modifier = Modifier.align(Alignment.Start),
            style = textStyle
         )

         // DE for german holidays or a list of the states
         val statesString = if (holiday.states[0] == "DE") "DE" else holiday.states.joinToString(separator = ",")
         Text(
            text = "($statesString)",
            modifier = Modifier.align(Alignment.Start),
            style = TextStyle(
               fontSize = dimensionResource(id = R.dimen.small_text_size).value.sp,
               fontStyle = FontStyle.Italic
            )
         )
      }
      Column(
         modifier = Modifier
            .padding(0.dp, 5.dp)
            .weight(1.0f)
      ) {
         Text(
            text = LocalContext.current.getString(R.string.diff_text, holiday.getDiffToNow()),
            modifier = Modifier.align(Alignment.End),
            style = textStyle
         )
      }
   }
   if (isExpanded) {
      Row(
         modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Top)
      ) {
         Column(
            modifier = Modifier
               .padding(0.dp, 5.dp)
               .weight(3.0f)
         ) {
            Text(
               text = holiday.desc,
               modifier = Modifier.align(Alignment.Start),
               style = TextStyle(fontStyle = FontStyle.Italic, fontSize = textStyle.fontSize)
            )
         }
      }
   }
}

@Composable
@Preview
fun HolidayListViewPreview() {
   val holidays = listOf(
      Holiday(1, "Mariä Himmelfahrt", listOf("BY"), "Marias Himmelfahrt"),
      Holiday(2, "Tag der Dt. Einheit", listOf("BY, BW, SN"), "Nationalfeiertag")
   )
   holidays[0].datum = getCalendar(2021, 8, 15)
   holidays[1].datum = getCalendar(2021, 10, 3)

   HolidayListView(holidays = holidays)
}

@Suppress("SameParameterValue")
private fun getCalendar(year: Int, month: Int, day: Int): Calendar {
   val cal = Calendar.getInstance()
   cal.set(year, month - 1, day)
   return cal
}
