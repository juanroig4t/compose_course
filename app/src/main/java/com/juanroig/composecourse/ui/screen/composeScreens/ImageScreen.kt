package com.juanroig.composecourse.ui.screen.composeScreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.juanroig.composecourse.R
import com.juanroig.composecourse.ui.theme.ComposeCourseTheme

@Composable
fun ImageScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            // .background(Color.DarkGray)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            model = "https://img.freepik.com/foto-gratis/retrato-abstracto-ojo-elegancia-mujeres-jovenes-generado-ai_188544-9712.jpg?w=1800&t=st=1699948843~exp=1699949443~hmac=9ece5f37049b7500178b10bbad01ebee83217a47cd35ebf015e38c0aa1aaf231",
            contentDescription = "Eye"
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                CircularProgressIndicator()
            } else {
                SubcomposeAsyncImageContent()
            }
        }

        Image(
            modifier = Modifier
                .size(300.dp)
                .background(Color.DarkGray),
            painter = painterResource(id = R.drawable.africa),
            contentDescription = "Android image",
            contentScale = ContentScale.Crop,
            alpha = 1f
        )

        Image(
            modifier = Modifier
                .size(300.dp)
                .background(Color.DarkGray)
                .clip(CircleShape)
                .border(
                    BorderStroke(6.dp, Color.Red),
                    CircleShape
                ),
            painter = painterResource(id = R.drawable.africa),
            contentDescription = "Android image",
            contentScale = ContentScale.FillHeight,
            alpha = 1f,
            alignment = Alignment.Center
        )

        val rainbowColorsBrush = remember {
            Brush.sweepGradient(
                listOf(
                    Color(0xFF9575CD),
                    Color(0xFFBA68C8),
                    Color(0xFFE57373),
                    Color(0xFFFFB74D),
                    Color(0xFFFFF176),
                    Color(0xFFAED581),
                    Color(0xFF4DD0E1),
                    Color(0xFF9575CD)
                )
            )
        }

        Image(
            painter = painterResource(id = R.drawable.africa),
            contentDescription = "Africa",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .border(
                    BorderStroke(6.dp, rainbowColorsBrush),
                    CircleShape
                )
                .padding(6.dp)
                .clip(CircleShape)
        )

        Image(
            painter = painterResource(id = R.drawable.africa),
            contentDescription = "africa",
            modifier = Modifier
                .aspectRatio(16f / 9f)
                .blur(
                    radiusX = 10.dp,
                    radiusY = 10.dp,
                    edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(50.dp))
                )
        )

        Image(
            painter = painterResource(id = R.drawable.africa),
            contentDescription = "africa",
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
        )

        AsyncImage(
            model = "https://developer.android.com/images/brand/Android_Robot.png",
            contentDescription = "Android image"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ImageScreenPreview() {
    ComposeCourseTheme {
        ImageScreen()
    }
}
