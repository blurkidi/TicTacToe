package com.negusoft.tictactoe.data

/**
 * A 3x3 grid that holds player moves. Null represents
 * The class is immutable, a new instance is created each time the edit method is called.
 */
data class Grid(
    val topLeft: Player?,
    val top: Player?,
    val topRight: Player?,
    val centerLeft: Player?,
    val center: Player?,
    val centerRight: Player?,
    val bottomLeft: Player?,
    val bottom: Player?,
    val bottomRight: Player?
) {

    /** Get the the move at the given position. */
    operator fun get(column: Position.Row, row: Position.Column): Player? = TODO()

    /**
     * Edit the value on the given position.
     * Returns a new Grid instance with the new value.
     */
    fun edit(player: Player?, column: Position.Row, row: Position.Column): Grid {
        TODO()
    }
}