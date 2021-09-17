/*
 * Toolbar.kt
 *
 * Copyright 2021 by MicMun
 */
package com.luteapp.deufeitage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luteapp.deufeitage.R

/**
 * Composable for the toolbar.
 *
 * @author MicMun
 * @version 1.0, 06.08.21
 */

@Composable
fun Toolbar() {
   val height = 18.dp

   Column(
      modifier = Modifier
         .fillMaxWidth()
         .wrapContentSize(Alignment.Center)
   ) {
      Box(
         modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .clip(RectangleShape)
            .background(colorResource(id = R.color.colorPrimaryDark))
      )
      Box(
         modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .clip(RectangleShape)
            .background(colorResource(id = R.color.colorMiddle))
      )
      Box(
         modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .clip(RectangleShape)
            .background(colorResource(id = R.color.colorStart))
      )
   }
}

@Composable
@Preview
fun ToolbarPreview() {
   Toolbar()
}
