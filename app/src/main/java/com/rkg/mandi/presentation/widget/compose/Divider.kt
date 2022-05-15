package com.rkg.mandi.presentation.widget.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.rkg.mandi.R

@Composable
fun ColumnDivider(
    modifier: Modifier = Modifier,
    color: Color = colorResource(id = R.color.gray40),
    margin: Int = 16
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color)
            .padding(horizontal = margin.dp)
    )
}