package com.negusoft.tictactoe.data

enum class Player {
    X, O;

    fun toggle(): Player = if (this == X) O else X
}