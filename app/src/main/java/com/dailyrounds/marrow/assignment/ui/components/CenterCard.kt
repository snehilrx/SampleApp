package com.dailyrounds.marrow.assignment.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dailyrounds.marrow.assignment.ui.theme.SampleAppTheme

@Composable
fun CenterCard(composable: @Composable ColumnScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = SampleAppTheme.dimens.grid_3
            ), contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(SampleAppTheme.dimens.grid_4, SampleAppTheme.dimens.grid_3),
                verticalArrangement = Arrangement.spacedBy(SampleAppTheme.dimens.grid_3)
            ) {
                composable()
            }
        }
    }
}