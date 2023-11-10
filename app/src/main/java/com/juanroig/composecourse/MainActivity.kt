package com.juanroig.composecourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juanroig.composecourse.ui.theme.ComposeCourseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCourseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BasicLayoutsScreen()
                }
            }
        }
    }
}

@Composable
fun BasicLayoutsScreen() {
//    Box {
//        Text("Android")
//        Text("Compose")
//    }
//    Column {
//        Text("Android")
//        Text("Compose")
//    }
//    Row {
//        Text("Android")
//        Text("Compose")
//    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Android image"
            )
            Column {
                Text("Android Compose")
                Text("Layouts")
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Box(
                modifier = Modifier.size(100.dp)
                    .padding(20.dp)
                    .background(color = Color.Red)
                    .align(Alignment.TopStart)
                // El orden de las propiedades del modifier importa.
            )
            Box(
                modifier = Modifier.size(50.dp)
                    .background(color = Color.Blue)
                    .padding(20.dp)
                    .align(Alignment.TopStart)

            )
            Box(
                modifier = Modifier.size(50.dp)
                    .background(color = Color.Green)
                    .padding(20.dp)
                    .align(Alignment.TopEnd)

            )
        }

    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeCourseTheme {
        BasicLayoutsScreen()
    }
}