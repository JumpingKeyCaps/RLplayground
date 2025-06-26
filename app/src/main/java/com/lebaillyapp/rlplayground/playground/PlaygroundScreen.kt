package com.lebaillyapp.rlplayground.playground

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.atan2
import kotlin.math.sqrt

@Composable
fun PlaygroundScreen() {
    val gravity = 500f
    val bounceFactor = 0.7f
    val ballRadius = 10f
    val minLaunchSpeed = 200f
    val maxLaunchSpeed = 2200f
    val ballTailLength = 50

    val state = remember { PlaygroundState(gravity, bounceFactor, ballRadius) }
    var lastTime by remember { mutableStateOf(System.nanoTime()) }

    // Initialisation des obstacles
    LaunchedEffect(Unit) {
        listOf(

            Obstacle(Vector2(40f, 40f), Vector2(150f, 150f)),
            Obstacle(Vector2(40f, 195f), Vector2(150f, 150f)),
            Obstacle(Vector2(40f, 350f), Vector2(150f, 150f)),
            Obstacle(Vector2(40f, 40f), Vector2(150f, 150f)),
            Obstacle(Vector2(40f, 195f), Vector2(150f, 150f)),
            Obstacle(Vector2(40f, 350f), Vector2(150f, 150f)),
            Obstacle(Vector2(40f, 40f), Vector2(150f, 150f)),
            Obstacle(Vector2(40f, 195f), Vector2(150f, 150f)),
            Obstacle(Vector2(40f, 350f), Vector2(150f, 150f)),

        ).forEach { state.addObstacle(it) }
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        val down = awaitFirstDown()
                        val downTime = System.currentTimeMillis()
                        val downPos = down.position
                        val id = down.id

                        val obstacle = state.findObstacleAt(downPos)
                        if (obstacle != null) {
                            // Déplacement d’obstacle
                            var last = downPos
                            while (true) {
                                val event = awaitPointerEvent()
                                val drag = event.changes.firstOrNull { it.id == id } ?: break
                                if (drag.changedToUp()) break
                                val delta = drag.position - last
                                state.moveObstacle(obstacle, delta)
                                last = drag.position
                                drag.consume()
                            }
                        } else {
                            // Flick de tir
                            var lastPos = downPos
                            var upPos = downPos
                            var upTime = downTime

                            while (true) {
                                val event = awaitPointerEvent()
                                val drag = event.changes.firstOrNull { it.id == id } ?: break
                                if (drag.changedToUp()) {
                                    upPos = drag.position
                                    upTime = System.currentTimeMillis()
                                    break
                                } else {
                                    lastPos = drag.position
                                }
                            }

                            val dt = (upTime - downTime).coerceAtLeast(1).toFloat() / 1000f
                            val dragVector = upPos - downPos
                            val velocity = Vector2(dragVector.x / dt, dragVector.y / dt)

                            // Optionnel : limiter la vitesse pour éviter les dingueries
                            val speed = velocity.magnitude().coerceIn(minLaunchSpeed, maxLaunchSpeed)
                            val angle = atan2(-velocity.y, velocity.x)

                            state.spawnBall(downPos, angleRad = angle, speed = speed)
                        }
                    }
                }
            }
    ) {
        val now = System.nanoTime()
        val dt = (now - lastTime) / 1_000_000_000f
        lastTime = now

        state.update(dt, size, tailLength = ballTailLength)
        state.draw(drawContext.canvas)
    }
}
fun Vector2.magnitude(): Float = sqrt(x * x + y * y)