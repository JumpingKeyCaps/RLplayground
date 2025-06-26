package com.lebaillyapp.rlplayground.playground

data class Ball(
    val position: Vector2,
    val velocity: Vector2,
    val trail: MutableList<Vector2> = mutableListOf()
)