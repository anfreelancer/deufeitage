/*
 * YearSelector.kt
 *
 * Copyright 2021 by MicMun
 */
package com.luteapp.deufeitage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luteapp.deufeitage.R
import com.luteapp.deufeitage.model.YearItem

/**
 * Composable for year selection.
 *
 * @author MicMun
 * @version 1.1, 12.08.21
 */

@Composable
fun YearSelector(years: List<YearItem>, initValue: YearItem, listener: YearSelectedListener) {
   val listState = rememberLazyListState()
   var selectedItem by remember { mutableStateOf(initValue) }

   LazyRow(
      state = listState,
      modifier = Modifier
         .fillMaxWidth()
         .wrapContentHeight(),
      horizontalArrangement = Arrangement.SpaceAround
   ) {
      items(years, key = null) { year ->
         Box(
            Modifier
               .selectable(
                  selected = selectedItem == year,
                  onClick = {
                     selectedItem = year
                     listener.onYearSelected(year)
                  }
               )
               .border(1.dp, color = Color.DarkGray)
               .height(50.dp)
         ) {
            val modifier = if (selectedItem == year) {
               Modifier.background(color = colorResource(id = R.color.selectedYear))
            } else {
               Modifier.background(color = MaterialTheme.colors.background)
            }

            Box(
               modifier = modifier
                  .fillMaxHeight()
                  .padding(
                     dimensionResource(id = R.dimen.year_margin_horizontal),
                     dimensionResource(id = R.dimen.year_margin_vertical)
                  )
            ) {
               Text(
                  text = year.toString(), modifier = Modifier.align(Center),
                  style = TextStyle(fontSize = dimensionResource(id = R.dimen.year_text_size).value.sp)
               )
            }
         }
      }
   }
}

@Composable
@Preview
fun YearSelectorPreview() {
   val years = listOf(
      YearItem(2017),
      YearItem(2018),
      YearItem(2019),
      YearItem(2020),
      YearItem(2021),
      YearItem(2022),
      YearItem(2023),
      YearItem(2024)
   )
   YearSelector(years = years, initValue = years[2]) { }
}

/**
 * Interface for year selection.
 *
 * @author MicMun
 * @version 1.0, 09.08.21
 */
fun interface YearSelectedListener {
   fun onYearSelected(year: YearItem)
}
