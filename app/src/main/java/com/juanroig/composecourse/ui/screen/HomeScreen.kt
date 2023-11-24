package com.juanroig.composecourse.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly

@Composable
fun HomeScreen(
    goToDetail: (movieId: Int) -> Unit
) {
    var textFieldText by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Screen", modifier = Modifier.padding(16.dp))

        TextField(value = textFieldText, onValueChange = {
            textFieldText = it
        })

        Button(onClick = {
            if (textFieldText.isDigitsOnly() && textFieldText.isNotBlank()) {
                goToDetail(textFieldText.toInt())
            } else {
                Toast.makeText(
                    context,
                    "Please enter a valid number",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {
            Text(text = "Go to Detail")
        }
    }
}
