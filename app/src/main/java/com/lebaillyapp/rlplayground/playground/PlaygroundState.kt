package com.lebaillyapp.rlplayground.playground

import android.content.res.Resources
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class PlaygroundState(
    private val gravity: Float,
    private val bounceFactor: Float,
    private val radius: Float,
) {
    val balls = mutableStateListOf<Ball>()
    val obstacles = mutableStateListOf<Obstacle>()

    fun spawnBall(offset: Offset, angleRad: Float, speed: Float) {
        val velocity = Vector2(
            cos(angleRad) * speed,
            -sin(angleRad) * speed
        )
        balls.add(Ball(position = Vector2(offset.x, offset.y), velocity = velocity))
    }

    fun addObstacle(obstacle: Obstacle) {
        obstacles.add(obstacle)
    }

    fun findObstacleAt(offset: Offset): Obstacle? {
        return obstacles.reversed().find { ob ->
            offset.x in ob.position.x..(ob.position.x + ob.size.x) &&
                    offset.y in ob.position.y..(ob.position.y + ob.size.y)
        }
    }

    fun moveObstacle(obstacle: Obstacle, delta: Offset) {
        obstacle.position.x += delta.x
        obstacle.position.y += delta.y
    }



    fun update(dt: Float, bounds: Size, tailLength: Int) {
        for (ball in balls) {
            // Traînée
            ball.trail.add(ball.position.copy())
            if (ball.trail.size > tailLength) {
                ball.trail.removeAt(0)
            }

            // Gravité
            ball.velocity.y += gravity * dt

            // Déplacement
            ball.position.x += ball.velocity.x * dt
            ball.position.y += ball.velocity.y * dt

            // Bords écran
            if (ball.position.x - radius < 0f) {
                ball.position.x = radius
                ball.velocity.x = -ball.velocity.x * bounceFactor
            } else if (ball.position.x + radius > bounds.width) {
                ball.position.x = bounds.width - radius
                ball.velocity.x = -ball.velocity.x * bounceFactor
            }
            if (ball.position.y - radius < 0f) {
                ball.position.y = radius
                ball.velocity.y = -ball.velocity.y * bounceFactor
            } else if (ball.position.y + radius > bounds.height) {
                ball.position.y = bounds.height - radius
                ball.velocity.y = -ball.velocity.y * bounceFactor
                ball.velocity.x *= 0.98f
            }

            // Collision avec obstacles
            for (ob in obstacles) {
                val closestX = ball.position.x.coerceIn(ob.position.x, ob.position.x + ob.size.x)
                val closestY = ball.position.y.coerceIn(ob.position.y, ob.position.y + ob.size.y)

                val dx = ball.position.x - closestX
                val dy = ball.position.y - closestY

                if (dx * dx + dy * dy < radius * radius) {
                    // Collision : résolution simple en fonction du bord le plus proche
                    val distLeft = abs(ball.position.x - ob.position.x)
                    val distRight = abs(ball.position.x - (ob.position.x + ob.size.x))
                    val distTop = abs(ball.position.y - ob.position.y)
                    val distBottom = abs(ball.position.y - (ob.position.y + ob.size.y))

                    val minDist = listOf(distLeft, distRight, distTop, distBottom).minOrNull()

                    when (minDist) {
                        distLeft -> {
                            ball.position.x = ob.position.x - radius
                            ball.velocity.x = -ball.velocity.x * bounceFactor
                        }
                        distRight -> {
                            ball.position.x = ob.position.x + ob.size.x + radius
                            ball.velocity.x = -ball.velocity.x * bounceFactor
                        }
                        distTop -> {
                            ball.position.y = ob.position.y - radius
                            ball.velocity.y = -ball.velocity.y * bounceFactor
                        }
                        distBottom -> {
                            ball.position.y = ob.position.y + ob.size.y + radius
                            ball.velocity.y = -ball.velocity.y * bounceFactor
                        }
                    }
                }
            }
        }
    }

    fun draw(canvas: Canvas) {
        // Obstacles (coins arrondis)
        val obstaclePaint = Paint().apply { color = Color.DarkGray }
        val cornerRadius = 12f
        for (ob in obstacles) {
            canvas.drawRoundRect(
                left = ob.position.x,
                top = ob.position.y,
                right = ob.position.x + ob.size.x,
                bottom = ob.position.y + ob.size.y,
                radiusX = cornerRadius,
                radiusY = cornerRadius,
                paint = obstaclePaint
            )
        }

        // Balles + traînées (inchangé)
        for (ball in balls) {
            ball.trail.forEachIndexed { index, pos ->
                val alpha = (index.toFloat() / ball.trail.size).coerceIn(0f, 1f)
                val paint = Paint().apply {
                    color = Color.White.copy(alpha = alpha * 0.3f)
                }
                canvas.drawCircle(
                    center = Offset(pos.x, pos.y),
                    radius = radius * 0.4f,
                    paint = paint
                )
            }

            val paint = Paint().apply { color = Color.White }
            canvas.drawCircle(
                center = Offset(ball.position.x, ball.position.y),
                radius = radius,
                paint = paint
            )
        }
    }
}