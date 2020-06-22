package com.negusoft.tictactoe.data

/**
 * A 3x3 grid that holds player moves. Null represents
 * The class is immutable, a new instance is created each time the edit method is called.
 */
data class Grid(
    val topLeft: Player? = null,
    val top: Player? = null,
    val topRight: Player? = null,
    val centerLeft: Player? = null,
    val center: Player? = null,
    val centerRight: Player? = null,
    val bottomLeft: Player? = null,
    val bottom: Player? = null,
    val bottomRight: Player? = null
) {

    val isFull: Boolean
        get() = topLeft != null && top != null && topRight != null
                && centerLeft != null && center != null && centerRight != null
                && bottomLeft != null && bottom != null && bottomRight != null

    /** Get the the move at the given position. */
    operator fun get(column: Position.Row, row: Position.Column): Player? = when(column) {
        Position.Row.TOP -> when(row) {
            Position.Column.LEFT -> topLeft
            Position.Column.CENTER -> top
            Position.Column.RIGHT -> topRight
        }
        Position.Row.CENTER -> when(row) {
            Position.Column.LEFT -> centerLeft
            Position.Column.CENTER -> center
            Position.Column.RIGHT -> centerRight
        }
        Position.Row.BOTTOM -> when(row) {
            Position.Column.LEFT -> bottomLeft
            Position.Column.CENTER -> bottom
            Position.Column.RIGHT -> bottomRight
        }
    }

    /**
     * Edit the value on the given position.
     * Returns a new Grid instance with the new value.
     */
    fun edit(player: Player?, column: Position.Row, row: Position.Column): Grid = when(column) {
        Position.Row.TOP -> when(row) {
            Position.Column.LEFT -> copy(topLeft = player)
            Position.Column.CENTER -> copy(top = player)
            Position.Column.RIGHT -> copy(topRight = player)
        }
        Position.Row.CENTER -> when(row) {
            Position.Column.LEFT -> copy(centerLeft = player)
            Position.Column.CENTER -> copy(center = player)
            Position.Column.RIGHT -> copy(centerRight = player)
        }
        Position.Row.BOTTOM -> when(row) {
            Position.Column.LEFT -> copy(bottomLeft = player)
            Position.Column.CENTER -> copy(bottom = player)
            Position.Column.RIGHT -> copy(bottomRight = player)
        }
    }
}