/*
 * StateSpinner.kt
 *
 * Copyright 2021 by MicMun
 */
package com.luteapp.deufeitage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luteapp.deufeitage.R
import com.luteapp.deufeitage.model.StateItem

/**
 * Compose Spinner for country selection.
 *
 * @author MicMun
 * @version 1.1, 12.08.21
 */
@Composable
fun StateSpinner(states: List<StateItem>, initValue: StateItem, listener: StateSelectedListener) {
   val text = remember { mutableStateOf(initValue.name) } // initial value
   val isOpen = remember { mutableStateOf(false) } // initial value
   val openCloseOfDropDownList: (Boolean) -> Unit = {
      isOpen.value = it
   }
   val userSelectedState: (StateItem) -> Unit = {
      text.value = it.name
      listener.onStateSelected(it)
   }
   Box {
      Column {
         OutlinedTextField(
            value = text.value,
            onValueChange = { text.value = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = dimensionResource(id = R.dimen.state_text_size).value.sp),
            trailingIcon = {
               Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
            }
         )
         DropDownList(
            requestToOpen = isOpen.value,
            list = states,
            openCloseOfDropDownList,
            userSelectedState
         )
      }
      Spacer(
         modifier = Modifier
            .matchParentSize()
            .background(Color.Transparent)
            .padding(8.dp)
            .clickable(
               onClick = { isOpen.value = true }
            )
      )
   }
}

@Composable
fun DropDownList(
   requestToOpen: Boolean = false,
   list: List<StateItem>,
   request: (Boolean) -> Unit,
   selectedState: (StateItem) -> Unit
) {
   DropdownMenu(
      modifier = Modifier.fillMaxWidth(),
      expanded = requestToOpen, onDismissRequest = { request(false) },
   ) {
      list.forEach { state ->
         DropdownMenuItem(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
               request(false)
               selectedState(state)
            }
         ) {
            Text(
               state.name, modifier = Modifier
                  .wrapContentWidth()
                  .align(Alignment.CenterVertically),
               style = TextStyle(fontSize = dimensionResource(id = R.dimen.state_text_size).value.sp)
            )
         }
      }
   }
}

@Composable
@Preview
fun StateSpinnerPreview() {
   val states = listOf(StateItem("AA", "Alle Feiertage"), StateItem("BY", "Bayern"))
   StateSpinner(states = states, initValue = states[1]) { }
}

/**
 * Interface for state selection.
 *
 * @author MicMun
 * @version 1.0, 09.08.21
 */
fun interface StateSelectedListener {
   fun onStateSelected(state: StateItem)
}
