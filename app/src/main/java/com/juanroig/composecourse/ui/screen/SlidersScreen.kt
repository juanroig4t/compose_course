package com.juanroig.composecourse.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juanroig.composecourse.ui.theme.ComposeCourseTheme

@Composable
fun SlidersScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        var sliderPosition by remember { mutableFloatStateOf(0f) }
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it }
        )
        Text(text = sliderPosition.toString())

        var sliderPositionRange by remember { mutableFloatStateOf(0f) }
        Slider(
            value = sliderPositionRange,
            onValueChange = { sliderPositionRange = it },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            steps = 3,
            valueRange = 0f..50f
        )
        Text(text = sliderPositionRange.toString())

        var rangeSliderPosition by remember { mutableStateOf(0f..100f) }
        RangeSlider(
            value = rangeSliderPosition,
            steps = 5,
            onValueChange = { range -> rangeSliderPosition = range },
            valueRange = 0f..100f,
            onValueChangeFinished = {}
        )
        Text(text = rangeSliderPosition.toString())
    }
}

@Preview(showBackground = true)
@Composable
fun SlidersScreenPreview() {
    ComposeCourseTheme {
        SlidersScreen()
    }
}
