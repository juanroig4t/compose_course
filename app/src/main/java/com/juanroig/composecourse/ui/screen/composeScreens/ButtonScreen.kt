package com.juanroig.composecourse.ui.screen.composeScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juanroig.composecourse.ui.theme.ComposeCourseTheme

@Composable
fun ButtonScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            // .background(Color.DarkGray)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Button(
//            onClick = { /*TODO*/ },
//            enabled = true,
//            shape = MaterialTheme.shapes.medium,
//            colors = ButtonDefaults.buttonColors(
//                contentColor = Color.Green,
//                containerColor = Color.Gray
//            ),
//            elevation = ButtonDefaults.elevatedButtonElevation(
//                defaultElevation = 10.dp,
//                pressedElevation = 20.dp
//            ),
//            border = BorderStroke(2.dp, Color.Red),
//            contentPadding = PaddingValues(16.dp),
//        ) {
//            Text("Custom Button")
//        }

        Button(
            onClick = { /*TODO*/ }
        ) {
            Text("Button")
        }

        ElevatedButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add")
            Spacer(modifier = Modifier.size(8.dp))
            Text("Elevated Button")
        }

        FilledTonalButton(onClick = { /*TODO*/ }) {
            Text(text = "Filled Tonal Button")
        }

        OutlinedButton(onClick = { /*TODO*/ }) {
            Text(text = "Outlined Button")
        }

        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Text Button")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonScreenPreview() {
    ComposeCourseTheme {
        ButtonScreen()
    }
}
