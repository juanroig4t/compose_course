package com.juanroig.composecourse.ui.screen.composeScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juanroig.composecourse.ui.theme.ComposeCourseTheme

@Composable
fun TextScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Jetpack Compose",
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            fontSize = 32.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            textDecoration = TextDecoration.Underline,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Texto aplicando un estilo de material",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Green,
                        fontSize = 20.sp
                    )
                ) {
                    append("Texto con distintos fprmatos en el mismo texto, ")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("En negrita, ")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Light,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("con subrallado")
                }
            },
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Texto largo multilinea maximo 3 lineas, con overflow en ellipsis. Loren ipsum dolor sit amet. Lorem ipsum dolor sit amet.Loren ipsum dolor sit amet. Lorem ipsum dolor sit amet. Loren ipsum dolor sit amet. Lorem ipsum dolor sit amet.",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "Texto largo multilinea sin limite\n" +
                "Loren ipsum dolor sit amet. Lorem ipsum dolor sit amet.Loren ipsum dolor sit amet. " +
                "Loren ipsum dolor sit amet. Lorem ipsum dolor sit amet.Loren ipsum dolor sit amet. " +
                "Loren ipsum dolor sit amet. Lorem ipsum dolor sit amet.Loren ipsum dolor sit amet. " +
                "Lorem ipsum dolor sit amet. Loren ipsum dolor sit amet. Lorem ipsum dolor sit amet.",
            color = Color.LightGray,
            style = MaterialTheme.typography.bodyLarge
        )

        var texto by remember {
            mutableStateOf("")
        }

        TextField(
            value = texto,
            onValueChange = { texto = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextAndTextFieldScreenPreview() {
    ComposeCourseTheme {
        TextScreen()
    }
}
