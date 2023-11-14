package com.juanroig.composecourse.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juanroig.composecourse.ui.theme.ComposeCourseTheme

@Composable
fun SelectionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Checkboxes()
        CustomSwitch()
        RadioButtons()
        Chips()
    }
}

data class ToggleableInfo(
    val isCheked: Boolean,
    val text: String
)

@Composable
private fun Checkboxes() {
    val checboxes = remember {
        mutableStateListOf(
            ToggleableInfo(
                isCheked = false,
                text = "Option 1"
            ),
            ToggleableInfo(
                isCheked = false,
                text = "Option 2"
            ),
            ToggleableInfo(
                isCheked = false,
                text = "Option 3"
            )
        )
    }

    var triState by remember {
        mutableStateOf(ToggleableState.Indeterminate)
    }
    val toggleTriState = {
        triState = when (triState) {
            ToggleableState.Indeterminate -> ToggleableState.On
            ToggleableState.On -> ToggleableState.Off
            ToggleableState.Off -> ToggleableState.On
        }
        checboxes.indices.forEach { index ->
            checboxes[index] = checboxes[index].copy(
                isCheked = triState == ToggleableState.On
            )
        }
    }

    Row(
        modifier = Modifier
            .clickable { toggleTriState() }
            .padding(end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TriStateCheckbox(
            state = triState,
            onClick = toggleTriState
        )
        Text(text = "Options")
    }

    checboxes.forEachIndexed { index, info ->
        Row(
            modifier = Modifier
                .padding(start = 32.dp)
                .clickable {
                    checboxes[index] = info.copy(isCheked = !info.isCheked)
                }
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = info.isCheked,
                onCheckedChange = { isCheked ->
                    checboxes[index] = info.copy(isCheked = isCheked)
                }
            )
            Text(text = info.text)
        }
    }
}

@Composable
private fun CustomSwitch() {
    var switch by remember { mutableStateOf(ToggleableInfo(false, "Any configuration")) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = switch.text
        )
        Switch(
            checked = switch.isCheked,
            onCheckedChange = { isChecked ->
                switch = switch.copy(isCheked = isChecked)
            }
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = switch.text
        )
        Switch(
            checked = switch.isCheked,
            onCheckedChange = { isChecked ->
                switch = switch.copy(isCheked = isChecked)
            },
            thumbContent = {
                Icon(
                    imageVector = if (switch.isCheked) Icons.Outlined.Check else Icons.Outlined.Close,
                    contentDescription = "icon"
                )
            }
        )
    }
}

@Composable
private fun RadioButtons() {
    val radioButtons = remember {
        mutableStateListOf(
            ToggleableInfo(
                isCheked = true,
                text = "Option 1"
            ),
            ToggleableInfo(
                isCheked = false,
                text = "Option 2"
            ),
            ToggleableInfo(
                isCheked = false,
                text = "Option 3"
            )
        )
    }

    radioButtons.forEach { info ->
        Row(
            modifier = Modifier
                .clickable {
                    radioButtons.replaceAll {
                        it.copy(
                            isCheked = it.text == info.text
                        )
                    }
                }
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = info.isCheked,
                onClick = {
                    radioButtons.replaceAll {
                        it.copy(
                            isCheked = it.text == info.text
                        )
                    }
                }
            )
            Text(text = info.text)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chips() {
    Column(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AssistChip(
            onClick = { Log.d("Assist chip", "hello world") },
            label = { Text("Assist chip") },
            leadingIcon = {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "Localized description",
                    Modifier.size(AssistChipDefaults.IconSize)
                )
            }
        )

        var selected by remember { mutableStateOf(false) }

        FilterChip(
            onClick = { selected = !selected },
            label = {
                Text("Filter chip")
            },
            selected = selected,
            leadingIcon = if (selected) {
                {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Done icon",
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            } else {
                null
            }
        )

        InputChip(
            onClick = {
                // onDismiss()
                selected = !selected
            },
            label = { Text("Input chip") },
            selected = selected,
            avatar = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Localized description",
                    Modifier.size(InputChipDefaults.AvatarSize)
                )
            },
            trailingIcon = {
                if (selected) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Localized description",
                        Modifier.size(InputChipDefaults.AvatarSize)
                    )
                }
            }
        )

        SuggestionChip(
            onClick = { Log.d("Suggestion chip", "hello world") },
            label = { Text("Suggestion chip") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SelectionScreenPreview() {
    ComposeCourseTheme {
        SelectionScreen()
    }
}
