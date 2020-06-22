package com.negusoft.tictactoe.data

/**
 * The Row/Column positions in a grid
 */
data class Position(val column: Column, val row: Row) {
    enum class Column { LEFT, CENTER, RIGHT}
    enum class Row { TOP, CENTER, BOTTOM }
}