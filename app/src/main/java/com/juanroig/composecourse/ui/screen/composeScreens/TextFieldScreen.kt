package com.juanroig.composecourse.ui.screen.composeScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juanroig.composecourse.ui.theme.ComposeCourseTheme

@Composable
fun TextFieldScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var texto by remember {
            mutableStateOf("")
        }

        TextField(
            value = texto,
            onValueChange = { texto = it },
            enabled = true,
            readOnly = false,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Right),
            label = {
                Text(
                    text = "Introduce la cantidad"
                )
            },
            placeholder = {
                Text(
                    text = "Euros"
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.AccountBalance, contentDescription = "account balance")
            },
            trailingIcon = {
                Icon(imageVector = Icons.Outlined.Person, contentDescription = "person")
            },
//            prefix = {
//                Text(
//                    text = "$",
//                )
//            },
            suffix = {
                Text(
                    text = "€"
                )
            },
            supportingText = {
                Text(
                    text = "* Required field"
                )
            },
            isError = false,
//            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { println("action next presed") }
            ),
            maxLines = 2
        )

        TextField(
            value = texto,
            onValueChange = { texto = it },
            enabled = true,
            readOnly = false,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Right),
            placeholder = {
                Text(
                    text = "Introduce la cantidad"
                )
            },
            trailingIcon = {
                Icon(imageVector = Icons.Outlined.Payments, contentDescription = "person")
            },
            prefix = {
                Text(
                    text = "$"
                )
            },
            supportingText = {
                Text(
                    text = "* Required field"
                )
            },
            isError = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = false,
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { println("action next presed") }
            ),
            maxLines = 1
        )

        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            enabled = true,
            readOnly = true,
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Right,
                color = Color.White
            ),
            placeholder = {
                Text(
                    text = "Introduce la cantidad"
                )
            },
            trailingIcon = {
                Icon(imageVector = Icons.Outlined.Person, contentDescription = "person")
            },
            prefix = {
                Text(
                    text = "$"
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldScreenPreview() {
    ComposeCourseTheme {
        TextFieldScreen()
    }
}
