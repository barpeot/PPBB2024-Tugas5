package com.example.waterbottle

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WaterBottle(
    modifier: Modifier = Modifier,
    totalWaterAmt: Int,
    unit: String,
    usedWaterAmt: Int,
    waterColor: Color = Color(0xff279eff),
    bottleColor: Color = Color.White,
    capColor: Color = Color(0xff0065b9)
) {
    val waterPercentage = animateFloatAsState(
        targetValue = usedWaterAmt.toFloat()/totalWaterAmt.toFloat(),
        label = "Water Waves animation",
        animationSpec = tween(durationMillis = 1000)
    ).value

    val usedWaterAmtAnim = animateIntAsState(
        targetValue = usedWaterAmt,
        label = "Used Water Amt Anim",
        animationSpec = tween(durationMillis = 1000)
    ).value

    Box(modifier = modifier
        .width(200.dp)
        .height(600.dp)){

        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val capWidth = size.width * 0.55f
            val capHeight = size.height * 0.1f

            val bottleBodyPath = Path().apply {
                moveTo(
                    x = width * 0.3f, y = height * 0.1f
                )
                lineTo(
                    x = width * 0.3f, y = height * 0.2f
                )
                quadraticBezierTo(
                    0f,
                    height*0.3f,
                    0f,
                    height*0.4f
                )
                lineTo(
                    0f,
                    height*0.95f
                )
                quadraticBezierTo(
                    0f,
                    height,
                    width*0.05f,
                    height
                )
                lineTo(
                    width*0.95f,
                    height
                )
                quadraticBezierTo(
                    width,
                    height,
                    width,
                    height*0.95f
                )
                lineTo(
                    width,
                    height*0.4f
                )
                quadraticBezierTo(
                    width,
                    height*0.3f,
                    width*0.7f,
                    height*0.2f
                )
                lineTo(
                    width*0.7f,
                    height*0.1f
                )
                close()
            }

            clipPath(
                bottleBodyPath
            ){
                drawRect(
                    bottleColor,
                    size = size
                )

                val waterWavesYPosition = (1-waterPercentage) * size.height
                val waterPath = Path().apply {
                    moveTo(
                        0f,
                        waterWavesYPosition
                    )
                    lineTo(
                        size.width,
                        waterWavesYPosition
                    )
                    lineTo(
                        size.width,
                        size.height
                    )
                    lineTo(
                        0f,
                        size.height
                    )
                    close()
                }
                drawPath(
                    waterPath,
                    waterColor
                )
            }

            drawRoundRect(
                color = capColor,
                size = Size(capWidth, capHeight),
                topLeft = Offset(size.width/2-capWidth/2f, 0f),
                cornerRadius = CornerRadius(45f, 45f)
            )
        }

        val text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = if (waterPercentage <= 0.5f) waterColor else bottleColor,
                    fontSize = 44.sp
                )
            ){
                append(usedWaterAmtAnim.toString())
            }
            withStyle(
                SpanStyle(
                    color = if (waterPercentage <= 0.5f) waterColor else bottleColor,
                    fontSize = 22.sp
                )
            ){
                append(" ")
                append(unit)
            }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(text = text)
        }
    }
}