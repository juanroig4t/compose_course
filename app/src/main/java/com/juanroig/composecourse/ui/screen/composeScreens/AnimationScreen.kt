package com.juanroig.composecourse.ui.screen.composeScreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.juanroig.composecourse.ui.theme.ComposeCourseTheme

@Composable
fun AnimationScreen() {
    var visible by remember {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedAppearDisappear(visible) {
            visible = !visible
        }

        AnimationDelete(visible) {
            visible = !visible
        }

        AnimationChangeColor()

        AnimationChangePosition()

        AnimatedPadding()
    }

//    AnimatedElevation()
//    AnimatedTextAndColorScale()
//    AnimatedContentExampleSwitch()
//    AnimateBetweenComposableDestinations()
}

@Composable
fun AnimatedTextAndColorScale() {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color(0xFF60DDAD),
        targetValue = Color(0xFF4285F4),
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "color"
    )
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
        label = "scale"
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Hello",
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        transformOrigin = TransformOrigin.Center
                    }
                    .align(Alignment.Center),
                // Text composable does not take TextMotion as a parameter.
                // Provide it via style argument but make sure that we are copying from current theme
                style = LocalTextStyle.current.copy(textMotion = TextMotion.Animated),
                color = animatedColor
            )
        }
    }
}

@Composable
fun AnimatedElevation() {
    val mutableInteractionSource = remember {
        MutableInteractionSource()
    }
    val pressed = mutableInteractionSource.collectIsPressedAsState()
    val elevation = animateDpAsState(
        targetValue = if (pressed.value) {
            32.dp
        } else {
            8.dp
        },
        label = "elevation",
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .graphicsLayer {
                    this.shadowElevation = elevation.value.toPx()
                }
                .clickable(interactionSource = mutableInteractionSource, indication = null) {
                }
                .background(Color.Green)
        )
    }
}

@Composable
private fun AnimatedPadding() {
    var toggled by remember {
        mutableStateOf(false)
    }
    val animatedPadding by animateDpAsState(
        if (toggled) {
            0.dp
        } else {
            20.dp
        },
        label = "padding"
    )
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .padding(animatedPadding)
            .background(Color(0xff53D9A1))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                toggled = !toggled
            }
    )
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
private fun AnimationChangePosition() {
    Text(text = "Animacion de posicion")

    var toggled by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .clickable(indication = null, interactionSource = interactionSource) {
                toggled = !toggled
            }
    ) {
        val offsetTarget = if (toggled) {
            IntOffset(150, 150)
        } else {
            IntOffset.Zero
        }
        val offset = animateIntOffsetAsState(
            targetValue = offsetTarget,
            label = "offset",
            animationSpec = tween(
                durationMillis = 1000
            )
        )
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Blue)
        )
        Box(
            modifier = Modifier
                .layout { measurable, constraints ->
                    val offsetValue = if (isLookingAhead) offsetTarget else offset.value
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width + offsetValue.x, placeable.height + offsetValue.y) {
                        placeable.placeRelative(offsetValue)
                    }
                }
                .size(100.dp)
                .background(Color.Green)
        )
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Blue)
        )
    }
}

@Composable
private fun AnimationChangeColor() {
    Text(text = "Animacion cambio de color")

    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val color by infiniteTransition.animateColor(
        initialValue = Color.Green,
        targetValue = Color.Blue,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color"
    )
    Column(
        modifier = Modifier
            .drawBehind {
                drawRect(color)
            }
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }

    Spacer(modifier = Modifier.size(16.dp))
}

@Composable
private fun AnimationDelete(
    visible: Boolean,
    changeVisibility: () -> Unit = {}
) {
    Text(
        modifier = Modifier.clickable {
            changeVisibility()
        },
        text = "Animacion borrar"
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1.0f else 0f,
        label = "alpha",
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .graphicsLayer {
                alpha = animatedAlpha
            }
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Green)
    )

    Spacer(modifier = Modifier.size(16.dp))
}

@Composable
private fun ColumnScope.AnimatedAppearDisappear(
    visible: Boolean,
    changeVisibility: () -> Unit = {}
) {
    Text(text = "Animacion mostrar / ocultar")

    Spacer(modifier = Modifier.size(16.dp))

    AnimatedVisibility(
        visible = visible
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color.Blue)
                .padding(20.dp)
        )
    }

    if (visible) {
        Spacer(modifier = Modifier.size(16.dp))
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                changeVisibility()
            }
            .height(100.dp)
            .background(color = Color.Green)
            .padding(20.dp)

    )

    Spacer(modifier = Modifier.size(16.dp))
}

@Preview(showBackground = true)
@Composable
fun AnimationScreenPreview() {
    ComposeCourseTheme {
        AnimationScreen()
    }
}
